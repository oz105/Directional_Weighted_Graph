package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class Ex2ClientTest {
    private Arena arena ;
    private static game_service game;
    private static List<CL_Pokemon> pokemonsList;
    private CL_Agent agant ;
    private Ex2Client ex2 ;
    private directed_weighted_graph g ;
    private directed_weighted_graph graph ;
    private DWGraph_Algo algo ;


    @BeforeEach
    public void init (){
        game = Game_Server_Ex2.getServer(0);
        arena = new Arena();
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
        pokemonsList = (Arena.json2Pokemons(game.getPokemons()));
        arena.setPokemons(pokemonsList);
        game.startGame();
    }



    @Test
    void refresh() {
        // the test of it is on the screen .

    }


    @Test
    void stringToGraph() {
        graph = new DWGraph_DS();
        algo = new DWGraph_Algo();
        String s = "{\"Edges\":[{\"src\":0,\"w\":1.4004465106761335,\"dest\":1},{\"src\":0,\"w\":1.4620268165085584,\"dest\":10},{\"src\":1,\"w\":1.8884659521433524,\"dest\":0},{\"src\":1,\"w\":1.7646903245689283,\"dest\":2},{\"src\":2,\"w\":1.7155926739282625,\"dest\":1},{\"src\":2,\"w\":1.1435447583365383,\"dest\":3},{\"src\":3,\"w\":1.0980094622804095,\"dest\":2},{\"src\":3,\"w\":1.4301580756736283,\"dest\":4},{\"src\":4,\"w\":1.4899867265011255,\"dest\":3},{\"src\":4,\"w\":1.9442789961315767,\"dest\":5},{\"src\":5,\"w\":1.4622464066335845,\"dest\":4},{\"src\":5,\"w\":1.160662656360925,\"dest\":6},{\"src\":6,\"w\":1.6677173820549975,\"dest\":5},{\"src\":6,\"w\":1.3968360163668776,\"dest\":7},{\"src\":7,\"w\":1.0176531013725074,\"dest\":6},{\"src\":7,\"w\":1.354895648936991,\"dest\":8},{\"src\":8,\"w\":1.6449953452844968,\"dest\":7},{\"src\":8,\"w\":1.8526880332753517,\"dest\":9},{\"src\":9,\"w\":1.4575484853801393,\"dest\":8},{\"src\":9,\"w\":1.022651770039933,\"dest\":10},{\"src\":10,\"w\":1.1761238717867548,\"dest\":0},{\"src\":10,\"w\":1.0887225789883779,\"dest\":9}],\"Nodes\":[{\"pos\":\"35.18753053591606,32.10378225882353,0.0\",\"id\":0},{\"pos\":\"35.18958953510896,32.10785303529412,0.0\",\"id\":1},{\"pos\":\"35.19341035835351,32.10610841680672,0.0\",\"id\":2},{\"pos\":\"35.197528356739305,32.1053088,0.0\",\"id\":3},{\"pos\":\"35.2016888087167,32.10601755126051,0.0\",\"id\":4},{\"pos\":\"35.20582803389831,32.10625380168067,0.0\",\"id\":5},{\"pos\":\"35.20792948668281,32.10470908739496,0.0\",\"id\":6},{\"pos\":\"35.20746249717514,32.10254648739496,0.0\",\"id\":7},{\"pos\":\"35.20319591121872,32.1031462,0.0\",\"id\":8},{\"pos\":\"35.19597880064568,32.10154696638656,0.0\",\"id\":9},{\"pos\":\"35.18910131880549,32.103618700840336,0.0\",\"id\":10}]}" ;
        graph = (DWGraph_DS) ex2.stringToGraph(s);
        algo.init(graph);
        assertEquals(algo.getGraph(), graph);

    }
}