import api.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DWGraph_AlgoTest {
    public static directed_weighted_graph dwg1= new DWGraph_DS();
    public static directed_weighted_graph dwg2= new DWGraph_DS();
    public static dw_graph_algorithms dwga1= new DWGraph_Algo();
    public static dw_graph_algorithms dwga2= new DWGraph_Algo();

    public directed_weighted_graph wgSpecific(directed_weighted_graph dwg) {
        dwg = new DWGraph_DS();
        for (int i = 0; i < 11; i++) {
            node_data temp = new NodeData();
            dwg.addNode(temp);
        }
        dwg.connect(0, 3, 1);
        dwg.connect(0, 1, 7);
        dwg.connect(1, 2, 7);
        dwg.connect(2, 7, 7);
        dwg.connect(3, 6, 1);

        dwg.connect(5, 6, 7);
        dwg.connect(6, 9, 7);
        dwg.connect(6, 10, 1);
        dwg.connect(7, 2, 7);

        dwg.connect(8, 4, 1);
        dwg.connect(10, 8, 1);

        return dwg;
    }
    @Test
    void save(){
        node_data node0 = new NodeData();
        dwg1.addNode(node0);
        node_data node1 = new NodeData();
        dwg1.addNode(node1);
        node_data node2 = new NodeData();
        dwg1.addNode(node2);
        dwg1.connect(0, 1, 0.1);
        dw_graph_algorithms dwga = new DWGraph_Algo();
        dwga.init(dwg1);
        dwga.save("file");

    }
    @Test
    void load(){
        dwg1= wgSpecific(dwg1);
        dwga1.init(dwg1);
        dwga1.save("C:\\Users\\abhau\\IdeaProjects\\Directional_Weighted_Graph\\file.json");
        dwga2.load("C:\\Users\\abhau\\IdeaProjects\\Directional_Weighted_Graph\\file.json");
        DWGraph_Algo temp = (DWGraph_Algo)dwga1;
        boolean b = temp.equal(dwga2);
        assertTrue(b);
    }

}
