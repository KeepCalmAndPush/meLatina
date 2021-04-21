
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Graphics;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Администратор
 */
public class WordScreen extends Canvas{

    private String[] strings = {""};
    private CommandListener listener;
    private int numberOfWordsRequested = 1;

    public WordScreen(String string) {
        super();
        setString(string);
    }

    public WordScreen(String[] strings) {
        super();
        setStrings(strings);
    }
    
    protected void paint(Graphics g) {

        int screenWidth = getWidth();
        int screenHeight = getHeight();

        g.setColor(0x00FFFFFF);
        g.fillRect(0, 0, screenWidth, screenHeight);

        int maxStringWidth = screenWidth - 2 - 2;

        g.setColor(0);

        int stringHeight = g.getFont().getHeight();
        int blockHeight = stringHeight * strings.length;
        blockHeight = Math.max(blockHeight, 1);
        
        int originY = (screenHeight - blockHeight) / 2;

        for (int i = 0; i < strings.length; i++) {
            String currentString = strings[i];
            
            int stringWidth = g.getFont().stringWidth(currentString);
            int originX = (screenWidth - stringWidth) / 2;
            stringWidth = Math.min(maxStringWidth, stringWidth);
            
            g.drawString(currentString, originX, originY, 0);
            originY += stringHeight;
        }
    }

    /**
     * @return the strings
     */
    public String[] getStrings() {
        return strings;
    }

    /**
     * @param strings the strings to set
     */
    public void setStrings(String[] strings) {
        this.strings = strings;
        repaint();
    }

    /**
     * @return the currentString
     */
    public String getString() {
        String str = "";
        for (int i = 0; i < strings.length; i++) {
            str += strings[i];
            if (i < strings.length - 1) {
                str += "\n";
            }
        }
        return str;
    }

    /**
     * @param currentString the currentString to set
     */
    public void setString(String string) {
        String[] stringsArray = {string};
        setStrings(stringsArray);
    }

    protected void keyPressed(int keyCode) {
        if (keyCode == Canvas.KEY_NUM1) {
            numberOfWordsRequested = 1;
        } else if (keyCode == Canvas.KEY_NUM2) {
            numberOfWordsRequested = 2;
        } else if (keyCode == Canvas.KEY_NUM3) {
            numberOfWordsRequested = 3;
        } else if (keyCode == Canvas.KEY_NUM4) {
            numberOfWordsRequested = 4;
        } else if (keyCode == Canvas.KEY_NUM5) {
            numberOfWordsRequested = 5;
        } else {
            return;
        }

        Command command = new Command("", Command.ITEM, 1);
        listener.commandAction(command, this);
    }

    public void setCommandListener(CommandListener l) {
        listener = l;
        super.setCommandListener(l);
    }

    /**
     * @return the numberOfWordsRequested
     */
    public int getNumberOfWordsRequested() {
        return numberOfWordsRequested;
    }
}
