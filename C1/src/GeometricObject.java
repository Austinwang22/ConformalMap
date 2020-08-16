import java.util.*;
import java.io.*;

public class GeometricObject {

    public int numVertices, numFaces;
    public double[][] vertices; //all vertices
    public Triangle[] triangles; //all triangles
    public ArrayList<Integer>[] adjacentTriangles;
    public ArrayList<Integer>[] faces; //all indices of triangles containing vertex v at faces[v]
    public ArrayList<Edge>[] edges; //all edges containing vertex v at edges[v]

    public GeometricObject(String fileName) throws IOException
    {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        StringTokenizer st = new StringTokenizer(br.readLine());
        numVertices = Integer.parseInt(st.nextToken());
        numFaces = Integer.parseInt(st.nextToken());
        vertices = new double[numVertices][3];
        faces = new ArrayList[numVertices];
        edges = new ArrayList[numVertices];
        triangles = new Triangle[numFaces];
        adjacentTriangles = new ArrayList[numFaces];
        for (int i = 0; i < numVertices; i++)
        {
            edges[i] = new ArrayList<>();
            faces[i] = new ArrayList<>();
        }
        for (int i = 0; i < numFaces; i++)
            adjacentTriangles[i] = new ArrayList<>();
        for (int i = 0; i < numVertices; i++)
        {
            st = new StringTokenizer(br.readLine());
            double x = Double.parseDouble(st.nextToken());
            double y = Double.parseDouble(st.nextToken());
            double z = Double.parseDouble(st.nextToken());
            vertices[i] = new double[]{x,y,z};
        }
        for (int i = 0; i < numFaces; i++)
        {
            st = new StringTokenizer(br.readLine());
            int numPoints = Integer.parseInt(st.nextToken());
            int[] indices = new int[3];
            for (int j = 0; j < 3; j++)
            {
                int index = Integer.parseInt(st.nextToken());
                indices[j] = index;
            }
            Triangle face = new Triangle(indices[0],
                                         indices[1],
                                         indices[2]);
            triangles[i] = face;
            for (int index : indices)
            {
                faces[index].add(i);
                for (int other : indices)
                {
                    if (index != other)
                    {
                        Edge e = new Edge(index, other);
                        if (!edges[index].contains(e))
                            edges[index].add(e);
                    }
                }
            }
        }
        getAdjacentTriangles();
    }

    private void getAdjacentTriangles()
    {
        for (int i = 0; i < numFaces; i++)
        {
            Triangle t1 = triangles[i];
            for (int j = 0; j < numFaces; j++)
            {
                Triangle t2 = triangles[j];
                if (Triangle.areAdjacent(t1, t2))
                {
                    if (!adjacentTriangles[i].contains(j))
                        adjacentTriangles[i].add(j);
                    if (!adjacentTriangles[j].contains(i))
                        adjacentTriangles[j].add(i);
                }
            }
        }
    }

    public double[] computeCurvature(double[] metric)
    {
        double[] curvatures = new double[numVertices];
        Arrays.fill(curvatures, 2 * Math.PI);
        for (int i = 0; i < numFaces; i++)
        {
            Triangle t = triangles[i];
            int v1 = t.v1;
            int v2 = t.v2;
            int v3 = t.v3;
            double len12 = Math.exp(metric[v1]) + Math.exp(metric[v2]);
            double len23 = Math.exp(metric[v2]) + Math.exp(metric[v3]);
            double len31 = Math.exp(metric[v3]) + Math.exp(metric[v1]);
            double angle1 =
                    Math.acos((Math.pow(len12, 2) + Math.pow(len31,2) - Math.pow(len23, 2))/(2 * len12 * len31));
            double angle2 =
                    Math.acos((Math.pow(len12, 2) + Math.pow(len23,2) - Math.pow(len31, 2))/(2 * len12 * len23));
            double angle3 =
                    Math.acos((Math.pow(len23, 2) + Math.pow(len31,2) - Math.pow(len12, 2))/(2 * len23 * len31));
            curvatures[v1] -= angle1;
            curvatures[v2] -= angle2;
            curvatures[v3] -= angle3;
        }
        return curvatures;
    }
}
