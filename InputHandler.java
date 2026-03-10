import java.util.*;                                                                          // นำเข้าคลาสทั้งหมดในแพ็กเกจ java.util (เช่น Scanner, List, Set, Arrays)

public class InputHandler {                                                                  // ประกาศคลาส InputHandler สำหรับจัดการการรับข้อมูลจากผู้ใช้งานผ่านคอนโซล
    private Scanner scanner;                                                                 // ประกาศตัวแปร scanner สำหรับอ่านค่าที่ผู้ใช้พิมพ์เข้ามาทางคีย์บอร์ด

    public InputHandler() {                                                                  // Constructor สำหรับกำหนดค่าเริ่มต้นเมื่อสร้างออบเจ็กต์ InputHandler
        this.scanner = new Scanner(System.in);                                               // สร้างออบเจ็กต์ Scanner โดยให้ผูกกับ System.in (รับค่าจากคีย์บอร์ดมาตรฐาน)
    }                                                                                        // ปิด Constructor

    public void start() {                                                                    // เมธอดหลักของคลาสนี้ ทำหน้าที่ควบคุมลำดับการทำงานของโปรแกรมส่วนกราฟ
        Graph graph = createGraphFromInput();                                                // เรียกใช้เมธอดสร้างกราฟจากการพิมพ์ป้อนข้อมูล และเก็บผลลัพธ์ไว้ในตัวแปร graph
        if (graph == null) return;                                                           // ตรวจสอบว่าสร้างกราฟสำเร็จหรือไม่ หากเกิดข้อผิดพลาด (เป็น null) ให้จบการทำงานทันที

        System.out.println("\n=== Minimum Spanning Tree (MST) Application ===");             // พิมพ์ข้อความแสดงชื่อแอปพลิเคชัน (การหาต้นไม้แผ่กิ่งก้านที่น้อยที่สุด)
        System.out.println("1. Kruskal's Algorithm");                                        // พิมพ์ตัวเลือกที่ 1: ใช้อัลกอริทึมของครัสกาล (Kruskal)
        System.out.println("2. Prim's Algorithm");                                           // พิมพ์ตัวเลือกที่ 2: ใช้อัลกอริทึมของพริม (Prim)
        System.out.print("Select an option (1 or 2): ");                                     // พิมพ์ข้อความให้ผู้ใช้เลือกตัวเลือก (พิมพ์ต่อท้ายไม่ขึ้นบรรทัดใหม่)
        
        int choice = scanner.nextInt();                                                      // รับค่าตัวเลขจำนวนเต็มที่ผู้ใช้เลือก (1 หรือ 2) มาเก็บในตัวแปร choice
        scanner.nextLine();                                                                  // ดักจับและเคลียร์อักขระขึ้นบรรทัดใหม่ (ปุ่ม Enter) ที่ค้างอยู่ในระบบของ Scanner

        SpanningTreeStrategy strategy = null;                                                // ประกาศตัวแปรอ้างอิง interface SpanningTreeStrategy เพื่อเตรียมเก็บอัลกอริทึม (Strategy Pattern)
        String startPoint = "";                                                              // ประกาศตัวแปรสำหรับเก็บชื่อจุดยอดเริ่มต้น (จำเป็นต้องใช้สำหรับ Prim's Algorithm)

        if (choice == 1) {                                                                   // ตรวจสอบเงื่อนไข: หากผู้ใช้พิมพ์เลือกตัวเลือกที่ 1
            strategy = new KruskalAlgorithm();                                               // สร้างออบเจ็กต์ของคลาส KruskalAlgorithm และผูกเข้ากับตัวแปร strategy
        } else if (choice == 2) {                                                            // ตรวจสอบเงื่อนไข: หากผู้ใช้พิมพ์เลือกตัวเลือกที่ 2
            strategy = new PrimAlgorithm();                                                  // สร้างออบเจ็กต์ของคลาส PrimAlgorithm และผูกเข้ากับตัวแปร strategy
            System.out.print("Enter starting point (e.g., V1): ");                           // พิมพ์ถามจุดยอดเริ่มต้น เพราะอัลกอริทึมของพริมต้องระบุจุดเริ่มเดินเสมอ
            startPoint = scanner.nextLine().trim();                                          // รับข้อความชื่อจุดยอดจากผู้ใช้ พร้อมกับใช้ trim() ตัดช่องว่างหัวท้ายออก
        } else {                                                                             // กรณีผู้ใช้พิมพ์ตัวเลขอื่นๆ ที่ไม่ใช่ 1 หรือ 2
            System.out.println("Invalid choice! Exiting program.");                          // พิมพ์แจ้งเตือนว่าตัวเลือกไม่ถูกต้อง และกำลังจะออกจากโปรแกรม
            return;                                                                          // หยุดและออกจากการทำงานของเมธอดนี้ทันที
        }                                                                                    // ปิดบล็อกเงื่อนไขตรวจสอบตัวเลือก

        List<Edge> resultTree = strategy.generateTree(graph, startPoint);                    // เรียกเมธอดหา MST ตามอัลกอริทึมที่ถูกเลือก ส่งกราฟและจุดเริ่มไปประมวลผล แล้วเก็บผลลัพธ์เป็น List

        System.out.println("\n--- Resulting Minimum Spanning Tree ---");                     // พิมพ์ข้อความหัวข้อเตรียมแสดงผลลัพธ์ของเส้นเชื่อมที่ได้จากการคำนวณ
        resultTree.forEach(System.out::println);                                             // วนลูปดึงเส้นเชื่อมออกมาสั่ง print ทีละบรรทัด (ใช้ Method Reference เพื่อความสั้นกระชับ)
    }                                                                                        // ปิดเมธอด start

