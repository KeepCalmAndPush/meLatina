
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Vector;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Администратор
 */
public class ProverbsCluster {
    private String letter;
    private TextProvider provider;
    private static Hashtable sizes;
    {
        sizes = new Hashtable();
        sizes.put("G", new Integer(15));
        sizes.put("U", new Integer(41));
        sizes.put("H", new Integer(45));
        sizes.put("V", new Integer(93));
        sizes.put("I", new Integer(84));
        sizes.put("J", new Integer(10));
        sizes.put("L", new Integer(31));
        sizes.put("M", new Integer(67));
        sizes.put("N", new Integer(106));
        sizes.put("A", new Integer(140));
        sizes.put("O", new Integer(47));
        sizes.put("B", new Integer(30));
        sizes.put("P", new Integer(100));
        sizes.put("C", new Integer(59));
        sizes.put("Q", new Integer(56));
        sizes.put("D", new Integer(54));
        sizes.put("R", new Integer(24));
        sizes.put("E", new Integer(48));
        sizes.put("S", new Integer(135));
        sizes.put("F", new Integer(53));
        sizes.put("T", new Integer(45));
    }
    
    public ProverbsCluster(String letter, Object caller) {
        this.letter = letter;
        String fileName = "/res/" + letter + ".txt";
        try {
            FileReader reader = new FileReader(fileName, null, caller);
            provider = new TextProvider(reader, '$', 128);
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @return the letter
     */
    public String getLetter() {
        return letter;
    }

    public String proverbAtIndex(int index) {
        if (index >= getSize()) {
            return null;
        }
        
        Vector proverbs = provider.getResultsList();
        while (proverbs.size() <= index) {
            try {
                provider.nextElement();
            } catch (IOException ex) {
                return null;
            }
        }
        
        return (String) proverbs.elementAt(index);
    }

    public int getSize() {
        return ((Integer)sizes.get(letter)).intValue();
    }
}
