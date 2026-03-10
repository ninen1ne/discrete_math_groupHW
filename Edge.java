
public class Edge implements Comparable<Edge> {                                  // ประกาศคลาส Edge และ implement Comparable เพื่อให้จัดเรียงลำดับได้

    String edgeId;                                                               // ตัวแปรเก็บรหัสหรือชื่อของเส้นเชื่อม
    String u, v;                                                                 // ตัวแปรเก็บชื่อจุดยอด (Node) ต้นทาง (u) และปลายทาง (v)
    int weight;                                                                  // ตัวแปรเก็บค่าน้ำหนัก (เช่น ระยะทาง หรือต้นทุน) ของเส้นเชื่อม

    public Edge(String edgeId, String u, String v, int weight) {                 // Constructor สำหรับรับค่ามาสร้างออบเจ็กต์ Edge
        this.edgeId = edgeId;                                                    // กำหนดค่ารหัสเส้นเชื่อมให้กับตัวแปรในคลาส
        this.u = u;                                                              // กำหนดค่าจุดยอดต้นทางให้กับตัวแปรในคลาส
        this.v = v;                                                              // กำหนดค่าจุดยอดปลายทางให้กับตัวแปรในคลาส
        this.weight = weight;                                                    // กำหนดค่าน้ำหนักให้กับตัวแปรในคลาส
    }

    @Override                                                                    // บอกให้รู้ว่ากำลังเขียนทับเมธอดของ Interface Comparable
    public int compareTo(Edge other) {                                           // เมธอดสำหรับนำ Edge ปัจจุบัน ไปเปรียบเทียบกับ Edge ตัวอื่น
        return Integer.compare(this.weight, other.weight);                       // เปรียบเทียบน้ำหนักจากน้อยไปมาก (ใช้วิธีที่ปลอดภัยจาก Integer Overflow)
    }

    @Override                                                                    // บอกให้รู้ว่ากำลังเขียนทับเมธอดจากคลาสแม่ (Object)
    public String toString() {                                                   // เมธอดสำหรับแปลงข้อมูลในออบเจ็กต์นี้ให้ออกมาเป็นข้อความ String
        return "Edge: " + edgeId + " (" + u + "-" + v + ") | Weight: " + weight; // จัดรูปแบบข้อความที่จะให้แสดงผลเวลาสั่ง print
    }
}