    private Graph createGraphFromInput() {                                                   // เมธอดตัวช่วย (Private) สำหรับรับข้อมูลจุดยอดและเส้นเชื่อม เพื่อนำไปประกอบเป็นออบเจ็กต์กราฟ
        System.out.print("Enter number of vertices: ");                                      // พิมพ์ข้อความถามจำนวนจุดยอดทั้งหมดในกราฟ
        int numVertices = scanner.nextInt();                                                 // รับค่าตัวเลขจำนวนจุดยอดมาเก็บไว้
        scanner.nextLine();                                                                  // เคลียร์ปุ่ม Enter ที่ตกค้างอยู่ในระบบหลังจากรับค่าตัวเลข

        Set<String> vertices = new HashSet<>();                                              // สร้าง Set สำหรับเก็บชื่อจุดยอดทั้งหมด (ใช้ Set เพื่อป้องกันชื่อซ้ำกัน)
        System.out.println("Enter " + numVertices + " vertex names separated by space (e.g., V1 V2 V3):"); // อธิบายให้ผู้ใช้พิมพ์ชื่อจุดยอดทั้งหมด โดยเว้นวรรคคั่น
        String[] vNames = scanner.nextLine().split("\\s+");                                  // รับข้อความยาวๆ มา แล้วตัดแบ่งด้วยช่องว่าง (Space) ออกเป็นอาร์เรย์ของชื่อจุดยอด
        vertices.addAll(Arrays.asList(vNames));                                              // แปลงอาร์เรย์เป็น List ชั่วคราว และโอนข้อมูลทั้งหมดไปใส่ใน Set ของจุดยอดรวดเดียว

        System.out.print("Enter number of edges: ");                                         // พิมพ์ข้อความถามจำนวนเส้นเชื่อมทั้งหมดในกราฟ
        int numEdges = scanner.nextInt();                                                    // รับค่าตัวเลขจำนวนเส้นเชื่อมมาเก็บไว้
        scanner.nextLine();                                                                  // เคลียร์ปุ่ม Enter ที่ตกค้างอยู่ในระบบอีกครั้ง

        Set<String> edges = new HashSet<>();                                                 // สร้าง Set สำหรับเตรียมเก็บรหัสของเส้นเชื่อม
        Graph graph = new Graph(vertices, edges);                                            // สร้างออบเจ็กต์ Graph ตัวใหม่ โดยป้อนจุดยอดที่ได้มา และเซ็ตของเส้นเชื่อม(ที่ตอนนี้ยังว่างอยู่)เข้าไป

        System.out.println("Enter edges in format: [EdgeID] [Source] [Destination] [Weight]"); // อธิบายรูปแบบการป้อนข้อมูลเส้นเชื่อมให้ผู้ใช้ทราบ
        System.out.println("Example: E1 V1 V2 5");                                           // ยกตัวอย่างการป้อนข้อมูล (รหัส จุดต้นทาง จุดปลายทาง น้ำหนัก)
        
        for (int i = 0; i < numEdges; i++) {                                                 // วนลูปเพื่อรับข้อมูลเส้นเชื่อมทีละเส้น ตามจำนวนที่ผู้ใช้ระบุไว้ก่อนหน้า
            System.out.print("Edge " + (i + 1) + ": ");                                      // พิมพ์บอกว่ากำลังรับข้อมูลของเส้นเชื่อมลำดับที่เท่าไหร่
            String edgeId = scanner.next();                                                  // รับคำแรกเป็น รหัสเส้นเชื่อม (Edge ID)
            String src = scanner.next();                                                     // รับคำที่สองเป็น ชื่อจุดต้นทาง (Source)
            String dest = scanner.next();                                                    // รับคำที่สามเป็น ชื่อจุดปลายทาง (Destination)
            int weight = scanner.nextInt();                                                  // รับคำที่สี่เป็น ตัวเลขค่าน้ำหนัก (Weight) ของเส้นเชื่อมนี้
            
            edges.add(edgeId);                                                               // นำรหัสเส้นเชื่อมที่รับมา แอดเข้าไปในเซ็ตของรหัสเส้นเชื่อมหลัก
            
            graph.defineEdge(edgeId, src, dest, weight);                                     // นำข้อมูลของเส้นเชื่อมทั้งหมดที่รับมา ไปลงทะเบียนสร้างความสัมพันธ์ในออบเจ็กต์กราฟ
        }                                                                                    // ปิดลูปรับข้อมูลเส้นเชื่อม

        return graph;                                                                        // คืนค่าออบเจ็กต์กราฟที่ถูกเติมข้อมูลจนสมบูรณ์แล้ว กลับไปให้เมธอด start ใช้งานต่อ
    }                                                                                        // ปิดเมธอด createGraphFromInput
}                                                                                            // ปิดคลาส InputHandler
