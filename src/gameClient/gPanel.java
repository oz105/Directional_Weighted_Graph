package gameClient;

import api.DWGraph_DS;
import api.directed_weighted_graph;
import api.edge_data;
import api.node_data;
import gameClient.util.Point3D;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class gPanel extends JPanel {
    private directed_weighted_graph graph;


    public gPanel(directed_weighted_graph g) {
        super();
        this.setBackground(Color.white);
        this.graph = g;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("name", Font.PLAIN, 20));
        g.setColor(Color.black);
        double x1;
        double y1;
        for (node_data n : graph.getV()) {
            g.setColor(Color.black);
            x1 = n.getLocation().x();
            y1 = n.getLocation().y();
            g.drawOval((int) (x1), (int) (y1), 25, 25);

        }
        for (node_data n : graph.getV()) {
            for (edge_data e : graph.getE(n.getKey())) {
                double weight = e.getWeight();
                double x2 = graph.getNode(e.getDest()).getLocation().x();
                double y2 = graph.getNode(e.getDest()).getLocation().y();
                x1 = n.getLocation().x();
                y1 = n.getLocation().y();
                g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
//                g.drawString("weight: "+weight , (int)((x1+x2)/2),(int)((y1+y2)/2));
            }
        }


    }
}
