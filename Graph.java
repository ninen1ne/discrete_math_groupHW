import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;

public class Graph {
    private Set<String> v;
    private Set<String> e;
    private Map<String, Set<String>> eEndpoint;
    private Map<String, Integer> eWeight;

    public Graph(Set<String> v, Set<String> e) {
        this.v = v;
        this.e = e;
        this.eEndpoint = new HashMap<>();
        this.eWeight = new HashMap<>();
    }

    public Set<String> getVertices() { return v; }
    public void setVertices(Set<String> v) { this.v = v; }
    
    public Set<String> getEdgeIds() { return e; }
    public void setEdgeIds(Set<String> e) { this.e = e; }
    
    public Map<String, Set<String>> getEdgeEndpoints() { return eEndpoint; }
    public void setEdgeEndpoints(Map<String, Set<String>> eEndpoint) { this.eEndpoint = eEndpoint; }

    public Integer getEdgeWeight(String edgeId) { return eWeight.getOrDefault(edgeId, 0); }

    public void defineEdge(String edgeId, String v1, String v2, int weight) {
        if (!e.contains(edgeId)) return;
        Set<String> endpoints = new HashSet<>();
        endpoints.add(v1);
        endpoints.add(v2);
        eEndpoint.put(edgeId, endpoints);
        eWeight.put(edgeId, weight);
    }

    public boolean isValidConnection(String vStart, String vEnd, String edgeId) {
        if (!eEndpoint.containsKey(edgeId)) return false;
        Set<String> points = eEndpoint.get(edgeId);
        return points.contains(vStart) && points.contains(vEnd);
    }
}