import java.io.Serializable;

public class User implements Serializable {

    private Controller mainController;
    private int wins = 0;
    private int ties = 0;
    private int loses = 0;
    private int score = 0;
    private String userName;
    private String password;

    public User(Controller cnt, String userName, String password) {
        mainController = cnt;
        this.userName = userName;
        this.password = password;

    }

    public String getUsername() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public int getWins() {
        return wins;
    }

    public int getTies() {
        return ties;
    }

    public int getLoses() {
        return loses;
    }

    public int getScore() {
        return 2*wins + ties - loses;
    }

    public void setPassword(char[] password) {
        this.password = String.valueOf(password);
    }

    public void setScore(int state) {
        if (state == Game.LOSE){
            loses++;
        }
        else if (state == Game.WIN){
            wins++;
        }
        else{
            ties++;
        }
    }

    public void setOpponentScore(int state) {
        if (state == Game.LOSE){
            wins++;
        }
        else if (state == Game.WIN){
            loses++;
        }
        else{
            ties++;
        }
    }
}
