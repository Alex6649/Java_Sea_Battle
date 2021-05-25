import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class GameClientServer extends Thread {
    DataInputStream dis;
    DataOutputStream dos;
    int turn;
    Game game;

    public GameClientServer(Game game, DataInputStream dis, DataOutputStream dos, int pos) {
        this.game = game;
        this.dis = dis;
        this.dos = dos;
        this.turn = pos;
    }

    public GameClientServer() {
    }

    public void run() {
        while (!this.game.isOver) {
            this.game.GameStatus();
        }
    }

    public void process() throws IOException, InterruptedException {
        if (this.turn == 1) {
            dos.writeInt(1);
            PrinterGame();
            dos.writeUTF("Your turn...");
            String line = this.dis.readUTF();
            this.game.Step(line);
            PrinterGame();
        }
        turn = dis.readInt();
    }

    public void PrinterGame() throws IOException {
        String str = "";
        dos.writeUTF("\n");
        dos.writeUTF("MyMap");
        dos.writeUTF("   A B C D E F G H I J ");
        for (int i = 1; i < 11; i++) {
            str = "";
            if (i < 10)
                str = str + " " + i;
            else
                str = str + i;
            for (int j = 0; j < 10; j++) {
                str = str + "|" + this.game.myMap.mp[i - 1][j];
            }
            str = str + "|";
            dos.writeUTF(str);
        }
        dos.writeUTF("EnemyMap");
        dos.writeUTF("   A B C D E F G H I J ");
        for (int i = 1; i < 11; i++) {
            str = "";
            if (i < 10)
                str = str + " " + i;
            else
                str = str + i;
            for (int j = 0; j < 10; j++) {
                str = str + "|" + this.game.actionMap.mp[i - 1][j];
            }
            str = str + "|";
            dos.writeUTF(str);
        }
    }
}
