import java.util.ArrayList;

public class Triangle {

    public int v1, v2, v3; //indices of each vertex this Triangle contains
    public double[] coords1, coords2, coords3;
    public ArrayList<Edge> edges;

    public Triangle(int one, int two, int three)
    {
        v1 = one;
        v2 = two;
        v3 = three;
        edges = new ArrayList<>();
        edges.add(new Edge(v1, v2));
        edges.add(new Edge(v1, v3));
        edges.add(new Edge(v2, v3));
    }

    public Triangle(double[] c1, double[] c2, double[] c3)
    {
        coords1 = c1;
        coords2 = c2;
        coords3 = c3;
    }

    //If clockwise, determinant < 0. If counterclockwise, determinant > 0.
    public boolean isClockwise()
    {
        double[][] matrix = new double[3][3];
        matrix[0][2] = 1; matrix[1][2] = 1; matrix[2][2] = 1;
        matrix[0][0] = coords1[0]; matrix[0][1] = coords1[1];
        matrix[1][0] = coords2[0]; matrix[1][1] = coords2[1];
        matrix[2][0] = coords3[0]; matrix[2][1] = coords3[1];
        double x = (matrix[0][0] * (matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1]));
        double y = (matrix[0][1] * (matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0]));
        double z = (matrix[0][2] * (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0]));
        double determinant = x - y + z;
        return determinant < 0;
    }

    public static boolean areAdjacent(Triangle t1, Triangle t2)
    {
        ArrayList<Integer> one = new ArrayList<>();
        one.add(t1.v1); one.add(t1.v2); one.add(t1.v3);
        ArrayList<Integer> two = new ArrayList<>();
        two.add(t2.v1); two.add(t2.v2); two.add(t2.v3);
        int count = 0;
        for (int i : one)
            if (two.contains(i))
                count++;
        return count == 2;
    }

    public static Edge getCommonEdge(Triangle t1, Triangle t2)
    {
        for (Edge e : t1.edges)
            if (t2.edges.contains(e))
                return e;
        return null;
    }

    public static int getOtherVertex(Edge common, Triangle t)
    {
        ArrayList<Integer> arr = new ArrayList<>();
        arr.add(common.index1); arr.add(common.index2);
        if (!arr.contains(t.v1))
            return t.v1;
        if (!arr.contains(t.v2))
            return t.v2;
        return t.v3;
    }

    @Override
    public boolean equals(Object o)
    {
        ArrayList<Integer> one = new ArrayList<>();
        one.add(v1); one.add(v2); one.add(v3);
        ArrayList<Integer> two = new ArrayList<>();
        Triangle t = (Triangle) o;
        two.add(t.v1); two.add(t.v2); two.add(t.v3);
        int count = 0;
        for (int i : one)
            if (two.contains(i))
                count++;
        return count == 3;
    }
}
