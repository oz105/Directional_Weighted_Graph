package gameClient;

import api. *;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Ex2ClientTest {




    @Test
    void findValuestPokemon() {
        // איך אפשר לבדוק את זה זה לא תלוי בנו

    }

    @Test
    void sleepTime() {
    }

    @Test
    void noAgentOnPokemonEdge() {
    }

    @Test
    void refresh() {

    }

    @Test
    void initGraphPathsAndWeights() {
        directed_weighted_graph g1 = new DWGraph_DS();
        directed_weighted_graph g2 = new DWGraph_DS();
        this.initStartNodeOfAgents(g1);
        assertTrue(this.graphOfGame==g1);
        assertFalse(this.graphOfGame==g2);

        this.initStartNodeOfAgents(g2);
        assertTrue(this.graphOfGame==g2);
        assertFalse(this.graphOfGame==g1);

    }


    @Test
    void initStartNodeOfAgents() {

    }

    @Test
    void stringToGraph() {
    }
}