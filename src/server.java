import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;

public class server {
    public static int n1;
    public static String[] hang = {"", "muoi", "tram", "nghin", "trieu", "ty"};
    public static String[] so = {"", "mot", "hai", "ba", "bon", "nam", "sau", "bay", "tam", "chin"};

    public static String docto(int n) {
        if (n == 0)
            return "khong";
        if (n <= 10)
            return so[n];
        if (n < 20)
            return "muoi " + docdonvi(n%10);
        n1 = n;
        int lopdonvi = n % 1000;
        n /= 1000;
        int lopnghin = n % 1000;
        n /= 1000;
        int loptrieu = n % 1000;
        n = n1;

        String res = "";
        if (n1 < 1000)
            res = doc(lopdonvi);
        else res = doc999(lopdonvi);

        if (lopnghin != 0)
            res = docto(lopnghin) + " nghin " + res;
        if (loptrieu != 0)
            res = docto(loptrieu) + " trieu " + res;
        return res;
    }

    public static String doc(int n) {

        String res ="";
        if (n % 10 != 0) res = docdonvi(n % 10) + " " + res;
        n /= 10;
        if (n % 10 != 0) res = docchuc(n%10) + " " + res;
        n /= 10;
        if (n % 10 != 0) res = doctram(n%10) + " " + res;
        return res;
    }

    public static String doc999(int n) {

        String res ="";
        res = docdonvi(n % 10) + " " + res;
        n /= 10;
        res = docchuc(n%10) + " " + res;
        n /= 10;
        res = doctram(n%10) + " " + res;
        return res;
    }

    public static String doctram(int n) {
        if (n == 0) {
            if (n1 > 1000)
                return "khong tram";
            else
                return "";
        } else
            return so[n] + " tram";
    }

    public static String docchuc(int n) {
        if (n == 0) {
            if (n1 % 100 != 0)
                return "linh";
            else return "";
        }
        return so[n] + " muoi";


    }

    public static String docdonvi(int n) {
        if (n == 0)
            return "";
        //if (n == 5)
        //    return "lam";
        if (n < 10)
            return so[n];
        return "";
    }
    public static String process(String input_from_client) {
        String s = input_from_client;
        int n = Integer.parseInt(s);
        String res = docto(n);
        return res;
    }


    public static void main(String argv[]) throws Exception
    {
        String sentence_from_client;
        String sentence_to_client;
        ServerSocket welcomeSocket = new ServerSocket(6543);

        while(true) {
            System.out.println("Server is running");

            Socket connectionSocket = welcomeSocket.accept();

            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

            DataOutputStream outToClient =
                    new DataOutputStream(connectionSocket.getOutputStream());

            sentence_from_client = inFromClient.readLine();

            if (sentence_from_client.equals("break")) {
                System.out.println("Server is closed");
                return;
            }

            sentence_to_client = process(sentence_from_client)+ '\n';
            outToClient.writeBytes(sentence_to_client);
            //return;
        }

    }

}
