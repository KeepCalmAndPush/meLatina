import java.util.Vector;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Администратор
 */
public class StringUtils {

    private static final String LATIN_LETTERS   = "àáâãäå¸æçèéêëìíîïðñòóôõö÷øùüûúýþÿÀÁÂÃÄÅ¨ÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÜÛÚÝÞß";
    private static final String RUSSIAN_LETTERS = "абвгдеёжзийклмнопрстуфхцчшщьыъэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЬЫЪЭЮЯ";
    
    public static Vector splitDelimiter(String text, char delimiter) {
        Vector splittedString = null;
        StringBuffer buffer = new StringBuffer("");

        if (text != null) {
            splittedString = new Vector();
            for (int i = 0; i < text.length(); i++) {
                char currentChar = text.charAt(i);
                if (currentChar == delimiter) {
                    splittedString.addElement(buffer.toString());
                    buffer = new StringBuffer("");
                } else {
                    int indexOfChar = LATIN_LETTERS.indexOf(currentChar);
                    if (indexOfChar >= 0) {
                        currentChar = RUSSIAN_LETTERS.charAt(indexOfChar);
                    }
                    buffer.append(currentChar);
                }
            }
            splittedString.addElement(buffer.toString());
        }
        return splittedString;
    }
}
