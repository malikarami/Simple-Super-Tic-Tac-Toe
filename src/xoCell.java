import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class xoCell extends JButton implements MouseListener {

    private Game mainGame;
    private int cellNumber;
    private int boardNumber;
    private Font f = new Font("Arial", Font.BOLD, 20);

    public xoCell(Game g, int c, int b) {
        mainGame = g;
        cellNumber = c;
        boardNumber = b;
        setSize(new Dimension(60,60));
        setBackground(Color.BLACK);
        setForeground(Color.red);
        setFont(f);
        //setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.gray));
        Border outerB = BorderFactory.createMatteBorder(1,1,1,1, Color.black);
        Border inerB = BorderFactory.createMatteBorder(1,1,1,1, Color.gray);
        this.setBorder(BorderFactory.createCompoundBorder(outerB, inerB));
        this.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(this)){
            if (!this.getText().equals("X") && !this.getText().equals("O") ) {
                if (!mainGame.isValidCell(boardNumber))
                    return;
                String s = mainGame.newMove(boardNumber, cellNumber);
                if (s.equals("O"))
                    setForeground(Color.cyan);
                else
                    setForeground(Color.red);
                this.setText(s);
                this.setVisible(true);
            }
        }
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

    public void delMouseListener() {
        this.removeMouseListener(this);
    }
}
