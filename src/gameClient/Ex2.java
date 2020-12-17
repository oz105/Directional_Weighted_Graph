package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import gameClient.util.Point3D;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
// צעדים שיאכל כבר בפעם הראשונה
// גרף לא קשיר, שלא ייכנס לקודקוד שאין ממנו צלעות
// אולי רק לאיפה שיש מעגל

// להוסיף RESIZE
// להוסיף אינט שיזכור את הקודקוד האחרון שהיה בו כדי למנוע מנצב בו הוא הולך שוב ושוב על אותה הצלע
// רידמי וכו


public class Ex2 implements ActionListener {
    private static JLabel idLabel ;
    private static JTextField idText ;
    private static JLabel levelLabel ;
    private static JTextField levelText ;
    private static JButton button ;

    public static void main(String[] args) {
        JPanel panel = new JPanel() ;
        JFrame frame = new JFrame() ;
        frame.setSize(350 , 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel) ;
        panel.setLayout(null);

        idLabel = new JLabel("ID:") ;
        idLabel.setBounds(20 , 30 , 80 , 25 );
        panel.add(idLabel) ;

        idText = new JTextField() ;
        idText.setBounds(80,30,165,25);
        panel.add(idText);

        levelLabel = new JLabel("Level:") ;
        levelLabel.setBounds(20 , 70 , 80 , 25 );
        panel.add(levelLabel);

        levelText = new JTextField();
        levelText.setBounds(80 , 70 , 165 , 25);
        panel.add(levelText);

        button = new JButton("Start");
        button.setBounds(10,110,80,25);
        button.addActionListener(new Ex2());
        panel.add(button);

        frame.setVisible(true);



    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String id = idText.getText();
        String level = levelText.getText();
        try{
            Integer.parseInt(id) ;
            Thread Ex2Client = new Thread(new Ex2Client(level));
            Ex2Client.start();
        } catch (NumberFormatException numberFormatException) {
            System.out.println("ID should contains only numbers");

        }


    }
}


///////////////////////////////////////////////////////////////////////


