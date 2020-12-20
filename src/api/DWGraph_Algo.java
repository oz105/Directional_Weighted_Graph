package api;
import java.io.*;
import java.util.*;
import com.google.gson.*;
import gameClient.util.Point3D;

/**
 * This Class represents a Directed Weighted Graph Theory Algorithms including:
 * 0. clone(); (copy)
 * 1. init(graph);
 * 2. isConnected(); // strongly
 * 3. double shortestPathDist(int src, int dest);
 * 4. List<node_data> shortestPath(int src, int dest);
 * 5. Save(file); // JSON file
 * 6. Load(file); // JSON file
 *
 */

public class DWGraph_Algo implements dw_graph_algorithms {
    private directed_weighted_graph gAlgo;

    public DWGraph_Algo() {
        gAlgo = new DWGraph_DS();
    }

    /**
     * Init the graph on which this set of algorithms operates on.
     *
     * @param g
     */
    @Override
    public void init(directed_weighted_graph g) {
        this.gAlgo = g;
    }

    /**
     * Return the underlying graph of which this class works.
     *
     * @return
     */
    @Override
    public directed_weighted_graph getGraph() {
        return this.gAlgo;
    }

    /**
     * Compute a deep copy of this weighted graph.
     *
     * @return
     */
    @Override
    public directed_weighted_graph copy() {
        directed_weighted_graph deepCopy = new DWGraph_DS(gAlgo);
        return deepCopy;
    }

    /**
     * Returns true if and only if (iff) there is a valid path from each node to each
     * other node.
     *Returns true if and only if (iff) there is a valid path from EVREY node to each
     * other node. NOTE: assume directional graph.
     * Used BFS algorithm form both ways one time on the regular edges of the graph
     * and secound time on the reverse edges of the graph
     * if both checks return true we will say this graph is Connected.
     * @return
     */
    @Override
    public boolean isConnected() {
        if (this.gAlgo.getV().size() == 0 || this.gAlgo.getV().size() == 1) {
            int test = 0;
            return true;
        }
        if (this.gAlgo.edgeSize() == 0 || this.gAlgo.nodeSize() > this.gAlgo.edgeSize() + 1)
            return false;
        node_data temp = null;
        for (node_data node : this.gAlgo.getV()) {
            temp = node;
            break;
        }
        return bfs(temp);

    }

