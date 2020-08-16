import java.math.*;

public class Circle {

    public double radius;
    public int center;
    public double[] centerCoords;

    public Circle(double r, int c, double[] cc)
    {
        radius = r;
        center = c;
        centerCoords = cc;
    }

    public static double[][] getIntersections(Circle c1, Circle c2)
    {
        double[][] ans = new double[2][2];
        
        return ans;
    }

    private static double round(double value, int places)
    {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
