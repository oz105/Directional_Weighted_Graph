package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import gameClient.util.Point3D;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Ex2 {
    private static MyFrame window;
    private static Arena arena;
    private static dw_graph_algorithms algo ;

    public static void main(String[] args) {
        int levelGame = 22;
        game_service game = Game_Server_Ex2.getServer(levelGame);
        directed_weighted_graph grapicGame = stringToGraph(game.getGraph());
        init(game);
        game.startGame();
        int ind = 0;
        long dt = 100;


        while (game.isRunning()) {
            moveAgants(game, grapicGame);
            try {
                if (ind % 1 == 0) {
                    window.repaint();
                }
                Thread.sleep(dt * 4);
                ind++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String res = game.toString();

        System.out.println(res);
        System.exit(0);

    }


    private static void init(game_service game) {
        String gameGraph = game.getGraph();
        String gamePokemons = game.getPokemons();
        directed_weighted_graph gg = stringToGraph(gameGraph);
        arena = new Arena();
        arena.setGraph(gg);
        arena.setPokemons(Arena.json2Pokemons(gamePokemons));
        window = new MyFrame("test Ex2");
        window.setSize(1000, 700);
        window.update(arena);

        window.show();
        String gameInfo = game.toString();
        JSONObject line;
        try {
            line = new JSONObject(gameInfo);
            JSONObject ttt = line.getJSONObject("GameServer");
            System.out.println(ttt);
            int numOfAgents = ttt.getInt("agents");
            System.out.println(gameInfo);
            ArrayList<CL_Pokemon> pokemonsList = Arena.json2Pokemons(game.getPokemons());
            int src_node = 0;
            for (int a = 0; a < pokemonsList.size(); a++) {
                Arena.updateEdge(pokemonsList.get(a), gg);
            }
            for (int a = 0; a < numOfAgents; a++) {
                int ind = a % pokemonsList.size();
                CL_Pokemon c = pokemonsList.get(ind);
                int nn = c.get_edge().getDest();
                if (c.getType() < 0) {
                    nn = c.get_edge().getSrc();
                }
                game.addAgent(nn);
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


    private static void moveAgants(game_service game, directed_weighted_graph gg) {
        String lg = game.move();
        List<CL_Agent> agentsList = Arena.getAgents(lg, gg);
        arena.setAgents(agentsList);
        //ArrayList<OOP_Point3D> rs = new ArrayList<OOP_Point3D>();
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
                CL_Pokemon pok = pokemonList.get(0) ;
                for (int j = 0; j <pokemonList.size() ; j++) {
                    pok = pokemonList.get(i) ;
                    break;
                }
                dest = nextNode(gg, src , pok.get_edge().getSrc());
                // עושה בעיות  , צריך לשלוח בעצם את הsrc של הצלע שעליה נמצא הפוקימון לא הצלחתי להבין איך משיגים אותה
                game.chooseNextEdge(ag.getID(), dest);
                System.out.println("Agent: " + id + ", val: " + v + "   turned to node: " + dest);
            }
        }
    }
    private static int nextNode(directed_weighted_graph g, int src , int pokSrc) {
        algo.init(g) ;
        List path = algo.shortestPath(src, pokSrc) ;
        int ans = -1;
        if (path.size() == 1) {
            return ((NodeData) path.get(0)).getKey() ;
        }
        else {
            return ((NodeData) path.get(1)).getKey() ;
        }
    }

}