    /**
     * returns the length of the shortest path between src to dest
     * Note: if no such path --> returns -1
     * Use Dijkstra algorithm
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if ((gAlgo.getNode(src) != null) && (gAlgo.getNode(dest) != null)) {
            if (src == dest) return 0;
            Dijkstra(gAlgo.getNode(src));
            if (gAlgo.getNode(dest).getWeight() != Integer.MAX_VALUE) {
                return gAlgo.getNode(dest).getWeight();
            }
        }
        return -1;
    }

    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * Use Dijkstra algorithm
     * Note if no such path --> returns null;
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<node_data> shortestPath(int src, int dest) {
        if (gAlgo.getNode(src) == null || gAlgo.getNode(dest) == null) return null;
        int tempkey = dest;
        boolean flag = true;
        LinkedList<node_data> path = new LinkedList<node_data>();
        if (src == dest) {
            path.add(gAlgo.getNode(src));
            return path;
        }
        Dijkstra(gAlgo.getNode(src));
        if ((gAlgo.getNode(dest).getWeight() == Integer.MAX_VALUE)) return null;
        path.addLast(gAlgo.getNode(dest));
        ;
        while (flag) {
            try {
                tempkey = Integer.parseInt(gAlgo.getNode(tempkey).getInfo());
                path.addFirst(gAlgo.getNode(tempkey));
                ;
            } catch (NumberFormatException e) {
                System.out.println("there is a bug");
                return null;
            } catch (Exception e) {
                System.out.println("there is a bug");
                return null;
            }
            if (tempkey == src) {
                flag = false;
            }
//            }
        }
        return path;
    }

    /**
     * Saves this weighted (directed) graph to the given
     * file name - in JSON format
     *
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) {
        Gson gson = new GsonBuilder().create();
        JsonObject jsonObject = new JsonObject();
        JsonArray vertices = new JsonArray();
        JsonArray edges = new JsonArray();
        JsonObject savedNode = new JsonObject();
        JsonObject edge = new JsonObject();
        for (node_data n : this.getGraph().getV()) {
            double x = n.getLocation().x(), y = n.getLocation().y(), z = n.getLocation().z();
            savedNode.addProperty("pos", x + "," + y + "," + z);
            savedNode.addProperty("id", n.getKey());
            vertices.add(savedNode);
            savedNode = new JsonObject();
            for (edge_data e : this.getGraph().getE(n.getKey())) {
                edge.addProperty("src", e.getSrc());
                edge.addProperty("w", e.getWeight());
                edge.addProperty("dest", e.getDest());
                edges.add(edge);
                edge = new JsonObject();
            }
        }
        jsonObject.add("Edges", edges);
        jsonObject.add("Nodes", vertices);
        File f = new File(file);
        try {
            FileWriter fileWriter = new FileWriter(f);
            fileWriter.write(gson.toJson(jsonObject));
            fileWriter.close();
            return true;
        } catch ( IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     *
     * @param file - file name of JSON file
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
        directed_weighted_graph grpahLoaded = new DWGraph_DS();
        JsonObject jsonObject;
        File f = new File(file);
        try {
            FileReader fileReader = new FileReader(f);
            jsonObject = new JsonParser().parse(fileReader).getAsJsonObject();
            JsonArray edges = jsonObject.getAsJsonArray("Edges");
            JsonArray vertices = jsonObject.getAsJsonArray("Nodes");

            for (JsonElement v : vertices) {
                int key = ((JsonObject)v).get("id").getAsInt();
                String pos = ((JsonObject) v).get("pos").getAsString();
                geo_location p = new Point3D(pos);
                node_data n = new NodeData(key);
                n.setLocation(p);
                grpahLoaded.addNode(n);
            }

            for (JsonElement edge : edges) {
                int dest = ((JsonObject) edge).get("dest").getAsInt();
                int src = ((JsonObject) edge).get("src").getAsInt();
                double weight = ((JsonObject) edge).get("w").getAsDouble();
                edge_data e = new EdgeData(src, dest, weight);
                grpahLoaded.connect(e.getSrc(), e.getDest(), e.getWeight());
            }
            this.gAlgo = grpahLoaded;
            return true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * This method gets a random node int the graph, by using bfs algorithm if checks if there's a
     * path from that node to all the other nodes, if there's no path from this node to another it
     * return false, if there is- it will check if there's a path from every node in the graph to
     * the given node
     * if there is then this graph is strongly connected and the method return true, if not then
     * it returns false.
     */
    public boolean bfs(node_data node) {
        for (node_data n : this.gAlgo.getV()) {
            n.setTag(-1);

        }
        int counter = 0;
        node_data temp = null;
        Queue<node_data> q = new LinkedList<node_data>();
        q.add(node);
        node.setTag(1);
        counter++;
        while (!q.isEmpty()) {
            if (q.peek() != null) {
                temp = q.poll();
            }
            for (edge_data e : this.gAlgo.getE(temp.getKey())) {
                node_data n2 = gAlgo.getNode(e.getDest());
                if (n2.getTag() == -1) {
                    counter++;
                    if (this.gAlgo.getE(n2.getKey()) != null) {
                        q.add(n2);
                        n2.setTag(1);
                    }
                }
            }
        }
        if (counter != gAlgo.getV().size()) {
            return false;
        }
        counter = 0;
        temp = null;
        q = new LinkedList<node_data>();
        q.add(node);
        node.setTag(2);
        counter++;
        while (!q.isEmpty()) {
            if (q.peek() != null) {
                temp = q.poll();
            }
            if (gAlgo instanceof DWGraph_DS) {
                for (edge_data e : ((DWGraph_DS) (this.gAlgo)).getOV(temp.getKey())) {
                    node_data n2 = gAlgo.getNode(e.getDest());
                    if (n2.getTag() == 1) {
                        counter++;
                        if (((DWGraph_DS) (this.gAlgo)).getOV(n2.getKey()) != null) {
                            q.add(n2);
                            n2.setTag(2);
                        }
                    }
                }
            }

        }
        return counter == gAlgo.getV().size();

    }
    /**
     * Dijkstra Algorithm
     * in this method we will mark all the nodes as unvisited (tag =  -1 -> means unvisited )
     * and we will mark the weight of every node as infinity (weight = Integer.MAX_VALUE)
     * and we put in the info of the node from how he get his weight (info = "n.getKey")
     * we will create a PriorityQueue that will be give Priority base on the smallest weight
     * During the algorithm for every node we will saved 3 things
     * his weight form the src node - this will be store in the weight
     * and from who he gets that weight - this will be store in the info (the key of the node)
     * and if this node already been visited - this will be store in the tag .
     * and when we first visit in some node (means his tag = -1)
     * we add him to the PriorityQueue .
     * the algorithm ends when the PriorityQueue is empty .
     * In the end of the algorithm each node will hold 3 things
     * 1.the smallest weight from src node - will be store in the weight.
     * 2.from who he gets this weight - will be store in the Info.
     * 3.if this node already been visited - this will be store in the tag (if tag != -1 -> means visited).
     * if the Info contains a number
     * (the key from who he gets his weight its means he he already have been visited).
     * @param src
     * @return
     */
    public int Dijkstra(node_data src) {
        double weight = 0;
        int countVisit = 0;
        node_data tempKey;
        PriorityQueue<node_data> PQ = new PriorityQueue<node_data>(new node_dataCompereByWeight());
        for (node_data n : gAlgo.getV()) {
            n.setWeight(Integer.MAX_VALUE);
            n.setInfo("");
            n.setTag(-1);
        }
        if (gAlgo.getNode(src.getKey()) != null) {
            src.setWeight(0);
            src.setInfo("" + src.getKey());
            src.setTag(1);
            PQ.add(src);
            countVisit++;
        }
        while (!(PQ.isEmpty())) {
            if (PQ.peek() != null) {
                tempKey = PQ.poll();
                if (tempKey != null) {
                    for (edge_data e : gAlgo.getE(tempKey.getKey())) {
                        node_data n = gAlgo.getNode(e.getDest());
                        weight = tempKey.getWeight() + (gAlgo.getEdge(tempKey.getKey(), n.getKey()).getWeight());
                        if ((n.getTag() < 0) || n.getWeight() > weight) {
                            PQ.add(n);
                            n.setWeight(weight);
                            n.setInfo("" + tempKey.getKey());
                            n.setTag(1);
                            countVisit++;
                        }
                    }
                }
            }
        }
        return countVisit;
    }

    /**
     * This method check if both algorithms graph
     * equals , return true only if they are
     * false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DWGraph_Algo that = (DWGraph_Algo) o;
        return Objects.equals(gAlgo, that.gAlgo);
    }

}