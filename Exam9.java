public class Exam9 {                                                             // ประกาศคลาสหลักสำหรับการทดสอบ (Exam9)
    public static void main(String[] args) {                                     // เมธอดหลัก (Main) ที่เป็นจุดเริ่มต้นการทำงานของโปรแกรม
        // ----- STACK SECTION -----                                             // ส่วนที่ 1: การทดสอบการประเมินค่านิพจน์คณิตศาสตร์ (ใช้ Stack)
        String infixExpression = "( 15 + 5 ) * 4 - 20 / 2";                      // กำหนดค่านิพจน์แบบ Infix (ตัวดำเนินการอยู่ตรงกลาง)
        String postfixExpression = "15 5 + 4 * 20 2 / -";                        // กำหนดค่านิพจน์แบบ Postfix (ตัวดำเนินการอยู่ด้านหลัง)
        String prefixExpression = "- * + 15 5 4 / 20 2";                         // กำหนดค่านิพจน์แบบ Prefix (ตัวดำเนินการอยู่ด้านหน้า)

        System.out.println("=== Test Same Equation ===");                        // พิมพ์หัวข้อการทดสอบลงบนหน้าจอ
        System.out.println("Expected Final Result : 70.0\n");                    // พิมพ์ผลลัพธ์ที่คาดหวัง เพื่อไว้เทียบกับค่าที่โปรแกรมคำนวณได้

        ExpressionEvaluator eval = new ExpressionEvaluator(infixExpression);     // สร้างออบเจ็กต์ eval และโยนค่าสมการ Infix เข้าไปตั้งต้น
        System.out.println("1. Infix Input   : " + infixExpression);             // พิมพ์แสดงสมการ Infix ที่เป็น Input
        System.out.println("   Calculated    : " + eval.evaluateInfix());        // สั่งคำนวณค่าสมการ Infix และพิมพ์ผลลัพธ์ออกมา

        eval.setExpression(postfixExpression);                                   // เปลี่ยนสมการในออบเจ็กต์ eval ให้กลายเป็นสมการแบบ Postfix
        System.out.println("2. Postfix Input : " + postfixExpression);           // พิมพ์แสดงสมการ Postfix ที่เป็น Input
        System.out.println("   Calculated    : " + eval.evaluatePostfix());      // สั่งคำนวณค่าสมการ Postfix และพิมพ์ผลลัพธ์ออกมา

        eval.setExpression(prefixExpression);                                    // เปลี่ยนสมการในออบเจ็กต์ eval ให้กลายเป็นสมการแบบ Prefix
        System.out.println("3. Prefix Input  : " + prefixExpression);            // พิมพ์แสดงสมการ Prefix ที่เป็น Input
        System.out.println("   Calculated    : " + eval.evaluatePrefix());       // สั่งคำนวณค่าสมการ Prefix และพิมพ์ผลลัพธ์ออกมา

        // ----- GRAPH SECTION -----                                             // ส่วนที่ 2: การทดสอบระบบกราฟ (Graph)
        InputHandler input = new InputHandler();                                 // สร้างออบเจ็กต์ input สำหรับรอรับข้อมูลกราฟจากผู้ใช้หรือไฟล์
        input.start();                                                           // เรียกใช้เมธอดเพื่อเริ่มต้นกระบวนการทำงานในส่วนของกราฟ
    }

}                                                                                // ปิดคลาส Exam9
