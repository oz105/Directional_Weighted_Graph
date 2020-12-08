package api;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class EdgeDataTest {
    private static long start;
    private static DWGraph_DS g;
    private static DWGraph_DS emptyGraph;

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
    void getSrc() {
        assertAll(" ",
                () -> assertEquals(2, g.getEdge(2,4).getSrc() ),
                () -> assertEquals(1, g.getEdge(1,8).getSrc() ),
                () -> assertNotEquals(8, g.getEdge(1,8).getSrc()),
                () -> assertEquals(3, g.getEdge(3,6).getSrc() )
        );
    }

    @Test
    void getDest() {
        assertAll(" ",
                () ->assertEquals(8, g.getEdge(1,8).getDest() ),
                () ->assertEquals(4, g.getEdge(2,4).getDest() ),
                () ->assertEquals(6, g.getEdge(3,6).getDest() ),
                () ->assertNotEquals(1, g.getEdge(1,8).getDest() )
        );
    }

    @Test
    void getWeight() {
        assertAll(" ",
                () ->assertEquals(5, g.getEdge(1,8).getWeight() ),
                () ->assertEquals(2, g.getEdge(2,4).getWeight() ),
                () ->assertEquals(3, g.getEdge(3,6).getWeight()),
                () ->assertNotEquals(8, g.getEdge(1,8).getWeight() )

        );

    }

    @Test
    void getInfoAndSetInfo() {
        g.getEdge(1,8).setInfo("1 to 8");
        g.getEdge(2,4).setInfo("2 to 4");
        g.getEdge(3,6).setInfo("3 to 6");
        assertAll(" ",
                () ->assertEquals("1 to 8", g.getEdge(1,8).getInfo() ),
                () ->assertEquals("2 to 4", g.getEdge(2,4).getInfo() ),
                () ->assertEquals("3 to 6", g.getEdge(3,6).getInfo())
        );
        g.getEdge(1,8).setInfo("1to8");
        g.getEdge(2,4).setInfo("2to4");
        g.getEdge(3,6).setInfo("3to6");
        assertAll(" ",
                () ->assertNotEquals("1 to 8", g.getEdge(1,8).getInfo() ),
                () ->assertNotEquals("2 to 4", g.getEdge(2,4).getInfo() ),
                () ->assertNotEquals("3 to 6", g.getEdge(3,6).getInfo())
        );
    }

    @Test
    void getTagAndSetTag() {
        g.getEdge(1,8).setTag(18);
        g.getEdge(2,4).setTag(24);
        g.getEdge(3,6).setTag(36);
        assertAll(" ",
                () ->assertEquals(18, g.getEdge(1,8).getTag() ),
                () ->assertEquals(24, g.getEdge(2,4).getTag() ),
                () ->assertEquals(36, g.getEdge(3,6).getTag())
        );
        g.getEdge(1,8).setTag(81);
        g.getEdge(2,4).setTag(42);
        g.getEdge(3,6).setTag(63);
        assertAll(" ",
                () ->assertNotEquals(18, g.getEdge(1,8).getInfo() ),
                () ->assertNotEquals(24, g.getEdge(2,4).getInfo() ),
                () ->assertNotEquals(36, g.getEdge(3,6).getInfo())
        );



    }

}