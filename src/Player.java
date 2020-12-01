public class Player {
    private char name;

    public Player() {
        this.name = ' ';
    }

    public Player(char name) {
        this.name = name;
    }

    public void setName(char name) {
        this.name = name;
    }

    public char getName() {
        return name;
    }
}
