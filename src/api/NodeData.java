package api;
import gameClient.util.Point3D;
import java.util.Objects;



/**
 * Class that represents Vertex in the directed weighted graph .
 * there is COPY CONSTRUCTOR
 * CONSTRUCTOR by Key
 * and empty CONSTRUCTOR
 */

public class NodeData implements node_data  {
    private int key;
    private int tag;
    private String info;
    private double weight;
    private geo_location position ;
    private static int keyMaker=0;


    //CONSTRUCTOR
    public NodeData() {
        this.key = keyMaker;
        keyMaker++;
        this.tag = -1;
        this.info = "";
        this.weight = 0.0;
        position = new Point3D(0,0,0) ;
    }
    public NodeData(int key) {
        this.key = key;
        this.tag = -1;
        this.info = "";
        this.weight = 0.0;
        position = new Point3D(0,0,0) ;
    }
    //COPY CONSTRUCTOR
    public NodeData(node_data n) {
        this.key = n.getKey();
        this.tag = n.getTag();
        this.info = n.getInfo();
        this.weight = n.getWeight() ;
        this.position = n.getLocation() ;

    }


    /**
     * Returns the key (id) associated with this node.
     *
     * @return
     */
    @Override
    public int getKey() {
        return this.key;
    }

    /**
     * Returns the location of this node, if
     * none return null.
     * @return
     */
    @Override
    public geo_location getLocation() {
        return position ;
    }

    /**
     * Allows changing this node's location.
     * @param p - new location (position) of this node.
     */
    @Override
    public void setLocation(geo_location p) {
        this.position = p ;
    }
    /**
     * Returns the weight associated with this node.
     * @return
     */
    @Override
    public double getWeight() {
        return this.weight;
    }

    /**
     * Allows changing this node's weight.
     * @param w - the new weight
     */
    @Override
    public void setWeight(double w) {
        this.weight = w;
    }

    /**
     * Returns the remark (meta data) associated with this node.
     * @return
     */
    @Override
    public String getInfo() {
        return this.info;
    }

    /**
     * Allows changing the remark (meta data) associated with this node.
     * @param s
     */
    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    /**
     * Temporal data which will be used in Dijkstra algorithms
     * use for sign node if it already been visited.
     * @return
     */
    @Override
    public int getTag() {
        return this.tag;
    }

    /**
     * Allows setting the "tag" value for marking an node
     * used in Dijkstra algorithms , if tag = -1 -> means have not been visited yet.
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        this.tag = t;
    }


    /**
     * This method check if both node data
     * equals , return true only if they are equals
     * false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeData nodeData = (NodeData) o;
        return key == nodeData.key;
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }


    @Override
    public String toString() {
        return "{"+ key + '}';
    }
}

