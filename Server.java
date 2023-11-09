import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {

        ServerSocket serverSocket = null;

        try { //계산을 두 번 이상 하기 위해 

            serverSocket = new ServerSocket(8080); //서버 소켓 생성 
            System.out.println("서버가 8080 포트에서 대기 중...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("클라이언트가 연결되었습니다.");

                try (
                    BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)
                ) {
                    String expression;

                    while ((expression = reader.readLine()) != null) {
                        if (expression.equalsIgnoreCase("exit")) {
                            System.out.println("클라이언트와의 연결을 종료합니다.");
                            clientSocket.close(); //exit 입력받을 경우 연결 종료 
                            break;
                        }

                        try {
                            String[] tokens = expression.split(" "); //입력받은 인수 나눠서 저장

                            if (tokens.length != 3) {//인수가 많은 경우의 예외처리 
                                throw new IllegalArgumentException("인수가 너무 많습니다");
                            }

                            String operator = tokens[0];
                            int num1 = Integer.parseInt(tokens[1]);
                            int num2 = Integer.parseInt(tokens[2]);

                            int result = 0;

                            switch (operator.toUpperCase()) {
                                case "ADD":
                                    result = num1 + num2;
                                    break;
                                case "DIV":
                                    if (num2 == 0) {
                                        throw new ArithmeticException("0으로 나눌 수 없습니다");
                                    }
                                    result = num1 / num2;
                                    break;
                                case "MIN":
                                    result = num1 - num2;
                                    break;
                                case "MUL":
                                    result = num1 * num2;
                                    break;
                                default:
                                    throw new IllegalArgumentException("잘못된 연산자입니다");
                            }

                            writer.println("결과: " + result);
                        } catch (Exception e) {
                            writer.println("오류: " + e.getMessage());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close(); //서버 소켓 닫기 
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
