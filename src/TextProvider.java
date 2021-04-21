
import java.io.IOException;
import java.util.Vector;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Администратор
 */
public class TextProvider {
    
    private FileReader reader;
    private Vector resultsList = new Vector();
    private StringBuffer buffer = new StringBuffer();
    private int elementsServed;
    private int bytesReadSize;
    private char delimiter;

    public TextProvider(FileReader reader, char delimiter, int bytesReadSize) {
        super();
        this.reader = reader;
        this.delimiter = delimiter;
        this.bytesReadSize = bytesReadSize;
    }

    public String nextElement() throws IOException {
        if (getElementsServed() < resultsList.size()) {
            int indexToReturn = getElementsServed();
            String nextElement = (String) resultsList.elementAt(indexToReturn);
            setElementsServed(getElementsServed() + 1);
            return nextElement;
        }

        readNextElements();

        return nextElement();
    }

    public Vector readNextElements() {
        String chunk = reader.readNextBytes(getBytesReadSize());
        getBuffer().append(chunk);

        Vector results = StringUtils.splitDelimiter(getBuffer().toString(), getDelimiter());
        String lastElement = (String) results.lastElement();

        if (reader.isReadingCompleted()) {
            for (int i = 0; i < results.size(); i++){
                String element = (String) results.elementAt(i);
                resultsList.addElement(element);
            }
            buffer = new StringBuffer("");
        }
        else if(results.size() > 1) {
            for (int i = 0; i < results.size() - 1; i++){
                String element = (String) results.elementAt(i);
                resultsList.addElement(element);
            }
            buffer = new StringBuffer(lastElement);
        }

        return resultsList;
    }
    
    /**
     * @return the reader
     */
    protected FileReader getReader() {
        return reader;
    }

    /**
     * @return the resultsList
     */
    public Vector getResultsList() {
        return resultsList;
    }

    /**
     * @param resultsList the resultsList to set
     */
    protected void setResultsList(Vector resultsList) {
        this.resultsList = resultsList;
    }

    /**
     * @return the finished
     */
    public boolean isFinished() {
        return reader.isReadingCompleted();
    }

    /**
     * @return the buffer
     */
    public StringBuffer getBuffer() {
        return buffer;
    }

    /**
     * @return the elementsServed
     */
    public int getElementsServed() {
        return elementsServed;
    }

    /**
     * @param elementsServed the elementsServed to set
     */
    public void setElementsServed(int elementsServed) {
        this.elementsServed = elementsServed;
    }

    /**
     * @return the bytesReadSize
     */
    public int getBytesReadSize() {
        return bytesReadSize;
    }

    /**
     * @param bytesReadSize the bytesReadSize to set
     */
    public void setBytesReadSize(int bytesReadSize) {
        this.bytesReadSize = bytesReadSize;
    }
    
    /**
     * @return the delimiter
     */
    public char getDelimiter() {
        return delimiter;
    }

    /**
     * @param delimiter the delimiter to set
     */
    public void setDelimiter(char delimiter) {
        this.delimiter = delimiter;
    }

}
