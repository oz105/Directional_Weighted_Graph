package temp;

/**
 * This class represents a 3D point in space.
 */

public class Point3D
{
    private double _x,_y,_z;
    public Point3D(double x, double y, double z)
    {
        _x=x;
        _y=y;
        _z=z;
    }

    public Point3D(Point3D p) {
        this(p.x(), p.y(), p.z());
    }
    public Point3D(double x, double y) {this(x,y,0);}

    public double x() {return _x;}

    public double y() {return _y;}

    public double z() {return _z;}


    public String toString() { return _x+","+_y+","+_z; }

}

