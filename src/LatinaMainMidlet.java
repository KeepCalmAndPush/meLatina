/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Vector;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.*;

/**
 * @author Администратор
 */
public class LatinaMainMidlet extends MIDlet implements CommandListener {
    private Display display;
    private TOCScreen tocSreen;
    private ProverbsClusterBrowser proverbsBrowser;
    private WordScreen exitAlert;
    
    private String[] fileNames = {"A","B","C", "D","E","F","G","H","I","J","L","M","N","O","P","Q","R","S","T","U","V",};
    private Vector proverbsClusters = new Vector(fileNames.length);
    
    public LatinaMainMidlet() {
        super();
        display = Display.getDisplay(this);
    }

    public void startApp() {
        showTableOfContents();
        for (int i = 0; i < fileNames.length; i++) {
            String letter = fileNames[i];
            ProverbsCluster cluster = new ProverbsCluster(letter, this);
            proverbsClusters.addElement(cluster);
        }
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    private void showTableOfContents() {
        tocSreen = new TOCScreen(proverbsClusters);
        tocSreen.setCommandListener(this);

        display.setCurrent(tocSreen);
    }

    public void commandAction(Command c, Displayable d) {
        if (d == tocSreen) {
            handleTOCCommand(c);
        } else if (d == proverbsBrowser) {
            handleProverbsBrowserCommand(c);
        } else if (d == exitAlert) {
            handleAlertCommand(c);
        }
    }

    private void handleTOCCommand(Command c) {
        if (c.getCommandType() == Command.EXIT) {
            showExitAlert();
        } else if (c.getCommandType() == Command.ITEM) {
            gotoProverbsBrowser();
        }
    }

    private void handleProverbsBrowserCommand(Command c) {
        if (c.getCommandType() == ProverbsClusterBrowser.COMMAND_TOC) {
            display.setCurrent(tocSreen);
        }
    }

    private void handleAlertCommand(Command c) {
        if (c.getCommandType() == Command.EXIT) {
            notifyDestroyed();
        } else if (c.getCommandType() == Command.BACK) {
           display.setCurrent(tocSreen);
        }
    }

    private void showExitAlert() {
        exitAlert = new WordScreen("Выйти?");
        Command exitCommand = new Command("Да", Command.EXIT, 1);
        Command stayCommand = new Command("Нет", Command.BACK, 2);
        exitAlert.addCommand(exitCommand);
        exitAlert.addCommand(stayCommand);
        exitAlert.setCommandListener(this);

        display.setCurrent(exitAlert);
    }

    private void gotoProverbsBrowser() {
        ProverbsCluster cluster = (ProverbsCluster) proverbsClusters.elementAt(tocSreen.getSelectedIndex());
        proverbsBrowser = new ProverbsClusterBrowser(cluster, this);
        display.setCurrent(proverbsBrowser);
    }
}
