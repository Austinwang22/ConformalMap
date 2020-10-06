public class Vertex {

    public int index;
    public double x, y, z;

    public Vertex(int i, double x, double y, double z)
    {
        index = i;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Vertex addVector(Vertex v, double[] vector)
    {
        double x = v.x + vector[0];
        double y = v.y + vector[1];
        double z = v.z + vector[2];
        return new Vertex(-1, x, y, z);
    }

    /**
     * Computes the vector found by doing v1 - v2
     * @param v1 the first Vertex
     * @param v2 the second Vertex
     * @return the vector
     */
    public static double[] subtract(Vertex v1, Vertex v2)
    {
        double[] vector = new double[3];
        vector[0] = v1.x - v2.x;
        vector[1] = v1.y - v2.y;
        vector[2] = v1.z - v2.z;
        return vector;
    }

    public static double[] scaleVector(double scalar, double[] vector)
    {
        vector[0] *= scalar;
        vector[1] *= scalar;
        vector[2] *= scalar;
        return vector;
    }

    /**
     * Computes the euclidean distance between two given Vertices.
     * @param v1 the first Vertex
     * @param v2 the second Vertex
     * @return the distance between v1 and v2
     */
    public static double distance(Vertex v1, Vertex v2)
    {
        double dX = v1.x - v2.x;
        double dY = v1.y - v2.y;
        double dZ = v1.z - v2.z;
        return Math.sqrt(dX*dX + dY*dY + dZ*dZ);
    }
}
