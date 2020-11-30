package ex2;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class DWGraph_Algo implements dw_graph_algorithms {
    private directed_weighted_graph gAlgo ;

    /**
     * Init the graph on which this set of algorithms operates on.
     *
     * @param g
     */
    @Override
    public void init(directed_weighted_graph g) {
        this.gAlgo = g ;
    }

    /**
     * Return the underlying graph of which this class works.
     *
     * @return
     */
    @Override
    public directed_weighted_graph getGraph() {
        return this.gAlgo ;
    }

    /**
     * Compute a deep copy of this weighted graph.
     *
     * @return
     */
    @Override
    public directed_weighted_graph copy() {
        directed_weighted_graph deepCopy = new DWGraph_DS(gAlgo);
        return deepCopy ;
    }

    /**
     * Returns true if and only if (iff) there is a valid path from each node to each
     * other node. NOTE: assume directional graph (all n*(n-1) ordered pairs).
     *
     * @return
     */
    @Override
    public boolean isConnected() {
        if(this.gAlgo.getV().size()==0 ||this.gAlgo.getV().size()==1)return true;
        if(this.gAlgo.edgeSize()==0 || this.gAlgo.nodeSize()>this.gAlgo.edgeSize()+1)
            return false;
        node_data temp = null;
        for (node_data node : this.gAlgo.getV()) {
            temp=node;
            break;
        }
        return this.bfs(temp);

    }

    /**
     * returns the length of the shortest path between src to dest
     * Note: if no such path --> returns -1
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        node_data n1 = this.gAlgo.getNode(src);
        node_data n2 = this.gAlgo.getNode(dest);

        if(n1==null || n2 == null) return -1;
//        int x = bfs(n1);//////////// הדייאקסטרה שעשית הוא המיטבי לגרף מכוון ממושקל
        return n2.getTag();
    }

    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * see: https://en.wikipedia.org/wiki/Shortest_path_problem
     * Note if no such path --> returns null;
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<node_data> shortestPath(int src, int dest) {
        return null;
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
        return false;
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
        return false;
    }

    public boolean bfs(node_data node){
        for (node_data n : this.gAlgo.getV()) {
            n.setTag(-1);

        }
        int counter = 0;
        node_data temp = null;
        Queue<node_data> q = new LinkedList<node_data>() ;
        q.add(node);
        node.setTag(1);
        counter ++;
        while(!q.isEmpty()){
            if(q.peek()!=null){
                temp = q.poll();
            }
            for (node_data n2 : this.gAlgo.getV(temp.getKey())) {
                if (n2.getTag() == -1) {
                    counter++;
                    if(this.gAlgo.getV(n2.getKey()).size != 0) {
                        q.add(n2);
                        n2.setTag(1);
                    }
                }
            }
        }
        if(counter!=gAlgo.getV().size()){
            return false;
        }
        counter = 0;
        temp = null;
        q = new LinkedList<node_data>() ;
        q.add(node);
        counter ++;
        while(!q.isEmpty()){
            if(q.peek()!=null){
                temp = q.poll();
            }
            for (node_data n2 : this.gAlgo.getOV(temp.getKey())) {
                if (n2.getTag() == 1) {
                    counter++;
                    if(this.gAlgo.getOV(n2.getKey()).size != 0) {
                        q.add(n2);
                        n2.setTag(2);
                    }
                }
            }
        }
        return counter==gAlgo.getV().size();

    }
}
