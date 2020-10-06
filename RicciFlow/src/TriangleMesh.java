import java.util.*;
import java.io.*;

public class TriangleMesh {

    public int V, F, E;
    public Vertex[] vertices;
    public Triangle[] faces;
    public ArrayList<Triangle>[] adj;

    public TriangleMesh(String fileName) throws IOException
    {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        StringTokenizer st = new StringTokenizer(br.readLine());
        if (!st.nextToken().equals("OFF"))
            return;
        st = new StringTokenizer(br.readLine());
        V = Integer.parseInt(st.nextToken());
        F = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());
        vertices = new Vertex[V];
        faces = new Triangle[F];
        for (int i = 0; i < V; i++)
        {
            st = new StringTokenizer(br.readLine());
            double v1 = Double.parseDouble(st.nextToken());
            double v2 = Double.parseDouble(st.nextToken());
            double v3 = Double.parseDouble(st.nextToken());
            Vertex v = new Vertex(i, v1, v2, v3);
            vertices[i] = v;
        }
        for (int i = 0; i < F; i++)
        {
            st = new StringTokenizer(br.readLine());
            int numVertices = Integer.parseInt(st.nextToken());
            Vertex v1 = vertices[Integer.parseInt(st.nextToken())];
            Vertex v2 = vertices[Integer.parseInt(st.nextToken())];
            Vertex v3 = vertices[Integer.parseInt(st.nextToken())];
            Triangle t = new Triangle(i, v1, v2, v3);
            faces[i] = t;
        }
//        System.out.println("Computing adjacency list");
//        adj = getAdjacencyList();
//        System.out.println("Finished computing adjacency list");
    }

    public ArrayList<Triangle>[] getAdjacencyList()
    {
        ArrayList<Triangle>[] adj = new ArrayList[F];
        for (int i = 0; i < F; i++)
            adj[i] = new ArrayList<>();
        for (int i = 0; i < F; i++)
        {
            Triangle t = faces[i];
            for (int j = 0; j < F; j++)
            {
                if (t.isAdjacent(faces[j]))
                    if (!adj[i].contains(faces[j]))
                        adj[i].add(faces[j]);
            }
        }
        this.adj = adj;
        return adj;
    }

    /**
     * Computes the Gaussian curvature at each vertex based on the metric.
     * @param metric the metric to be used to compute K
     * @return K
     */
    public double[] computeCurvature(double[] metric)
    {
        double[] K = new double[V];
        Arrays.fill(K, 2 * Math.PI);
        for (int i = 0; i < F; i++)
        {
            Triangle t = faces[i];
            int v1 = t.v1.index;
            int v2 = t.v2.index;
            int v3 = t.v3.index;
            double len12 = Math.exp(metric[v1]) + Math.exp(metric[v2]);
            double len23 = Math.exp(metric[v2]) + Math.exp(metric[v3]);
            double len31 = Math.exp(metric[v3]) + Math.exp(metric[v1]);
            double angle1 =
                    Math.acos((Math.pow(len12, 2) + Math.pow(len31,2) - Math.pow(len23, 2))/(2 * len12 * len31));
            double angle2 =
                    Math.acos((Math.pow(len12, 2) + Math.pow(len23,2) - Math.pow(len31, 2))/(2 * len12 * len23));
            double angle3 =
                    Math.acos((Math.pow(len23, 2) + Math.pow(len31,2) - Math.pow(len12, 2))/(2 * len23 * len31));
            K[v1] -= angle1;
            K[v2] -= angle2;
            K[v3] -= angle3;
        }
        return K;
    }
}