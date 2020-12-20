package gameClient;

import Server.Game_Server_Ex2;
import api.DWGraph_DS;
import api.NodeData;
import api.game_service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArenaTest {
    private Arena arena ;
    private static game_service game;
    private DWGraph_DS g ;
    private static List<CL_Pokemon> pokemonsList;

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
    void setAndGetGraph() {
        arena.setGraph(g);
        assertEquals(g,arena.getGraph());
        arena.setGraph(null);
        assertEquals(null , arena.getGraph());

    }

    @Test
    void getAndSetGrade() {
        assertTrue(arena.getGrade() == 0);
        arena.setGrade(150);
        assertEquals(150,arena.getGrade());
    }

    @Test
    void getAndSetMoves() {
        assertTrue(arena.getMoves() == 0);
        arena.setMoves(100);
        assertEquals(10,arena.getMoves());
    }

    @Test
    void getAndSetGameLevel() {
        System.out.println(arena.getGameLevel());
        assertTrue(arena.getGameLevel() == 0);
        arena.setGameLevel(2);
        assertTrue(arena.getGameLevel() == 2);
    }

}