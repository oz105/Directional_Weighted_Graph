package api;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;


/**
 * This Class represents an directional weighted graph.
 * It support a large number of nodes.
 * The implementation based on an efficient compact representation.
 * there is COPY CONSTRUCTOR
 * and empty CONSTRUCTOR
 */

public class DWGraph_DS implements directed_weighted_graph {
    private HashMap<Integer, HashMap<Integer, edge_data>> edgesOfGraph;
    private HashMap<Integer, node_data> verticesOfGraph;
    private HashMap<Integer, HashMap<Integer, edge_data>> reverse;

    private int verticesSize, edgeSize, modeCount;

    //CONSTRUCTOR
    public DWGraph_DS() {
        this.verticesSize = 0;
        this.edgeSize = 0;
        this.modeCount = 0;
        this.verticesOfGraph = new HashMap<Integer, node_data>();
        this.edgesOfGraph = new HashMap<Integer, HashMap<Integer, edge_data>>();
        this.reverse = new HashMap<Integer, HashMap<Integer, edge_data>>();
    }

    //COPY CONSTRUCTOR
    public DWGraph_DS(directed_weighted_graph copyG) {
        double weight = 0.0;
        this.verticesOfGraph = new HashMap<Integer, node_data>();
        this.edgesOfGraph = new HashMap<Integer, HashMap<Integer, edge_data>>();
        this.reverse = new HashMap<Integer, HashMap<Integer, edge_data>>();
        for (node_data node : copyG.getV()) {
            node_data tempNode = new NodeData(node);
            this.verticesOfGraph.put(tempNode.getKey(), tempNode);
            HashMap tempMap = new HashMap<Integer, edge_data>();
            HashMap tempMap2 = new HashMap<Integer, edge_data>();
            this.edgesOfGraph.put(node.getKey(), tempMap);
            this.reverse.put(node.getKey(), tempMap2);
        }
        for (node_data node : copyG.getV()) {
            for (edge_data e : copyG.getE(node.getKey())) {
                edge_data copyE = new EdgeData(e);
                this.connect(copyE.getSrc(), copyE.getDest(), copyE.getWeight());
            }
        }
        this.verticesSize = copyG.nodeSize();
        this.edgeSize = copyG.edgeSize();
        this.modeCount = copyG.getMC();
    }


    /**
     * returns the node_data by the node_id,
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public node_data getNode(int key) {
        if (verticesOfGraph.containsKey(key)) {
            return verticesOfGraph.get(key);
        }
        return null;
    }

    /**
     * returns the data of the edge (src,dest), null if none.
     * Note: this method run in O(1) time.
     * @param src
     * @param dest
     * @return
     */
    @Override
    public edge_data getEdge(int src, int dest) {
        if (edgesOfGraph.containsKey(src) && edgesOfGraph.get(src).containsKey(dest)) {
            return edgesOfGraph.get(src).get(dest);
        }
        return null;
    }

    /**
     * adds a new node to the graph with the given node_data.
     * Note: this method run in O(1) time.
     * @param n
     */
    @Override
    public void addNode(node_data n) {
        if (!(verticesOfGraph.containsKey(n.getKey()))) {
            verticesOfGraph.put(n.getKey(), n);
            HashMap tempMap = new HashMap<Integer, edge_data>();
            HashMap tempMap2 = new HashMap<Integer, edge_data>();
            edgesOfGraph.put(n.getKey(), tempMap);
            reverse.put(n.getKey(), tempMap2);
            this.verticesSize++;
            this.modeCount++;
        }
    }

    /**
     * Connects an edge with weight w between node src to node dest.
     * Note: this method run in O(1) time.
     * @param src  - the source of the edge.
     * @param dest - the destination of the edge.
     * @param w    - positive weight representing the cost (aka time, price, etc) between src-->dest.
     */
    @Override
    public void connect(int src, int dest, double w) { //we dont change the weight of the edge.
        if (src != dest) {
            if (verticesOfGraph.containsKey(src) && verticesOfGraph.containsKey(dest)) {
                if (!(edgesOfGraph.get(src).containsKey(dest))) {
                    edge_data addedEdge = new EdgeData(src, dest, w);
                    this.edgesOfGraph.get(src).put(dest, addedEdge);
                    addedEdge = new EdgeData(dest, src, w);
                    this.reverse.get(dest).put(src, addedEdge);
                    this.edgeSize++;
                    this.modeCount++;
                }
            }
        }
    }

