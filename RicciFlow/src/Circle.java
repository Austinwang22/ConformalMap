public class Circle {

    public Vertex center;
    public double radius;

    public Circle(Vertex v, double r)
    {
        center = v;
        radius = r;
    }

    /**
     * Finds the intersection points of two 2D circles.
     * @param c0 the first circle
     * @param c1 the second circle
     * @return the intersection points
     */
    public static Vertex[] intersections(Circle c0, Circle c1)
    {
        Vertex[] ans = new Vertex[2];
        Vertex p0 = c0.center, p1 = c1.center;
        double r0 = c0.radius, r1 = c1.radius;
        double d = Vertex.distance(p0, p1);
        double a = (r0*r0 - r1*r1 + d*d) / (2 * d);
        double h = Math.sqrt(r0*r0 - a*a);
        double[] vector01 = Vertex.subtract(p1, p0);
        vector01 = Vertex.scaleVector(a/d, vector01);
        Vertex p2 = Vertex.addVector(p0, vector01);
        Vertex int1 = new Vertex(-1,
                p2.x + (h/d)*(p1.y - p0.y),
                p2.y - (h/d)*(p1.x - p0.x), 0);
        Vertex int2 = new Vertex(-1,
                p2.x - (h/d)*(p1.y - p0.y),
                p2.y + (h/d)*(p1.x - p0.x), 0);
        ans[0] = int1;
        ans[1] = int2;
        return ans;
    }

    public static void main(String[] args)
    {
        Circle c0 = new Circle(new Vertex(-1,0,0,0),3);
        Circle c1 = new Circle(new Vertex(-1,4,0,0),0.5);
        Vertex[] intersections = Circle.intersections(c1,c0);
        for (Vertex v : intersections)
            System.out.println(v.x + " " + v.y + " " + v.z);
    }
}
