package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import gameClient.util.Point3D;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Ex2Client implements Runnable {
    private int gameLevel;
    private int grade;
    private int Moves;
    private long sleepTime;
    private boolean getToDestntion;
    private static MyFrame window;
    private static Arena arena;
    private static dw_graph_algorithms algo;


    @Override
    public void run() {
        int levelGame = 1;
        game_service game = Game_Server_Ex2.getServer(levelGame);
//        game.login(207935214);
        directed_weighted_graph grapicGame = stringToGraph(game.getGraph());
        init(game);
        game.startGame();
        int ind = 0;
        long dt = 100;

        while (game.isRunning()) {
            moveAgants(game, grapicGame);
            String agentsString = game.getAgents();
            List<CL_Agent> agentsList = Arena.getAgents(agentsString, grapicGame);
            window.update(arena);
            try {
                if (ind % 1 == 0) {
                    window.repaint();
                }
//                getToDestntion = true ;
//                for (int i = 0; i < agentsList.size(); i++) {
//                    if (agentsList.get(i).getMakeToDest() != -1) {
//                        getToDestntion = false;
//                        i = 0;
//                    }
//                    Thread.sleep(dt * 3);
//                }
                Thread.sleep(dt * 4);
//                    ind++;
                game.move();

            } catch (Exception e) {
                e.printStackTrace();
            }
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

        String res = game.toString();
        System.out.println(res);
        System.exit(0);

    }

    private static void init(game_service game) {
        String gameGraphString = game.getGraph();
        String gamePokemonsString = game.getPokemons();
        directed_weighted_graph gg = stringToGraph(gameGraphString);
        arena = new Arena();
        arena.setGraph(gg);
        arena.setPokemons(Arena.json2Pokemons(gamePokemonsString));

        //////////////////////// test start
//        List<CL_Pokemon> pock = Arena.json2Pokemons(gamePokemonsString);
////        for (CL_Pokemon pocki : pock) {
////            Arena.updateEdge(pocki, gg);
////        }
//        arena.setPokemons(pock);
//        String s = gamePokemonsString;
//
////        Point3D ;
//        ////////////////// test end
//        System.out.println(Arena.json2Pokemons(gamePokemonsString));// test
        window = new MyFrame("test Ex2");
        window.setSize(800, 500);
        window.update(arena);

        window.show();
        String gameInfo = game.toString();
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(gameInfo);
            JSONObject gameServer = jsonObject.getJSONObject("GameServer");
            System.out.println(gameServer);
            int numOfAgents = gameServer.getInt("agents");
            System.out.println(gameInfo);
            ArrayList<CL_Pokemon> pokemonsList = Arena.json2Pokemons(game.getPokemons());
            for (int a = 0; a < pokemonsList.size(); a++) {
                Arena.updateEdge(pokemonsList.get(a), gg);
            }
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

    private static void moveAgants(game_service game, directed_weighted_graph gg) {
        int srcPok = 0;
        boolean hasEdge = true;
//        String lg1 = game.move();
        String lg = game.getAgents();
        List<CL_Agent> agentsList = Arena.getAgents(lg, gg);
        arena.setAgents(agentsList);
        String fs = game.getPokemons();
        List<CL_Pokemon> pokemonList = Arena.json2Pokemons(fs);
        arena.setPokemons(pokemonList);
        for (int i = 0; i < agentsList.size(); i++) {
            CL_Agent ag = agentsList.get(i);
            int id = ag.getID();
            int dest = ag.getNextNode();
            int src = ag.getSrcNode();
            double v = ag.getValue();
            if (dest == -1) {
                CL_Pokemon pok = null;
                for (int j = 0; j < pokemonList.size(); j++) {
                    pok = pokemonList.get(i);
                    arena.updateEdge(pok, gg);
                    break;
                }
                srcPok = pok.get_edge().getSrc();
//                Point3D p = pok.getLocation();
//                gg.getE();
//                p.close2equals();
//                gg.getE().
//                System.out.println(pok.get_edge());
//                String s = game.getPokemons();
//                dest = nextNode1(gg, src , pok.getLocation());

                dest = nextNode(gg, src, srcPok, pok);
                game.chooseNextEdge(ag.getID(), dest);
                String res = game.toString();
                System.out.println(res);
                System.out.println("Agent: " + id + ", val: " + v + "   turned to node: " + dest);
            }
        }
    }

    private static int nextNode(directed_weighted_graph g, int src, int pokSrc, CL_Pokemon pok) {
        algo = new DWGraph_Algo();
        algo.init(g);
        if (src == pokSrc) {
            return pok.get_edge().getDest();
        }
        List<node_data> path = algo.shortestPath(src, pokSrc);
        int ans = -1;
        if (path.size() == 1) {
            return path.get(0).getKey();
        } else {
            return path.get(1).getKey();
        }
    }

    public static CL_Pokemon findClosetPokemon(directed_weighted_graph g, List<CL_Pokemon> pokemonList, int srcAgent) {
        int pokIndex = 0;
        double min = Double.MAX_VALUE;
        double dis = 0;
        algo = new DWGraph_Algo();
        algo.init(g);
        for (int i = 0; i < pokemonList.size(); i++) {
            arena.updateEdge(pokemonList.get(i), g);
            dis = algo.shortestPathDist(srcAgent, pokemonList.get(i).get_edge().getSrc());
            if (dis < min) {
                min = dis;
                pokIndex = i;
            }
        }
        return pokemonList.get(pokIndex);
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

