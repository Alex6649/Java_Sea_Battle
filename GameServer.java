import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer extends Thread {
    int serverPort = 8080;
    ServerSocket ss;
    int gamers = 0;
    final int playerLimit = 2;
    GameClientServer player1;
    GameClientServer player2;
    int turn = 0;

    public static void main(String[] args) {
        GameServer gServer = new GameServer();
        gServer.start();
    }

    public GameServer() {
        this.player1 = new GameClientServer();
        this.player2 = new GameClientServer();
    }

    public void NewPlayer() throws IOException {
        if (gamers == 0) {
            Socket player1 = ss.accept();
            System.out.println("Player 1 joined!");
            this.player1 = new GameClientServer(
                    new Game("src/com/myProject/maps/map1.txt", "src/com/myProject/maps/map2.txt"),
                    new DataInputStream(player1.getInputStream()), new DataOutputStream(player1.getOutputStream()), 1);
            this.player1.start();
            gamers++;
            turn = 1;
        }
        if (gamers == 1) {
            Socket player2 = ss.accept();
            System.out.println("Player 2 joined!");
            this.player2 = new GameClientServer(
                    new Game("src/com/myProject/maps/map2.txt", "src/com/myProject/maps/map1.txt"),
                    new DataInputStream(player2.getInputStream()), new DataOutputStream(player2.getOutputStream()), 2);
            this.player2.start();
            gamers++;
        }
    }

    public void run() throws NullPointerException {
        try {
            this.ss = new ServerSocket(serverPort);
            System.out.println("Server is working!");
            while (true) {
                while (gamers < 2) {
                    NewPlayer();
                }
                Synchronize();
                processGame();
                checkStatus();
                if ((player1.game.isOver) || (player2.game.isOver))
                    break;
            }
            ss.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void checkStatus() throws IOException {
        if ((player1.game.isOver) || (player2.game.isOver)) {
            player1.dos.writeUTF("Game Over");
            player2.dos.writeUTF("Game Over");
        }
    }

    public void processGame() throws IOException, InterruptedException {
        if (turn == 1) {
            System.out.println("Player 1 turn");
            player1.turn = 1;
            player2.turn = 2;
            player1.process();
            if (player1.turn == 2) {
                player2.turn = 1;
                turn = 2;
            }
        }
        Synchronize();
        if (turn == 2) {
            System.out.println("Player 2 turn");
            player2.turn = 1;
            player1.turn = 2;
            player2.process();
            if (player2.turn == 2) {
                player1.turn = 1;
                turn = 1;
            }
        }
    }

    public void Synchronize() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                player1.game.myMap.mp[i][j] = player2.game.enemyMap.mp[i][j];
                player2.game.myMap.mp[i][j] = player1.game.enemyMap.mp[i][j];
            }
        }
    }
}
