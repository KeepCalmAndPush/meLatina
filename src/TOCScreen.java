
import java.util.Vector;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Graphics;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Администратор
 */
public class TOCScreen extends Canvas {

    private final int GRID_W_COUNT = 6;
    private final int GRID_H_COUNT = 4;

    private int letterContainerWidth;
    private int letterContainerHeight;

    public TOCScreen(Vector proverbsClusters) {
        super();
        this.proverbsClusters = proverbsClusters;
        Command selectionCommand = new Command("Выбор", Command.ITEM, 1);
        Command exitCommand = new Command("Выход", Command.EXIT, 2);

        addCommand(exitCommand);
        addCommand(selectionCommand);
    }

    private Vector proverbsClusters;
    private int selectedIndex = 0;

    public void paint(Graphics g) {
        drawTableOfContents(g);
    }

    private void drawTableOfContents(Graphics g) {
        g.setColor(0x00FFFFFF);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(0x00000000);

        int width = getWidth();
        int height = getHeight();

        letterContainerWidth = width / GRID_W_COUNT;
        letterContainerHeight = height / GRID_H_COUNT;

        int insetsW = (width - letterContainerWidth * GRID_W_COUNT) / 2;
        int insetsH = (height - letterContainerHeight * GRID_H_COUNT) / 2;

        cycle: for (int h = 0; h < GRID_H_COUNT; h++) {
            for (int w = 0; w < GRID_W_COUNT; w++) {
                int currenIndex = w + GRID_W_COUNT * h;
                if (currenIndex == proverbsClusters.size()) {
                    break cycle;
                }

                int originX = w * letterContainerWidth + insetsW;
                int originY = h * letterContainerHeight + insetsH;

                ProverbsCluster cluster = (ProverbsCluster)proverbsClusters.elementAt(currenIndex);
                String str = cluster.getLetter();

                boolean selected = currenIndex == getSelectedIndex();
                drawLetter(str, originX, originY, selected, g);
            }
            }
        }

    private void drawLetter(String letter, int x, int y, boolean selected, Graphics g){
        int currentColor = g.getColor();
        int bgColor = selected ? 0x00FF0000 : 0x00FFFFFF;
        int letterColor = selected ? 0x00FFFFFF : 0x00FF0000;
        g.setColor(bgColor);
        g.fillRect(x, y, letterContainerWidth, letterContainerHeight);

        int letterWidth = g.getFont().stringWidth(letter);
        int letterHeight = g.getFont().getHeight();
        int letterX = x + ((letterContainerWidth - letterWidth) / 2);
        int letterY = y + ((letterContainerHeight - letterHeight) / 2);
        g.setColor(letterColor);
        g.drawString(letter, letterX, letterY, 0);
        g.setColor(currentColor);
    }

    protected void keyPressed(int keyCode) {
        int minIndex = 0;
        int maxIndex = proverbsClusters.size() - 1;
        int action = getGameAction(keyCode);
        boolean topRow = selectedIndex < GRID_W_COUNT;
        boolean bottomRow = selectedIndex + GRID_W_COUNT >= GRID_W_COUNT * GRID_W_COUNT;

        switch (action) {
            case UP:
                if(!topRow){
                    setSelectedIndex(Math.max(getSelectedIndex() - GRID_W_COUNT, minIndex));
                }
                break;
            case DOWN:
                if(!bottomRow){
                    setSelectedIndex(Math.min(getSelectedIndex() + GRID_W_COUNT, maxIndex));
                }
                break;
            case LEFT:
                 setSelectedIndex(Math.max(getSelectedIndex() - 1, minIndex));
                break;
            case RIGHT:
                setSelectedIndex(Math.min(getSelectedIndex() + 1, maxIndex));
                break;
            default:
                return;
        }

        repaint();
    }

    /**
     * @return the selectedIndex
     */
    public int getSelectedIndex() {
        return selectedIndex;
    }

    /**
     * @param selectedIndex the selectedIndex to set
     */
    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }
    }