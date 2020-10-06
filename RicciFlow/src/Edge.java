public class Edge {

    public Vertex v1, v2;

    public Edge(Vertex one, Vertex two)
    {
        v1 = one;
        v2 = two;
    }

    @Override
    public boolean equals(Object o)
    {
        Edge e = (Edge) o;
        if (v1.index == e.v1.index && v2.index == e.v2.index)
            return true;
        return v2.index == e.v1.index && v1.index == e.v2.index;
    }
}
