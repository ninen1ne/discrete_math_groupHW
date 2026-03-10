import java.util.HashSet;                                                        // นำเข้าคลาส HashSet สำหรับเก็บข้อมูลที่ไม่ซ้ำกัน
import java.util.Set;                                                            // นำเข้าอินเทอร์เฟซ Set สำหรับสร้างชุดข้อมูล
import java.util.Stack;                                                          // นำเข้าคลาส Stack สำหรับใช้โครงสร้างข้อมูลแบบ LIFO (เข้าหลังออกก่อน)

public class ExpressionEvaluator {                                               // ประกาศคลาสหลักชื่อ ExpressionEvaluator สำหรับคำนวณนิพจน์
    private String expression;                                                   // ประกาศตัวแปรเก็บนิพจน์ (สมการ) ในรูปแบบ String
    private HashSet<Character> operatorsSet = new HashSet<>(Set.of('+', '-', '*', '/')); // สร้าง Set เพื่อเก็บเครื่องหมายทางคณิตศาสตร์พื้นฐาน

    public ExpressionEvaluator(String expression) {                              // Constructor รับค่านิพจน์เริ่มต้นเมื่อสร้างออบเจ็กต์
        this.expression = expression;                                            // กำหนดค่านิพจน์ที่รับมาให้กับตัวแปรในคลาส
    }                                                                            // ปิด Constructor

    public void setExpression(String expression) {                               // เมธอดสำหรับตั้งค่านิพจน์ใหม่เข้าไปแทนที่ของเดิม
        this.expression = expression;                                            // อัปเดตตัวแปร expression เป็นค่าใหม่
    }                                                                            // ปิดเมธอด

