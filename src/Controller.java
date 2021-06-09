
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Controller implements Serializable{

    private static ArrayList<User> users = new ArrayList<>();
    public static final int USERNOTFOUND = 0, INCORRECTPASSWORD = 1, MATCHED = 2, SAMEUSER = 4;
    public static final int LOGIN = 0, NEWACC = 1, DELETEACC = 2, CHANGEPASS = 3, OPPONENT = 4;
    private static HashMap<String, String> userPass = new HashMap<>();
    private LoginMenu loginFrame;
    private MainMenu mainMenu = null;
    private Game game;
    private User mainUser, secondUser;

    public Controller() throws IOException {
        //todo play music
        //todo after adding A NEW user add it to the hashmap
        loadUsers();
        loginFrame = new LoginMenu(this);
    }

    public static void main(String[] args) throws IOException {
        new Controller();
        //todo save users after password change and after game winnings
    }

    public static void saveUsers() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("Users.xo");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(users);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void loadUsers() {
        try{
            FileInputStream fileInputStream = new FileInputStream("Users.xo");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            users = (ArrayList<User>)objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException | ClassNotFoundException ignored) {
        }
        for (User user: users){
            userPass.put(user.getUsername(), user.getPassword());
        }
    }

    public int attempt(String text, char[] password) {
        if (userPass.isEmpty())
            return USERNOTFOUND;
        if (userPass.containsKey(text)) {
            if (userPass.get(text).equals(String.copyValueOf(password))) {
                if (mainMenu != null)
                    if (mainMenu.getMainUser().getUsername().equals(text))
                        return SAMEUSER;
                return MATCHED;
            }
            return INCORRECTPASSWORD;
        }
        return USERNOTFOUND;
    }

    public void successfulLogin(String text) throws IOException {
        loginFrame.setVisible(false); //todo بعد از اتمام بازی باید دوباره به این جا برگردیم
        for (User u : users)
            if (u.getUsername().equals(text))
                mainMenu = new MainMenu(this, u);
    }

    public void addNewAccount(String u, char[] password) {
        users.add(new User(this, u, String.valueOf(password)));
        userPass.put(u, String.valueOf(password));
        saveUsers();
    }

    public void deleteAccount(String u, char[] password) {
        int i = -1;
        userPass.remove(u);
        for (User s : users)
            if (s.getUsername().equals(u))
                i = users.indexOf(s);
        users.remove(i);
        saveUsers();
    }

    public void changePassword(String u, char[] password) {
        userPass.remove(u);
        userPass.put(u, String.valueOf(password));
        for (User s : users)
            if (s.getUsername().equals(u))
                s.setPassword(password);
        saveUsers();
    }

    public void startTheGame(String u, int rounds, int undos) {
        for (User s : users)
            if (s.getUsername().equals(u)){
                new Game(this, mainMenu.getMainUser(), s, rounds, undos);
                mainMenu.setVisible(false);
                secondUser = s;
            }
        mainUser = mainMenu.getMainUser();
    }

    public void logout() {
        mainMenu.setMainUser(null);
        mainUser = null;
        secondUser = null;
        loginFrame.setVisible(true);
    }

    public void gameIsOver(int state){
        mainMenu.setVisible(true);
        mainUser.setScore(state);
        secondUser.setOpponentScore(state);
        saveUsers();
    }

    public ArrayList<String> sortUsers(){
        users.sort((o1 , o2) -> {
            if(o1.getScore() != o2.getScore()) return o2.getScore() - o1.getScore();
            else if(o1.getWins() != o2.getWins()) return o2.getWins() - o1.getWins();
            else if(o1.getTies() != o2.getTies()) return o2.getTies() - o1.getTies();
            else if(o1.getLoses() != o2.getLoses()) return o1.getLoses() - o2.getLoses();
            else return o1.getUsername().toLowerCase().compareTo(o2.getUsername().toLowerCase());
        });
        ArrayList <String> sorted = new ArrayList<>();
        sorted.add("Username: score | wins | ties | loses");
        sorted.add("\n______________________________\n");
        for (User u: users ) {
            sorted.add(u.getUsername() + " : " + u.getScore() +" | " + u.getWins() +" | " + u.getTies() +" | " + u.getLoses());
        }
        return sorted;
    }

}
