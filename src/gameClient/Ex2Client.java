package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import gameClient.util.Point3D;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.List;

public class Ex2Client implements Runnable {
    private static HashMap<Integer, HashMap<Integer, List<node_data>>> graphPaths;
    private static HashMap<Integer, HashMap<Integer, Double>> graphWeights;
    private static List<CL_Pokemon> pokemonsList;
    private static List<CL_Agent> agentsList;
    private static boolean pokOnEdge = false;
    private static MyFrame window;
    private static Arena arena;
    private static dw_graph_algorithms algo;
    private static directed_weighted_graph graphOfGame;
    private static game_service game;
    private int gameLevel;
    private int grade;
    private int Moves;


    @Override
    public void run() {
        int levelGame = 5;
        game = Game_Server_Ex2.getServer(levelGame);
        game.login(207935214);
        init(game);
        game.startGame();
        int ind = 0;
        long dt = 100;

        while (game.isRunning()) {
            window.update(arena);
            try {
                if (ind % 1 == 0) { window.repaint(); }
                synchronized (this) {
                    moveAgants(game, graphOfGame);
                }
                if(!checkAgentOnPokemonEdge()){
                    int rnd = (int)(Math.random()*5 );
                    Thread.sleep(rnd);
                }
                Thread.sleep(dt);

            } catch (Exception e) {
                e.printStackTrace();
            }
            refresh();
        }

        String res = game.toString();
        System.out.println(res);
        System.exit(0);

    }

    private void init(game_service game) {
        arena = new Arena();
        algo = new DWGraph_Algo();
        algo.init(stringToGraph(game.getGraph()));
        graphOfGame = algo.getGraph();
        arena.setGraph(graphOfGame);
        pokemonsList = (Arena.json2Pokemons(game.getPokemons()));
        arena.setPokemons(pokemonsList);
        window = new MyFrame("test Ex2");
        window.setSize(800, 500);
        window.update(arena);
        window.setVisible(true);
        initGraphPathsAndWeights(graphOfGame);
        for (int a = 0; a < pokemonsList.size(); a++) {
            Arena.updateEdge(pokemonsList.get(a), graphOfGame);
        }
        initStartNodeOfAgents();
    }


    private static void moveAgants(game_service game, directed_weighted_graph g) {
        int srcPok = 0;
        pokOnEdge = false;
        String agentsMove = game.move(); // Use max 10 times in 1 sec .
        agentsList = Arena.getAgents(agentsMove, g);
        arena.setAgents(agentsList);
        pokemonsList = Arena.json2Pokemons(game.getPokemons());
        for (CL_Pokemon pok : pokemonsList) {
            arena.updateEdge(pok, g);
        }
        arena.setPokemons(pokemonsList);
        for (CL_Agent agent : agentsList) {
            agent.setOnPokemonEdge(false);
            int id = agent.getID();
            int dest = agent.getNextNode();
            int src = agent.getSrcNode();
            double v = agent.getValue();
            if (dest == -1) {
//                CL_Pokemon pok = findClosetPokemon(pokemonsList,src) ;
                synchronized (Thread.currentThread()){
                    CL_Pokemon pok = findValuestPokemon(pokemonsList, src);
                    srcPok = pok.get_edge().getSrc();
                    dest = nextNode(agent, srcPok, pok);
                    game.chooseNextEdge(agent.getID(), dest);
                }
            }
            String res = game.toString();
            System.out.println(res);
            System.out.println("Agent: " + id + ", val: " + v + "   turned to node: " + dest + "   on edge " + pokOnEdge);

        }
    }

    private static int nextNode(CL_Agent a, int pokSrc, CL_Pokemon pok) {
        if (a.getSrcNode() == pokSrc) {
            pokOnEdge = true;
            a.setOnPokemonEdge(true);
            return pok.get_edge().getDest();
        }
        List<node_data> path = graphPaths.get(a.getSrcNode()).get(pokSrc);
        if (path.size() < 1) {
            return -1;
        } else {
            return path.get(1).getKey();
        }
    }

    public static CL_Pokemon findValuestPokemon(List<CL_Pokemon> pokemonList, int srcAgent) {
        int pokIndex = 0;
        double max = 0;
        double dis;
        double valueWithDis;
        for (int i = 0; i < pokemonList.size(); i++) {
            if (pokemonList.get(i).getTarget() != 1) {
                dis = graphWeights.get(srcAgent).get(pokemonList.get(i).get_edge().getSrc());
                valueWithDis = (pokemonList.get(i).getValue() / dis);
                if (valueWithDis > max) {
                    max = valueWithDis;
                    pokIndex = i;
                }
            }
        }
        pokemonList.get(pokIndex).targetd();
        return pokemonList.get(pokIndex);
    }

