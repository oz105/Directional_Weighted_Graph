package api;

public class NodeData implements node_data , geo_location {
    private int key;
    private int tag;
    private String info;
    private double weight;
    private geo_location p ;

    //CONSTRUCTOR
    public NodeData(int key) {
        this.key = key;
        this.tag = -1;
        this.info = "";
        this.weight = 0.0;

    }
    //COPY CONSTRUCTOR
    public NodeData(node_data n) {
        this.key = n.getKey();
        this.tag = n.getTag();
        this.info = n.getInfo();
        this.weight = n.getWeight() ;

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
     *
     * @return
     */
    @Override
    public geo_location getLocation() {
        return  p ;
    }

    /**
     * Allows changing this node's location.
     *
     * @param p - new new location  (position) of this node.
     */
    @Override
    public void setLocation(geo_location p) {
        this.p = p ;
    }

    /**
     * Returns the weight associated with this node.
     *
     * @return
     */
    @Override
    public double getWeight() {
        return this.weight;
    }

    /**
     * Allows changing this node's weight.
     *
     * @param w - the new weight
     */
    @Override
    public void setWeight(double w) {
        this.weight = w;
    }

    /**
     * Returns the remark (meta data) associated with this node.
     *
     * @return
     */
    @Override
    public String getInfo() {
        return this.info;
    }

    /**
     * Allows changing the remark (meta data) associated with this node.
     *
     * @param s
     */
    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    /**
     * Temporal data (aka color: e,g, white, gray, black)
     * which can be used be algorithms
     *
     * @return
     */
    @Override
    public int getTag() {
        return this.tag;
    }

    /**
     * Allows setting the "tag" value for temporal marking an node - common
     * practice for marking by algorithms.
     *
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        this.tag = t;
    }

    @Override
    public double x() {
        return this.getLocation().x();
    }

    @Override
    public double y() {
        return this.getLocation().y();
    }

    @Override
    public double z() {
        return this.getLocation().z();
    }

    @Override
    public double distance(geo_location g) {
        double res = 0.0 ;
        double twoX = (this.p.x() - g.x())*(this.p.x() - g.x()) ;
        double twoY = (this.p.y() - g.y())*(this.p.y() - g.y()) ;
        double twoZ = (this.p.z() - g.z())*(this.p.z() - g.z()) ;
        res = twoX + twoY + twoZ ;
        return Math.sqrt(res) ;
    }
}

