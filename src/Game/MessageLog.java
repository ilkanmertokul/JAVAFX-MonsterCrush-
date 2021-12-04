package Game;

import javafx.scene.control.Label;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**This class logs the given message and displays on the screen.
 * @author ilkan Mert Okul*/
public class MessageLog extends Label {

    /**Queue to keep data and poll oldest ones.*/
    Queue<String> allMessages = new ConcurrentLinkedQueue<>();

    /**Max lines that our screen can hold.*/
    private final int maxLine = 9;

    /**Current line.*/
    private int currentLine = 0;

    MessageLog(int xLoc, int yLoc){
        this.setTranslateX(xLoc);
        this.setTranslateY(yLoc);
        logMessage("The program has been started.");
    }

    /**Logs the given message and updates it.
     * @param message to log and print on screen.*/
    public void logMessage(String message){
        allMessages.add("L" + (currentLine++) +": " +message + "\n");
        if(allMessages.size() > maxLine) allMessages.poll();
        this.setText(allMessages.toString());
    }

    /**This method cleans all messages and screen.*/
    public void clearLog(){
        currentLine = 0;
        allMessages.clear();
        this.logMessage("Restarted.");
    }
}
