import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class RicciFlow {

    public TriangleMesh mesh;
    public double[] metric;

    public RicciFlow(TriangleMesh m)
    {
        mesh = m;
        metric = new double[m.V];
    }

    public void updateMetric(double dT, int numIter)
    {
        for (int i = 0; i < numIter; i++)
        {
            double[] K = mesh.computeCurvature(metric);
            for (int j = 0; j < mesh.V; j++)
                metric[j] -= dT * K[j];
        }
    }

    public void readMetric(String fileName) throws IOException
    {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        StringTokenizer st = new StringTokenizer(br.readLine());
        System.out.println("Reading in metric from file");
        int V = Integer.parseInt(st.nextToken());
        metric = new double[V];
        for (int i = 0; i < V; i++)
        {
            st = new StringTokenizer(br.readLine());
            metric[i] = Double.parseDouble(st.nextToken());
        }
        System.out.println("Finished reading metric");
    }

    public static void main(String[] args) throws IOException
    {
        TriangleMesh m = new TriangleMesh("kitten.off");
        RicciFlow r = new RicciFlow(m);
        System.out.println("Starting Ricci Flow");
        r.updateMetric(0.33, 15000);
        System.out.println("Completed Ricci Flow");
        double[] K = r.mesh.computeCurvature(r.metric);
        for (double d : r.metric)
            System.out.println(d);
    }
}
