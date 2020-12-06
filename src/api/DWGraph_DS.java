package api;

import com.google.gson.Gson;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;

public class DWGraph_DS implements directed_weighted_graph {
    private HashMap<Integer, HashMap<Integer, edge_data>> edgesOfGraph;
    private HashMap<Integer, HashMap<Integer, edge_data>> reverse;
    private HashMap<Integer, node_data> verticesOfGraph;
    private int verticesSize, edgeSize, modeCount;

    //CONSTRUCTOR
    public DWGraph_DS() {
//        this.verticesSize = 0;
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
        for (node_data node : copyG.getV()) {
            node_data tempNode = new NodeData(node);
            this.verticesOfGraph.put(tempNode.getKey(), tempNode);
            HashMap tempMap = new HashMap<node_data, edge_data>();
            HashMap tempMap2 = new HashMap<node_data, edge_data>();
            this.edgesOfGraph.put(node.getKey(), tempMap);
            this.reverse.put(node.getKey(), tempMap);
        }
        for (node_data node : copyG.getV()) {
            for (edge_data e : copyG.getE(node.getKey())) {
                edge_data copyE = new EdgeData(e) ;
                this.connect(copyE.getSrc(),copyE.getDest(),copyE.getWeight());
            }
        }
        this.verticesSize = copyG.nodeSize();
        this.edgeSize = copyG.edgeSize();
        this.modeCount = copyG.getMC();
    }



    /**
     * returns the node_data by the node_id,
     *
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
     * Note: this method should run in O(1) time.
     *
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
     * Note: this method should run in O(1) time.
     *
     * @param n
     */
    @Override
    public void addNode(node_data n) {
        if (!(verticesOfGraph.containsKey(n.getKey()))) {
            node_data addedNode = new NodeData(n.getKey());
            verticesOfGraph.put(n.getKey(), addedNode);
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
     * * Note: this method should run in O(1) time.
     *
     * @param src  - the source of the edge.
     * @param dest - the destination of the edge.
     * @param w    - positive weight representing the cost (aka time, price, etc) between src-->dest.
     */
    @Override
    public void connect(int src, int dest, double w) {
        if (src != dest) {
            if (verticesOfGraph.containsKey(src) && !(edgesOfGraph.get(src).containsKey(dest))) {
                edge_data addedEdge = new EdgeData(src , dest , w) ;
                this.edgesOfGraph.get(src).put(dest, addedEdge );
                this.reverse.get(dest).put(src, addedEdge );
                this.edgeSize++;
                this.modeCount++;
            }
            /// Only if we need to update the weight if it is not equal .
            else if (edgesOfGraph.get(src).get(dest).getWeight()!= w) {
                edgesOfGraph.get(src).get(dest);
                this.modeCount++;
            }
        }
    }
    
    /**
     * This method returns a pointer (shallow copy) for the
     * collection representing all the nodes in the graph.
     * Note: this method should run in O(1) time.
     *
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
     * Note: this method should run in O(k) time, k being the collection size.
     *
     * @param node_id
     * @return Collection<edge_data>
     */
    @Override
    public Collection<edge_data> getE(int node_id) {
        if(edgesOfGraph.containsKey(node_id)){
            return edgesOfGraph.get(node_id).values();
        }
        return null ;
    }

    /**
     * Deletes the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * This method should run in O(k), V.degree=k, as all the edges should be removed.
     *
     * @param key
     * @return the data of the removed node (null if none).
     */
    @Override
    //Need to check this.
    public node_data removeNode(int key) {
        if (verticesOfGraph.containsKey(key)){
            int num_of_edges =  edgesOfGraph.get(key).values().size() ;
            edgesOfGraph.remove(key);
            verticesSize -- ;
            edgeSize -= num_of_edges ;
            for (Integer id : reverse.get(key).keySet()){
                edgesOfGraph.get(id).remove(key) ;
                reverse.get(key).remove(id) ;
                edgeSize -- ;
            }
            modeCount ++ ;
            reverse.remove(key) ;
            return verticesOfGraph.remove(key);
        }
        return null;
    }

    /**
     * Deletes the edge from the graph,
     * Note: this method should run in O(1) time.
     *
     * @param src
     * @param dest
     * @return the data of the removed edge (null if none).
     */
    @Override
    public edge_data removeEdge(int src, int dest) {
        if(edgesOfGraph.containsKey(src) && edgesOfGraph.get(src).containsKey(dest)){
            edgesOfGraph.get(src).remove(dest);
            reverse.get(dest).remove(src);
            edgeSize -- ;
            modeCount ++ ;
        }
        return null;
    }

    /**
     * Returns the number of vertices (nodes) in the graph.
     * Note: this method should run in O(1) time.
     *
     * @return
     */
    @Override
    public int nodeSize() {
        return this.verticesSize;
    }

    /**
     * Returns the number of edges (assume directional graph).
     * Note: this method should run in O(1) time.
     *
     * @return
     */
    @Override
    public int edgeSize() {
        return this.edgeSize;
    }

    /**
     * Returns the Mode Count - for testing changes in the graph.
     *
     * @return
     */
    @Override
    public int getMC() {
        return this.modeCount;
    }

    public Collection<Integer> getV (int id){
        return edgesOfGraph.get(id).keySet() ;

    }

    public Collection<edge_data> getOV (int id){

        return reverse.get(id).values() ;
    }

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

    public boolean equal(directed_weighted_graph p){
        if(p==this) return true;
        else if(p==null) return false;
        if(this.edgeSize!= p.edgeSize() || this.verticesOfGraph.size()!=p.getV().size()){
            return false;
        }
        if(p instanceof DWGraph_DS) {
            DWGraph_DS temp = (DWGraph_DS)p;
            String thisS = this.toString();
            String tempS = temp.toString();
            int comp = thisS.compareTo(tempS);
            return (comp == 0);
        }
        return false;
    }
    public String toString(){
        Gson gson = new Gson();
        String str = gson.toJson(this);
        return str;
    }


}