//package gameClient;
//
//import Server.Game_Server_Ex2;
//import api.*;
//import gameClient.util.Point3D;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import javax.swing.*;
//import java.util.ArrayList;
//import java.util.List;
//// צעדים שיאכל כבר בפעם הראשונה
//// גרף לא קשיר, שלא ייכנס לקודקוד שאין ממנו צלעות
//// אולי רק לאיפה שיש מעגל
//
//// להוסיף RESIZE
//// להוסיף אינט שיזכור את הקודקוד האחרון שהיה בו כדי למנוע מנצב בו הוא הולך שוב ושוב על אותה הצלע
//// רידמי וכו
//
//
//public class Ex2 {
//    public static int count = 0 ;
//    private static MyFrame window;
//    private static Arena arena;
//    private static dw_graph_algorithms algo;
//
//
//    public static void main(String[] args) {
//        int levelGame = 0;
//        game_service game = Game_Server_Ex2.getServer(levelGame);
////        game.login(207935214);
//        directed_weighted_graph grapicGame = stringToGraph(game.getGraph());
//        init(game);
//        game.startGame();
//        int ind = 0;
//        long dt = 100;
//
//        while (game.isRunning()) {
//            String agentsString = game.move();
//            List<CL_Agent> agentsList = Arena.getAgents(agentsString, grapicGame);
//            moveAgants(game, grapicGame);
//            window.update(arena);
//            try {
//                if (ind % 1 == 0) {
//                    window.repaint();
//                }
//                Thread.sleep(dt*4);
//                ind++;
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        String res = game.toString();
//        System.out.println(res);
//        System.out.println("count move " + count);
//        System.exit(0);
//
//    }
//
//    private static void init(game_service game) {
//        String gameGraphString = game.getGraph();
//        String gamePokemonsString = game.getPokemons();
//        directed_weighted_graph gg = stringToGraph(gameGraphString);
//        arena = new Arena();
//        arena.setGraph(gg);
//        arena.setPokemons(Arena.json2Pokemons(gamePokemonsString));
//
//        //////////////////////// test start
////        List<CL_Pokemon> pock = Arena.json2Pokemons(gamePokemonsString);
//////        for (CL_Pokemon pocki : pock) {
//////            Arena.updateEdge(pocki, gg);
//////        }
////        arena.setPokemons(pock);
////        String s = gamePokemonsString;
////
//////        Point3D ;
////        ////////////////// test end
////        System.out.println(Arena.json2Pokemons(gamePokemonsString));// test
//        window = new MyFrame("test Ex2");
//        window.setSize(800, 500);
//        window.update(arena);
//
//        window.show();
//        String gameInfo = game.toString();
//        JSONObject jsonObject;
//        try {
//            jsonObject = new JSONObject(gameInfo);
//            JSONObject gameServer = jsonObject.getJSONObject("GameServer");
//            System.out.println(gameServer);
//            int numOfAgents = gameServer.getInt("agents");
//            System.out.println(gameInfo);
//            ArrayList<CL_Pokemon> pokemonsList = Arena.json2Pokemons(game.getPokemons());
//            for (int a = 0; a < pokemonsList.size(); a++) {
//                Arena.updateEdge(pokemonsList.get(a), gg);
//            }
//            for (int a = 0; a < numOfAgents; a++) {
//                int ind = a % pokemonsList.size();
//                CL_Pokemon pok = pokemonsList.get(ind);
//                int startPointAgent = pok.get_edge().getDest();
//                if (pok.getType() < 0) {
//                    startPointAgent = pok.get_edge().getSrc();
//                }
//                game.addAgent(startPointAgent);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void moveAgants(game_service game, directed_weighted_graph gg) {
//        count ++ ;
//        int srcPok = 0;
//        CL_Pokemon pok = null;
//        String gamePokemons = game.getPokemons();
//        String agentsString = game.getAgents();
////        String agentsString = game.move();
//        List<CL_Agent> agentsList = Arena.getAgents(agentsString, gg);
//        List<CL_Pokemon> pokemonList = Arena.json2Pokemons(gamePokemons);
//        arena.setAgents(agentsList);
//        arena.setPokemons(pokemonList);
//        for (int i = 0; i < agentsList.size(); i++) {
//            CL_Agent ag = agentsList.get(i);
//            int id = ag.getID();
//            int dest = ag.getNextNode();
//            int src = ag.getSrcNode();
//            double v = ag.getValue();
//            if (dest == -1) {
//                pok = findClosetPokemon(gg, pokemonList, src);
//                arena.updateEdge(pok, gg);
//                srcPok = pok.get_edge().getSrc();
//                dest = nextNode(gg, src, srcPok, pok);
//                game.chooseNextEdge(ag.getID(), dest);
//                String res = game.toString();
//                System.out.println(res);
//                System.out.println("Agent: " + id + ", val: " + v + "   turned to node: " + dest + "  speed " + agentsList.get(i).getSpeed());
//            }
//        }
//    }
//
//
////    private static void moveAgants(game_service game, directed_weighted_graph gg) {
////        int srcPok = 0;
////        boolean hasEdge = true;
////        String lg = game.move();
////        List<CL_Agent> agentsList = Arena.getAgents(lg, gg);
////        arena.setAgents(agentsList);
////        String fs = game.getPokemons();
////        List<CL_Pokemon> pokemonList = Arena.json2Pokemons(fs);
////        arena.setPokemons(pokemonList);
////        for (int i = 0; i < agentsList.size(); i++) {
////            CL_Agent ag = agentsList.get(i);
////            int id = ag.getID();
////            int dest = ag.getNextNode();
////            int src = ag.getSrcNode();
////            double v = ag.getValue();
////            if (dest == -1) {
////                CL_Pokemon pok = null;
////                for (int j = 0; j < pokemonList.size(); j++) {
////                    pok = pokemonList.get(i);
////                    arena.updateEdge(pok, gg);
////                    break;
////                }
////                srcPok = pok.get_edge().getSrc();
//////                Point3D p = pok.getLocation();
//////                gg.getE();
//////                p.close2equals();
//////                gg.getE().
//////                System.out.println(pok.get_edge());
//////                String s = game.getPokemons();
//////                dest = nextNode1(gg, src , pok.getLocation());
////
////                dest = nextNode(gg, src, srcPok, pok);
////                game.chooseNextEdge(ag.getID(), dest);
////                String res = game.toString();
////                System.out.println(res);
////                System.out.println("Agent: " + id + ", val: " + v + "   turned to node: " + dest);
////            }
////        }
////    }
//
//    private static int nextNode(directed_weighted_graph g, int src, int pokSrc, CL_Pokemon pok) {
//        algo = new DWGraph_Algo();
//        algo.init(g);
//        if (src == pokSrc) {
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
//
////    private static int nextNode1(directed_weighted_graph gg, int keyS, Point3D p) {
////        int keyD = findCloseNode(gg, p);
////        algo = new DWGraph_Algo();
////        algo.init(gg);
////        List<node_data> spd = algo.shortestPath(keyS, keyD);
////        if (spd.size() == 1) {
////            return spd.get(0).getKey();
////        } else if (spd.get(1).getKey() == keyS) {
////
////        }
////        return spd.get(1).getKey();
////    }
//
//    private static int findCloseNode(directed_weighted_graph gg, Point3D p) {
//
//        for (node_data node : gg.getV()) {
//            for (edge_data e : gg.getE(node.getKey())) {
//                if (betweenEdge(gg, e, p)) {
//                    return e.getSrc();
//
//                }
//            }
//        }
//        return -1;
//    }
//
//    private static boolean betweenEdge(directed_weighted_graph gg, edge_data e, Point3D p) {
//        double xP = p.x();
//        double yP = p.y();
//
//        double xS = gg.getNode(e.getSrc()).getLocation().x();
//        double yS = gg.getNode(e.getSrc()).getLocation().y();
//
//        double xD = gg.getNode(e.getDest()).getLocation().x();
//        double yD = gg.getNode(e.getDest()).getLocation().y();
//
//
//        if (xD == xS) {
//            if (xP != xD) {
//
//                return false;
//            } else if (yD <= yS) {
//                if (yD <= yP && yP <= yS) {
//
//                    return true;
//                } else {
//
//                    return false;
//                }
//            } else {
//                // then yS < yD
//                if (yS <= yP && yP <= yD) {
//                    return true;
//                } else {
//
//                    return false;
//                }
//
//            }
//        } else if (yD == yS) {
//            if (yP != yD) {
//                // if the pock s not between them
//                return false;
//            } else if (xD <= xS) {
//                if (xD <= xP && xP <= xS) {
//
//                    return true;
//                } else {
//
//                    return false;
//                }
//            } else {
//                // then xS < xD
//                if (xS <= xP && xP <= xD) {
//                    return true;
//                } else {
//
//                    return false;
//                }
//
//            }
//        }
//
//
//        double m = (yD - yS) / (xD - xS);
//        double n = yD - m * xD;
//        double checkY = m * xP + n;
//        return checkY == yP;
//
//    }
//
//    public static CL_Pokemon findClosetPokemon(directed_weighted_graph g,List<CL_Pokemon>pokemonList,int srcAgent){
//
//        int pokIndex = 0;
//        double min = Double.MAX_VALUE;
//        double dis = 0;
//        algo = new DWGraph_Algo();
//        algo.init(g);
//        for (int i = 0; i < pokemonList.size(); i++) {
//            arena.updateEdge(pokemonList.get(i), g);
//            dis = algo.shortestPathDist(srcAgent, pokemonList.get(i).get_edge().getSrc());
//            if (dis < min) {
//                min = dis;
//                pokIndex = i;
//            }
//        }
//        return pokemonList.get(pokIndex);
//    }
//
//    public static directed_weighted_graph stringToGraph(String s) {
//        directed_weighted_graph g = new DWGraph_DS();
//        try {
//            JSONObject jsonObject = new JSONObject(s);
//            JSONArray edges = jsonObject.getJSONArray("Edges");
//            JSONArray vertices = jsonObject.getJSONArray("Nodes");
//            for (int i = 0; i < vertices.length(); i++) {
//                JSONObject v = vertices.getJSONObject(i);
//                int key = v.getInt("id");
//                String p = v.getString("pos");
//                geo_location pos = new Point3D(p);
//                node_data n = new NodeData(key);
//                n.setLocation(pos);
//                g.addNode(n);
//            }
//            for (int i = 0; i < edges.length(); i++) {
//                JSONObject e = edges.getJSONObject(i);
//                int src = e.getInt("src");
//                double weight = e.getDouble("w");
//                int dest = e.getInt("dest");
//                edge_data edge = new EdgeData(src, dest, weight);
//                g.connect(edge.getSrc(), edge.getDest(), edge.getWeight());
//            }
//            return g;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
////    public static void main(String[] args) {
////        int levelGame =0;
////        game_service game = Game_Server_Ex2.getServer(levelGame);
//////        game.login(207935214);
//////        game.timeToEnd();
////        directed_weighted_graph grapicGame = stringToGraph(game.getGraph());
////
////        init(game);
////
//////        System.out.println("tte: "+game.startGame());
////        int ind = 0;
////        long dt = 100;
////
////        while (game.isRunning()) {
////            String agentsString = game.move();
////            List<CL_Agent> agentsList = Arena.getAgents(agentsString, grapicGame);
////            moveAgants(game, grapicGame);
////            window.update(arena);
////            try {
////                if (ind % 1 == 0) {
////                    window.repaint();
////                }
////                Thread.sleep(dt*4);
////                ind++;
////
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
////        }
////        String res = game.toString();
////        System.out.println(res);
////        System.out.println("count move " + count);
////        System.exit(0);
////        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////
////    }
////
////    private static void init(game_service game) {
////        String gameGraphString = game.getGraph();
////        String gamePokemonsString = game.getPokemons();
////        directed_weighted_graph gg = stringToGraph(gameGraphString);
////        arena = new Arena();
////        arena.setGraph(gg);
////        arena.setPokemons(Arena.json2Pokemons(gamePokemonsString));
////
////        window = new MyFrame("test Ex2");
////        window.setSize(1000, 700);
////        window.update(arena);
////
////        window.show();
////        String gameInfo = game.toString();
////        JSONObject jsonObject;
////        try {
////            jsonObject = new JSONObject(gameInfo);
////            JSONObject gameServer = jsonObject.getJSONObject("GameServer");
////            System.out.println(gameServer);
////            int numOfAgents = gameServer.getInt("agents");
////            System.out.println(gameInfo);
////            ArrayList<CL_Pokemon> pokemonsList = Arena.json2Pokemons(game.getPokemons());
////            for (int a = 0; a < pokemonsList.size(); a++) {
////                Arena.updateEdge(pokemonsList.get(a), gg);
////            }
////            for (int a = 0; a < numOfAgents; a++) {
////                int ind = a % pokemonsList.size();
////                CL_Pokemon pok = pokemonsList.get(ind);
////                int startPointAgent = pok.get_edge().getDest();
////                if (pok.getType() < 0) {
////                    startPointAgent = pok.get_edge().getSrc();
////                }
////                game.addAgent(startPointAgent);
////            }
////        } catch (JSONException e) {
////            e.printStackTrace();
////        }
////    }
////
////    private static void moveAgants(game_service game, directed_weighted_graph gg) {
////        count ++ ;
////        int srcPok = 0;
////        CL_Pokemon pok = null;
////        String gamePokemons = game.getPokemons();
////        String agentsString = game.getAgents();
//////        String agentsString = game.move();
////        List<CL_Agent> agentsList = Arena.getAgents(agentsString, gg);
////        List<CL_Pokemon> pokemonList = Arena.json2Pokemons(gamePokemons);
////        arena.setAgents(agentsList);
////        arena.setPokemons(pokemonList);
////        for (int i = 0; i < agentsList.size(); i++) {
////            CL_Agent ag = agentsList.get(i);
////            int id = ag.getID();
////            int dest = ag.getNextNode();
////            int src = ag.getSrcNode();
////            double v = ag.getValue();
////            if (dest == -1) {
////                pok = findClosetPokemon(gg, pokemonList, src);
////                arena.updateEdge(pok, gg);
////                srcPok = pok.get_edge().getSrc();
////                dest = nextNode(gg, src, srcPok, pok);
////                game.chooseNextEdge(ag.getID(), dest);
////                String res = game.toString();
////                System.out.println(res);
////                System.out.println("Agent: " + id + ", val: " + v + "   turned to node: " + dest + "  speed " + agentsList.get(i).getSpeed());
////            }
////        }
////    }
////
////
//////    private static void moveAgants(game_service game, directed_weighted_graph gg) {
//////        int srcPok = 0;
//////        boolean hasEdge = true;
//////        String lg = game.move();
//////        List<CL_Agent> agentsList = Arena.getAgents(lg, gg);
//////        arena.setAgents(agentsList);
//////        String fs = game.getPokemons();
//////        List<CL_Pokemon> pokemonList = Arena.json2Pokemons(fs);
//////        arena.setPokemons(pokemonList);
//////        for (int i = 0; i < agentsList.size(); i++) {
//////            CL_Agent ag = agentsList.get(i);
//////            int id = ag.getID();
//////            int dest = ag.getNextNode();
//////            int src = ag.getSrcNode();
//////            double v = ag.getValue();
//////            if (dest == -1) {
//////                CL_Pokemon pok = null;
//////                for (int j = 0; j < pokemonList.size(); j++) {
//////                    pok = pokemonList.get(i);
//////                    arena.updateEdge(pok, gg);
//////                    break;
//////                }
//////                srcPok = pok.get_edge().getSrc();
////////                Point3D p = pok.getLocation();
////////                gg.getE();
////////                p.close2equals();
////////                gg.getE().
////////                System.out.println(pok.get_edge());
////////                String s = game.getPokemons();
////////                dest = nextNode1(gg, src , pok.getLocation());
//////
//////                dest = nextNode(gg, src, srcPok, pok);
//////                game.chooseNextEdge(ag.getID(), dest);
//////                String res = game.toString();
//////                System.out.println(res);
//////                System.out.println("Agent: " + id + ", val: " + v + "   turned to node: " + dest);
//////            }
//////        }
//////    }
////
////    private static int nextNode(directed_weighted_graph g, int src, int pokSrc, CL_Pokemon pok) {
////        algo = new DWGraph_Algo();
////        algo.init(g);
////        if (src == pokSrc) {
////            return pok.get_edge().getDest();
////        }
////        List<node_data> path = algo.shortestPath(src, pokSrc);
////        int ans = -1;
////        if (path.size() == 1) {
////            return path.get(0).getKey();
////        } else {
////            return path.get(1).getKey();
////        }
////    }
//////
//////    private static int nextNode1(directed_weighted_graph gg, int keyS, Point3D p) {
//////        int keyD = findCloseNode(gg, p);
//////        algo = new DWGraph_Algo();
//////        algo.init(gg);
//////        List<node_data> spd = algo.shortestPath(keyS, keyD);
//////        if (spd.size() == 1) {
//////            return spd.get(0).getKey();
//////        } else if (spd.get(1).getKey() == keyS) {
//////
//////        }
//////        return spd.get(1).getKey();
//////    }
//////
//////    private static int findCloseNode(directed_weighted_graph gg, Point3D p) {
//////
//////        for (node_data node : gg.getV()) {
//////            for (edge_data e : gg.getE(node.getKey())) {
//////                if (betweenEdge(gg, e, p)) {
//////                    return e.getSrc();
//////
//////                }
//////            }
//////        }
//////        return -1;
//////    }
//////
//////    private static boolean betweenEdge(directed_weighted_graph gg, edge_data e, Point3D p) {
//////        double xP = p.x();
//////        double yP = p.y();
//////
//////        double xS = gg.getNode(e.getSrc()).getLocation().x();
//////        double yS = gg.getNode(e.getSrc()).getLocation().y();
//////
//////        double xD = gg.getNode(e.getDest()).getLocation().x();
//////        double yD = gg.getNode(e.getDest()).getLocation().y();
//////
//////
//////        if (xD == xS) {
//////            if (xP != xD) {
//////
//////                return false;
//////            } else if (yD <= yS) {
//////                if (yD <= yP && yP <= yS) {
//////
//////                    return true;
//////                } else {
//////
//////                    return false;
//////                }
//////            } else {
//////                // then yS < yD
//////                if (yS <= yP && yP <= yD) {
//////                    return true;
//////                } else {
//////
//////                    return false;
//////                }
//////
//////            }
//////        } else if (yD == yS) {
//////            if (yP != yD) {
//////                // if the pock s not between them
//////                return false;
//////            } else if (xD <= xS) {
//////                if (xD <= xP && xP <= xS) {
//////
//////                    return true;
//////                } else {
//////
//////                    return false;
//////                }
//////            } else {
//////                // then xS < xD
//////                if (xS <= xP && xP <= xD) {
//////                    return true;
//////                } else {
//////
//////                    return false;
//////                }
//////
//////            }
//////        }
//////        double m = (yD - yS) / (xD - xS);
//////        double n = yD - m * xD;
//////        double checkY = m * xP + n;
//////        return checkY == yP;
//////
//////    }
////
////    public static CL_Pokemon findClosetPokemon(directed_weighted_graph g,List<CL_Pokemon>pokemonList,int srcAgent){
////        int pokIndex = 0;
////        double min = Double.MAX_VALUE;
////        double dis = 0;
////        algo = new DWGraph_Algo();
////        algo.init(g);
////        for (int i = 0; i < pokemonList.size(); i++) {
////            arena.updateEdge(pokemonList.get(i), g);
////            dis = algo.shortestPathDist(srcAgent, pokemonList.get(i).get_edge().getSrc());
////            if (dis < min) {
////                min = dis;
////                pokIndex = i;
////            }
////        }
////        return pokemonList.get(pokIndex);
////    }
////
////    public static directed_weighted_graph stringToGraph(String s) {
////        directed_weighted_graph g = new DWGraph_DS();
////        try {
////            JSONObject jsonObject = new JSONObject(s);
////            JSONArray edges = jsonObject.getJSONArray("Edges");
////            JSONArray vertices = jsonObject.getJSONArray("Nodes");
////            for (int i = 0; i < vertices.length(); i++) {
////                JSONObject v = vertices.getJSONObject(i);
////                int key = v.getInt("id");
////                String p = v.getString("pos");
////                geo_location pos = new Point3D(p);
////                node_data n = new NodeData(key);
////                n.setLocation(pos);
////                g.addNode(n);
////            }
////            for (int i = 0; i < edges.length(); i++) {
////                JSONObject e = edges.getJSONObject(i);
////                int src = e.getInt("src");
////                double weight = e.getDouble("w");
////                int dest = e.getInt("dest");
////                edge_data edge = new EdgeData(src, dest, weight);
////                g.connect(edge.getSrc(), edge.getDest(), edge.getWeight());
////            }
////            return g;
////        } catch (Exception e) {
////            e.printStackTrace();
////            return null;
////        }
////    }
////
//////    // test start
//////    public boolean hasPock (directed_weighted_graph gg, List<CL_Pokemon> pockList, edge_data e){
//////        for(CL_Pokemon pock : pockList){
//////            if (pock.get_edge() == e){
//////
//////                return  true;
//////            }
//////
//////        }
//////        return false;
//////
//////    }
//////
//////    // end of test
//
//}
//
