package gameClient;
import Server.Game_Server_Ex2;
import api.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import gameClient.util.Point3D;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;

public class Ex2 {
    int levelGame = 0 ;
    game_service game = Game_Server_Ex2.getServer(levelGame);
    directed_weighted_graph graphh = new DWGraph_DS();


    public static void main(String[] args) {
        int levelGame = 0 ;
        game_service game = Game_Server_Ex2.getServer(levelGame);
        String graph = game.getGraph() ;
        String pok = game.getPokemons() ;
        dw_graph_algorithms algo = new DWGraph_Algo() ;
        directed_weighted_graph graphh = new DWGraph_DS();
        algo.init(graphh);
        algo.load("data\\A0") ;
        GFrame f = new GFrame(algo.getGraph()) ;


    }
}

