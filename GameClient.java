import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.InputStreamReader;

public class GameClient extends Thread {
    DataInputStream dis;
    DataOutputStream dos;
    Socket socket;
    String serverName;
    int serverPort;
    int turn;
    boolean readingMaps = false;

    public GameClient(String serverName) {
        this.serverName = serverName;
        this.serverPort = 8080;
    }

    public static void main(String[] args) {
        GameClient gClient = new GameClient("localhost");
        gClient.start();
    }

    public void run() {
        try {
            connectToServer();
            System.out.println("Welcome to my game!");
            System.out.println("Please write your coordinates in format CharacterNumeral\nWithout spaces\nExample: B5");
            System.out.println("If you want to quit from game, please write 'exit'");
            System.out.println("Please wait other players");
            while (true) {
                this.turn = dis.readInt();
                process();
                if (dis.readUTF() == "Game Over")
                    break;
                else {
                    continue;
                }
            }
            socket.close();
            dis.close();
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void process() throws IOException {
        if (this.turn == 1) {
            printMaps();
            doStep();
            printMaps();
            dos.writeInt(2);
        }
        
    }

    private void printMaps() throws IOException {
        for (int i = 0; i < 25; i++) {
            String line = "";
            line = dis.readUTF();
            System.out.println(line);
        }
    }

    private void doStep() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Your turn...");
        String step = in.readLine();
        if ((step.length() > 2) || (step.length() < 2) || (step.isEmpty())) {
            System.out.println("Wrong writting! Try again.");
        }
        if (step == "exit") {
            System.out.println("Thank you for game!\nGood luck!!!");
            this.dis.close();
            this.dos.close();
        }
        this.dos.writeUTF(step);
    }

    void connectToServer() throws UnknownHostException, IOException {
        socket = new Socket(serverName, serverPort);
        dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());
    }
}
