package api;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
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
        if((gAlgo.getNode(src) != null) && (gAlgo.getNode(dest) != null )){
            if(src==dest) return 0 ;
            Dijkstra(gAlgo.getNode(src));
            if(gAlgo.getNode(dest).getWeight() != Integer.MAX_VALUE){
                return gAlgo.getNode(dest).getTag() ;
            }
        }
        return -1 ;
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
        if(gAlgo.getNode(src) == null || gAlgo.getNode(dest) == null ) return null  ;
        int tempkey = dest ;
        boolean flag = true ;
        LinkedList<node_data> path = new LinkedList<node_data>() ;
        if(src==dest) {
            path.add(gAlgo.getNode(src));
            return path ;
        }
        Dijkstra(gAlgo.getNode(src));
        if((gAlgo.getNode(dest).getWeight() == Integer.MAX_VALUE)) return null ;
        path.addLast(gAlgo.getNode(dest)); ;
        while (flag) {
            try{
                tempkey = Integer.parseInt(gAlgo.getNode(tempkey).getInfo());
                path.addFirst(gAlgo.getNode(tempkey)); ;
            }catch (NumberFormatException e) {
                System.out.println("there is a bug");
                return null ;
            }catch (Exception e ){
                System.out.println("there is a bug");
                return null ;
            }
            if(tempkey == src){
                flag = false ;
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
//            for (node_data n2 : this.gAlgo.getV(temp.getKey())) {
            for (edge_data e : this.gAlgo.getE(temp.getKey())) {
                node_data n2 = gAlgo.getNode(e.getDest());
                if (n2.getTag() == -1) {
                    counter++;
//                    if(this.gAlgo.getV(n2.getKey()).size != 0) {
                    if(this.gAlgo.getE(n2.getKey()) != null) {
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
//            for (node_data n2 : this.gAlgo.getOV(temp.getKey())) {
            if(gAlgo instanceof DWGraph_DS) {
                for (edge_data e :((DWGraph_DS)(this.gAlgo)).getOV(temp.getKey())) {
                    node_data n2 = gAlgo.getNode(e.getDest());
                    if (n2.getTag() == 1) {
                        counter++;
//                    if(this.gAlgo.getOV(n2.getKey()).size != 0) {
                        if(((DWGraph_DS)(this.gAlgo)).getOV(n2.getKey()) != null) {
                            q.add(n2);
                            n2.setTag(2);
                        }
                    }
                }
            }

        }
        return counter==gAlgo.getV().size();

    }
    public int Dijkstra (node_data src) {
        double weight = 0 ;
        int countVisit = 0;
        node_data tempKey ;
        PriorityQueue<node_data> PQ = new PriorityQueue<node_data>(new node_dataCompereByTag());
        for (node_data n : gAlgo.getV()) {
            n.setWeight(Integer.MAX_VALUE);
            n.setInfo("");
        }
        if(gAlgo.getNode(src.getKey()) != null ) {
            src.setWeight(0);
            src.setInfo(""+src.getKey());
            PQ.add(src) ;
            countVisit ++ ;
        }
        while(!(PQ.isEmpty())) {
            if(PQ.peek() != null) {
                tempKey = PQ.poll();
                if(tempKey != null) {
                    for (edge_data e : gAlgo.getE(tempKey.getKey())) {
                        node_data n = gAlgo.getNode(e.getDest());
                        weight = tempKey.getWeight() + (gAlgo.getEdge(tempKey.getKey(),n.getKey()).getWeight()) ;
                        if(!(NumberOrNot(n.getInfo())) || n.getWeight() > weight ) {
                            PQ.add(n) ;
                            n.setWeight(weight);
                            n.setInfo(""+tempKey.getKey());
                            countVisit ++ ;
                        }
                    }
                }
            }
        }
        return countVisit ;
    }
    public boolean NumberOrNot (String s) {
        try { Integer.parseInt(s) ; }
        catch (NumberFormatException e) { return false ; }
        return true ;
    }

}