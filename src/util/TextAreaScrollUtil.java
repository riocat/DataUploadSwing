package util;

import javax.swing.*;

/**
 * Created by rio on 2018/10/24.
 */
public class TextAreaScrollUtil {

    public static void TextAreaScroll(JTextArea textArea,String text){
        textArea.append(text + "\r\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
}
