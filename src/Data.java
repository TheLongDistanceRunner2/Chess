import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Data {
    private String fileName;
    private List<List<Chessman>> chessmen;
    private final int size = 8;

    public Data(String fileName) {
        this.fileName = fileName;
        this.chessmen = new ArrayList<List<Chessman>>(size);

        // instantiate all columns:
        for (int i = 0; i < size; i++) {
            chessmen.add(new ArrayList<Chessman>());
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<List<Chessman>> getChessmen() {
        return chessmen;
    }

    public void setChessmen(List<List<Chessman>> chessmen) {
        this.chessmen = chessmen;
    }

    public int getSize() {
        return size;
    }

    public void readFile() {
        Scanner sc2 = null;

        try {
            sc2 = new Scanner(new File(fileName));

            for (int i = 0; i < 8; i++) {
                String line = sc2.nextLine();
                String[] inLine = line.split(" ");

                for (int j = 0; j < 8; j++) {
                    String tmpWord = inLine[j];
                    //System.out.println("->  " + tmpWord);

                    Chessman tmpChessman = new Chessman(i, convertColumn(j), new Player(tmpWord.charAt(0)), tmpWord.charAt(1));
                    chessmen.get(i).add(tmpChessman);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void showData() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                System.out.print(this.chessmen.get(i).get(j).getPlayer().getName()
                        + "" + this.chessmen.get(i).get(j).getChessman() + " ");
            }
            System.out.println();
        }
    }

    //============================================
    // private:

    private char convertColumn(int j) {
        if (j == 0) {
            return 'A';
        }
        else if (j == 1) {
            return 'B';
        }
        else if (j == 2) {
            return 'C';
        }
        else if (j == 3) {
            return 'D';
        }
        else if (j == 4) {
            return 'E';
        }
        else if (j == 5) {
            return 'F';
        }
        else if (j == 6) {
            return 'G';
        }
        else if (j == 7) {
            return 'H';
        }
        return 'x';
    }
}
