import java.util.ArrayList;
import java.util.HashSet;

public class Triangle {

    public int index;
    public Vertex v1, v2, v3;
    public ArrayList<Edge> edges;

    public Triangle(int i, Vertex one, Vertex two, Vertex three)
    {
        index = i;
        v1 = one;
        v2 = two;
        v3 = three;
        edges = new ArrayList<>();
        edges.add(new Edge(v1, v2));
        edges.add(new Edge(v1, v3));
        edges.add(new Edge(v2, v3));
    }

    /**
     * Determines if this Triangle is clockwise.
     * @return true if this Triangle is clockwise; otherwise,
     *         false
     */
    public boolean isClockwise()
    {
        double[][] matrix = new double[3][3];
        matrix[0][2] = 1; matrix[1][2] = 1; matrix[2][2] = 1;
        matrix[0][0] = v1.x; matrix[0][1] = v1.y;
        matrix[1][0] = v2.x; matrix[1][1] = v2.y;
        matrix[2][0] = v3.x; matrix[2][1] = v3.y;
        double x = (matrix[0][0] * (matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1]));
        double y = (matrix[0][1] * (matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0]));
        double z = (matrix[0][2] * (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0]));
        double determinant = x - y + z;
        return determinant < 0;
    }

    /**
     * Finds the common edge between this Triangle and another specified Triangle.
     * @param t the other Triangle
     * @return the common edge
     */
    public Edge getCommonEdge(Triangle t)
    {
        for (Edge e : edges)
            if (t.edges.contains(e))
                return e;
        return null;
    }

    /**
     * Given an Edge on this Triangle, finds the other Vertex on this Triangle
     * but not on the Edge.
     * @param e an Edge on this Triangle
     * @return the other Vertex
     */
    public Vertex getOtherVertex(Edge e)
    {
        HashSet<Integer> vertices = new HashSet<>();
        vertices.add(e.v1.index);
        vertices.add(e.v2.index);
        if (!vertices.contains(v1.index))
            return v1;
        if (!vertices.contains(v2.index))
            return v2;
        return v3;
    }

    /**
     * Determines if this Triangle is adjacent to another Triangle.
     * @param t the other Triangle to be checked for adjacency
     * @return true if they are adjacent; otherwise,
     *         false
     */
    public boolean isAdjacent(Triangle t)
    {
        HashSet<Integer> t1 = new HashSet<>();
        t1.add(v1.index); t1.add(v2.index); t1.add(v3.index);
        HashSet<Integer> t2 = new HashSet<>();
        t2.add(t.v1.index); t2.add(t.v2.index); t2.add(t.v3.index);
        int count = 0;
        for (int i : t1)
            if (t2.contains(i))
                count++;
        return count == 2;
    }
}