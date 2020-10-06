import java.util.*;
import java.io.*;

public class Embed {

    public TriangleMesh mesh;
    public RicciFlow r;
    public Vertex[] embeddedVertices;

    public Embed(TriangleMesh m, RicciFlow r)
    {
        mesh = m;
        this.r = r;
        embeddedVertices = new Vertex[m.V];
    }

    public void bfs()
    {
        LinkedList<Triangle> queue = new LinkedList<>();
        Triangle[] faces = mesh.faces;
        System.out.println("Computing adjacency list");
        ArrayList<Triangle>[] adj = mesh.getAdjacencyList();
        System.out.println("Finished computing adjacency list");
        boolean[] visited = new boolean[mesh.F];
        visited[0] = true;
        queue.push(faces[0]);
        embed(faces[0], null);
        while (!queue.isEmpty())
        {
            Triangle parent = queue.pop();
            ArrayList<Triangle> children = adj[parent.index];
            for (Triangle child : children)
            {
                if (!visited[child.index])
                {
                    visited[child.index] = true;
                    queue.add(child);
                    embed(child, parent);
                }
            }
            visited[parent.index] = true;
        }
    }

    public void embed(Triangle t, Triangle parent)
    {
        if (parent == null)
        {
            System.out.println("Embedding first triangle");
            embeddedVertices[t.v1.index] = new Vertex(t.v1.index, 0,0,0);
            double l12 = Math.exp(r.metric[t.v1.index]) + Math.exp(r.metric[t.v2.index]);
            double l13 = Math.exp(r.metric[t.v1.index]) + Math.exp(r.metric[t.v3.index]);
            double l23 = Math.exp(r.metric[t.v2.index]) + Math.exp(r.metric[t.v3.index]);
            double angle1 =
                    Math.acos((Math.pow(l12, 2) + Math.pow(l13,2) - Math.pow(l23, 2))/(2 * l12 * l13));
            embeddedVertices[t.v2.index] =
                    new Vertex(t.v2.index, l12, 0, 0);
            embeddedVertices[t.v3.index] =
                    new Vertex(t.v3.index,l13*Math.cos(angle1),l13*Math.sin(angle1),0);
            System.out.println("Finished embedding first triangle");
            return;
        }
        Edge common = t.getCommonEdge(parent);
        Vertex unknown3 = t.getOtherVertex(common);
        Vertex known1 = embeddedVertices[common.v1.index];
        Vertex known2 = embeddedVertices[common.v2.index];
        //System.out.println("Both vertices are: " + known1.index + " " + known2.index);
//        System.out.println("unknown3 is null: " + (unknown3==null));
//        System.out.println("known1 is null: " + (known1==null));
//        System.out.println("known2 is null: " + (known2==null));
        double l13 = Math.exp(r.metric[known1.index]) + Math.exp(r.metric[unknown3.index]);
        double l23 = Math.exp(r.metric[known2.index]) + Math.exp(r.metric[unknown3.index]);
        Circle c1 = new Circle(known1, l13);
        Circle c2 = new Circle(known2, l23);
        Vertex[] intersections = Circle.intersections(c1,c2, r.metric);
        Triangle t1 = new Triangle(-1, known1, known2, intersections[0]);
        boolean parentClockwise = parent.isClockwise();
        Vertex newPoint;
        if (parentClockwise && !t1.isClockwise())
            newPoint = intersections[0];
        else
            newPoint = intersections[1];
        newPoint.index = unknown3.index;
        embeddedVertices[unknown3.index] = newPoint;
    }

    public void writeFile(String fileName) throws IOException
    {
        PrintWriter pw = new PrintWriter(new FileWriter(fileName));
        pw.println("OFF");
        pw.println(mesh.V + " " + mesh.F + " " + mesh.E);
        for (Vertex v : embeddedVertices)
            pw.println(v.x + " " + v.y + " " + v.z);
        for (Triangle t : mesh.faces)
            pw.println("3 " + t.v1.index + " " + t.v2.index + " " + t.v3.index);
        pw.close();
    }

    public static void main(String[] args) throws IOException
    {
        TriangleMesh m = new TriangleMesh("kittenopen.off");
        RicciFlow r = new RicciFlow(m);
        r.readMetric("kittenopenu.in");
        Embed e = new Embed(m, r);
        e.bfs();
        e.writeFile("kittenEmbedded.off");
    }
}