    public String infixToPostfix(String infixExpression) {                       // เมธอดสำหรับแปลงนิพจน์จาก Infix (เครื่องหมายอยู่ตรงกลาง) เป็น Postfix
        Stack<Character> myStack = new Stack<>();                                // สร้าง Stack สำหรับเก็บเครื่องหมายชั่วคราว (ตาม Shunting Yard Algorithm)
        StringBuilder postfixFormatString = new StringBuilder();                 // สร้างตัวสร้าง String สำหรับประกอบข้อความผลลัพธ์ที่เป็น Postfix
        int i = 0;                                                               // กำหนดตัวแปร i สำหรับใช้รัน index อ่านตัวอักษร

        while (i < infixExpression.length()) {                                   // วนลูปจนกว่าจะอ่านอักขระครบทุกตัวในสมการ Infix
            char currChar = infixExpression.charAt(i);                           // อ่านอักขระที่ตำแหน่ง i มาเก็บไว้ใน currChar

            if (currChar == ' ') {                                               // ตรวจสอบว่าอักขระปัจจุบันเป็นช่องว่างหรือไม่
                i++;                                                             // ถ้าเป็นช่องว่าง ให้เพิ่มค่า i เพื่อข้ามไปเลย
                continue;                                                        // ข้ามการทำงานรอบนี้และเริ่มลูปรอบถัดไปทันที
            }                                                                    // ปิดบล็อก if

            if (Character.isDigit(currChar)) {                                   // ตรวจสอบว่าอักขระปัจจุบันเป็น "ตัวเลข" หรือไม่
                while (i < infixExpression.length() && Character.isDigit(infixExpression.charAt(i))) { // วนลูปอ่านตัวเลขกรณีมีหลายหลักติดกัน
                    postfixFormatString.append(infixExpression.charAt(i));       // นำตัวเลขแต่ละหลักไปต่อท้ายข้อความผลลัพธ์
                    i++;                                                         // ขยับ index ไปยังตัวอักษรถัดไป
                }                                                                // ปิดลูป while ของการอ่านตัวเลข
                postfixFormatString.append(' ');                                 // เติมช่องว่าง 1 ตัว หลังตัวเลขชุดนั้นเพื่อคั่นข้อมูล
                i--;                                                             // ลดค่า i ลง 1 เพื่อชดเชยค่าที่ถูกบวกเกินมาจากลูป while ย่อย
            } else if (operatorsSet.contains(currChar)) {                        // หากไม่ใช่ตัวเลข ให้เช็คว่าเป็น "เครื่องหมายทางคณิตศาสตร์" หรือไม่
                while (!myStack.isEmpty() && myStack.peek() != '('               // ตรวจสอบว่า Stack ไม่ว่าง และเครื่องหมายตัวบนสุดไม่ใช่วงเล็บเปิด
                        && getPrecedence(currChar) <= getPrecedence(myStack.peek())) { // และความสำคัญของเครื่องหมายปัจจุบัน น้อยกว่า/เท่ากับ ตัวบนสุดของ Stack
                    postfixFormatString.append(myStack.pop()).append(' ');       // ดึงเครื่องหมายจาก Stack ออกมาต่อท้ายผลลัพธ์ พร้อมกับเว้นวรรค
                }                                                                // ปิดลูป while ย่อย
                myStack.push(currChar);                                          // นำเครื่องหมายตัวปัจจุบันที่กำลังอ่าน ดันลงไปเก็บใน Stack
            } else if (currChar == '(') {                                        // ตรวจสอบว่าเป็น "วงเล็บเปิด" หรือไม่
                myStack.push(currChar);                                          // ถ้าเป็น ให้นำไปดันลงใน Stack ได้เลย
            } else if (currChar == ')') {                                        // ตรวจสอบว่าเป็น "วงเล็บปิด" หรือไม่
                while (!myStack.isEmpty() && myStack.peek() != '(') {            // วนลูปดึงข้อมูลออกจาก Stack เรื่อยๆ จนกว่าจะเจอวงเล็บเปิด
                    postfixFormatString.append(myStack.pop()).append(' ');       // นำเครื่องหมายที่ดึงออกมาไปต่อท้ายผลลัพธ์ พร้อมเว้นวรรค
                }                                                                // ปิดลูป while ของการเคลียร์ข้อมูลในวงเล็บ
                if (!myStack.isEmpty() && myStack.peek() == '(') {               // เช็คอีกครั้งเพื่อความแน่ใจว่าตัวบนสุดเป็นวงเล็บเปิด
                    myStack.pop();                                               // ดึงวงเล็บเปิดออกจาก Stack ทิ้งไป (ไม่ต้องเอาไปแสดงในผลลัพธ์)
                }                                                                // ปิดเงื่อนไข
            }                                                                    // ปิดเงื่อนไขทั้งหมด
            i++;                                                                 // ขยับ index เพื่ออ่านอักขระตัวถัดไปในลูปหลัก
        }                                                                        // ปิดลูป while หลัก

        while (!myStack.isEmpty()) {                                             // เมื่ออ่านสมการจบแล้ว ตรวจสอบว่ายังมีเครื่องหมายตกค้างใน Stack ไหม
            postfixFormatString.append(myStack.pop()).append(' ');               // ถ้ามีให้ดึงออกมาให้หมดแล้วต่อท้ายผลลัพธ์พร้อมเว้นวรรค
        }                                                                        // ปิดลูป while การเคลียร์ Stack รอบสุดท้าย

        return postfixFormatString.toString().trim();                            // แปลงผลลัพธ์เป็น String ลบช่องว่างส่วนเกินที่หัวท้าย แล้วส่งค่ากลับ
    }                                                                            // ปิดเมธอด infixToPostfix

