package ex2;

public class EdgeData implements edge_data {
    private int src, dest, tag;
    private double weight;
    private String info;

    //CONSTRUCTOR
    public EdgeData(int src , int dest , double w) {
        this.src = src ;
        this.dest = dest ;
        this.weight = w ;
        this.tag = -1 ;
        this.info = "" ;
    }

    //COPY CONSTRUCTOR
    public EdgeData(edge_data e) {
        this.src = e.getSrc() ;
        this.dest = e.getDest() ;
        this.weight = e.getWeight() ;
        this.info = e.getInfo() ;
        this.tag =e.getTag() ;
    }

    /**
     * The id of the source node of this edge.
     *
     * @return
     */
    @Override
    public int getSrc() {
        return this.src;
    }

    /**
     * The id of the destination node of this edge
     *
     * @return
     */
    @Override
    public int getDest() {
        return this.dest;
    }

    /**
     * @return the weight of this edge (positive value).
     */
    @Override
    public double getWeight() {
        return this.weight;
    }

    /**
     * Returns the remark (meta data) associated with this edge.
     *
     * @return
     */
    @Override
    public String getInfo() {
        return this.info;
    }

    /**
     * Allows changing the remark (meta data) associated with this edge.
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
     * This method allows setting the "tag" value for temporal marking an edge - common
     * practice for marking by algorithms.
     *
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        this.tag = t;
    }
}
