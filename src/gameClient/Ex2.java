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


        int levelGame = 3;
        game_service game = Game_Server_Ex2.getServer(levelGame);
        directed_weighted_graph grapicGame = stringToGraph(game.getGraph());
        init(game);
        game.startGame();
        int ind = 0;
        long dt = 100;


        while (game.isRunning()) {
            moveAgants(game, grapicGame);
            window.update(arena);
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

        //////////////////////// test start
        List<CL_Pokemon> pock = Arena.json2Pokemons(gamePokemons);

        for(CL_Pokemon pocki : pock) {
            Arena.updateEdge(pocki,gg );

        }
        arena.setPokemons(pock);
        String s=gamePokemons;

//        Point3D ;
        ////////////////// test end
        System.out.println(Arena.json2Pokemons(gamePokemons));// test
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
//                Point3D p = pok.getLocation();
//                gg.getE();
//                p.close2equals();
//                gg.getE().
//                System.out.println(pok.get_edge());
//                String s = game.getPokemons();
                dest = nextNode1(gg, src , pok.getLocation());
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

    private static int nextNode1(directed_weighted_graph gg, int keyS, Point3D p) {
        int keyD =  findCloseNode(gg, p);
        algo= new DWGraph_Algo();
        algo.init(gg);
        List<node_data> spd= algo.shortestPath(keyS, keyD);
        if(spd.size() ==1){
            // the
            return spd.get(0).getKey();
        }
        else if(spd.get(1).getKey()==keyS){

        }
        return spd.get(1).getKey();

    }
    private static int findCloseNode(directed_weighted_graph gg, Point3D p){

        for(node_data node : gg.getV()) {
            for (edge_data e : gg.getE(node.getKey())) {
                if(betweenEdge(gg, e, p)) {

                    return e.getSrc();

                }
            }
        }
        return -1;
    }
    private static boolean betweenEdge(directed_weighted_graph gg,edge_data e, Point3D p) {
        double xP = p.x();
        double yP = p.y();

        double xS = gg.getNode(e.getSrc()).getLocation().x();
        double yS = gg.getNode(e.getSrc()).getLocation().y();

        double xD = gg.getNode(e.getDest()).getLocation().x();
        double yD = gg.getNode(e.getDest()).getLocation().y();


        if (xD == xS) {
            if (xP != xD) {

                return false;
            } else if (yD <= yS) {
                if (yD <= yP && yP <= yS) {

                    return true;
                } else {

                    return false;
                }
            } else {
                // then yS < yD
                if (yS <= yP && yP <= yD) {
                    return true;
                } else {

                    return false;
                }

            }
        } else if (yD == yS) {
            if (yP != yD) {
                // if the pock s not between them
                return false;
            } else if (xD <= xS) {
                if (xD <= xP && xP <= xS) {

                    return true;
                } else {

                    return false;
                }
            } else {
                // then xS < xD
                if (xS <= xP && xP <= xD) {
                    return true;
                } else {

                    return false;
                }

            }
        }


        double m = (yD - yS) / (xD - xS);
        double n = yD - m * xD;
        double checkY = m * xP + n;
        return checkY==yP;

    }


}