    /**
     * This method returns a pointer (shallow copy) for the
     * collection representing all the nodes in the graph.
     * Note: this method run in O(1) time.
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_data> getV() {
        return verticesOfGraph.values();
    }

    /**
     * This method returns a pointer (shallow copy) for the
     * collection representing all the edges getting out of
     * the given node (all the edges starting (source) at the given node).
     * Note: this method run in O(k) time, k is the collection size.
     * @param node_id
     * @return Collection<edge_data>
     */
    @Override
    public Collection<edge_data> getE(int node_id) {
        if (edgesOfGraph.containsKey(node_id)) {
            return edgesOfGraph.get(node_id).values();
        }
        return null;
    }

    /**
     * Deletes the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * This method run in O(k), V.degree=k, all the edges connected to this node will be removed.
     * MC will grow ++ for every edge will be deleted and for the node itself .
     * @param key
     * @return the data of the removed node (null if none).
     */
    @Override
    //Need to check this.
    public node_data removeNode(int key) {
        if (verticesOfGraph.containsKey(key)) {
            int num_of_edges = edgesOfGraph.get(key).values().size(); // num of edges go out from this key .
            for (Integer id : reverse.get(key).keySet()) {
                edgesOfGraph.get(id).remove(key); // delete the edges go in this key.
                edgeSize--;
                modeCount++;
            }
            for (Integer id : edgesOfGraph.get(key).keySet()) {
                reverse.get(id).remove(key);
            }
            edgesOfGraph.remove(key);
            reverse.remove(key);
            edgeSize -= num_of_edges;
            verticesSize--;
            modeCount+= num_of_edges+1; //add to MC the num of edges deleted and +1 for the remove node change .
            return verticesOfGraph.remove(key);
        }
        return null;
    }

    /**
     * Deletes the edge from the graph,
     * Note: this method run in O(1) time.
     * @param src
     * @param dest
     * @return the data of the removed edge (null if none).
     */
    @Override
    public edge_data removeEdge(int src, int dest) {
        if (edgesOfGraph.containsKey(src) && edgesOfGraph.get(src).containsKey(dest)) {
            edgesOfGraph.get(src).remove(dest);
            reverse.get(dest).remove(src);
            edgeSize--;
            modeCount++;
        }
        return null;
    }

    /**
     * Returns the number of vertices (nodes) in the graph.
     * Note: this method run in O(1) time.
     * @return
     */
    @Override
    public int nodeSize() {
        return this.verticesSize;
    }

    /**
     * Returns the number of edges (assume directional graph).
     * Note: this method run in O(1) time.
     * @return
     */
    @Override
    public int edgeSize() {
        return this.edgeSize;
    }
    /**
     * Returns the Mode Count - for testing changes in the graph.
     * the MC grow for every change we make in the graph.
     * if we delete a Node from the graph the mc will grow also
     * for each edge we delete from during the removing of the node
     * and in the end will be grow in 1 (mc ++ ) for the deleting the node itself.
     * @return
     */
    @Override
    public int getMC() {
        return this.modeCount;
    }

    /**
     * This method returns a pointer (shallow copy) for the
     * collection representing all the edges but not the regular edges
     * this will return reverse edges.
     * meaning every edge that were from src s to dest d
     * will be now from d -> s
     * @param id
     * @return Collection<edge_data>
     * This method will
     */
    public Collection<edge_data> getOV(int id) {
        return reverse.get(id).values();
    }

    /**
     * This method check if both graphs
     * equals , return true only if they are equals
     * false otherwise.
     */
    //equals with override when we will use assert equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DWGraph_DS that = (DWGraph_DS) o;
        return verticesSize == that.verticesSize &&
                edgeSize == that.edgeSize &&
                Objects.equals(edgesOfGraph, that.edgesOfGraph) &&
                Objects.equals(verticesOfGraph, that.verticesOfGraph);
    }

//    public String toString() {
//        Gson gson = new Gson();
//        String str = gson.toJson(this);
//        return str;
//    }

    @Override
    public String toString() {
        String str = "";
        for (Integer x : edgesOfGraph.keySet()) {
            str += "" + x + "{ " + x + " }--> [";
            for (Integer i : edgesOfGraph.get(x).keySet()) {
                str += i + " -> weight: " + edgesOfGraph.get(x).get(i).getWeight() + ", ";
            }
            str += "] \n";
        }

        return str += "}";
    }
}





