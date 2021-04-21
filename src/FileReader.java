
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Администратор
 */
public class FileReader {
    private String fileName;
    private String encoding;
    private boolean readingCompleted = false;
//    private StringBuffer content = new StringBuffer(1024);
    private Reader in;
    
    public FileReader(String fileName, String encoding, Object caller) throws UnsupportedEncodingException {
        super();

        this.fileName = fileName;
        this.encoding = encoding;

        if (encoding != null) {
            in = new InputStreamReader(caller.getClass().getResourceAsStream(fileName), encoding);
        } else {
            in = new InputStreamReader(caller.getClass().getResourceAsStream(fileName));
        }
        
    }

    public synchronized String readFile(){
        StringBuffer temp = new StringBuffer(1024);
        while (isReadingCompleted() == false) {
            String chunk = readNextBytes(1024);
            temp.append(chunk);
        }

        return temp.toString();
    }

    public synchronized String readNextBytes(int numberOfBytes) {
        StringBuffer temp = new StringBuffer(numberOfBytes);
        char[] buffer = new char[numberOfBytes];
        int numberOfBytesRead;
        
        try {
            if ((numberOfBytesRead = in.read(buffer, 0, buffer.length)) != -1) {
                temp.append(buffer);
            }
            
            if (numberOfBytesRead < numberOfBytes) {
                readingCompleted = true;
            }

        } catch (IOException ex) {
            return "";
        }
        return temp.toString();
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @return the encoding
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * @return the readingCompleted
     */
    public boolean isReadingCompleted() {
        return readingCompleted;
    }

//    /**
//     * @return the content
//     */
//    public StringBuffer getContent() {
//        return content;
//    }
}
