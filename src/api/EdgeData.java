package api;

import java.util.Objects;


/**
 * Class that represents Edges between Vertex in the directed weighted graph .
 * there is COPY CONSTRUCTOR
 * and empty CONSTRUCTOR
 */

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
     * @return The id of the source node of this edge.
     */
    @Override
    public int getSrc() {
        return this.src;
    }

    /**
     * @return The id of the destination node of this edge .
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
     * @return the remark (meta data) associated with this edge.
     */
    @Override
    public String getInfo() {
        return this.info;
    }

    /**
     * Allows changing the remark (meta data) associated with this edge.
     * @param s
     */
    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    /**
     * @return Temporal data (aka color: white,black..)
     * which can be used be algorithms
     */
    @Override
    public int getTag() {
        return this.tag;
    }

    /**
     * This method allows setting the "tag" value for temporal marking an edge - common
     * practice for marking by algorithms.
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        this.tag = t;
    }

    /**
     * This method check if both edge data
     * equals , return true only if they are equals
     * false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EdgeData edgeData = (EdgeData) o;
        return src == edgeData.src &&
                dest == edgeData.dest &&
                Double.compare(edgeData.weight, weight) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(src, dest, weight);
    }

    @Override
    public String toString() {
        return "EdgeData{" +
                "src=" + src +
                ", dest=" + dest +
                ", weight=" + weight +
                '}';
    }
}
