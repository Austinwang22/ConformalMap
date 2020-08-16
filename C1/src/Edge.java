import java.util.*;

public class Edge {

    public int index1, index2;

    public Edge(int i1, int i2)
    {
        index1 = i1;
        index2 = i2;
    }

    @Override
    public boolean equals(Object o)
    {
        Edge e = (Edge) o;
        if (index1 == e.index1 || index2 == e.index2)
            return true;
        if (index1 == e.index2 || index2 == e.index1)
            return true;
        return false;
    }
}
