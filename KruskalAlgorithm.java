import java.util.*;                                                                          // นำเข้าคลาสทั้งหมดในแพ็กเกจ java.util เช่น List, Map, Set, ArrayList, HashMap, Collections

public class KruskalAlgorithm implements SpanningTreeStrategy {                              // ประกาศคลาส KruskalAlgorithm โดยนำอินเทอร์เฟซ SpanningTreeStrategy มาใช้งาน
    
    class DisjointSet {                                                                      // ประกาศคลาสซ้อน (Inner Class) ชื่อ DisjointSet สำหรับใช้ตรวจสอบและป้องกันการเกิดวงวน (Cycle)
        Map<String, String> parent = new HashMap<>();                                        // สร้าง Map ชื่อ parent เก็บข้อมูลว่าโหนดไหนเป็นพ่อ (Parent/หัวหน้า) ของโหนดไหน
        Map<String, Integer> rank = new HashMap<>();                                         // สร้าง Map ชื่อ rank เก็บระดับความลึกหรือความสูงของต้นไม้แต่ละกลุ่ม เพื่อใช้ตอนรวมกลุ่ม
        
        public void makeSet(String vertex) {                                                 // เมธอดสำหรับสร้างกลุ่มเริ่มต้นให้กับแต่ละจุดยอด
            parent.put(vertex, vertex);                                                      // กำหนดให้จุดยอดนั้นเป็นพ่อของตัวเอง (เป็นหัวหน้ากลุ่มของตัวเองไปก่อน)
            rank.put(vertex, 0);                                                             // กำหนดให้ระดับความลึกเริ่มต้นของกลุ่มนี้มีค่าเป็น 0
        }                                                                                    // ปิดเมธอด makeSet
        
        public String find(String vertex) {                                                  // เมธอดสำหรับค้นหาว่าจุดยอดนี้อยู่กลุ่มไหน (ใครคือหัวหน้ากลุ่มตัวจริงที่อยู่บนสุด)
            if (!parent.get(vertex).equals(vertex)) {                                        // ถ้าพ่อของจุดยอดนี้ ไม่ใช่ตัวมันเอง (แปลว่ามันยังไม่ใช่หัวหน้าสูงสุด)
                parent.put(vertex, find(parent.get(vertex)));                                // เรียกตัวเองซ้ำ (Recursive) ไปเรื่อยๆ จนเจอหัวหน้า และนำมาอัปเดตเป็นพ่อสายตรง (Path Compression)
            }                                                                                // ปิดเงื่อนไข
            return parent.get(vertex);                                                       // คืนค่าชื่อหัวหน้ากลุ่มตัวจริงของเซ็ตนี้กลับไป
        }                                                                                    // ปิดเมธอด find
        
        public boolean union(String v1, String v2) {                                         // เมธอดสำหรับรวมกลุ่มจุดยอด 2 จุดเข้าด้วยกัน (ตรวจสอบว่าจะนำเส้นเชื่อมนี้มาใช้ได้ไหม)
            String root1 = find(v1);                                                         // หาหัวหน้ากลุ่มตัวจริงของจุดยอดที่ 1
            String root2 = find(v2);                                                         // หาหัวหน้ากลุ่มตัวจริงของจุดยอดที่ 2
            
            if (root1.equals(root2)) return false;                                           // ถ้าหัวหน้ากลุ่มเป็นคนเดียวกัน (แปลว่าเชื่อมกันอยู่แล้ว) ให้คืนค่า false เพราะถ้าเชื่อมอีกจะเกิดวงวน (Cycle)

            int rank1 = rank.get(root1);                                                     // ดึงระดับความลึก (Rank) ของกลุ่มที่ 1 ออกมา
            int rank2 = rank.get(root2);                                                     // ดึงระดับความลึก (Rank) ของกลุ่มที่ 2 ออกมา

            if (rank1 < rank2) {                                                             // ถ้าระดับความลึกกลุ่มที่ 1 น้อยกว่ากลุ่มที่ 2
                parent.put(root1, root2);                                                    // ให้นำกลุ่มที่ 1 ไปต่อใต้กลุ่มที่ 2 (กลุ่มที่ 2 เป็นหัวหน้าแทน) เพื่อรักษาสมดุลความสูง
            } else if (rank1 > rank2) {                                                      // ถ้าระดับความลึกกลุ่มที่ 1 มากกว่ากลุ่มที่ 2
                parent.put(root2, root1);                                                    // ให้นำกลุ่มที่ 2 ไปต่อใต้กลุ่มที่ 1 (กลุ่มที่ 1 เป็นหัวหน้าแทน)
            } else {                                                                         // ถ้าระดับความลึกของทั้งสองกลุ่มเท่ากันพอดี
                parent.put(root2, root1);                                                    // ให้กลุ่มที่ 1 เป็นหัวหน้า (จะให้ใครเป็นก็ได้)
                rank.put(root1, rank1 + 1);                                                  // เพิ่มระดับความลึกของกลุ่มที่ 1 ขึ้นอีก 1 เพราะมีกลุ่มอื่นมาต่อท้ายเพิ่ม
            }                                                                                // ปิดเงื่อนไข
            return true;                                                                     // คืนค่า true บ่งบอกว่ารวมกลุ่มสำเร็จ (เส้นเชื่อมนี้ใช้ได้ ไม่ทำให้เกิด Cycle)
        }                                                                                    // ปิดเมธอด union
    }                                                                                        // ปิดคลาส DisjointSet

