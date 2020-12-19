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
import java.util.Iterator;
import java.util.List;

public class Ex2Client implements Runnable {
    private static HashMap<Integer, HashMap<Integer, List<node_data>>> graphPaths;
    private static HashMap<Integer, HashMap<Integer, List<edge_data>>> graphEdgesPaths;
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
    private static double min = -1;
    private static HashMap<Integer, CL_Pokemon> takenPock = new HashMap<>();

    public Ex2Client(String level) {
        int after_parse;
        try {
            after_parse = Integer.parseInt(level);
            this.gameLevel = after_parse;
        } catch (NumberFormatException e) {
            System.out.println("the level should be only numbers");
        }
    }

    @Override
    public void run() {
        game = Game_Server_Ex2.getServer(gameLevel);
//        game.login(207935214);
        init(game);
        game.startGame();
        int ind = 0;
        long dt = 100;

        while (game.isRunning()) {
            window.update(arena);
            try {
                if (ind % 1 == 0) {
                    window.repaint();
                }
                synchronized (this) {
                    moveAgants(game, graphOfGame);
                }
                if (noAgentOnPokemonEdge() != null) {
                    double d = sleepTime();
                    Thread.sleep((long) (d));
//                    Thread.sleep((long)(min));
                } else {
                    Thread.sleep(150);
                }

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
//        indxOfPock= new int[agentsList.size()];
    }


    private void moveAgants(game_service game, directed_weighted_graph g) {
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
        min = -1;
        for (CL_Agent agent : agentsList) {

            int id = agent.getID();
            int dest = agent.getNextNode();
            int src = agent.getSrcNode();
            double v = agent.getValue();
            //
//            agent.set_SDT(100);
//            if(agent.get_sg_dt()<min && min !=-1){
//                min = agent.get_sg_dt();
//            }
            //
            if (dest == -1) {
                agent.setOnPokemonEdge(false);
                synchronized (Thread.currentThread()) {
                    CL_Pokemon pok = findValuestPokemon(src);
                    srcPok = pok.get_edge().getSrc();
                    dest = nextNode(agent, srcPok, pok);
                    agent.set_curr_fruit(pok);
                    takenPock.put(agent.getID(), pok);
                    game.chooseNextEdge(agent.getID(), dest);
                }
            }
            System.out.println("Agent: " + id + ", val: " + v + "   turned to node: " + dest + "   on edge " + pokOnEdge);

        }
    }

    private int nextNode(CL_Agent a, int pokSrc, CL_Pokemon pok) {
        if (a.getSrcNode() == pokSrc) {
            pokOnEdge = true;
            a.setOnPokemonEdge(true);
            pok.targetd();
            return pok.get_edge().getDest();
        }
        List<node_data> path = graphPaths.get(a.getSrcNode()).get(pokSrc);
        return path.get(1).getKey();
    }

    public CL_Pokemon findValuestPokemon(int srcAgent) {
        int pokIndex = 0;
        double max = 0;
        double dis;
        double valueWithDis;
//        boolean
        for (int i = 0; i < pokemonsList.size(); i++) {
            if (!takenPock.containsValue(pokemonsList.get(i))) {
                dis = graphWeights.get(srcAgent).get(pokemonsList.get(i).get_edge().getSrc());
                valueWithDis = (pokemonsList.get(i).getValue() / dis);
                if (valueWithDis > max) {
                    max = valueWithDis;
                    pokIndex = i;
                }
            }
        }

        pokemonsList.get(pokIndex).targetd();
        return pokemonsList.get(pokIndex);
    }


    public CL_Pokemon findClosetPokemon(int srcAgent) {
        double weight = 0;
        double min = 0;
        int index = 0;
        for (int i = 0; i < pokemonsList.size(); i++) {
            weight = graphWeights.get(srcAgent).get(pokemonsList.get(i).get_edge().getSrc());
            if (weight < min) {
                min = weight;
                index = i;
            }
        }
        return pokemonsList.get(index);
    }

    //    public double sleepTime (){
//        System.out.println("in sleepTime");
//        double min = -1;
//        double d = 1;
//        for (int i = 0; i < agentsList.size(); i++) {
//            System.out.println("1*****************");
//
//            if(agentsList.get(i).isOnPokemonEdge()) {
//                System.out.println("2*****************");
//
//
//                if (agentsList.get(i).get_curr_fruit() != null) {
//                    geo_location srcLocation = graphOfGame.getNode(agentsList.get(i).getSrcNode()).getLocation();
//                    geo_location agentLocation = agentsList.get(i).getLocation();
//                    geo_location fruitLocation = agentsList.get(i).get_curr_fruit().getLocation();
//                    double pockToAgdis = agentLocation.distance(fruitLocation);
//                    edge_data e = agentsList.get(i).get_curr_fruit().get_edge();
//                    geo_location destLocation = graphOfGame.getNode(e.getDest()).getLocation();
//
//                    double edgeLength = srcLocation.distance(destLocation);
//                    double norm = pockToAgdis/edgeLength;
//                    d= norm*e.getWeight();
//                    d/= agentsList.get(i).getSpeed();
//                    System.out.println("3*****************");
//
////                    d = srcLocation.distance(fruitLocation);
////                    d *= (agentsList.get(i).get_curr_fruit().get_edge().getWeight());
////                    d /= agentsList.get(i).getSpeed();
//                    System.out.println("d value: "+d);
//                    if(d<min){
//                        min = d;
//                    }
//                }
//            }
//        }
//        if(min!=-1){
//            return min;
//        }
//        return 1 ;
//    }
    public double sleepTime() {
        double de, dist, w, d;
        double min =-1;
        for (int i = 0; i < agentsList.size(); i++) {

            CL_Agent agent= agentsList.get(i);
            if(agent.get_curr_edge()!=null) {
                w = agent.get_curr_edge().getWeight();
                geo_location dest = graphOfGame.getNode(agent.get_curr_edge().getDest()).getLocation();
                geo_location src = graphOfGame.getNode(agent.get_curr_edge().getSrc()).getLocation();
                de = src.distance(dest);
                dist = agent.getLocation().distance(dest);
                if (agent.get_curr_fruit().get_edge() == agent.get_curr_edge()) {
                    dist = agent.get_curr_fruit().getLocation().distance(agent.getLocation());
                }


                double norm = dist / de;
                double dt = w * norm / agent.getSpeed();
                d = (long) (100.0 * dt);
                if (min == -1 || d < min) {
                    min = d;
                }
            }

    }
        if(min!=-1) {
            return min;
        }
        return 100;
    }



    public void refresh () {
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

//
//    public CL_Agent whoOnPokemonEdge() {
//        CL_Agent tempAgent = null;
//        for (CL_Agent a : agentsList) {
//            if (a.isOnPokemonEdge()) {
//                tempAgent = a;
//            }
//        }
//        return tempAgent;
//    }
//
//    public CL_Pokemon whoIsOnTheEdge(edge_data e) {
//        CL_Pokemon tempPokemon = null;
//        for (CL_Pokemon pok : pokemonsList) {
//            if (pok.get_edge().equals(e)) {
//                tempPokemon = pok;
//            }
//        }
//        return tempPokemon;
//    }
//
//    public double timeToEatPokemon(CL_Agent agent, CL_Pokemon pok) {
//        double agent_speed = agent.getSpeed();
//        double edge_weight = pok.get_edge().getWeight();
//        geo_location pok_location = pok.getLocation();
//        geo_location agent_location = agent.getLocation();
//        double dis = agent_location.distance(pok_location);
////        if (edge_weight * dis / agent_speed < 2){
////
////            return true;
////        }
//        return ((edge_weight * dis) / agent_speed);
//    }

//    public boolean noAgentOnPokemonEdge() {
//        boolean res = true;
//        for (CL_Agent a : agentsList) {
//            if (a.isOnPokemonEdge()) {
//                res = false;
//                break;
//            }
//        }
//        return res;
//    }

    public CL_Agent noAgentOnPokemonEdge() {
        boolean res = true;
        for (CL_Agent a : agentsList) {
            if (a.isOnPokemonEdge()) {
                return a;
            }
        }
        return null;
    }

    public boolean agentsSameDest(CL_Agent agent , int dest) {
        for (CL_Agent a : agentsList) {
            if (a.get_curr_edge() != null ) {
                if (a.get_curr_edge().getDest()==dest) {
                    return true ;
                }
            }
        }
        return false ;
    }
//
//    public void pokemonOnThePath(List<node_data> path) {
//        node_data dest = null;
//        edge_data e = null;
//        Iterator<node_data> it = path.iterator();
//        while (it.hasNext()) {
//            node_data src = it.next();
//            if (it.hasNext()) {
//                dest = it.next();
//            }
//            if (dest != null) {
//                e = graphOfGame.getEdge(it.next().getKey(), dest.getKey());
//                for (int i = 0; i < pokemonsList.size(); i++) {
//                    if (pokemonsList.get(i).get_edge().equals(e)) {
//                        pokemonsList.get(i).targetd();
//                    }
//                }
//            }
//        }
//    }


    //Functions for init

    public void initGraphPathsAndWeights(directed_weighted_graph g) {
        graphPaths = new HashMap<Integer, HashMap<Integer, List<node_data>>>();
        graphWeights = new HashMap<Integer, HashMap<Integer, Double>>();
        for (node_data n : g.getV()) {
            HashMap<Integer, List<node_data>> tempMap = new HashMap<>();
            HashMap<Integer, List<edge_data>> tempMap3 = new HashMap<>();
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


}


