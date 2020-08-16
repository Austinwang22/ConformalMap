import java.util.*;
import java.io.*;

public class RicciFlow {

    public GeometricObject object;
    public double[] metric;

    public RicciFlow(GeometricObject g)
    {
        object = g;
        metric = new double[g.numVertices];
    }

    public RicciFlow(String fileName) throws IOException
    {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        StringTokenizer st;
        metric = new double[10358];
        for (int i = 0; i < 10358; i++)
        {
            st = new StringTokenizer(br.readLine());
            metric[i] = Double.parseDouble(st.nextToken());
        }
    }

    public void updateMetric(double dT, int iterations)
    {
        double max = Double.MIN_VALUE;
        for (int i = 0; i < iterations; i++)
        {
            max = Double.MIN_VALUE;
            double[] curvature = object.computeCurvature(metric);
            for (double d : curvature)
                max = Math.max(max, d);
            System.out.println(max);
            for (int j = 0; j < object.numVertices; j++)
                metric[j] -= dT * curvature[j];
        }
    }

    public static void main(String[] args) throws IOException
    {
        GeometricObject kitten = new GeometricObject("kitten.in");
        RicciFlow r = new RicciFlow(kitten);
        r.updateMetric(0.33, 15000);
        for (double d : r.metric)
            System.out.println(d);
    }
}
