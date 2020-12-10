package gameClient;

import api.directed_weighted_graph;

import javax.swing.*;

public class GFrame extends JFrame {
    gPanel panel ;


    public GFrame (directed_weighted_graph g) {
        super() ;
        panel = new gPanel(g) ;
        this.add(panel) ;
        this.setSize(600 , 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

    }

}