    private int getPrecedence(char operator) {                                   // เมธอดสำหรับตรวจสอบลำดับความสำคัญของเครื่องหมาย
        switch (operator) {                                                      // ใช้คำสั่ง switch ตรวจสอบอักขระที่รับเข้ามา
            case '+':                                                            // กรณีเป็นเครื่องหมายบวก
            case '-':                                                            // หรือเครื่องหมายลบ
                return 1;                                                        // ให้ระดับความสำคัญที่ 1 (ทำทีหลังสุด)
            case '*':                                                            // กรณีเป็นเครื่องหมายคูณ
            case '/':                                                            // หรือเครื่องหมายหาร
                return 2;                                                        // ให้ระดับความสำคัญที่ 2 (ทำก่อนบวกและลบ)
            case '^':                                                            // กรณีเป็นเครื่องหมายยกกำลัง
                return 3;                                                        // ให้ระดับความสำคัญที่ 3 (สูงสุด ทำก่อนเพื่อน)
            default:                                                             // กรณีไม่ใช่เครื่องหมายใดๆ ที่กำหนดไว้
                return -1;                                                       // ส่งค่า -1 กลับไปเพื่อบอกว่าไม่รู้จัก
        }                                                                        // ปิดคำสั่ง switch
    }                                                                            // ปิดเมธอด getPrecedence

    public double evaluateInfix() {                                              // เมธอดสำหรับคำนวณหาคำตอบของสมการแบบ Infix
        String postfixExpr = infixToPostfix(this.expression);                    // แปลงสมการที่เก็บไว้ในคลาส ให้กลายเป็น Postfix ก่อน
        return evaluatePostfix(postfixExpr);                                     // นำสมการ Postfix ที่แปลงเสร็จไปเข้าฟังก์ชันคำนวณและส่งคืนผลลัพธ์
    }                                                                            // ปิดเมธอด evaluateInfix

    public double evaluatePrefix() {                                             // เมธอดสำหรับคำนวณหาคำตอบของสมการแบบ Prefix (เครื่องหมายอยู่หน้า)
        Stack<Double> myStack = new Stack<>();                                   // สร้าง Stack เก็บตัวเลข (Double) ระหว่างการคำนวณ
        String[] tokens = this.expression.trim().split("\\s+");                  // ตัดช่องว่างหัวท้าย และแยกข้อความทุกช่องว่างออกเป็นอาร์เรย์ (Token)

        int i = tokens.length - 1;                                               // ตั้งค่าเริ่มต้นของ index ที่คำสุดท้าย (เพราะ Prefix ต้องอ่านจากขวาไปซ้าย)
        while (i >= 0) {                                                         // วนลูปถอยหลังจนถึงคำแรก
            String token = tokens[i];                                            // อ่านค่าของ Token (คำ) ปัจจุบัน
            if (token.length() == 1 && operatorsSet.contains(token.charAt(0))) { // ตรวจสอบว่า Token นั้นเป็นเครื่องหมายทางคณิตศาสตร์หรือไม่
                double a = myStack.pop();                                        // ถ้าใช่ ให้ดึงตัวเลข 2 ตัวล่าสุดออกจาก Stack (ตัวแรกเป็นตัวตั้ง)
                double b = myStack.pop();                                        // ดึงตัวเลขตัวที่ 2 ออกมา (เป็นตัวกระทำ)
                double val = performAction(token.charAt(0), a, b);               // นำตัวเลขทั้ง 2 ไปคำนวณร่วมกับเครื่องหมายที่เจอ
                myStack.push(val);                                               // นำผลลัพธ์ที่คำนวณได้ ดันกลับลงไปใน Stack
            } else {                                                             // ถ้า Token ไม่ใช่เครื่องหมาย (แสดงว่าเป็นตัวเลข)
                myStack.push(Double.parseDouble(token));                         // ให้แปลงข้อความเป็นเลขทศนิยมแล้วดันเก็บใน Stack
            }                                                                    // ปิดเงื่อนไข
            i--;                                                                 // ลดค่า index ลงเพื่อขยับไปอ่านคำก่อนหน้าทางซ้าย
        }                                                                        // ปิดลูป while
        return myStack.pop();                                                    // ผลลัพธ์สุดท้ายจะเหลือใน Stack เพียง 1 ตัว ให้ดึงออกมาส่งคืนเป็นคำตอบ
    }                                                                            // ปิดเมธอด evaluatePrefix

    public double evaluatePostfix() {                                            // เมธอดสำหรับสั่งคำนวณสมการแบบ Postfix (โดยดึงค่าสมการจากตัวแปรคลาส)
        return evaluatePostfix(this.expression);                                 // โยนสมการไปให้เมธอด evaluatePostfix ด้านล่างคำนวณให้
    }                                                                            // ปิดเมธอด

