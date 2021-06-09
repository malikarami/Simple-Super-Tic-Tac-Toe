import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class UserPassFrame extends JFrame {

    private Controller mainController;
    private JTextField username = new JTextField();
    private JPasswordField password = new JPasswordField();
    private JPasswordField newPassword = new JPasswordField();
    private JLabel tag = new JLabel("نام کاربری و رمزعبور خود را وارد کنید", SwingConstants.CENTER);
    private JLabel title = new JLabel("", SwingConstants.CENTER);
    private JButton confirm = new JButton("«تایید»");
    private Font f = new Font("Tahoma", Font.BOLD, 15);
    private Font f2 = new Font("Tahoma", Font.BOLD, 20);
    private int mood = -1;

    public UserPassFrame(Controller mainController, int t) {
        this.mainController = mainController;
        mood = t;
        if (mood == mainController.CHANGEPASS){
        setSize(new Dimension(400,500));
            setLayout(new GridLayout(6,1));
            tag.setText("نام کاربری، رمز قدیم و رمز جدید خود را وارد کنید");
        } else{
            setSize(new Dimension(400,400));
            setLayout(new GridLayout(5,1));
        }
        if (mood == mainController.OPPONENT)
            tag.setText("نام کاربری و رمز عبور بازیکن دوم را وارد کنید");
        setBackground(Color.gray);
        setLocation((int) (Toolkit.getDefaultToolkit().getScreenSize().width / 2 - this.getSize().getWidth() / 2)
                , (int) (Toolkit.getDefaultToolkit().getScreenSize().height / 2 - this.getSize().getHeight() / 2));
        initComponents();
        addComponents();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private void addComponents() {
        add(title);
        add(tag);
        add(username);
        add(password);
        if (mood == mainController.CHANGEPASS)
            add(newPassword);
        add(confirm);
    }

    private void initComponents() {
        title.setFont(f2);
        title.setBackground(Color.gray);
        title.setForeground(Color.red);
        title.setOpaque(true);
        title.setSize(new Dimension(400,100));
        if (mood == mainController.LOGIN)
            title.setText("ورود به حساب کاربری");
        else if (mood == mainController.NEWACC)
            title.setText("ایجاد حساب کاربری جدید");
        else if (mood == mainController.CHANGEPASS)
            title.setText("تغییر رمز عبور");
        else if (mood == mainController.OPPONENT)
            title.setText("انتخاب حریف");
        else
            title.setText("حذف حساب کاربری");

        tag.setFont(f);
        tag.setBackground(Color.gray);
        tag.setForeground(Color.black);
        tag.setOpaque(true);
        tag.setSize(new Dimension(400,100));

        username.setSize(new Dimension(400,100));
        username.setBorder(BorderFactory.createMatteBorder(20,25,25,20,Color.gray));

        password.setSize(new Dimension(400,100));
        password.setBorder(BorderFactory.createMatteBorder(20,25,25,20,Color.gray));

        newPassword.setSize(new Dimension(400,100));
        newPassword.setBorder(BorderFactory.createMatteBorder(20,25,25,20,Color.gray));

        confirm.setBackground(Color.gray);
        confirm.setForeground(Color.black);
        confirm.setFont(f);
        confirm.setSize(new Dimension(400,100));
        confirm.addActionListener(I-> {
                int state = mainController.attempt(username.getText(), password.getPassword());
                    if (state == mainController.MATCHED){
                        if (mood == mainController.LOGIN){
                            JOptionPane.showMessageDialog(null,"ورود با موفقیت انجام شد","Successful Login", JOptionPane.INFORMATION_MESSAGE);
                            try {
                                mainController.successfulLogin(username.getText());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            this.dispose();
                        }
                        else if(mood == mainController.NEWACC){
                            JOptionPane.showMessageDialog(null,"نام کاربری موجود است","ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                        else if (mood == mainController.OPPONENT){
                            //JOptionPane.showMessageDialog(null,"حریف با موفقیت انتخاب شد","Opponent", JOptionPane.INFORMATION_MESSAGE);
                            int rounds = Integer.valueOf(JOptionPane.showInputDialog(null, "تعداد دورهای بازی را وارد کنید"));
                            while (rounds > 9)
                                rounds = Integer.valueOf(JOptionPane.showInputDialog(null, "تعداد دورها نمیتواند از 9 بیشتر باشد"));
                            int undos = Integer.valueOf(JOptionPane.showInputDialog(null, "تعداد دفعات undo را وارد کنید"));
                            while ((undos > rounds && rounds != 0) || undos >= 9) {
                                if (undos >= 9) {
                                    undos = Integer.valueOf(JOptionPane.showInputDialog(null, "تعداد دفعات undo نمیتواند بیشتر از 10 باشد"));
                                    continue;
                                }
                                undos = Integer.valueOf(JOptionPane.showInputDialog(null, "تعداد دفعات undo نمیتواند از دورها بیشتر باشد"));
                            }
                            mainController.startTheGame(username.getText(), rounds, undos);
                            this.dispose();
                        }
                        else if (mood == mainController.CHANGEPASS){
                            JOptionPane.showMessageDialog(null,"رمز عبور با موفقیت تغییر یافت","Change Password", JOptionPane.INFORMATION_MESSAGE);
                            mainController.changePassword(username.getText(), newPassword.getPassword());
                            this.dispose();
                        }
                        else{
                            JOptionPane.showMessageDialog(null,"حساب کاربری با موفقیت حذف شد","Delete Account", JOptionPane.INFORMATION_MESSAGE);
                            mainController.deleteAccount(username.getText(), password.getPassword());
                            this.dispose();
                        }
                    }
                    else if (state == mainController.USERNOTFOUND){
                        if(mood == mainController.NEWACC){
                            JOptionPane.showMessageDialog(null,"ثبت نام کاربر با موفقیت انجام شد","Sign Up", JOptionPane.INFORMATION_MESSAGE);
                            mainController.addNewAccount(username.getText(), password.getPassword());
                            this.dispose();
                            return;
                        }
                        JOptionPane.showMessageDialog(null,"نام کاربری یافت نشد","ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                    else if (state == mainController.INCORRECTPASSWORD){
                        if(mood == mainController.NEWACC){
                            JOptionPane.showMessageDialog(null,"نام کاربری موجود است","ERROR", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        JOptionPane.showMessageDialog(null,"رمز عبور اشتباه است","ERROR", JOptionPane.ERROR_MESSAGE);}
                    else
                        JOptionPane.showMessageDialog(null,"حریفی غیر از خود انتخاب کنید","ERROR", JOptionPane.ERROR_MESSAGE);
        });
    }
}
