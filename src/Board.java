import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel {

    private Game mainGame;
    private xoCell[] cells = new xoCell[9];
    private int boardNumber;

    public Board(Game g, int b) {
        mainGame = g;
        boardNumber = b;
        setLayout(new GridLayout(3,3));
        setPreferredSize(new Dimension(180,180));
        setMinimumSize(new Dimension(180,180));
        setMaximumSize(new Dimension(180,180));
        for (int i = 0; i < 9 ; i++) {
            cells[i] = new xoCell(mainGame, i, boardNumber);
            this.add(cells[i]);
        }
        setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.CYAN));
        setVisible(true);
    }

    public xoCell[] getCells() {
        return cells;
    }

    public void setAllCells(String xo) {
        for (int i = 0; i < 9; i++) {
            cells[i].setText(xo);
            if (xo.equals("O"))
                cells[i].setForeground(Color.cyan);
            else
                cells[i].setForeground(Color.red);
            cells[i].delMouseListener();
        }
    }
}