    public void refresh (){
        String gameString = game.toString();
        Gson gson = new Gson();
        JsonObject jsonObjectGame = gson.fromJson(gameString, JsonObject.class);
        JsonObject gameServer = jsonObjectGame.getAsJsonObject("GameServer");
        gameLevel = gameServer.get("game_level").getAsInt();
        arena.setGameLevel(gameLevel);
        grade = gameServer.get("grade").getAsInt();
        arena.setGrade(grade);
        Moves = gameServer.get("moves").getAsInt();
        arena.setMoves(Moves);
        arena.setTimeToEnd(game.timeToEnd());
    }

    public boolean checkAgentOnPokemonEdge (){
        boolean res = false ;
        for (CL_Agent a : agentsList){
            if(a.isOnPokemonEdge()){
                res = true ;
                break;
            }
        }
        return res ;
    }


    //Functions for init

    public void initGraphPathsAndWeights(directed_weighted_graph g) {
        graphPaths = new HashMap<Integer, HashMap<Integer, List<node_data>>>();
        graphWeights = new HashMap<Integer, HashMap<Integer, Double>>();
        for (node_data n : g.getV()) {
            HashMap<Integer, List<node_data>> tempMap = new HashMap<>();
            HashMap<Integer, Double> tempMap2 = new HashMap<>();
            graphPaths.put(n.getKey(), tempMap);
            graphWeights.put(n.getKey(), tempMap2);
        }
        for (node_data n : g.getV()) {
            for (node_data destNode : g.getV()) {
                List<node_data> path = algo.shortestPath(n.getKey(), destNode.getKey());
                double weight = algo.shortestPathDist(n.getKey(), destNode.getKey());
                graphPaths.get(n.getKey()).put(destNode.getKey(), path);
                graphWeights.get(n.getKey()).put(destNode.getKey(), weight);
            }
        }
    }

    public void initStartNodeOfAgents() {
        String gameInfo = game.toString();
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(gameInfo);
            JSONObject gameServer = jsonObject.getJSONObject("GameServer");
            System.out.println(gameServer);
            int numOfAgents = gameServer.getInt("agents");
            System.out.println(gameInfo);
            for (int a = 0; a < numOfAgents; a++) {
                int ind = a % pokemonsList.size();
                CL_Pokemon pok = pokemonsList.get(ind);
                int startPointAgent = pok.get_edge().getDest();
                if (pok.getType() < 0) {
                    startPointAgent = pok.get_edge().getSrc();
                }
                game.addAgent(startPointAgent);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static directed_weighted_graph stringToGraph(String s) {
        directed_weighted_graph g = new DWGraph_DS();
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray edges = jsonObject.getJSONArray("Edges");
            JSONArray vertices = jsonObject.getJSONArray("Nodes");
            for (int i = 0; i < vertices.length(); i++) {
                JSONObject v = vertices.getJSONObject(i);
                int key = v.getInt("id");
                String p = v.getString("pos");
                geo_location pos = new Point3D(p);
                node_data n = new NodeData(key);
                n.setLocation(pos);
                g.addNode(n);
            }
            for (int i = 0; i < edges.length(); i++) {
                JSONObject e = edges.getJSONObject(i);
                int src = e.getInt("src");
                double weight = e.getDouble("w");
                int dest = e.getInt("dest");
                edge_data edge = new EdgeData(src, dest, weight);
                g.connect(edge.getSrc(), edge.getDest(), edge.getWeight());
            }
            return g;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//    public static CL_Pokemon findClosetPokemon(List<CL_Pokemon> pokemonList, int srcAgent) {
//        int pokIndex = 0;
//        double min = Double.MAX_VALUE;
//        double dis = 0;
//        for (int i = 0; i < pokemonList.size(); i++) {
//            dis = graphWeights.get(srcAgent).get(pokemonList.get(i).get_edge().getSrc());
//            if (dis < min) {
//                min = dis;
//                pokIndex = i;
//            }
//        }
//        return pokemonList.get(pokIndex);
//    }
//
//
//    public boolean makeToDest(List<CL_Agent> agentsList) {
//        boolean res = true;
//        for (int i = 0; i < agentsList.size(); i++) {
//            if (agentsList.get(i).getMakeToDest() != -1) {
//                res = false;
//            }
//        }
//        return res;
//    }


    //    private static int nextNode(directed_weighted_graph g, int src, int pokSrc, CL_Pokemon pok) {
//        algo.init(g);
//        if (src == pokSrc) {
//            pokOnEdge = true;
//            ;
//            return pok.get_edge().getDest();
//        }
//        List<node_data> path = algo.shortestPath(src, pokSrc);
//        int ans = -1;
//        if (path.size() == 1) {
//            return path.get(0).getKey();
//        } else {
//            return path.get(1).getKey();
//        }
//    }
}