    private double evaluatePostfix(String expr) {                                // เมธอดสำหรับรับสมการแบบ Postfix เฉพาะเจาะจงมาคำนวณ
        Stack<Double> myStack = new Stack<>();                                   // สร้าง Stack สำหรับเก็บตัวเลขระหว่างคำนวณ
        String[] tokens = expr.trim().split("\\s+");                             // ตัดช่องว่างหัวท้าย และแยกข้อความด้วยช่องว่างออกเป็น Token

        int i = 0;                                                               // ตั้งค่าเริ่มต้น index ที่ 0 (เพราะ Postfix ต้องอ่านจากซ้ายไปขวา)
        while (i < tokens.length) {                                              // วนลูปอ่านจนกว่าจะครบทุก Token
            String token = tokens[i];                                            // ดึงค่า Token ตำแหน่งปัจจุบันมาเก็บไว้
            if (token.length() == 1 && operatorsSet.contains(token.charAt(0))) { // ตรวจสอบว่าเป็นเครื่องหมายทางคณิตศาสตร์หรือไม่
                double a = myStack.pop();                                        // ถ้าใช่ ให้ดึงเลขตัวแรกออกมา (ระวัง: กรณี LIFO อันนี้จะเป็นตัวกระทำ หรือ b)
                double b = myStack.pop();                                        // ดึงตัวเลขตัวที่สองออกมา (อันนี้จะเป็นตัวตั้ง หรือ a)
                double val = performAction(token.charAt(0), b, a);               // คำนวณค่า (สังเกตว่าสลับตำแหน่ง b และ a เพื่อให้การลบ/หาร ถูกต้อง)
                myStack.push(val);                                               // นำผลลัพธ์ที่ได้ยัดกลับลงไปใน Stack
            } else {                                                             // ถ้าเป็นตัวเลข
                myStack.push(Double.parseDouble(token));                         // แปลงข้อความเป็นตัวเลขทศนิยมแล้วดันเก็บใน Stack
            }                                                                    // ปิดเงื่อนไข
            i++;                                                                 // เพิ่มค่า index เพื่ออ่านคำถัดไปทางขวา
        }                                                                        // ปิดลูป while
        return myStack.pop();                                                    // ส่งคืนคำตอบสุดท้ายที่อยู่บนสุดของ Stack
    }                                                                            // ปิดเมธอด

    private double performAction(char operator, double a, double b) {            // เมธอดตัวช่วยสำหรับคำนวณคณิตศาสตร์ตัวเลข 2 ชุด
        double value = 0;                                                        // ประกาศตัวแปรเก็บค่าผลลัพธ์ เริ่มต้นที่ 0
        switch (operator) {                                                      // ตรวจสอบชนิดเครื่องหมาย
            case '+':                                                            // หากเป็นเครื่องหมายบวก
                value = a + b;                                                   // นำ a มาบวก b
                break;                                                           // ออกจาก switch
            case '-':                                                            // หากเป็นเครื่องหมายลบ
                value = a - b;                                                   // นำ a ลบด้วย b
                break;                                                           // ออกจาก switch
            case '*':                                                            // หากเป็นเครื่องหมายคูณ
                value = a * b;                                                   // นำ a คูณ b
                break;                                                           // ออกจาก switch
            case '/':                                                            // หากเป็นเครื่องหมายหาร
                value = a / b;                                                   // นำ a หารด้วย b
                break;                                                           // ออกจาก switch
            default:                                                             // กรณีไม่ใช่เครื่องหมายใดเลย
                break;                                                           // ไม่ต้องคำนวณ ปล่อยให้คืนค่า 0 ตามค่าเริ่มต้น
        }                                                                        // ปิดคำสั่ง switch
        return value;                                                            // ส่งคืนค่าผลลัพธ์การคำนวณ
    }                                                                            // ปิดเมธอด performAction
}                                                                                // ปิดคลาส ExpressionEvaluator