    @Override                                                                                // ระบุว่าเป็นการเขียนทับเมธอดบังคับจากอินเทอร์เฟซ SpanningTreeStrategy
    public List<Edge> generateTree(Graph graph, String... startVertex) {                     // เมธอดหลักสำหรับสร้าง MST รับข้อมูลกราฟและจุดเริ่มต้น (Kruskal ไม่สนใจจุดเริ่มต้น)
        List<Edge> result = new ArrayList<>();                                               // สร้าง List สำหรับเก็บเส้นเชื่อมที่จะถูกเลือกเป็นคำตอบสุดท้าย (MST)
        List<Edge> allEdges = new ArrayList<>();                                             // สร้าง List สำหรับเก็บเส้นเชื่อมทั้งหมดในกราฟ เพื่อเตรียมนำไปเรียงลำดับน้ำหนัก
        DisjointSet ds = new DisjointSet();                                                  // สร้างออบเจ็กต์ DisjointSet สำหรับใช้ตรวจสอบ Cycle ระหว่างการเลือกเส้น

        for (String v : graph.getVertices()) ds.makeSet(v);                                  // วนลูปนำจุดยอดทั้งหมดในกราฟมาสร้างกลุ่มเริ่มต้น (แยกทุกคนอยู่คนละกลุ่ม)

        for (String edgeId : graph.getEdgeIds()) {                                           // วนลูปดึงรหัสเส้นเชื่อมทั้งหมดที่มีในกราฟออกมา
            Set<String> endpoints = graph.getEdgeEndpoints().get(edgeId);                    // ดึงข้อมูลจุดยอดปลายทางทั้งสองของเส้นเชื่อมเส้นนี้
            if (endpoints != null && endpoints.size() == 2) {                                // ตรวจสอบว่ามีข้อมูลจุดยอด และมีแค่ 2 จุดหัวท้ายจริงๆ ใช่ไหม
                Iterator<String> it = endpoints.iterator();                                  // สร้างตัวชี้ (Iterator) เพื่อใช้ดึงข้อมูลจุดยอดออกจาก Set
                allEdges.add(new Edge(edgeId, it.next(), it.next(), graph.getEdgeWeight(edgeId))); // สร้างออบเจ็กต์ Edge เก็บข้อมูลแล้วจับยัดใส่ลิสต์ allEdges รอไว้เลย
            }                                                                                // ปิดเงื่อนไข
        }                                                                                    // ปิดลูปดึงเส้นเชื่อม

        Collections.sort(allEdges);                                                          // พระเอกของ Kruskal: นำเส้นเชื่อมทั้งหมดมาจัดเรียงลำดับ "น้ำหนัก" จากน้อยไปมาก

        for (Edge edge : allEdges) {                                                         // วนลูปหยิบเส้นเชื่อมที่เรียงน้ำหนักแล้วมาพิจารณาทีละเส้น (เริ่มจากเส้นที่น้ำหนักน้อยสุดเสมอ)
            if (ds.union(edge.u, edge.v)) {                                                  // ลองนำจุดต้นทางและปลายทางของเส้นนี้มารวมกลุ่มกัน ถ้าสำเร็จ (ไม่เกิด Cycle)
                result.add(edge);                                                            // ให้เพิ่มเส้นเชื่อมนี้เข้าไปเป็นหนึ่งในเส้นทางของคำตอบ MST
            }                                                                                // ปิดเงื่อนไข
            if (result.size() == graph.getVertices().size() - 1) break;                      // กฎของต้นไม้: ถ้าได้จำนวนเส้นเชื่อมเท่ากับ (จำนวนจุดยอด - 1) ถือว่าเชื่อมครบแล้ว ให้หยุดลูปทันที
        }                                                                                    // ปิดลูปตรวจสอบเส้นเชื่อม

        return result;                                                                       // คืนค่า List ของเส้นเชื่อมทั้งหมดที่ถูกเลือกเป็น MST กลับไปให้คลาสหลักนำไปแสดงผล
    }                                                                                        // ปิดเมธอด generateTree
}                                                                                            // ปิดคลาส KruskalAlgorithm
