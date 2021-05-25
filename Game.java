import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Game {
    Map myMap;
    Map enemyMap;
    Map actionMap;
    boolean isOver;

    public Game(String path1, String path2) throws IOException {
        this.myMap = new Map(10);
        this.enemyMap = new Map(10);
        this.actionMap = new Map(10);
        this.myMap.Download(path1);
        this.enemyMap.Download(path2);
        this.actionMap.Download("src/com/myProject/maps/mapEmpty.txt");
        this.isOver = false;
    }

    public void GameStatus() {
        int count1 = 0;
        int count2 = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (myMap.mp[i][j] == 'X')
                    count1++;
                if (enemyMap.mp[i][j] == 'X')
                    count2++;
            }
        }
        if (count1 == 20) {
            isOver = true;
            System.out.println("You Win!");
        }
        if (count2 == 20) {
            isOver = true;
            System.out.println("You Lose!");
        }
    }

    public void WriteMaps(String dir) throws IOException {
        FileWriter out = new FileWriter(dir);
        out.write("MyMap\n");
        out.write("   А Б В Г Д Е Ж З И К \n");
        for (int i = 1; i < 11; i++) {
            if (i < 10)
                out.write(" " + i);
            else
                out.write("" + i);
            for (int j = 0; j < 10; j++) {
                out.append('|');
                out.append(myMap.mp[i - 1][j]);
            }
            out.write("|\n");
        }

        out.write("EnemyMap\n");
        out.write("   А Б В Г Д Е Ж З И К \n");
        for (int i = 1; i < 11; i++) {
            if (i < 10)
                out.write(" " + i);
            else
                out.write("" + i);
            for (int j = 0; j < 10; j++) {
                out.append('|');
                out.append(enemyMap.mp[i - 1][j]);
            }
            out.write("|\n");
        }
        out.close();
    }

    public void ReadMaps(String dir) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(dir)));
        String line;
        String who = "";
        int iter = 0;
        int j = 0;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
            if (line == "MyMap\n" || line == "EnemyMap\n") {
                who = line;
                iter = 0;
                j = 0;
                continue;
            } else {
                iter++;
                if (iter > 1) {
                    char[] chars = line.toCharArray();
                    for (int i = 2; i < chars.length && j < 10; i++) {
                        /*
                         * switch (chars[i]) { case ('_'): if (who == "MyMap\n") { myMap.mp[iter - 2][j]
                         * = ' '; j++; break; } else { enemyMap.mp[iter - 2][j] = ' '; j++; break; }
                         * case ('O'): if (who == "EnemyMap\n") { myMap.mp[iter - 2][j] = 'O'; j++;
                         * break; } else { enemyMap.mp[iter - 2][j] = 'O'; j++; break; } case ('X'): if
                         * (who == "EnemyMap\n") { myMap.mp[iter - 2][j] = 'X'; j++; break; } else {
                         * enemyMap.mp[iter - 2][j] = 'X'; j++; break; } case ('*'): if (who ==
                         * "EnemyMap\n") { myMap.mp[iter - 2][j] = '*'; j++; break; } else {
                         * enemyMap.mp[iter - 2][j] = '*'; j++; break; } default: break; }
                         */
                        if (chars[i] != '|') {
                            if (who == "MyMap\n") {
                                myMap.mp[iter - 2][j] = chars[i];
                                j++;
                                break;
                            }
                            if (who == "EnemyMap\n") {
                                enemyMap.mp[iter - 2][j] = chars[i];
                                j++;
                                break;
                            }
                        }
                    }
                }
            }
        }
        in.close();
        /*
         * for (int k = 0; k < 2; k++) for (int i = 0; i < 10; i++) { if (k == 0) {
         * System.out.println(myMap.mp[i]); } else { System.out.println(enemyMap.mp[i]);
         * }
         * 
         * }
         */
        GameStatus();
    }

   
    public boolean Step(String coord) {
        boolean boom = false;
        int[] pl = { 0, 0 };
        char[] ch = coord.toCharArray();
        switch (ch[0]) {
            case ('A'): {
                pl[1] = 0;
                break;
            }
            case ('B'): {
                pl[1] = 1;
                break;
            }
            case ('C'): {
                pl[1] = 2;
                break;
            }
            case ('D'): {
                pl[1] = 3;
                break;
            }
            case ('E'): {
                pl[1] = 4;
                break;
            }
            case ('F'): {
                pl[1] = 5;
                break;
            }
            case ('G'): {
                pl[1] = 6;
                break;
            }
            case ('H'): {
                pl[1] = 7;
                break;
            }
            case ('I'): {
                pl[1] = 8;
                break;
            }
            case ('J'): {
                pl[1] = 9;
                break;
            }
            default:
                break;
        }
        pl[0] = Character.getNumericValue(ch[1]) - 1;
        switch (this.enemyMap.mp[pl[0]][pl[1]]) {
            case ('_'):
                this.actionMap.mp[pl[0]][pl[1]] = '*';
                this.enemyMap.mp[pl[0]][pl[1]] = '*';
                break;
            case ('O'):
                this.actionMap.mp[pl[0]][pl[1]] = 'X';
                this.enemyMap.mp[pl[0]][pl[1]] = 'X';
                boom = true;
                break;
            default:
                break;
        }
        return boom;
    }
}
