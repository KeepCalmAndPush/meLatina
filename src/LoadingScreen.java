
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Администратор
 */
public class LoadingScreen extends Canvas {

    private int progress = 0;
    private int barColor = 0x00FF0000;
    private String title = "  Загрузка...";

    public LoadingScreen(String tiddle) {
        super();

        this.title = tiddle;
    }

    /**
     * @return the progress
     */
    public int getProgress() {
        return progress;
    }

    /**
     * @param progress the progress to set
     */
    public void setProgress(int progress) {
        this.progress = progress;
        repaint();
    }

    protected void paint(Graphics g) {
        g.setColor(0x00FFFFFF);
        g.fillRect(0, 0, getWidth(), getHeight());

        int BAR_HEIGHT = 5;
        int TITLE_TO_BAR_SPACE = 0;
        int BAR_H_OFFSET = 10;

        int barOriginY = (getHeight() - BAR_HEIGHT) / 2;

        if (title != null) {
            g.setColor(0);
            int titleHeight = g.getFont().getHeight();
            int titleWidth = g.getFont().stringWidth(title);

            int originX = (getWidth() - titleWidth) / 2;
            int originY = (getHeight() - titleHeight - TITLE_TO_BAR_SPACE - BAR_HEIGHT) / 2;
            barOriginY += titleHeight + TITLE_TO_BAR_SPACE;

            g.drawString(title, originX, originY, 0);
        }
        g.setColor(barColor);
        int barWidth = getWidth() - BAR_H_OFFSET - BAR_H_OFFSET;
        g.drawRect(10, barOriginY, barWidth, BAR_HEIGHT);
        int filledWidth = (barWidth * progress / 100);
        filledWidth = Math.min(filledWidth,  barWidth);
        g.fillRect(10, barOriginY, filledWidth, BAR_HEIGHT);
    }

    /**
     * @return the barColor
     */
    public int getTintColor() {
        return barColor;
    }

    /**
     * @param barColor the barColor to set
     */
    public void setTintColor(int tintColor) {
        this.barColor = tintColor;
        repaint();
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
        repaint();
    }
}
