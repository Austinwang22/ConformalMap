import java.util.*;
import java.io.*;

public class Embed {

    private static final int WHITE = 0, GRAY = 1, BLACK = 2;

    public GeometricObject g;
    public RicciFlow r;
    public double[][] embeddedVertices;

    public Embed(GeometricObject g)
    {
        this.g = g;
        r = new RicciFlow(g);
        embeddedVertices = new double[g.numVertices][2];
    }

    public Embed(GeometricObject g, RicciFlow r)
    {
        this.g = g;
        this.r = r;
        embeddedVertices = new double[g.numVertices][2];
    }

    public void bfs()
    {
        LinkedList<Integer> q = new LinkedList<>();
        Triangle[] triangles = g.triangles;
        ArrayList<Integer>[] adj = g.adjacentTriangles;
        int[] colors = new int[g.numFaces];
        colors[0] = GRAY;
        q.push(0);
        embedTriangle(triangles[0], null);
        int count = 0;
        while (q.size() != 0)
        {
            count++;
            System.out.println(count);
            int parent = q.pop();
            ArrayList<Integer> children = adj[parent];
            for (int child : children)
            {
                if (colors[child] == WHITE)
                {
                    colors[child] = GRAY;
                    embedTriangle(triangles[child], triangles[parent]);
                    q.add(child);
                }
            }
            colors[parent] = BLACK;
        }
    }

    public void embedTriangle(Triangle t, Triangle parent)
    {
        if (parent == null)
        {
            embeddedVertices[t.v1] = new double[]{0,0};
            double L12 = Math.exp(r.metric[t.v1]) + Math.exp(r.metric[t.v2]);
            double L13 = Math.exp(r.metric[t.v1]) + Math.exp(r.metric[t.v3]);
            double L23 = Math.exp(r.metric[t.v2]) + Math.exp(r.metric[t.v3]);
            embeddedVertices[t.v2] = new double[]{L12,0};
            double angle1 =
                    Math.acos((Math.pow(L12, 2) + Math.pow(L13,2) - Math.pow(L23, 2))/(2 * L12 * L13));
            embeddedVertices[t.v3] = new double[]{L13 * Math.cos(angle1), L13 * Math.sin(L13)};
            return;
        }
        Edge common = Triangle.getCommonEdge(t, parent);
        int v3 = Triangle.getOtherVertex(common, t);
        int v1 = common.index1;
        int v2 = common.index2;
        int v0 = Triangle.getOtherVertex(common, parent);
        Triangle t0 = new Triangle(g.vertices[v0], g.vertices[v1], g.vertices[v2]);
        boolean isClockwise = t0.isClockwise();
        double L13 = Math.exp(r.metric[v1]) + Math.exp(r.metric[v3]);
        double L23 = Math.exp(r.metric[v2]) + Math.exp(r.metric[v3]);
        Circle c1 = new Circle(L13, v1, g.vertices[v1]);
        Circle c2 = new Circle(L23, v2, g.vertices[v2]);
        double[][] intersections = Circle.getIntersections(c1,c2);
        Triangle t1 = new Triangle(g.vertices[v1], g.vertices[v2], intersections[0]);
        Triangle t2 = new Triangle(g.vertices[v1], g.vertices[v2], intersections[1]);
        double[] newPoint;
        if (isClockwise && !t1.isClockwise())
            newPoint = intersections[0];
        else
            newPoint = intersections[1];
        embeddedVertices[v3] = newPoint;
    }

    public void writeFile() throws IOException
    {
        PrintWriter pw = new PrintWriter(new FileWriter("embedded.out"));
        for (double[] d : embeddedVertices)
            pw.println(d[0] + " " + d[1]);
        pw.close();
    }

    public static void main(String[] args) throws IOException
    {
        GeometricObject g = new GeometricObject("kittenopen.in");
        RicciFlow r = new RicciFlow("kittenopenmetric.in");
        Embed e = new Embed(g, r);
        e.bfs();
        e.writeFile();
    }
}
