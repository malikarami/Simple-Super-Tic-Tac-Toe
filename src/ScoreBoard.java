import javax.swing.*;
import java.awt.*;

public class ScoreBoard extends JFrame {

    private Controller controller;

    public ScoreBoard(Controller mainController, MainMenu mainMenu) {
        super("Score Bored");
        controller = mainController;
        setLayout(new BorderLayout());
        setSize(new Dimension(500,500));
        setBackground(Color.BLACK);
        setLocation((int) (Toolkit.getDefaultToolkit().getScreenSize().width / 2 - this.getSize().getWidth() / 2)
                , (int) (Toolkit.getDefaultToolkit().getScreenSize().height / 2 - this.getSize().getHeight() / 2));
        initcomponents();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void initcomponents() {
        JList scores = new JList(controller.sortUsers().toArray());
        scores.setBackground(Color.black);
        scores.setForeground(Color.pink);
        scores.setOpaque(true);
        JScrollPane scrollPane = new JScrollPane(scores);
        scrollPane.setBackground(Color.black);
        scrollPane.setOpaque(true);
        scrollPane.setForeground(Color.PINK);
        scores.setFont(new Font("Tahoma", Font.BOLD, 15));
        this.add(scrollPane, BorderLayout.CENTER);
    }


}
