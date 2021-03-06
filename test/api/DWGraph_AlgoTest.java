package api;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_AlgoTest {


    private static long start;
    public static directed_weighted_graph dwg2= new DWGraph_DS();
    public static dw_graph_algorithms dwga1= new DWGraph_Algo();
    public static dw_graph_algorithms algo= new DWGraph_Algo();
    public static directed_weighted_graph dwgSpecifi ;
    private static directed_weighted_graph emptyGraph;
    private static directed_weighted_graph fullGraph;

    static int seed = 31;
    static int v_size = 10;
    static int e_size = v_size*3;
    static Random _rnd = new Random(seed);


    @Test
    void init() {
        algo.init(dwgSpecifi);
        assertAll("",
                () -> assertEquals(19 , algo.getGraph().edgeSize()),
                () -> assertEquals(12 , algo.getGraph().nodeSize())
        );
        algo.init(emptyGraph);
        assertAll("",
                () -> assertEquals(0 , algo.getGraph().edgeSize()),
                () -> assertEquals(0 , algo.getGraph().nodeSize())
        );
    }

    @Test
    void getGraph() {
        algo.init(emptyGraph);
        assertAll(
                () -> assertEquals(emptyGraph, algo.getGraph()),
                () -> assertEquals(emptyGraph.getMC(), algo.getGraph().getMC())
        );
        algo.init(dwgSpecifi);
        assertAll(
                () -> assertEquals(dwgSpecifi, algo.getGraph()),
                () -> assertEquals(dwgSpecifi.getMC(), algo.getGraph().getMC())
        );
    }

    @Test
    void copy() {
        algo.init(emptyGraph);
        directed_weighted_graph deepCopyG = algo.copy();
        assertEquals(emptyGraph.nodeSize(), deepCopyG.nodeSize());
        assertEquals(emptyGraph, deepCopyG, "after copy should be equals");
        assertNotEquals(dwgSpecifi, deepCopyG);
        algo.init(dwgSpecifi);
        deepCopyG = algo.copy();
        assertEquals(dwgSpecifi.nodeSize(), deepCopyG.nodeSize());
        assertEquals(dwgSpecifi.edgeSize(), deepCopyG.edgeSize());
        assertEquals(dwgSpecifi, deepCopyG, "after copy should be equals");
        assertNotEquals(emptyGraph, deepCopyG);
    }


    @Test
    void isConnected() {
        directed_weighted_graph graph = new DWGraph_DS() ;
        for (int i = 3; i <8 ; i++) {
            setupFullGraph(i) ;
            algo.init(fullGraph);
        }
        algo.init(emptyGraph);
        assertTrue(algo.isConnected());
        emptyGraph.addNode(new NodeData(0));
        assertTrue(algo.isConnected());
        emptyGraph.addNode(new NodeData(1));
        assertFalse(algo.isConnected());
        for (int i = 0; i <4 ; i++) {
            graph.addNode(new NodeData(i));
        }
        graph.connect(0,1,1);
        graph.connect(1,2,1);
        graph.connect(2,3,1);
        graph.connect(3,0,1);
        algo.init(graph);
        assertTrue(algo.isConnected());
        graph.removeNode(2);
        assertFalse(algo.isConnected());
    }

    @Test
    void shortestPathDist() {
        algo.init(dwgSpecifi);
        double _two = algo.shortestPathDist(9 , 4 ) ;
        double _three = algo.shortestPathDist(4 , 11 ) ;
        double _five = algo.shortestPathDist(0 , 11 ) ;
        double _fiveAgain = algo.shortestPathDist(11 , 0 ) ;
        double _eight = algo.shortestPathDist(11 , 4 ) ;
        double _ten = algo.shortestPathDist(8,1) ;
        double _zero = algo.shortestPathDist(7,7);
        algo.init(emptyGraph);
        double empty = algo.shortestPathDist(0,2) ;
        assertAll(
                () -> assertEquals(0, _zero, "the weight should be 0 "),
                () -> assertEquals(2, _two, "the weight should be 2"),
                () -> assertEquals(3, _three, "the weight should be 4"),
                () -> assertEquals(5, _five, "the weight should be 5"),
                () -> assertEquals(5, _fiveAgain , "the weight should be 5"),
                () -> assertEquals(8,_eight, "the weight should be 8"),
                () -> assertEquals(10, _ten, "there is no Path"),
                () -> assertEquals(-1,empty, "there is no Path , empty graph")
        );
        for (int i = 3; i <8 ; i++) {
            setupFullGraph(i) ;
            algo.init(fullGraph);
            assertEquals(20,algo.shortestPathDist(0, i-1));
        }
    }

    @Test
    void shortestPath() {
        setupFullGraph(30);
        dwga1.init(fullGraph);
        int arr [] = {0, 15, 17, 13, 29, 5};
        double d = shortestSU(fullGraph, arr);
        assertTrue(d == dwga1.shortestPathDist(arr[0], arr[arr.length-1]));
        List<node_data> sP = dwga1.shortestPath(arr[0], arr[arr.length-1]);
        boolean b = sP.size()==arr.length;
        assertTrue(b);
        if(b){
            int i=0;
            for(node_data curr: sP) {
                b &=curr.getKey()==arr[i];
                i++;
            }
        }
        assertTrue(b);






    }
    @Test
    void saveAndLoad(){
        directed_weighted_graph tempGraph = new DWGraph_DS() ;
        for (int i = 0; i <4 ; i++) {
            tempGraph.addNode(new NodeData(i));
        }
        tempGraph.connect(0,1,1);
        tempGraph.connect(2,1,1);
        tempGraph.connect(3,1,1);
        algo.init(tempGraph);
        directed_weighted_graph gCopy = algo.copy();
        dw_graph_algorithms temp = new DWGraph_Algo();
        algo.save("this is g graph");
        temp.load("this is g graph");
        assertEquals(gCopy, temp.getGraph());
        gCopy.removeNode(1);
        assertNotEquals(gCopy, temp.getGraph());
        setupFullGraph(5);
        algo.init(fullGraph);
        algo.save("this is fullGraph5");
        gCopy = algo.copy();
        temp.load("this is fullGraph5");
        assertEquals(gCopy, temp.getGraph());
        gCopy.removeNode(1);
        assertNotEquals(gCopy, temp.getGraph());
        algo.init(emptyGraph);
        algo.save("this is emptyGraph");
        temp.load("this is emptyGraph");
        gCopy = algo.copy();
        assertEquals(gCopy, temp.getGraph());
        gCopy.removeNode(1);
        assertEquals(gCopy, temp.getGraph());
    }

    @Test
    void testEquals() {
        int a = nextRnd(0, v_size*2);
//        wg1 = wgRealRand(v_size, a, wg1 );
        //*******************************************************************
    }

    //Functions

    /**
     * This method run before all the tests
     * its simple method that check the run time of the tests
     * And init the graph dwgSpecifi
     */
    @BeforeAll
    static void startRunTimeAndSetUpGraph(){
        start=new Date().getTime();
    }


    /**
     * This method run after all the test done
     * its it print the time it took in seconds .
     */
    @AfterAll
    static void endRunTime(){
        long end=new Date().getTime();
        double dt=(end-start)/1000.0;
        System.out.println("run in : "+dt+" seconds");
    }


    public directed_weighted_graph wgEmpty(int nodes, directed_weighted_graph dwgTemp) {
        dwgTemp = new DWGraph_DS();
        for (int i = 0; i < nodes; i++) {
            node_data temp = new NodeData();
            dwgTemp.addNode(temp);
        }
        return dwgTemp;
    }

    public directed_weighted_graph
    wgWhole(int nodes, directed_weighted_graph dwgTemp) {
        dwgTemp = new DWGraph_DS();
        for (int i = 0; i < nodes; i++) {
            node_data temp = new NodeData();
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
    public static void setupFullGraph(int fullSize) {
        fullGraph = new DWGraph_DS();
        for (int i = 0; i < fullSize; i++) {
            node_data n = new NodeData(i);
            fullGraph.addNode(n);
        }
        for (int i = 0; i < fullSize; i++) {
            for (int j = 0; j < fullSize; j++) {
                fullGraph.connect(i, j, v_size*2);
            }
        }
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
            wg.removeEdge(a[i], a[i+1]);
            wg.connect(a[i], a[i+1], w);
        }
        return d;
    }
    @BeforeEach
    void setUpGraphs () {
        emptyGraph = new DWGraph_DS() ;
        dwgSpecifi = new DWGraph_DS() ;
        for (int i = 0; i < 12 ; i++) {
            NodeData n = new NodeData(i) ;
            dwgSpecifi.addNode(n);
        }
        dwgSpecifi.connect(0,1,3);
        dwgSpecifi.connect(0,3,2);

        dwgSpecifi.connect(1,4,1);

        dwgSpecifi.connect(2,6,3);

        dwgSpecifi.connect(3,5,1);

        dwgSpecifi.connect(4,1,1);
        dwgSpecifi.connect(4,11,3);
        dwgSpecifi.connect(4,7,2);

        dwgSpecifi.connect(5,10,3);
        dwgSpecifi.connect(5,11,2);
        dwgSpecifi.connect(5,9,2);

        dwgSpecifi.connect(6,7,2);
        dwgSpecifi.connect(6,0,2);


        dwgSpecifi.connect(7,9,1);

        dwgSpecifi.connect(8,3,4);

        dwgSpecifi.connect(9,11,1);
        dwgSpecifi.connect(9,4,2);

        dwgSpecifi.connect(10,8,1);

        dwgSpecifi.connect(11,6,3); // 19 Edges
    }
}