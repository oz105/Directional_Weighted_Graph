//import api.geo_location;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class NodeDataTest {
//    static int seed = 31;
//    static int v_size = 10;
//    static int e_size = v_size * 3;
//    static Random _rnd = new Random(seed);
//
//    @Test
//    public int getKey() {
//        return this.key;
//    }
//
//    @Test
//    public geo_location getLocation() {
//        return  p ;
//    }
//
//    @Test
//    public void setLocation(geo_location p) {
//        this.p = p ;
//    }
//
//    @Test
//    public double getWeight() {
//        return this.weight;
//    }
//
//    @Test
//    public void setWeight(double w) {
//        this.weight = w;
//    }
//
//    @Test
//    public String getInfo() {
//        return this.info;
//    }
//
//    @Test
//    public void setInfo(String s) {
//        this.info = s;
//    }
//
//    @Test
//    public int getTag() {
//        return this.tag;
//    }
//    @Test
//    public void setTag(int t) {
//        this.tag = t;
//    }
//
//    @Test
//    public double x() {
//        return this.getLocation().x();
//    }
//
//    @Test
//    public double y() {
//        return this.getLocation().y();
//    }
//
//    @Test
//    public double z() {
//        return this.getLocation().z();
//    }
//
//    @Test
//    void double distance(geo_location g) {
//
//    }
//    public static int nextRnd(int min, int max) {
//        double v = nextRnd(0.0+min, (double)max);
//        int ans = (int)v;
//        return ans;
//    }
//    public static double nextRnd(double min, double max) {
//        double d = _rnd.nextDouble();
//        double dx = max-min;
//        double ans = d*dx+min;
//        return ans;
//    }
//}
