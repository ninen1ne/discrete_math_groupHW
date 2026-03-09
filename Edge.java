
public class Edge implements Comparable<Edge> {

    String edgeId;
    String u, v;
    int weight;

    public Edge(String edgeId, String u, String v, int weight) {
        this.edgeId = edgeId;
        this.u = u;
        this.v = v;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge other) {
        return this.weight - other.weight; // gay
    }

    @Override
    public String toString() {
        return "Edge: " + edgeId + " (" + u + "-" + v + ") | Weight: " + weight;
    }
}
