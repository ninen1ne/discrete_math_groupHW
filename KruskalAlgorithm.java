import java.util.*;

public class KruskalAlgorithm implements SpanningTreeStrategy {
    
    class DisjointSet {
        Map<String, String> parent = new HashMap<>();
        Map<String, Integer> rank = new HashMap<>();
        
        public void makeSet(String vertex) {
            parent.put(vertex, vertex);
            rank.put(vertex, 0);
        }
        
        public String find(String vertex) {
            if (!parent.get(vertex).equals(vertex)) {
                parent.put(vertex, find(parent.get(vertex)));
            }
            return parent.get(vertex);
        }
        
        public boolean union(String v1, String v2) {
            String root1 = find(v1);
            String root2 = find(v2);
            
            if (root1.equals(root2)) return false;

            int rank1 = rank.get(root1);
            int rank2 = rank.get(root2);

            if (rank1 < rank2) {
                parent.put(root1, root2);
            } else if (rank1 > rank2) {
                parent.put(root2, root1);
            } else {
                parent.put(root2, root1);
                rank.put(root1, rank1 + 1);
            }
            return true;
        }
    }

    @Override
    public List<Edge> generateTree(Graph graph, String... startVertex) {
        List<Edge> result = new ArrayList<>();
        List<Edge> allEdges = new ArrayList<>();
        DisjointSet ds = new DisjointSet();

        for (String v : graph.getVertices()) ds.makeSet(v);

        for (String edgeId : graph.getEdgeIds()) {
            Set<String> endpoints = graph.getEdgeEndpoints().get(edgeId);
            if (endpoints != null && endpoints.size() == 2) {
                Iterator<String> it = endpoints.iterator();
                allEdges.add(new Edge(edgeId, it.next(), it.next(), graph.getEdgeWeight(edgeId)));
            }
        }

        Collections.sort(allEdges);

        for (Edge edge : allEdges) {
            if (ds.union(edge.u, edge.v)) {
                result.add(edge);
            }
            if (result.size() == graph.getVertices().size() - 1) break;
        }

        return result;
    }
}