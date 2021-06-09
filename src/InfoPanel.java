import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InfoPanel extends JPanel implements MouseListener {

    private JLabel player1 = new JLabel("",SwingConstants.CENTER);
    private JLabel player2 = new JLabel("",SwingConstants.CENTER);
    private JLabel gameLimit = new JLabel("",SwingConstants.CENTER);
    private JPanel buttonPanel = new JPanel();
    private JButton undo = new JButton();
    private JButton nextTurn = new JButton();
    private Game mainGame;
    private Font f = new Font("Tahoma", Font.BOLD, 25);
    private Font f2 = new Font("Tahoma", Font.BOLD, 18);

    public InfoPanel(Game g) {
        mainGame = g;
        setLayout(new GridLayout(1,3));
        setPreferredSize(new Dimension(540,120));
        setBackground(Color.BLACK);
        initComponents();
        setVisible(true);
    }

    public JButton getUndo() {
        return undo;
    }

    private void initComponents() {
        playerTagSetting();
        buttonPanelSetting();
        this.add(player1);
        this.add(buttonPanel);
        this.add(player2);
    }

    private void buttonPanelSetting() {
        buttonPanel.setPreferredSize(new Dimension(140,120));
        buttonPanel.setLayout(new GridLayout(3,1));
        Border outerB = BorderFactory.createMatteBorder(3,3,3,3, Color.black);
        Border inerB = BorderFactory.createMatteBorder(1,1,1,1, Color.gray);

        gameLimit.setPreferredSize(new Dimension(140,40));
        gameLimit.setBackground(Color.BLACK);
        gameLimit.setOpaque(true);
        gameLimit.setForeground(Color.ORANGE);
        if (mainGame.getWinLimit() != 0)
            gameLimit.setText("0/"+(mainGame.getWinLimit())+" Rounds");
        else
            gameLimit.setText("No Round Limit");
        gameLimit.setFont(f2);

        undo.setPreferredSize(new Dimension(140,40));
        undo.setBackground(Color.BLACK);
        undo.setForeground(Color.white);
        undo.setBorder(BorderFactory.createCompoundBorder(outerB, inerB));
        undo.setText("Undo: "+ mainGame.getCurrentUndo() + "/" + mainGame.getUndoLimit());
        undo.setFont(f2);
        undo.addMouseListener(this);

        nextTurn.setPreferredSize(new Dimension(140,40));
        nextTurn.setBackground(Color.BLACK);
        nextTurn.setForeground(Color.white);
        nextTurn.setBorder(BorderFactory.createCompoundBorder(outerB, inerB));
        nextTurn.setText("Next Turn");
        nextTurn.setFont(f2);
        nextTurn.addMouseListener(this);

        buttonPanel.add(gameLimit);
        buttonPanel.add(undo);
        buttonPanel.add(nextTurn);

    }

    private void playerTagSetting() {
        player1.setPreferredSize(new Dimension(200, 120));
        player2.setPreferredSize(new Dimension(200,120));
        player1.setBackground(Color.BLACK);
        player2.setBackground(Color.BLACK);
        player1.setOpaque(true);
        player2.setOpaque(true);
        player1.setFont(f);
        player2.setFont(f);
        String str1 = "<html>"+mainGame.getUsernames().get(0)+"<br/>Score : 0<html>";
        String str2 = "<html>"+mainGame.getUsernames().get(1)+"<br/>Score : 0<html>";
        player1.setText(str1);
        player2.setText(str2);
        setPlayersColor();
    }

    public void updateScores(int winCount, int p1, int p2) {
        String str1 = "<html>"+mainGame.getUsernames().get(0)+"<br/>Score : "+(p1)+"<html>";
        String str2 = "<html>"+mainGame.getUsernames().get(1)+"<br/>Score : "+(p2)+"<html>";
        player1.setText(str1);
        player2.setText(str2);
        if (mainGame.getWinLimit() != 0)
            gameLimit.setText(winCount + "/" + mainGame.getWinLimit() + " Rounds");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(nextTurn)){
            if (mainGame.getWaitOnNextTurn()) {
                nextTurn.setBackground(Color.BLACK);
                mainGame.nextTurn();
                setPlayersColor();
            }
            else
                JOptionPane.showMessageDialog(null, "شما هنوز حرکتی انجام نداده اید", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        else{ //undo
            if (mainGame.getUndostate() == mainGame.ALLOWED){
                nextTurn.setBackground(Color.black);
                mainGame.undo();
            }
            else if (mainGame.getUndostate() == mainGame.NOTALLOWED)
                JOptionPane.showMessageDialog(null, "undo مجاز نیست", "ERROR", JOptionPane.ERROR_MESSAGE);
            else if (mainGame.getUndostate() == mainGame.CURRENTTURNLIMIT)
                JOptionPane.showMessageDialog(null, "شما در این دور یک بار از undo استفاده کرده اید", "ERROR", JOptionPane.ERROR_MESSAGE);
            else if (mainGame.getUndostate() == mainGame.UNDOLIMIT)
                JOptionPane.showMessageDialog(null, "شما از مقدار مجاز undo عبور کردید", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setPlayersColor() {
        player1.setForeground(Color.white);
        player2.setForeground(Color.white);
        if (mainGame.getCurrentRound() %2 == 0)
            player2.setForeground(Color.CYAN);
        else
            player1.setForeground(Color.red);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void showNextTurnButton() {
        if (mainGame.getCurrentRound() %2 == 0)
            nextTurn.setBackground(Color.cyan);
        else
            nextTurn.setBackground(Color.red);
    }
}
