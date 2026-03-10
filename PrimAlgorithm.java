import java.util.*;                                                                           // นำเข้าคลาสทั้งหมดในแพ็กเกจ java.util เช่น List, Set, ArrayList, HashSet, PriorityQueue

public class PrimAlgorithm implements SpanningTreeStrategy {                                  // ประกาศคลาส PrimAlgorithm โดย implement อินเทอร์เฟซ SpanningTreeStrategy
    @Override                                                                                 // ระบุว่าเป็นการเขียนทับเมธอดบังคับจากอินเทอร์เฟซ
    public List<Edge> generateTree(Graph graph, String... startVertex) {                      // เมธอดหลักสำหรับสร้าง MST รับข้อมูลกราฟและจุดเริ่มต้น (Prim ต้องใช้จุดเริ่ม)
        List<Edge> result = new ArrayList<>();                                                // สร้าง List สำหรับเก็บเส้นเชื่อมที่จะถูกเลือกเป็นคำตอบ MST
        Set<String> inMST = new HashSet<>();                                                  // สร้าง Set สำหรับเก็บจุดยอดที่ถูกรวมเข้าไปใน MST แล้ว (กันไม่ให้เดินวนกลับมาซ้ำ)
        PriorityQueue<Edge> pq = new PriorityQueue<>();                                       // สร้าง PriorityQueue สำหรับดึงเส้นเชื่อมที่มีน้ำหนักน้อยที่สุดออกมาพิจารณาก่อน

        if (graph.getVertices().isEmpty()) return result;                                     // ดักข้อผิดพลาด: ถ้ากราฟไม่มีจุดยอดเลย ให้คืนค่า List ว่างกลับไปทันที

        String start = (startVertex.length > 0 && !startVertex[0].isEmpty())                  // กำหนดจุดเริ่มต้น โดยเช็คว่ามีการส่งจุดเริ่มต้นมาให้หรือไม่ และต้องไม่เป็นค่าว่าง
                        ? startVertex[0]                                                      // ถ้ามีการส่งมา: ให้ใช้จุดยอดแรกที่ส่งมาเป็นจุดเริ่มต้น
                        : graph.getVertices().iterator().next();                              // ถ้าไม่มีการส่งมา: ให้ดึงจุดยอดแรกสุดที่เจอในเซ็ตของกราฟมาเป็นจุดเริ่มต้นแบบสุ่มแทน
        
        inMST.add(start);                                                                     // นำจุดเริ่มต้นใส่เข้าไปใน Set ของ MST (ทำเครื่องหมายว่าเราเริ่มเดินจากจุดนี้แล้ว)
        addEdgesToQueue(graph, start, inMST, pq);                                             // เรียกใช้เมธอดตัวช่วย เพื่อดึงเส้นเชื่อมทุกเส้นรอบๆ จุดเริ่มต้นนี้ ยัดลงไปใน PriorityQueue

        while (!pq.isEmpty() && result.size() < graph.getVertices().size() - 1) {             // วนลูปตราบใดที่คิวยังไม่ว่าง และจำนวนเส้นเชื่อมในคำตอบยังไม่ครบ (จำนวนจุดยอด - 1)
            Edge edge = pq.poll();                                                            // ดึงเส้นเชื่อมที่มีน้ำหนักน้อยที่สุดออกจากคิว (PriorityQueue จัดเรียงให้เราอัตโนมัติ)

            String nextVertex = !inMST.contains(edge.u) ? edge.u : (!inMST.contains(edge.v) ? edge.v : null); // เช็คว่าปลายทางของเส้นนี้ฝั่งไหน (u หรือ v) ที่เรายังไม่เคยไปเยือน

            if (nextVertex != null) {                                                         // ถ้าเจอจุดปลายทางที่ยังไม่เคยไปเยือน (แปลว่าเดินเส้นนี้แล้วจะไม่เกิดวงวน หรือ Cycle)
                inMST.add(nextVertex);                                                        // ทำเครื่องหมายจุดยอดใหม่นี้ใส่เข้าไปในเซ็ตของ MST ว่าไปเยือนเรียบร้อยแล้ว
                result.add(edge);                                                             // เพิ่มเส้นเชื่อมเส้นนี้เข้าไปเป็นส่วนหนึ่งของคำตอบ MST สุดท้าย
                addEdgesToQueue(graph, nextVertex, inMST, pq);                                // นำจุดยอดใหม่ไปหาเส้นเชื่อมรอบๆ ตัวมัน แล้วจับยัดลงคิวเพิ่มเพื่อเตรียมพิจารณาขยายต้นไม้ในรอบถัดไป
            }                                                                                 // ปิดเงื่อนไข
        }                                                                                     // ปิดลูป while หลัก
        return result;                                                                        // คืนค่า List ของเส้นเชื่อมทั้งหมดที่ถูกเลือกเป็น MST กลับไปให้คลาสหลักแสดงผล
    }                                                                                         // ปิดเมธอด generateTree

    private void addEdgesToQueue(Graph graph, String vertex, Set<String> inMST, PriorityQueue<Edge> pq) { // เมธอดตัวช่วย (Private) สำหรับค้นหาเส้นเชื่อมรอบๆ จุดยอด แล้วใส่ลงคิว
        for (String edgeId : graph.getEdgeIds()) {                                            // วนลูปดึงรหัสเส้นเชื่อมทั้งหมดในกราฟออกมาตรวจสอบทีละเส้น
            Set<String> endpoints = graph.getEdgeEndpoints().get(edgeId);                     // ดึงข้อมูลจุดยอดปลายทางทั้งสองด้านของเส้นเชื่อมเส้นนั้นๆ ออกมา
            if (endpoints != null && endpoints.contains(vertex)) {                            // ตรวจสอบว่าเส้นเชื่อมเส้นนี้ เชื่อมต่อติดกับจุดยอด (vertex) ที่เรากำลังพิจารณาอยู่หรือไม่
                String otherVertex = "";                                                      // สร้างตัวแปรสตริงเตรียมไว้เก็บชื่อจุดยอดอีกฝั่ง (จุดปลายทาง)
                for (String ep : endpoints) {                                                 // วนลูปตรวจสอบจุดยอดทั้งสองด้านของเส้นเชื่อมนี้
                    if (!ep.equals(vertex)) otherVertex = ep;                                 // ถ้าชื่อไม่ใช่จุดยอดต้นทาง ก็แปลว่าเป็นจุดยอดอีกฝั่งนึง ให้เก็บชื่อนั้นไว้
                }                                                                             // ปิดลูปดึงชื่อจุดปลายทาง
                
                if (!inMST.contains(otherVertex) && !otherVertex.isEmpty()) {                 // ตรวจสอบว่าจุดยอดปลายทางนั้น ต้องยังไม่เคยถูกนำไปรวมใน MST มาก่อน
                    pq.add(new Edge(edgeId, vertex, otherVertex, graph.getEdgeWeight(edgeId))); // ถ้ายังไม่เคยไป ให้สร้างออบเจ็กต์ Edge ของเส้นนี้ และยัดใส่ PriorityQueue รอไว้เลย
                }                                                                             // ปิดเงื่อนไขตรวจสอบการไปเยือน
            }                                                                                 // ปิดเงื่อนไขตรวจสอบการเชื่อมต่อ
        }                                                                                     // ปิดลูปเช็คเส้นเชื่อมทั้งหมด
    }                                                                                         // ปิดเมธอด addEdgesToQueue
}                                                                                             // ปิดคลาส PrimAlgorithm
