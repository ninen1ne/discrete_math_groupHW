import java.util.List;

public interface SpanningTreeStrategy {
    List<Edge> generateTree(Graph graph, String... startVertex);
}