import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public class LoginMenu extends JFrame implements MouseListener {

    private Controller mainController;
    private ImagePanel logo;
    private JButton createNewAccBut = new JButton();
    private JButton deleteAccBut = new JButton();
    private JButton changePassBut = new JButton();
    private JPanel logorexit = new JPanel();
    private JButton login = new JButton("«ورود به بازی»");
    private JButton exit = new JButton("«خروج از بازی»");
    private Font f = new Font("Tahoma", Font.BOLD, 20);

    public LoginMenu(Controller m) throws IOException {
        super("Login Menu");
        mainController = m;
        setLayout(new GridLayout(5,1));
        setSize(new Dimension(540,700));
        setBackground(Color.BLACK);
        setLocation((int) (Toolkit.getDefaultToolkit().getScreenSize().width / 2 - this.getSize().getWidth() / 2)
                , (int) (Toolkit.getDefaultToolkit().getScreenSize().height / 2 - this.getSize().getHeight() / 2));
        initcomponents();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initcomponents() throws IOException {

        //logo.setPreferredSize(new Dimension(540,300));
        //logo.setBackground(Color.black);
        logo = new ImagePanel("login.jpg", new Dimension(540,300));

        createNewAccBut.setPreferredSize(new Dimension(540,50));
        createNewAccBut.setBackground(Color.gray);
        createNewAccBut.setBorder(BorderFactory.createMatteBorder(10,10,10,10,Color.BLACK));
        createNewAccBut.setText("ساخت اکانت جدید");
        createNewAccBut.addMouseListener(this);

        deleteAccBut.setPreferredSize(new Dimension(540,50));
        deleteAccBut.setBackground(Color.gray);
        deleteAccBut.setBorder(BorderFactory.createMatteBorder(10,10,10,10,Color.BLACK));
        deleteAccBut.setText("حذف اکانت");
        deleteAccBut.addMouseListener(this);

        changePassBut.setPreferredSize(new Dimension(540,50));
        changePassBut.setBackground(Color.gray);
        changePassBut.setBorder(BorderFactory.createMatteBorder(10,10,10,10,Color.BLACK));
        changePassBut.setText("تغییر رمز عبور");
        changePassBut.addMouseListener(this);

        createNewAccBut.setFont(f);
        deleteAccBut.setFont(f);
        changePassBut.setFont(f);

        logorexit.setPreferredSize(new Dimension(540,50));
        logorexit.setBackground(Color.black);
        logorexitInitComponents();

        this.add(logo);
        this.add(createNewAccBut);
        this.add(changePassBut);
        this.add(deleteAccBut);
        this.add(logorexit);
    }

    private void logorexitInitComponents() {

        login.setBackground(Color.BLACK);
        login.setForeground(Color.white);
        login.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
        login.setFont(f);
        login.addMouseListener(this);

        exit.setBackground(Color.BLACK);
        exit.setForeground(Color.white);
        exit.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
        exit.setFont(f);
        exit.addMouseListener(this);

        logorexit.setLayout(new GridLayout(1,2));
        logorexit.add(exit);
        logorexit.add(login);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(exit))
            this.dispose();
        if (e.getSource().equals(login)){
            new UserPassFrame(mainController, mainController.LOGIN);
            return; }
        if (e.getSource().equals(createNewAccBut)){
            new UserPassFrame(mainController, mainController.NEWACC);
            return;}
        if (e.getSource().equals(deleteAccBut)){
            new UserPassFrame(mainController, mainController.DELETEACC);
            return;}
        if (e.getSource().equals(changePassBut)){
            new UserPassFrame(mainController, mainController.CHANGEPASS);
            return;}
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }


    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource().equals(login))
            login.setForeground(Color.GREEN);
        if (e.getSource().equals(exit))
            exit.setForeground(Color.red);
        if (e.getSource().equals(createNewAccBut))
            createNewAccBut.setBackground(Color.lightGray);
        if (e.getSource().equals(changePassBut))
            changePassBut.setBackground(Color.lightGray);
        if (e.getSource().equals(deleteAccBut))
            deleteAccBut.setBackground(Color.lightGray);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource().equals(login))
            login.setForeground(Color.white);
        if (e.getSource().equals(exit))
            exit.setForeground(Color.white);
        if (e.getSource().equals(createNewAccBut))
            createNewAccBut.setBackground(Color.gray);
        if (e.getSource().equals(changePassBut))
            changePassBut.setBackground(Color.gray);
        if (e.getSource().equals(deleteAccBut))
            deleteAccBut.setBackground(Color.gray);
    }
}
