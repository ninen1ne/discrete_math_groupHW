import java.util.Set;                                                                                     // นำเข้าอินเทอร์เฟซ Set สำหรับเก็บข้อมูลแบบไม่ซ้ำกัน
import java.util.Map;                                                                                     // นำเข้าอินเทอร์เฟซ Map สำหรับเก็บข้อมูลแบบคู่ Key-Value
import java.util.HashMap;                                                                                 // นำเข้าคลาส HashMap สำหรับจัดการข้อมูล Map
import java.util.HashSet;                                                                                 // นำเข้าคลาส HashSet สำหรับจัดการข้อมูล Set

public class Graph {                                                                                      // ประกาศคลาส Graph สำหรับสร้างและจัดการโครงสร้างข้อมูลแบบกราฟ
    private Set<String> v;                                                                                // ตัวแปรเก็บเซ็ตของจุดยอด (Vertices/Nodes) ทั้งหมดในกราฟ
    private Set<String> e;                                                                                // ตัวแปรเก็บเซ็ตของรหัสเส้นเชื่อม (Edges) ทั้งหมดในกราฟ
    private Map<String, Set<String>> eEndpoint;                                                           // Map สำหรับจับคู่รหัสเส้นเชื่อม กับจุดยอดปลายทางทั้งสองด้าน
    private Map<String, Integer> eWeight;                                                                 // Map สำหรับจับคู่รหัสเส้นเชื่อม กับค่าน้ำหนัก (Weight) ของเส้นนั้นๆ

    public Graph(Set<String> v, Set<String> e) {                                                          // Constructor สำหรับสร้างออบเจ็กต์กราฟ โดยรับจุดยอดและเส้นเชื่อมเริ่มต้น
        this.v = v;                                                                                       // กำหนดเซ็ตของจุดยอดให้กับตัวแปรในคลาส
        this.e = e;                                                                                       // กำหนดเซ็ตของรหัสเส้นเชื่อมให้กับตัวแปรในคลาส
        this.eEndpoint = new HashMap<>();                                                                 // สร้าง HashMap เปล่าสำหรับเตรียมเก็บข้อมูลจุดเชื่อมต่อ
        this.eWeight = new HashMap<>();                                                                   // สร้าง HashMap เปล่าสำหรับเตรียมเก็บข้อมูลน้ำหนักของเส้นเชื่อม
    }                                                                                                     // ปิด Constructor

    public Set<String> getVertices() { return v; }                                                        // เมธอดสำหรับดึงข้อมูลเซ็ตของจุดยอด (Getter)
    public void setVertices(Set<String> v) { this.v = v; }                                                // เมธอดสำหรับกำหนดค่าเซ็ตของจุดยอดใหม่ (Setter)
    
    public Set<String> getEdgeIds() { return e; }                                                         // เมธอดสำหรับดึงข้อมูลเซ็ตของรหัสเส้นเชื่อมทั้งหมด (Getter)
    public void setEdgeIds(Set<String> e) { this.e = e; }                                                 // เมธอดสำหรับกำหนดค่าเซ็ตของรหัสเส้นเชื่อมใหม่ (Setter)
    
    public Map<String, Set<String>> getEdgeEndpoints() { return eEndpoint; }                              // เมธอดสำหรับดึงข้อมูลการจับคู่เส้นเชื่อมกับจุดยอด (Getter)
    public void setEdgeEndpoints(Map<String, Set<String>> eEndpoint) { this.eEndpoint = eEndpoint; }      // เมธอดสำหรับกำหนดข้อมูลการจับคู่เส้นเชื่อมกับจุดยอดใหม่ (Setter)

    public Integer getEdgeWeight(String edgeId) { return eWeight.getOrDefault(edgeId, 0); }               // เมธอดดึงน้ำหนักของเส้นเชื่อม คืนค่า 0 หากไม่พบรหัสเส้นเชื่อมนั้นในระบบ

    public void defineEdge(String edgeId, String v1, String v2, int weight) {                             // เมธอดสำหรับกำหนดรายละเอียด (จุดยอดหัวท้ายและน้ำหนัก) ให้กับเส้นเชื่อม
        if (!e.contains(edgeId)) return;                                                                  // ตรวจสอบว่ามีรหัสเส้นเชื่อมนี้ในระบบหรือไม่ ถ้าไม่มีให้หยุดการทำงานทันที
        Set<String> endpoints = new HashSet<>();                                                          // สร้าง Set ชั่วคราวสำหรับเก็บจุดยอด 2 ฝั่งของเส้นเชื่อมนี้
        endpoints.add(v1);                                                                                // เพิ่มจุดยอดที่ 1 ลงใน Set 
        endpoints.add(v2);                                                                                // เพิ่มจุดยอดที่ 2 ลงใน Set
        eEndpoint.put(edgeId, endpoints);                                                                 // บันทึกข้อมูลจับคู่รหัสเส้นเชื่อม กับจุดยอดทั้งสองฝั่งลงใน Map
        eWeight.put(edgeId, weight);                                                                      // บันทึกค่าน้ำหนักของเส้นเชื่อมนี้ลงใน Map
    }                                                                                                     // ปิดเมธอด defineEdge

    public boolean isValidConnection(String vStart, String vEnd, String edgeId) {                         // เมธอดสำหรับตรวจสอบว่าเส้นเชื่อมนี้ เชื่อมต่อระหว่าง 2 จุดยอดที่ระบุจริงหรือไม่
        if (!eEndpoint.containsKey(edgeId)) return false;                                                 // ตรวจสอบว่ามีการกำหนดรายละเอียดให้เส้นเชื่อมนี้หรือยัง ถ้ายังให้คืนค่า false
        Set<String> points = eEndpoint.get(edgeId);                                                       // ดึงเซ็ตของจุดยอดที่เป็นปลายทางของเส้นเชื่อมนี้ออกมาตรวจสอบ
        return points.contains(vStart) && points.contains(vEnd);                                          // เช็คว่าในเซ็ตมีจุดยอดทั้ง 2 จุดที่ระบุหรือไม่ ถ้ามีครบทั้งคู่จะคืนค่า true
    }                                                                                                     // ปิดเมธอด isValidConnection
}                                                                                                         // ปิดคลาส Graph
