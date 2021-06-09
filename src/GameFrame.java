import javax.swing.*;
import java.awt.*;
public class GameFrame extends JFrame {

    private Game mainGame;
    private Board[] boards = new Board[9];
    private JPanel gameMap = new JPanel();
    private InfoPanel infoPanel;
    private JButton exit = new JButton();

    public GameFrame(Game g)  {
        super("Main Game");
        mainGame = g;
        setLayout(new BorderLayout());
        setSize(new Dimension(540,750));
        setLocation((int) (Toolkit.getDefaultToolkit().getScreenSize().width / 2 - this.getSize().getWidth() / 2)
                , (int) (Toolkit.getDefaultToolkit().getScreenSize().height / 2 - this.getSize().getHeight() / 2));
        infoPanel = new InfoPanel(mainGame);
        initComponents();
        this.add(infoPanel, BorderLayout.NORTH);
        this.add(gameMap, BorderLayout.CENTER);
        this.add(exit, BorderLayout.SOUTH);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initComponents() {
        gameMap.setPreferredSize(new Dimension(540,540));
        gameMap.setLayout(new GridLayout(3,3));
        gameMap.setVisible(true);
        for (int i = 0; i < 9 ; i++) {
            boards[i] = new Board(mainGame, i);
            gameMap.add(boards[i]);
        }
        exit.setPreferredSize(new Dimension(540,50));
        exit.setBackground(Color.black);
        exit.setForeground(new Color(237,75,14));
        exit.setFont(new Font("Arial", Font.BOLD, 20));
        exit.setText("Exit");
        exit.addActionListener(I-> {
            mainGame.finishGame(true, false);
        });
    }


    public void changeBoard(int b, String xo) {
        boards[b].setAllCells(xo);
    }

    public void updateScores(int winCount, int player1, int player2) {
        infoPanel.updateScores(winCount, player1, player2);
    }

    public void showCurrentBoard(int currentBoard) {
        Color c;
        if (mainGame.getCurrentRound() % 2 == 0)
            c = Color.cyan;
        else
            c = Color.red;
        if (currentBoard == 9)
            for (int i = 0; i < 9 ; i++) {
                boards[i].setBorder(BorderFactory.createMatteBorder(2,2,2,2,c));
            }
        else
            boards[currentBoard].setBorder(BorderFactory.createMatteBorder(2,2,2,2,c));
    }

    public void setBoardsBackToNormal() {
        for (int i = 0; i < 9 ; i++) {
            boards[i].setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.black));
        }
    }

    public void showNextTurnButton() {
        infoPanel.showNextTurnButton();
    }


    public void refreshUndoMove(String []gameMap, int b) {
        for (int i = 0; i < 9; i++) {
            if (gameMap[i].equals("X") || gameMap[i].equals("O")){
                boards[b].getCells()[i].setText(gameMap[i]);
                if (gameMap[i].equals("O"))
                    boards[b].getCells()[i].setForeground(Color.CYAN);
            } else
                boards[b].getCells()[i].setText(" ");
            boards[b].getCells()[i].addMouseListener(boards[b].getCells()[i]);
        }
    }

    public void updateUndoButton() {
        infoPanel.getUndo().setText("Undo: "+ mainGame.getCurrentUndo() + "/" + mainGame.getUndoLimit());
    }

    public void ultimateColorCheck() {
        for (int i = 0; i < 9; i++) {
            for (xoCell c: boards[i].getCells()) {
                if (c.getText().equals("X"))
                    c.setForeground(Color.red);
                else
                    c.setForeground(Color.cyan);
            }
        }
    }
}
