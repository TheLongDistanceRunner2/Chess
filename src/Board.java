import java.util.ArrayList;
import java.util.List;

public class Board {
    private Data data;
    private List<Chessman> chessmen;

    public Board() {
        this.data = null;
        this.chessmen = new ArrayList<>();
    }
}
