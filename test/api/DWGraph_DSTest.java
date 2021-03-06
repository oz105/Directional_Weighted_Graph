package api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_DSTest {


    public static directed_weighted_graph wg1 = new DWGraph_DS();
    public static directed_weighted_graph wg2 = new DWGraph_DS();
    public static directed_weighted_graph fullGraph = new DWGraph_DS();
    static int seed = 31;
    static int v_size = 10;
    static Random _rnd = new Random(seed);

    private directed_weighted_graph emptyGraph;
    private directed_weighted_graph g;

    @BeforeEach
    void setupGraphs() {
        emptyGraph = new DWGraph_DS();
        g = new DWGraph_DS();
        for (int i = 0; i < 11; i++) {
            NodeData n = new NodeData(i);
            g.addNode(n);
        }
        g.connect(1, 8, 5);
        g.connect(1, 3, 2);
        g.connect(1, 10, 1);
        g.connect(3, 9, 4);
        g.connect(9, 10, 3);
        g.connect(3, 6, 3);
        g.connect(7, 8, 2);
        g.connect(2, 6, 3);
        g.connect(2, 4, 2);
        g.connect(4, 5, 1);

    }


    public directed_weighted_graph wgEmpty(int nodes, directed_weighted_graph dwgTemp) {
        dwgTemp = new DWGraph_DS();
        for (int i = 0; i < nodes; i++) {
            node_data temp = new NodeData(i);
            dwgTemp.addNode(temp);
        }
        return dwgTemp;
    }


//    public directed_weighted_graph wgRandCreator(int nodes, int edges, directed_weighted_graph dwgTemp) {
//        dwgTemp = wgEmpty(nodes, dwgTemp);
//        int maxE = (nodes * (nodes - 1));
//        for (int j = 0; j < Math.min(edges, maxE); j++) {
//            int a = nextRnd(0, nodes - 1);
//            int b = nextRnd(0, nodes - 1);
//
//            while (dwgTemp.getEdge(a,b) != null) {
//                a = nextRnd(0, nodes - 1);
//                b = nextRnd(0, nodes - 1);
//
//            }
//            double w = nextRnd(1, (a + b) * 2);
//            dwgTemp.connect(a, b, w);
//        }
//        return dwgTemp;
//    }

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

    public directed_weighted_graph wgSpecific(directed_weighted_graph dwgTemp) {
        dwgTemp = new DWGraph_DS();
        for (int i = 0; i < 11; i++) {
            node_data temp = new NodeData(i);
            dwgTemp.addNode(temp);
        }
        dwgTemp.connect(0, 3, 1);
        dwgTemp.connect(0, 1, 7);
        dwgTemp.connect(1, 2, 7);
        dwgTemp.connect(2, 7, 7);
        dwgTemp.connect(3, 6, 1);

        dwgTemp.connect(5, 6, 7);
        dwgTemp.connect(6, 9, 7);
        dwgTemp.connect(6, 10, 1);
        dwgTemp.connect(7, 2, 7);

        dwgTemp.connect(8, 4, 1);
        dwgTemp.connect(10, 8, 1);

        return dwgTemp;
    }


    @Test
    void getNode() {
        assertAll(" ",
                () -> assertEquals(null, emptyGraph.getNode(0), "should be null"),
                () -> assertEquals(null, emptyGraph.getNode(1), "should be null"),
                () -> assertEquals(null, emptyGraph.getNode(2), "should be null"),
                () -> assertEquals(1, g.getNode(1).getKey(), "should be 1"),
                () -> assertEquals(2, g.getNode(2).getKey(), "should be 2"),
                () -> assertEquals(3, g.getNode(3).getKey(), "should be 3"),
                () -> assertNotEquals(9, g.getNode(8).getKey(), "should not be 9"),
                () -> assertNotEquals(0, g.getNode(1).getKey(), "should not be 0"),
                () -> assertNotEquals(1, g.getNode(2).getKey(), "should not be 1")
        );
    }

    @Test
    void getEdge() {
        assertAll(
                () -> assertEquals(null, emptyGraph.getEdge(0, 1), "should not be edge its empty graph should return -1"),
                () -> assertEquals(null, emptyGraph.getEdge(1, 2), "should not be edge its empty graph should return -1"),
                () -> assertEquals(null, emptyGraph.getEdge(2, 3), "should not be edge its empty graph should return -1"),
                () -> assertEquals(5, g.getEdge(1, 8).getWeight()),
                () -> assertEquals(2, g.getEdge(2, 4).getWeight()),
                () -> assertEquals(3, g.getEdge(2, 6).getWeight()),
                () -> assertEquals(3, g.getEdge(3, 6).getWeight()),
                () -> assertNotEquals(5, g.getEdge(3, 9).getWeight()),
                () -> assertNotEquals(4, g.getEdge(9, 10).getWeight())
        );
    }

    @Test
    void addNode() {
        assertEquals(11, g.nodeSize());
        NodeData n = new NodeData(100);
        g.addNode(n);
        assertEquals(12, g.nodeSize());
        assertFalse(g.getNode(n.getKey()) == null);
        for (int i = 0; i < 4; i++) {
            n = new NodeData(101 + i);
            g.addNode(n);
        }
        assertNotEquals(13, g.nodeSize());
        assertEquals(16, g.nodeSize());
    }

    @Test
    void connect() {
        setupFullGraph(20);
        wg1 = fullGraph;
        int numOfEdges = 0;
        wg1.connect(1, 1, 1);
        assertEquals(null, wg1.getEdge(1, 1));
        wg1.connect(12, 13, 2);
        assertNotEquals(5, wg1.getEdge(12, 13).getWeight());
        assertEquals(1, wg1.getEdge(12, 13).getWeight());
        wg1.connect(13, 13, 0);
        assertEquals(null, wg1.getEdge(13, 13));
        assertEquals(1, wg1.getEdge(13, 14).getWeight());
        numOfEdges = wg1.edgeSize();
        wg1.connect(12, 15, 1);
        assertEquals(380, numOfEdges);
        try {
            emptyGraph.connect(1, 2, 1);
            wg1.connect(3, 5, 0);
        } catch (NullPointerException e) {
            System.out.println("this is empty graph no need to enter the function");
            e.printStackTrace();
        }
    }

    @Test
    void getV() {
        int counterNodes = 0;
        wg1 = wgEmpty(v_size, wg1);
        assertEquals(v_size, wg1.nodeSize());
        for (node_data n : wg1.getV()) {
            counterNodes++;
        }
        assertEquals(10, counterNodes);
        wg1 = wgSpecific(wg1);
        node_data temp = null;
        Collection<node_data> col = wg1.getV();
        assertTrue(col.contains(wg1.getNode(1)));
        assertTrue(col.contains(wg1.getNode(2)));
        assertTrue(col.contains(wg1.getNode(3)));
        assertTrue(col.contains(wg1.getNode(4)));
        assertTrue(col.contains(wg1.getNode(5)));
        assertTrue(col.contains(wg1.getNode(6)));
        assertTrue(col.contains(wg1.getNode(7)));
        assertTrue(col.contains(wg1.getNode(8)));
        assertTrue(col.contains(wg1.getNode(9)));
        assertTrue(col.contains(wg1.getNode(10)));

    }

    @Test
    void getE() {
        wg1 = wgEmpty(3, wg1);
        wg1.connect(0, 1, 1);
        for (edge_data e : wg1.getE(0)) {

            wg1.getE(0).contains(1);
        }
        setupFullGraph(11);

        int counter = 0;
        for (int i = 0; i < 11; i++) {
            int arr[] = new int[11];
            counter = 0;
            for (edge_data e : ((DWGraph_DS) fullGraph).getE(i)) {
                assertTrue(e.getWeight() == 1);
                assertTrue(e.getSrc() == i);
                arr[e.getDest()]++;
                assertTrue(arr[e.getDest()] == 1);
                counter++;
            }
            assertTrue(10 == counter);
        }


    }

    @Test
    void removeNode() {
        setupFullGraph(v_size);
        wg1 = fullGraph;
        int key = nextRnd(0, v_size - 1);
        node_data removedNode = wg1.getNode(key);
        node_data temp = wg1.removeNode(key);
        assertEquals(removedNode, temp);
        boolean b = (wg1.getNode(key) == null);
        assertTrue(b);
        assertEquals(null, emptyGraph.removeNode(2));
        directed_weighted_graph graph = new DWGraph_DS();
        for (int i = 1; i < 5; i++) {
            node_data n = new NodeData(i);
            graph.addNode(n);
        }
        graph.connect(1, 2, 1);
        graph.connect(2, 1, 1);
        graph.connect(2, 3, 1);
        graph.connect(4, 2, 1);
        graph.connect(4, 3, 1);
        assertEquals(4, graph.nodeSize());
        assertEquals(5, graph.edgeSize());
        graph.removeNode(2);
        assertEquals(1, graph.edgeSize());
        assertEquals(null, graph.removeNode(2));
        assertEquals(3, graph.nodeSize());
    }

    @Test
    void removeEdge() {
        assertEquals(null, emptyGraph.removeNode(2));
        assertEquals(null, emptyGraph.removeNode(0));
        setupFullGraph(5);
        assertEquals(20, fullGraph.edgeSize());
        assertNotEquals(null , fullGraph.getEdge(0,1));
        assertNotEquals(null , fullGraph.getEdge(0,2));
        fullGraph.removeEdge(0, 1);
        assertEquals(19, fullGraph.edgeSize());
        assertNotEquals(null , fullGraph.getEdge(0,2));
        fullGraph.removeEdge(0, 2);
        assertEquals(18, fullGraph.edgeSize());
        fullGraph.removeEdge(0, 1);
        assertEquals(18, fullGraph.edgeSize());
        assertEquals(null , fullGraph.getEdge(0,1));
        assertEquals(null , fullGraph.getEdge(0,2));

    }

    @Test
    void nodeSize() {
        wg1 = wgEmpty(v_size, wg1);
        boolean b = (wg1.getV().size() == v_size);
        assertTrue(b);
        wg1.removeNode(1);
        b = (wg1.getV().size() == v_size - 1);
        assertTrue(b);
    }

    @Test
    void edgeSize() {
        wg1 = wgEmpty(v_size, wg1);
        assertTrue(0 == wg1.edgeSize());
        wg1.connect(0, 1, 1);
        assertTrue(1 == wg1.edgeSize());
        wg1 = wgSpecific(wg1);
        assertTrue(11 == wg1.edgeSize());
        for (int i = 3; i < 8; i++) {
//            wg1 = setupFullGraphs(i);


        }
        setupFullGraph(v_size);
        wg1 = fullGraph;
        boolean b = (wg1.edgeSize() == (v_size * (v_size - 1)));
        assertTrue(b);
        wg1.removeEdge(0, v_size - 1);

        b = (wg1.edgeSize() == (v_size * (v_size - 1)) - 1);
        assertTrue(b);

        wg1.connect(0, 1, 0);
        b = (wg1.edgeSize() == (v_size * (v_size - 1)) - 1);
        assertTrue(b);

        wg1.connect(0, 1, 3);
        b = (wg1.edgeSize() == (v_size * (v_size - 1)) - 1);
        assertTrue(b);

        wg1 = wgEmpty(v_size, wg1);
        b = (wg1.edgeSize() == 0);
        assertTrue(b);
        wg1.connect(v_size, v_size + 1, 0);
        b = (wg1.edgeSize() == 0);
        assertTrue(b);

        wg1.connect(v_size - 1, v_size + 1, 0);
        b = (wg1.edgeSize() == 0);
        assertTrue(b);

    }


    @Test
    void getMC() {
        wg1 = wgEmpty(v_size, wg1);
        int tempMC = wg1.getMC();
        wg1.connect(0, v_size - 1, 5);
        assertNotEquals(tempMC, wg1.getMC());

        tempMC = wg1.getMC();
        node_data temp = new NodeData(v_size);
        wg1.addNode(temp);
        assertNotEquals(tempMC, wg1.getMC());

        tempMC = wg1.getMC();
        wg1.removeEdge(0, v_size - 1);
        assertNotEquals(tempMC, wg1.getMC());

        tempMC = wg1.getMC();
        wg1.removeNode(v_size);
        assertNotEquals(tempMC, wg1.getMC());
    }

    @Test
    void getOV() {
        setupFullGraph(11);
        for (int i = 0; i < 11; i++) {
            int arr[] = new int[11];
            int counter = 0;
            for (edge_data e : ((DWGraph_DS) fullGraph).getOV(i)) {
                assertTrue(e.getWeight() == 1);
                assertTrue(e.getSrc() == i);
                arr[e.getDest()]++;
                assertTrue(arr[e.getDest()] == 1);
                counter++;
            }
            assertTrue(10 == counter);
        }
    }

    @Test
    void testEqual() {
        wg1 = wgEmpty(v_size, wg1);
        wg2 = wgEmpty(v_size, wg2);
        boolean b = wg2.equals(wg1);
        assertTrue(b);
        wg2.removeNode(1);
        b = wg2.equals(wg1);
        assertFalse(b);
        int a = nextRnd(1, v_size);
        wg2.removeEdge(0, a);
        b = wg2.equals(wg1);
        assertFalse(b);
    }

    @Test
    void creatingTime() {
        int vNum = 1000000;
        long startEmpty = System.currentTimeMillis();
        wg1 = wgEmpty(vNum, wg1);
        long endEmpty = System.currentTimeMillis();
        long emptyTime = (startEmpty - endEmpty) / 1000;
        assertTrue(emptyTime < 10);

        long startRand = System.currentTimeMillis();
        wg1 = wgRealRand(vNum, vNum * 10, wg1);
        long endRand = System.currentTimeMillis();
        long randTime = (startRand - endRand) / 1000;
        assertTrue(randTime < 40);
    }

    public static void setupFullGraph(int fullSize) {
        fullGraph = new DWGraph_DS();
        for (int i = 0; i < fullSize; i++) {
            node_data n = new NodeData(i);
            fullGraph.addNode(n);
        }
        for (int i = 0; i < fullSize; i++) {
            for (int j = 0; j < fullSize; j++) {
                fullGraph.connect(i, j, 1);
            }
        }
    }

    public static int nextRnd(int min, int max) {
        double v = nextRnd(0.0 + min, (double) max);
        int ans = (int) v;
        return ans;
    }

    public static double nextRnd(double min, double max) {
        double d = _rnd.nextDouble();
        double dx = max - min;
        double ans = d * dx + min;
        return ans;
    }

}