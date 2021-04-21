/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Администратор
 */
import java.util.Enumeration;
import java.util.Vector;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.ItemStateListener;
import javax.microedition.lcdui.StringItem;

public class ProverbsClusterBrowser extends Form implements CommandListener {

    public static final int COMMAND_TOC = Command.EXIT;
    private static final int COMMAND_NEXT = Command.ITEM;
    private static final int COMMAND_PREV = Command.BACK;

    private CommandListener publicComandListener;

    Vector commands = new Vector(3);
    
    private int currentProverbIndex = 0;
    private ProverbsCluster proverbsCluster;
    private StringItem stringItem;
    private int stringItemId;

    public ProverbsClusterBrowser(ProverbsCluster proverbsCluster, CommandListener commandListener) {
        super(null);

        this.proverbsCluster = proverbsCluster;
        this.publicComandListener = commandListener;

        updateDisplayedProverb();

        setCommandListener(this);
        
        setupCommands();
    }

    private void setupCommands() {
        commands = new Vector(3);
        Command nextCommand = null;
        Command prevCommand = null;

        int tocPriority = -1;
        int prevPriority = -1;
        int nextPriority = -1;
        
        int clusterSize = proverbsCluster.getSize();
        boolean isLast = currentProverbIndex == clusterSize - 1;
        boolean isFirst = currentProverbIndex == 0;

        if (isFirst) {
            tocPriority = 3;
            if (isLast == false) {
                nextPriority = 2;
            }
        } else {
            prevPriority = 3;
            if (isLast) {
                tocPriority = 2;
            } else {
                nextPriority = 2;
            }
        }

        if (tocPriority > 0) {
            Command tocCommand = new Command("Главн", COMMAND_TOC, tocPriority);
            commands.addElement(tocCommand);
        }

        if(prevPriority > 0) {
            prevCommand = new Command("Пред", COMMAND_PREV, prevPriority);
            commands.addElement(prevCommand);
        }

        if(nextPriority > 0) {
            nextCommand = new Command("След", COMMAND_NEXT, nextPriority);
            commands.addElement(nextCommand);
        }

        Enumeration elements = commands.elements();
        Command command = null;
        while (elements.hasMoreElements()) {
            command = (Command) elements.nextElement();
            addCommand(command);
        }
    }

    private void clearCommands() {
        Enumeration elements = commands.elements();
        Command command = null;
        while (elements.hasMoreElements()) {
            command = (Command) elements.nextElement();
            removeCommand(command);
        }
    }

    private void updateCommands() {
        clearCommands();
        setupCommands();
    }
    /**
     * @return the publicComandListener
     */
    public CommandListener getPublicComandListener() {
        return publicComandListener;
    }

    /**
     * @param publicComandListener the publicComandListener to set
     */
    public void setPublicComandListener(CommandListener publicComandListener) {
        this.publicComandListener = publicComandListener;
    }

    public void commandAction(Command c, Displayable d) {
        int comandType = c.getCommandType();
        if (comandType == COMMAND_TOC) {
            if (publicComandListener != null) {
                publicComandListener.commandAction(c, d);
            }
        } else if (comandType == COMMAND_PREV) {
            currentProverbIndex = Math.max(currentProverbIndex - 1, 0);
            updateDisplayedProverb();
            updateCommands();
        } else if (comandType == COMMAND_NEXT) {
            currentProverbIndex = Math.min(currentProverbIndex + 1, proverbsCluster.getSize() - 1);
            updateDisplayedProverb();
            updateCommands();
        }
    }

    private void updateDisplayedProverb() {
        if (stringItem != null) {
            delete(stringItemId);
        }
        stringItem = new StringItem(null, null);
        stringItemId = append(stringItem);

        String proverb = proverbsCluster.proverbAtIndex(currentProverbIndex);
        proverb = proverb + " (" + (currentProverbIndex + 1) + "/" + proverbsCluster.getSize() + ")";
        stringItem.setText(proverb);

        Thread nextProverbLoader = new Thread(new Runnable() {
            public void run() {
                int nextProverbIndex = Math.min(proverbsCluster.getSize() - 1, currentProverbIndex + 1);
                proverbsCluster.proverbAtIndex(nextProverbIndex);
            }
        });

        nextProverbLoader.start();
    }
}
