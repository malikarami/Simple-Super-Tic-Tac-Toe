import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class MainMenu extends JFrame implements MouseListener {

    private User mainUser;
    private Controller mainController;
    private ImagePanel logo;
    private JButton newGame = new JButton();
    private JButton scoreBoard = new JButton();
    private JButton exit = new JButton();
    private Font f = new Font("Tahoma", Font.BOLD, 20);

    public MainMenu(Controller controller, User user) throws IOException {
        super("Main Menu");
        mainController = controller;
        mainUser = user;
        setLayout(new GridLayout(4,1));
        setSize(new Dimension(500,700));
        setBackground(Color.BLACK);
        setLocation((int) (Toolkit.getDefaultToolkit().getScreenSize().width / 2 - this.getSize().getWidth() / 2)
                , (int) (Toolkit.getDefaultToolkit().getScreenSize().height / 2 - this.getSize().getHeight() / 2));
        initcomponents();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initcomponents() throws IOException {
        //logo.setPreferredSize(new Dimension(500,400));
        //logo.setBackground(Color.black);

        logo = new ImagePanel("MainMenu.jpg",new Dimension(500,200));

        newGame.setPreferredSize(new Dimension(500,100));
        newGame.setBackground(Color.gray);
        newGame.setBorder(BorderFactory.createMatteBorder(10,10,10,10,Color.BLACK));
        newGame.setText("شروع بازی جدید");
        newGame.setFont(f);
        newGame.addMouseListener(this);
        scoreBoard.setPreferredSize(new Dimension(500,100));
        scoreBoard.setBackground(Color.gray);
        scoreBoard.setBorder(BorderFactory.createMatteBorder(10,10,10,10,Color.BLACK));
        scoreBoard.setText("جدول امتیازات");
        scoreBoard.setFont(f);
        scoreBoard.addMouseListener(this);
        exit.setPreferredSize(new Dimension(500,100));
        exit.setBackground(Color.gray);
        exit.setForeground(Color.red);
        exit.setBorder(BorderFactory.createMatteBorder(10,10,10,10,Color.BLACK));
        exit.setText("خروج از حساب کاربری");
        exit.setFont(f);
        exit.addMouseListener(this);
        add(logo);
        add(newGame);
        add(scoreBoard);
        add(exit);
    }

    public void setMainUser(User mainUser) {
        this.mainUser = mainUser;
    }


    public User getMainUser() {
        return mainUser;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(exit)){
            this.dispose();
            mainController.logout();
        }
        else if (e.getSource().equals(newGame)){
            new UserPassFrame(mainController, mainController.OPPONENT);
        }
        else
            new ScoreBoard(mainController, this);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource().equals(newGame))
            newGame.setBackground(Color.lightGray);
        if (e.getSource().equals(scoreBoard))
            scoreBoard.setBackground(Color.lightGray);
        if (e.getSource().equals(exit))
            exit.setBackground(Color.lightGray);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource().equals(newGame))
            newGame.setBackground(Color.gray);
        if (e.getSource().equals(scoreBoard))
            scoreBoard.setBackground(Color.gray);
        if (e.getSource().equals(exit))
            exit.setBackground(Color.gray);
    }
}
