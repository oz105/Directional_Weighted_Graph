package api;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_AlgoTest {


    public static directed_weighted_graph dwg1= new DWGraph_DS();
    public static directed_weighted_graph dwg2= new DWGraph_DS();
    public static dw_graph_algorithms dwga1= new DWGraph_Algo();
    public static dw_graph_algorithms dwga2= new DWGraph_Algo();
    static int seed = 31;
    static int v_size = 10;
    static int e_size = v_size*3;
    static Random _rnd = new Random(seed);
//    static src.ex1.src.directed_weighted_graph g0 = new src.ex1.src.WGraph_DS(), g1;
//    static src.ex1.src.directed_weighted_graph_algorithms ga;

    public directed_weighted_graph wgSpecific(){
        //צריך לעשות ספציפי אחד כמו מה שנעשה בטסט של הגרף
        return null;
    }

    public directed_weighted_graph wgEmpty(int nodes, directed_weighted_graph dwgTemp) {
        dwgTemp = new DWGraph_DS();
        for (int i = 0; i < nodes; i++) {
            node_data temp = new NodeData(i);
            dwgTemp.addNode(temp);
        }
        return dwgTemp;
    }

    public directed_weighted_graph
    wgWhole(int nodes, directed_weighted_graph dwgTemp) {
        dwgTemp = new DWGraph_DS();
        for (int i = 0; i < nodes; i++) {
            node_data temp = new NodeData(i);
            dwgTemp.addNode(temp);
        }
        for (int i = 0; i < nodes - 1; i++) {
            for (int j = i + 1; j < nodes; j++) {
                dwgTemp.connect(i, j, 5);
            }

        }
        return dwgTemp;
    }
    public directed_weighted_graph wgRandCreator(int nodes, int edges, directed_weighted_graph dwgTemp) {
        dwgTemp = wgEmpty(nodes, dwgTemp);
        int maxE = (nodes * (nodes - 1) / 2);
        for (int j = 0; j < Math.min(edges, maxE); j++) {
            int a = nextRnd(0, nodes - 1);
            int b = nextRnd(0, nodes - 1);
            node_data nodeA = dwgTemp.getNode(a);
            node_data nodeB = dwgTemp.getNode(b);

//            while (dwgTemp.getEdge(a,b) !=-1) {
//                a = nextRnd(0, nodes - 1);
//                b = nextRnd(0, Math.abs(nodes - a));
//
//            }
            double w = nextRnd(1, (a + b) * 2);
            dwg2.connect(a, b, w);
        }
        return dwg2;
    }
    public directed_weighted_graph
    wgRealRand(int nodes, int edges, directed_weighted_graph dwgTemp) {
        dwgTemp = wgEmpty(nodes, dwgTemp);
        int maxE = (nodes * (nodes - 1) / 2);
        for (int j = 0; j < Math.min(edges, maxE); j++) {
            int a = nextRnd(0, nodes - 1);
            int b = nextRnd(0, nodes - 1);

            double w = nextRnd(1, (a + b) * 2);
            dwgTemp.connect(a, b, w);
        }
        return dwgTemp;
    }
    public double shortestSU(directed_weighted_graph wg, int []a){
//        double [] d = new double[a.length-1];
        double d =0;
        for (int i=0; i<a.length-1; i++){
            node_data n1 =wg.getNode(a[i]);
            node_data n2 = wg.getNode(a[i+1]);
//            d[i]=nextRnd(0.01, 1);
            double w = nextRnd(0.01, 1);
            d+=w;
            wg.connect(a[i], a[i+1], w);
        }
        return d;
    }
    @Test
    void init() {
    }

    @Test
    void getGraph() {
    }

    @Test
    void copy() {
    }

    @Test
    void isConnected() {
    }

    @Test
    void shortestPathDist() {
    }

    @Test
    void shortestPath() {
    }
    @Test
    void save(){
        node_data node0 = new NodeData(0);
        dwg1.addNode(node0);
        node_data node1 = new NodeData(1);
        dwg1.addNode(node1);
        node_data node2 = new NodeData(2);
        dwg1.addNode(node2);
        dwg1.connect(0, 1, 0.1);
        dw_graph_algorithms dwga = new DWGraph_Algo();
        dwga.init(dwg1);
        dwga.save("file");

    }
    @Test
    void load(){
//        dwg1= wgSpecific(dwg1);
        dwga1.init(dwg1);
        dwga1.save("C:\\Users\\abhau\\IdeaProjects\\Directional_Weighted_Graph\\file.json");
        dwga2.load("C:\\Users\\abhau\\IdeaProjects\\Directional_Weighted_Graph\\file.json");
        DWGraph_Algo temp = (DWGraph_Algo)dwga1;
        boolean b = temp.equal(dwga2);
        assertTrue(b);
    }
    @Test
    void bfs() {
    }

    @Test
    void dijkstra() {
    }

    @Test
    void numberOrNot() {
    }

    @Test
    void equal() {
    }

    @Test
    void testEquals() {
    }

    public static int nextRnd(int min, int max) {
        double v = nextRnd(0.0+min, (double)max);
        int ans = (int)v;
        return ans;
    }
    public static double nextRnd(double min, double max) {
        double d = _rnd.nextDouble();
        double dx = max-min;
        double ans = d*dx+min;
        return ans;
    }
}
}