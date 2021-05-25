import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

class Map {

    char[][] mp;

    public Map(int lenght) {
        this.mp = new char[lenght][lenght];
        for (int i = 0; i < lenght; i++) {
            for (int j = 0; j < lenght; j++) {
                mp[i][j] = ' ';
            }
        }
    }

    public void Download(String s) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(s)));
        String line;
        int iter = 0;
        while ((line = reader.readLine()) != null) {
            iter++;
            if (iter > 1) {
                char[] chars = line.toCharArray();
                int j = 0;
                for (int i = 2; i < chars.length && j < 10; i++) {
                    /*
                     * switch(chars[i]){ case ('_'): this.mp[iter-2][j] = ' '; j++; break; case
                     * ('O'): this.mp[iter-2][j] = 'O'; j++; break; default: break; }
                     */
                    if (chars[i] != '|') {
                        this.mp[iter - 2][j] = chars[i];
                        j++;
                    }
                }
            }
            
        }
        reader.close();
    }
}