/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Helper {
    
    public static void pause(int timeInSeconds) {
        try {
            TimeUnit.SECONDS.sleep(timeInSeconds);
        } catch (InterruptedException ex) {
            Logger.getLogger(Helper.class.getName()).log(Level.OFF, null, ex);
        }
    }

    public static String removeLastChar(String str) {
        String strNoLastChar = str.substring(0, str.length() - 1);
        return strNoLastChar;
    }

    public static String getLastChar(String str) {
        return str.substring(str.length() - 1);
    }

    public static String removeTrailingNewline(String str) {
        String lastChar = Helper.getLastChar(str);
        return lastChar.equals("\n") ? Helper.removeLastChar(str) : str;
    }

    public static String insertLabelNewlines(String str, int maxWidth) {
        String partialParsedStr = str.replaceAll("(.{" + maxWidth + "})", "$1\n");
        //Regex above will add trailing newlines if the string size is a
        //multiple of maxWidth. These must be removed.
        return Helper.removeTrailingNewline(partialParsedStr);
    }
    
}
