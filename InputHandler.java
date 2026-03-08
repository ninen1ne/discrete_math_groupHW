import java.util.*;

public class InputHandler {
    private Scanner scanner;

    public InputHandler() {
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        Graph graph = createGraphFromInput();
        if (graph == null) return;

        System.out.println("\n=== Minimum Spanning Tree (MST) Application ===");
        System.out.println("1. Kruskal's Algorithm");
        System.out.println("2. Prim's Algorithm");
        System.out.print("Select an option (1 or 2): ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); 

        SpanningTreeStrategy strategy = null;
        String startPoint = "";

        if (choice == 1) {
            strategy = new KruskalAlgorithm();
        } else if (choice == 2) {
            strategy = new PrimAlgorithm();
            System.out.print("Enter starting point (e.g., V1): ");
            startPoint = scanner.nextLine().trim();
        } else {
            System.out.println("Invalid choice! Exiting program.");
            return;
        }

        List<Edge> resultTree = strategy.generateTree(graph, startPoint);

        System.out.println("\n--- Resulting Minimum Spanning Tree ---");
        resultTree.forEach(System.out::println);
    }

    private Graph createGraphFromInput() {
        System.out.print("Enter number of vertices: ");
        int numVertices = scanner.nextInt();
        scanner.nextLine(); 

        Set<String> vertices = new HashSet<>();
        System.out.println("Enter " + numVertices + " vertex names separated by space (e.g., V1 V2 V3):");
        String[] vNames = scanner.nextLine().split("\\s+");
        vertices.addAll(Arrays.asList(vNames));

        System.out.print("Enter number of edges: ");
        int numEdges = scanner.nextInt();
        scanner.nextLine(); 

        Set<String> edges = new HashSet<>();
        Graph graph = new Graph(vertices, edges);

        System.out.println("Enter edges in format: [EdgeID] [Source] [Destination] [Weight]");
        System.out.println("Example: E1 V1 V2 5");
        
        for (int i = 0; i < numEdges; i++) {
            System.out.print("Edge " + (i + 1) + ": ");
            String edgeId = scanner.next();
            String src = scanner.next();
            String dest = scanner.next();
            int weight = scanner.nextInt();
            
            edges.add(edgeId); 
            
            graph.defineEdge(edgeId, src, dest, weight); 
        }

        return graph;
    }
}