package api;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class NodeDataTest {
    private static long start;
    private static DWGraph_DS g;
    private static DWGraph_DS emptyGraph;
    private geo_location p ;

    //Functions

    /**
     * This method run before all the tests
     * its simple method that check the run time of the tests
     * And init the graph g
     */

    @BeforeAll
    static void startRunTimeAndSetUpGraph(){
        start=new Date().getTime();
        g=new DWGraph_DS();
        for(int i=1;i< 11;i++){
            node_data n = new NodeData() ;
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
        emptyGraph=new DWGraph_DS();
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

    @Test
    void getKey() {
        boolean t = false ;
        try{
            emptyGraph.getNode(2).getKey() ;
        }catch (NullPointerException e ) {
            t=true ;
        }
        assertTrue(t);
        assertAll(" ",
                () -> assertEquals(0, g.getNode(0).getKey()),
                () -> assertEquals(1, g.getNode(1).getKey())
        );
    }

    @Test
    void getLocation() {

    }

    @Test
    void setLocation() {

    }

    @Test
    void getWeightAndSetWeight() {
        g.getNode(0).setWeight(12);
        g.getNode(1).setWeight(9);
        g.getNode(2).setWeight(7);
        assertEquals(12 , g.getNode(0).getWeight());
        assertEquals(9 , g.getNode(1).getWeight());
        assertEquals(7 , g.getNode(2).getWeight());
        g.getNode(0).setWeight(10);
        g.getNode(1).setWeight(7);
        g.getNode(2).setWeight(5.5);
        assertNotEquals(12 , g.getNode(0).getWeight());
        assertNotEquals(9 , g.getNode(1).getWeight());
        assertNotEquals(7 , g.getNode(2).getWeight());
    }


    @Test
    void getInfoAndSetInfo() {
        g.getNode(0).setInfo("0");
        g.getNode(1).setInfo("1");
        g.getNode(2).setInfo("2");
        assertEquals("0" , g.getNode(0).getInfo());
        assertEquals("1" , g.getNode(1).getInfo());
        assertEquals("2" , g.getNode(2).getInfo());
        g.getNode(0).setInfo("10");
        g.getNode(1).setInfo("01");
        g.getNode(2).setInfo("02");
        assertNotEquals("0" , g.getNode(0).getInfo());
        assertNotEquals("1" , g.getNode(1).getInfo());
        assertNotEquals("2" , g.getNode(2).getInfo());
    }


    @Test
    void getTagAndSetTag() {
        g.getNode(0).setTag(0);
        g.getNode(1).setTag(1);
        g.getNode(2).setTag(2);
        assertEquals(0 , g.getNode(0).getTag());
        assertEquals(1 , g.getNode(1).getTag());
        assertEquals(2 , g.getNode(2).getTag());

        g.getNode(0).setTag(10);
        g.getNode(1).setTag(11);
        g.getNode(2).setTag(12);
        assertNotEquals( 0, g.getNode(0).getTag());
        assertNotEquals(1 , g.getNode(1).getTag());
        assertNotEquals(1 , g.getNode(2).getTag());
    }


    @Test
    void x() {

    }

    @Test
    void y() {
    }

    @Test
    void z() {
    }

    @Test
    void distance() {
    }

}