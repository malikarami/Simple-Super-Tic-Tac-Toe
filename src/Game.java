import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Game {

    public static final int CURRENTTURNLIMIT = 1, ALLOWED = 0, NOTALLOWED = -1, UNDOLIMIT =2;
    public static final int DRAW = 0, WIN = 1 , LOSE = -1;
    private Controller controller;
    private HashMap<Integer, String > winBoards = new HashMap<>();
    private String[][] gameMap = new String[9][9];
    private int moveBoard;
    private int moveCell;
    private User mainUser;
    private User secondUser;
    private GameFrame gameFrame;
    //private GameFrame savedFrame;
    private boolean waitOnNextTurn = false;
    private int currentRound = 0; //zoj: O fard: X
    private int currentBoard = 9;
    private int previousBoard = 9;
    private int player1Score = 0;
    private int player2Score = 0;
    private int winLimit = 0, winCount = 0;
    private int undoLimit = 0, undoCount1 = 0, undoCount2 = 0, undostate = NOTALLOWED;
    private int winState = -1;

    public Game(Controller cnt, User mainUser, User secondUser, int rounds, int undos) {
        controller = cnt;
        this.mainUser = mainUser;
        this.secondUser = secondUser;
        winLimit = rounds;
        undoLimit = undos;
        gameFrame = new GameFrame(this);
        for (int i = 0; i < 9 ; i++) {
            for (int j = 0; j < 9 ; j++) {
                gameMap[i][j] = String.valueOf(j);
            }
        }
    }

    public boolean getWaitOnNextTurn() {
        return waitOnNextTurn;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public int getWinLimit() {
        return winLimit;
    }

    public int getUndostate() {
        return undostate;
    }

    public ArrayList<String> getUsernames(){
        ArrayList<String> usernames = new ArrayList<>();
        usernames.add(mainUser.getUsername());
        usernames.add(secondUser.getUsername());
        return usernames;
    }

    public void setCurrentBoard(int b) {
        currentBoard = b;
    }

    public boolean isValidCell(int boardNumber) { //یعنی سلول انتخاب شده از برد درستی انتخاب شده
        if (waitOnNextTurn)
            return false;
        if (winBoards.containsKey(boardNumber))
            return false;
        if (currentBoard == 9)
            return true;
        if (currentBoard == boardNumber)
            return true;
        return false;
    }

    public String newMove(int b, int c) { //شماره ی b را قبلا چک کردیم پس مطمئنیم که از بردهای پر شده نیست
        previousBoard = currentBoard;
        winState = -1;
        moveBoard = b;
        moveCell = c;
        gameFrame.setBoardsBackToNormal();
        if (currentRound %2 == 0) {
            gameMap[b][c] = "O";
        }else {
            gameMap[b][c] = "X";
        }
        if (isFull(b)) {
            winState = b;
            winCount++;
            winBoards.put(b, gameMap[b][c]);
            setCurrentBoard(9);
            adjustGraphicsToAWin(b, gameMap[b][c]);
        }
        else if (!winBoards.containsKey(c))
            setCurrentBoard(c);
        else
            setCurrentBoard(9);
        waitOnNextTurn = true;
        gameFrame.showNextTurnButton();
        if (undostate != CURRENTTURNLIMIT && undostate != UNDOLIMIT && undoLimit !=0)
            undostate = ALLOWED;
        isGameFinished();
        gameFrame.ultimateColorCheck();
        return gameMap[b][c];
    }

    private void isGameFinished() {
        //boolean f = true;
        if (winCount >= winLimit && winLimit != 0)
            finishGame(false, true);
        if (winBoards.size() == 9)
            finishGame(false, false);
        for (int i = 0; i < 9; i++) {
            if (!winBoards.containsKey(i)){
                for (int j = 0; j < 9; j++) {
                    if (!gameMap[i][j].equals("X") && !gameMap[i][j].equals("O"))
                        return;
                }
            }
        }
        finishGame(false, false);
    }

    public void finishGame(boolean exitButton, boolean winLimit) {
        int state;
        if (winLimit)
            JOptionPane.showMessageDialog(null, "به محدودیت تعداد دورهای بازی رسیدید", "بازی پایان یافت", JOptionPane.ERROR_MESSAGE);
        String winner;
        if (player1Score > player2Score){
            winner = " برنده ی بازی " + this.getUsernames().get(0);
            state = WIN;
        }
        else if (player1Score < player2Score){
            winner = " برنده ی بازی " + this.getUsernames().get(1);
            state = LOSE;
        }
        else{
            winner = " بازی مساوی به پایان رسید ";
            state = DRAW;
        }
        if (exitButton){
            winner = " برنده ی بازی "+ this.getUsernames().get((currentRound)%2);
            if (currentRound%2 == 0)
                state = WIN;
            else
                state = LOSE;
        }
        JOptionPane.showMessageDialog(null, winner, "WINNER", JOptionPane.INFORMATION_MESSAGE);
        gameFrame.dispose();
        controller.gameIsOver(state);
    }

    private void adjustGraphicsToAWin(int b, String xo) {
        gameFrame.changeBoard(b, xo);
        gameFrame.ultimateColorCheck();
        if (xo.equals("X"))
            player1Score++;
        else if (xo.equals("O"))
            player2Score++;
        gameFrame.updateScores(winCount,player1Score, player2Score);
    }

    private Boolean isFull(int b) {
        if (checkA(b)) //عمودی
            return true;
        if (checkO(b)) //افقی
            return true;
        if (checkM(b)) //مورب
            return true;
        return false;
    }

    private boolean checkM(int b) {
        int i = 4;
        if(gameMap[b][i].equals(gameMap[b][2]))
            if (gameMap[b][i].equals(gameMap[b][6]))
                return true;
        if(gameMap[b][i].equals(gameMap[b][0]))
            if (gameMap[b][i].equals(gameMap[b][8]))
                return true;
        return false;
    }

    private boolean checkO(int b) {
        for (int i = 0 ; i <= 6  ; i+=3)
            if(gameMap[b][i].equals(gameMap[b][i+1]))
                if (gameMap[b][i].equals(gameMap[b][i+2]))
                    return true;
        return false;
    }

    private boolean checkA(int b) {
        for (int i = 0 ; i < 3 ; i++)
            if(gameMap[b][i].equals(gameMap[b][i+3]))
                if (gameMap[b][i].equals(gameMap[b][i+6]))
                    return true;
        return false;
    }


    public void undo() {
        undostate = CURRENTTURNLIMIT;
        int undoCount;
        if (currentRound %2 == 0){
            undoCount2++;
            undoCount = undoCount2;
        }else {
            undoCount1++;
            undoCount = undoCount1;
        }
        if (undoCount >= undoLimit)
            undostate = UNDOLIMIT;
        gameFrame.updateUndoButton();
        waitOnNextTurn = false;
        gameMap[moveBoard][moveCell] = String.valueOf(moveCell);
        gameFrame.refreshUndoMove(gameMap[moveBoard], moveBoard);
        gameFrame.ultimateColorCheck();
        if (winState > 0){
            winBoards.remove(moveBoard);
            winCount--;
            if (currentRound %2 == 0) {
                player2Score--;
            }
            else {
                player1Score--;
            }
            winState = -1;
        }
        currentBoard = previousBoard;
        gameFrame.showCurrentBoard(currentBoard);
        gameFrame.updateScores(winCount,player1Score, player2Score);
        gameFrame.ultimateColorCheck();
    }

    public void nextTurn() {
        currentRound++;
        waitOnNextTurn = false;
        undostate = NOTALLOWED;
        gameFrame.showCurrentBoard(currentBoard);
        gameFrame.updateUndoButton();
        gameFrame.ultimateColorCheck();
    }

    public String getCurrentUndo() {
        if (currentRound %2 == 0)
            return String.valueOf(undoCount2);
        else
            return String.valueOf(undoCount1);
    }

    public String getUndoLimit() {
        return String.valueOf(undoLimit);
    }
}
