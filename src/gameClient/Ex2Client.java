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
    private int ID ;
    private static HashMap<Integer, HashMap<Integer, List<node_data>>> graphPaths;
    private static HashMap<Integer, HashMap<Integer, Double>> graphWeights;
    private static List<CL_Pokemon> pokemonsList;
    private static List<CL_Agent> agentsList;
    private static MyFrame window;
    private static Arena arena;
    private static dw_graph_algorithms algo;
    private static directed_weighted_graph graphOfGame;
    private static game_service game;
    private int gameLevel;
    private int grade;
    private int Moves;
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
    public Ex2Client(int level , int id) {
        this.ID = id ;
        this.gameLevel = level;
    }

    @Override
    public void run() {
        game = Game_Server_Ex2.getServer(gameLevel);
        game.login(ID);
        init(game);
        game.startGame();
        System.out.println("game start!");
        int ind = 0;

        while (game.isRunning()) {
            window.update(arena);
            try {
                if (ind % 1 == 0) {
                    window.repaint();
                }
                synchronized (this) {
                    moveAgents(game, graphOfGame);
                }
                if (noAgentOnPokemonEdge() != null) {
                    double d = sleepTime();
                    Thread.sleep((long) (d));
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
        System.out.println("the result are:");
        System.out.println("score is: " + grade + ", and the moves are: " + Moves);
        System.out.println();
        System.exit(0);

    }
    /**
     * This method Init the game before we start it.
     *
     */

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

    /**
     * This method will run over all the agents list
     * and give to any agent pokemon to collect
     * based on the function "findValuestPokemon"
     *
     */

    private void moveAgents(game_service game, directed_weighted_graph g) {
        int srcPok = 0 ;
        String agentsMove = game.move(); // Use max 10 times in 1 sec .
        agentsList = Arena.getAgents(agentsMove, g);
        arena.setAgents(agentsList);
        pokemonsList = Arena.json2Pokemons(game.getPokemons());
        for (CL_Pokemon pok : pokemonsList) {
            arena.updateEdge(pok, g);
        }
        arena.setPokemons(pokemonsList);
        for (CL_Agent agent : agentsList) {
            int dest = agent.getNextNode();
            int src = agent.getSrcNode();
            if (dest == -1) {
                agent.setOnPokemonEdge(false);
                synchronized (Thread.currentThread()) {
                    CL_Pokemon pok = findValuestPokemon(src);
                    srcPok = pok.get_edge().getSrc();
                    dest = nextNode(agent, srcPok, pok);
                    agent.set_curr_fruit(pok);
                    takenPock.put(agent.getID(), pok);
                    pok.targetd();
                    if (graphOfGame.getE(dest).size() == 0) {
                        dest = randDest();
                    }
                    game.chooseNextEdge(agent.getID(), dest);
                }
            }
        }
    }

    /**
     * This method will be used only if
     * there is no way out from the agent will be sent
     * and it will pick for him random node because
     * we dont want agent stuck in some node there is no way out.
     *
     */

    private int randDest() {
        int num_of_nodes = graphOfGame.nodeSize();
        int rand = (int) (Math.random() * num_of_nodes);
        if(graphOfGame.getE(rand).size() == 0) {
            randDest() ;
        }
        return rand;
    }

    /**
     * This method will go to the Hashmap that contains all the paths
     * in the graph and pull from there the next node the agent should go.
     *
     */
    private int nextNode(CL_Agent a, int pokSrc, CL_Pokemon pok) {
        if (a.getSrcNode() == pokSrc) {
            a.setOnPokemonEdge(true);
            pok.targetd();
            return pok.get_edge().getDest();
        }
        List<node_data> path = graphPaths.get(a.getSrcNode()).get(pokSrc);
        if(path.size() == 1) {
            return path.get(0).getKey();
        }
        return path.get(1).getKey();
    }
    /**
     * This method will looking for the valuest pokemon to each agent
     * based on his location.
     * will check what is the value of the pokemon and split it in the weight to get to him.
     *
     */

    public CL_Pokemon findValuestPokemon(int srcAgent) {
        int pokIndex = 0;
        double max = 0;
        double dis;
        double valueWithDis;
        for (int i = 0; i < pokemonsList.size(); i++) {
            if (!(takenPock.containsValue(pokemonsList.get(i)))) {
                if (!(pokemonsList.get(i).getTarget())) {
                    dis = graphWeights.get(srcAgent).get(pokemonsList.get(i).get_edge().getSrc());
                    valueWithDis = (pokemonsList.get(i).getValue() / dis);
                    if (valueWithDis > max) {
                        max = valueWithDis;
                        pokIndex = i;
                    }
                }
            }
        }
        return pokemonsList.get(pokIndex);
    }

    /**
     * This method will compute the time that the Tread should sleep ,
     * base on the agent is on the edge of the pokemon ,
     * and we will search the min time if there is more than 1 agent on pokemon edge
     *
     */

    public double sleepTime() {
        double de, w, d;
        double dist = 0;
        double min = -1;
        for (CL_Agent agent : agentsList) {
            if (takenPock.get(agent.getID()) != null) {
                if (takenPock.get(agent.getID()).get_edge() != null) {
                    edge_data e = takenPock.get(agent.getID()).get_edge();
                    w = e.getWeight();
                    geo_location dest = graphOfGame.getNode(e.getDest()).getLocation();
                    geo_location src = graphOfGame.getNode(e.getSrc()).getLocation();
                    de = src.distance(dest);
                    if (agent.get_curr_fruit() != null) {
                        dist = agent.get_curr_fruit().getLocation().distance(agent.getLocation());
                    }
                    double norm = dist / de;
                    double dt = w * norm / agent.getSpeed();
                    d = (long) (995.0 * dt);
                    if (min == -1 || d < min) {
                        min = d;
                    }
                }
            }
        }
        if (min == 0) {
            return 5;
        }
        if (min != -1 && dist != 0) {
            return min;
        }
        return 100;
    }

    /**
     * This method will return CL agent iff there is agent and pok on the same edge
     * and null otherwise .
     *
     */

    public CL_Agent noAgentOnPokemonEdge() {
        for (CL_Agent a : agentsList) {
            if (a.isOnPokemonEdge()) {
                return a;
            }
        }
        return null;
    }

    /**
     * This method show the information of the game on the gui
     * it's constantly pull information from the game such as time to end , score and moves and update it.
     *
     */

    public void refresh() {
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

    //Functions for init

    /**
     * Init the Hashmaps and set in them all the paths of the graph
     * and all the weights of the paths .
     * This is very efficiency because instead of
     * constantly performing the Dijkstra algorithm while the game is running
     * it just pulls the next node to which it should go what hashmap
     *
     * @param g
     */
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

    /**
     * Init the first node of the agent based on the src of the pok .
     *
     */
    public void initStartNodeOfAgents() {
        String gameInfo = game.toString();
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(gameInfo);
            JSONObject gameServer = jsonObject.getJSONObject("GameServer");
            int numOfAgents = gameServer.getInt("agents");
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

    /**
     * This methos get a String of Json and make from it directed_weighted_graph.
     *
     */

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


