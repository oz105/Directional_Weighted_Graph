package api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DDWGraph_DSTest {


    public static directed_weighted_graph wg1 = new DWGraph_DS();
    public static directed_weighted_graph wg2 = new DWGraph_DS();
    static int seed = 31;
    static int v_size = 10;
    static int e_size = v_size * 3;
    static Random _rnd = new Random(seed);

    private directed_weighted_graph emptyGraph;
    private directed_weighted_graph g;

    @BeforeEach
    void setupGraphs(){
        emptyGraph = new DWGraph_DS() ;
        g=new DWGraph_DS();
        for(int i=0;i< 11;i++){
            NodeData n = new NodeData() ;
            g.addNode(n);
        }
        g.connect(1,8,5);
        g.connect(1,3,2);
        g.connect(1,10,1);
        g.connect(3,9,4);
        g.connect(9,10,3);
        g.connect(3,6,3);
        g.connect(7,8,2);
        g.connect(2,6,3);
        g.connect(2,4,2);
        g.connect(4,5,1);

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
            wg2.connect(a, b, w);
        }
        return wg2;
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
        System.out.println(g.getNode(1).getKey());
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
        NodeData n = new NodeData() ;
        g.addNode(n);
        assertEquals(12, g.nodeSize());
        for (int i = 0; i < 4 ; i++) {
            n = new NodeData() ;
            g.addNode(n);
        }
        assertNotEquals(13, g.nodeSize());
        assertEquals(16, g.nodeSize());
    }

    @Test
    void connect() {






    }


    @Test
    void getV() {
        wg1 = wgEmpty(v_size, wg1);
        assertEquals(v_size, wg1.nodeSize());

        for(int i=0; i<v_size; i++){
            node_data temp = DDWGraph_DSTest.wg1.getNode(i);
            boolean b = temp ==null;
            assertFalse(b);
        }
    }

    @Test
    void getE() {
    }

    @Test
    void removeNode() {
        wg1 =wgWhole(v_size, wg1);
        int key = nextRnd(0, v_size-1);
        node_data removedNode = wg1.getNode(key);
        node_data temp = DDWGraph_DSTest.wg1.removeNode(key);
        assertEquals(removedNode, temp);
        boolean b = (DDWGraph_DSTest.wg1.getNode(key)==null);
        assertTrue(b);
    }

    @Test
    void removeEdge() {
    }

    @Test
    void nodeSize() {
        wg1 =wgEmpty(v_size, wg1);
        boolean b = (wg1.getV().size() == v_size);
        assertTrue(b);
        wg1.removeNode(1);
        b = (wg1.getV().size() == v_size-1);
        assertTrue(b);
    }
    @Test
    void edgeSize() {
    }


    @Test
    void getMC(){
        wg1 = wgEmpty(v_size, wg1);
        int tempMC = wg1.getMC();
        wg1.connect(0, v_size-1, 5);
        assertNotEquals(tempMC, wg1.getMC());

        tempMC = wg1.getMC();
        wg1.connect(0, v_size-1, 4);
        assertNotEquals(tempMC, wg1.getMC());

        tempMC = wg1.getMC();
        node_data temp = new NodeData(v_size);
        DDWGraph_DSTest.wg1.addNode(temp);
        assertNotEquals(tempMC, DDWGraph_DSTest.wg1.getMC());

        tempMC = DDWGraph_DSTest.wg1.getMC();
        DDWGraph_DSTest.wg1.removeEdge(0, v_size-1);
        assertNotEquals(tempMC, DDWGraph_DSTest.wg1.getMC());

        tempMC = DDWGraph_DSTest.wg1.getMC();
        DDWGraph_DSTest.wg1.removeNode( v_size);
        assertNotEquals(tempMC, DDWGraph_DSTest.wg1.getMC());
    }

    @Test
    void testGetV() {
    }

    @Test
    void getOV() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void testEqual() {
        wg1 =wgEmpty(v_size, wg1);
        wg2 =wgEmpty(v_size, wg2);
        boolean b = wg2.equals((DWGraph_DS) wg1);
        assertTrue(b);
        wg2.removeNode(1);
        b=wg2.equals((DWGraph_DS) wg1);
        assertFalse(b);

        wg1 =wgWhole(v_size, wg1);
        wg2 =wgWhole(v_size, wg2);
        b = wg2.equals((DWGraph_DS) wg1);
        assertTrue(b);
        int a = nextRnd(1, v_size);

        wg2.removeEdge(0,a );
        b=wg2.equals((DWGraph_DS) wg1);
        assertFalse(b);
    }



    @Test
    void testToString() {
    }

