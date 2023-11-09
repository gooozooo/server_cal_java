import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {

        try {
            Socket socket = new Socket("localhost", 8080);

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                System.out.println("사칙연산 식을 입력하세요 (ADD/DIV/MIN/MUL number1 number2), exit로 종료: ");
                String expression = userInput.readLine();

                writer.println(expression); //값 서버로 보내기 

                if (expression.equalsIgnoreCase("exit")) {
                    System.out.println("클라이언트를 종료합니다.");
                    break;
                }

                String result = reader.readLine();
                System.out.println("서버로부터 받은 결과: " + result);
            }

            socket.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
