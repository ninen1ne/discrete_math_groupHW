import java.util.*;

public class PrimAlgorithm implements SpanningTreeStrategy {
    @Override
    public List<Edge> generateTree(Graph graph, String... startVertex) {
        List<Edge> result = new ArrayList<>();
        Set<String> inMST = new HashSet<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>();

        if (graph.getVertices().isEmpty()) return result;

        String start = (startVertex.length > 0 && !startVertex[0].isEmpty()) 
                        ? startVertex[0] 
                        : graph.getVertices().iterator().next();
        
        inMST.add(start);
        addEdgesToQueue(graph, start, inMST, pq);

        while (!pq.isEmpty() && result.size() < graph.getVertices().size() - 1) {
            Edge edge = pq.poll();

            String nextVertex = !inMST.contains(edge.u) ? edge.u : (!inMST.contains(edge.v) ? edge.v : null);

            if (nextVertex != null) {
                inMST.add(nextVertex);
                result.add(edge);
                addEdgesToQueue(graph, nextVertex, inMST, pq);
            }
        }
        return result;
    }

    private void addEdgesToQueue(Graph graph, String vertex, Set<String> inMST, PriorityQueue<Edge> pq) {
        for (String edgeId : graph.getEdgeIds()) {
            Set<String> endpoints = graph.getEdgeEndpoints().get(edgeId);
            if (endpoints != null && endpoints.contains(vertex)) {
                String otherVertex = "";
                for (String ep : endpoints) {
                    if (!ep.equals(vertex)) otherVertex = ep;
                }
                
                if (!inMST.contains(otherVertex) && !otherVertex.isEmpty()) {
                    pq.add(new Edge(edgeId, vertex, otherVertex, graph.getEdgeWeight(edgeId)));
                }
            }
        }
    }
}