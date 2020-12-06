package api;

public class Main {

    public static void main(String[] args) {
        directed_weighted_graph g=new DWGraph_DS();
        for(int i=1;i< 12;i++){
            NodeData n = new NodeData() ;
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

    }

}
