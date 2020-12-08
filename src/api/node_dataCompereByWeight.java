package api;

import java.util.Comparator;

public class node_dataCompereByWeight implements Comparator<node_data> {

    @Override
    public int compare(node_data node1, node_data node2) {
            return Double.compare(node1.getWeight(),node2.getWeight()) ;
    }
}