//
//    @Test
//    void hasEdge(){
//        wg = wgEmpty(5);
//        assertFalse(wg1.hasEdge(0, 1));
//        wg1.connect(0, 4, 0.3);
//        assertTrue(wg1.hasEdge(0, 4));
//    }
//    @Test
//    void getEdge() {
//        wg = wgWhole(v_size);
//        int a = nextRnd(0, v_size / 2);
//        int b = nextRnd((v_size / 2 + 1), v_size - 1);
//        double d = wg1.getEdge(a, b);
//        assertNotEquals(-1, d);
//        wg1.removeEdge(a, b);
//        d = wg1.getEdge(b, a);
//        assertEquals(-1, d);
//        d = wg1.getEdge(a, v_size + 1);
//        assertEquals(-1, d);
//
//        wg1.connect(a, b, 0);
//        assertEquals(0, d);
//
//    }
//
//    @Test
//    void addNode() {
//        directed_weighted_graph
//        wg = wgWhole(v_size);
//        wg1.addNode(20);
//        boolean b = false;
//        if (wg1.getNode(20) != null) {
//            b = true;
//        }
//        assertEquals(true, b);
//    }
//
//    @Test
//    void connect() {
//        wg = wgWhole(v_size);
//        wg1.connect(1, 2, 0.7);
//        boolean b = false;
//        if (wg1.getEdge(1, 2) == 0.7) {
//            b = true;
//        }
//
//        assertEquals(true, b);
//        b = false;
//        if (wg1.getEdge(2, 1) == 0.7) {
//            b = true;
//        }
//
//        assertEquals(true, b);
//    }
//
//
//

//
//    @Test
//    void testGetV() {
//        wg = wgSpecific();
//        node_data temp = null;
//        Collection<node_data> col = wg1.getV(1);
//        assertTrue(col.contains(wg1.getNode(10)));
//
//        col = wg1.getV(10);
//        assertTrue(col.contains(wg1.getNode(1)));
//        assertTrue(col.contains(wg1.getNode(9)));
//
//        col = wg1.getV(9);
//        assertTrue(col.contains(wg1.getNode(10)));
//        assertTrue(col.contains(wg1.getNode(2)));
//
//        col = wg1.getV(2);
//        assertTrue(col.contains(wg1.getNode(9)));
//        assertTrue(col.contains(wg1.getNode(7)));
//
//        col = wg1.getV(7);
//        assertTrue(col.contains(wg1.getNode(2)));
//        assertTrue(col.contains(wg1.getNode(5)));
//
//        col = wg1.getV(5);
//        assertTrue(col.contains(wg1.getNode(7)));
//        assertTrue(col.contains(wg1.getNode(8)));
//
//        col = wg1.getV(8);
//        assertTrue(col.contains(wg1.getNode(5)));
//        assertTrue(col.contains(wg1.getNode(4)));
//
//        col = wg1.getV(4);
//        assertTrue(col.contains(wg1.getNode(8)));
//        assertTrue(col.contains(wg1.getNode(6)));
//
//        col = wg1.getV(6);
//        assertTrue(col.contains(wg1.getNode(4)));
//        assertTrue(col.contains(wg1.getNode(3)));
//
//        wg1.removeEdge(1, 10);
//
//        col = wg1.getV(1);
//        assertFalse(col.contains(wg1.getNode(10)));
//
//    }
//

//    @Test
//    void removeEdge() {
//        wg =wgEmpty(v_size);
//        int key1 = nextRnd(0, v_size/2);
//        int key2 = nextRnd(v_size/2 +1,v_size-1);
//
//        boolean b = wg2.hasEdge(key1, key2);
//        assertEquals(false, b);
//        wg1.connect(key1, key2, 6);
//
//        b = wg2.hasEdge(key1, key2);
//        assertEquals(true, b);
//    }
//

//    @Test
//    void edgeSize() {
//        wg =wgWhole(v_size);
//        boolean b = (wg1.edgeSize() == (v_size*(v_size-1)/2));
//        assertTrue(b);
//        wg1.removeEdge(0, v_size-1);
//        b = (wg1.edgeSize() == (v_size*(v_size-1)/2)-1);
//        assertTrue(b);
//
//        wg1.connect(0, 1, 0);
//        b = (wg1.edgeSize() == (v_size*(v_size-1)/2)-1);
//        assertTrue(b);
//
//        wg1.connect(0, 1, 3);
//        b = (wg1.edgeSize() == (v_size*(v_size-1)/2)-1);
//        assertTrue(b);
//
//        int edgesSize = nextRnd(1, (v_size*(v_size-1)/2)-1);
//        wg = wgRandCreator(v_size, edgesSize);
//        b = (wg1.edgeSize() == edgesSize);
//        assertTrue(b);
//
//        wg = wgEmpty(v_size);
//        b = (wg1.edgeSize() == 0);
//        assertTrue(b);
//        wg1.connect(v_size, v_size+1, 0);
//        b = (wg1.edgeSize() == 0);
//        assertTrue(b);
//
//        wg1.connect(v_size-1, v_size+1, 0);
//        b = (wg1.edgeSize() == 0);
//        assertTrue(b);
//
//    }

    @Test
    void creatingTime(){
        int vNum = 1000000;
            long startEmpty = System.currentTimeMillis();
            wg1 = wgEmpty(vNum, wg1);
            long endEmpty= System.currentTimeMillis();
            long emptyTime = (startEmpty-endEmpty)/1000;
            assertTrue(emptyTime<10);

        long startRand = System.currentTimeMillis();
        wg1 = wgRealRand(vNum, vNum*10, wg1);
        long endRand= System.currentTimeMillis();
        long randTime = (startRand-endRand)/1000;
        assertTrue(randTime<40);

//        long startWhole = System.currentTimeMillis();
//        wg = wgWhole(vNum);
//        long endWhole= System.currentTimeMillis();
//        long wholeTime = (startWhole-endWhole)/1000;
//        assertTrue(wholeTime<40);
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