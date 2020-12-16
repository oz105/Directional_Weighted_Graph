package api;

import java.util.Comparator;

public class node_dataCompereByTag implements Comparator<node_data> {

    /**
     * That method compere between 2 nodes_data by there weight
     * if the first one bigger will return 1
     * if the second one bigger will return -1
     * if they equals will return 0.
     * @return
     */

    @Override
    public int compare(node_data node1, node_data node2) {
            return Double.compare(node1.getWeight(),node2.getWeight()) ;
    }
}
