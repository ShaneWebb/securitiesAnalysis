package learner;

import main.Helper;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.Assert.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import main.Helper.*;

// Learner tests for general Java features.
public class BasicTest {
    
    public BasicTest() {
    }

    @Test
    @Disabled
    public void firstEverTest() {
        System.out.println("Hello World! I am now changed.");
        fail("I failed.");
    }
    
    @Test
    @Disabled
    public void threadTest() {
           
        class MySimpleParallelClass implements Runnable {
                               
            @Override
            public void run() {
                int shortPauseInSeconds = 1;
                for(int i = 0; i < 5; ++i){
                    System.out.println("Thread running! Count: " + i);
                    Helper.pause(shortPauseInSeconds);
                }
            }

        }
        
        Thread firstThread = new Thread(new MySimpleParallelClass(), "First");
        Thread secondThread = new Thread(new MySimpleParallelClass(), "Second");
        firstThread.start(); secondThread.start();
        
        int shortPauseInSeconds = 10;
        Helper.pause(shortPauseInSeconds);
        
    }
        
    @Test
    @Disabled
    public void genericsTest() {
        
        class myGenericClass<T> {
            T myGenericField;

            public myGenericClass(T myGenericField) {
                this.myGenericField = myGenericField;
            } 
            
        }
        
        //Apparently, you are allowed to specify nothing for <T>, and this
        //treats the generic as an object. Yet the assert works because
        //the string overrides equals().
        myGenericClass myStrGeneric = new myGenericClass("Hi");
        assertEquals("Must be a string.","Hi",myStrGeneric.myGenericField);
        
        //To compile, must specify <Integer> to allow the intValue() method to
        //be called. 
        myGenericClass<Integer> myIntGeneric = new myGenericClass(1);
        assertEquals("Must be equal to 1", 1, myIntGeneric.myGenericField.intValue());
        
    }
    
    @Test
    @Disabled
    public void newlineTest() {
        System.out.println("I am a test of\n"
                + "newlines!");
        assertTrue(true);
    }
    
    @Test
    @Disabled
    public void arrayListTest() {
        ArrayList<Integer> tempArrayList = new ArrayList();
        int size = tempArrayList.size();
        assertEquals("Size should be zero!", 0, size);
        
        tempArrayList.add(142343456);
        size = tempArrayList.size();
        assertEquals("Size should be one!", 1, size);
    }
    
    @Test
    @Disabled
    public void stringFindWithinTest() {
        String mystr = "I am looking for a\n"
                + "newline!";
        String[] split = mystr.split("\n");
        System.out.println(Arrays.toString(split));
        assertTrue(true);
    }
    
    @Test
    @Disabled
    public void removeTrailingNewlineTest() {
        String str = "123\n"; // Length 4, escape character does not count.
        String strNoLastChar = Helper.removeLastChar(str);
        assertEquals("String must have its last char removed!",3,strNoLastChar.length());
        
        String strNoNewline = "12345"; //Length 5
        String strTrailingNewline = "12345\n"; //Length 6
        String strMidNewline = "123\n45"; //Length 6
        String strMultiNewline = "123\n45\n"; //Length 7
        
        assertEquals("Must not alter strings with no trailing newline!",
                strNoNewline,Helper.removeTrailingNewline(strNoNewline));
        assertEquals("Must remove trailing newline!",
                5,Helper.removeTrailingNewline(strTrailingNewline).length());
        assertEquals("Must not remove middle newlines!",
                strMidNewline,Helper.removeTrailingNewline(strMidNewline));
        
        String strLastNewlineOnlyRemoved = Helper.removeTrailingNewline(strMultiNewline);
        
        Boolean lastNewLineOnlyRemoved = 
                (strLastNewlineOnlyRemoved.equals(strMidNewline) && 
                 strLastNewlineOnlyRemoved.length() == 6);
        
        assertTrue("Only the last newline may be removed!",lastNewLineOnlyRemoved);
        
    }


    
    @Test
    @Disabled
    public void stringRegexNewlineTest() {
        String str = ".\n.";
        String parsedStr = str.replaceAll("(.{10})", "$1\n");
        System.out.println(str.length());
        assertTrue(true);
    }

    @Test
    @Disabled
    public void enumInstancesTest() {
        testEnums appleInstance1 = testEnums.APPLE;
        testEnums appleInstance2 = testEnums.APPLE;
        
        int instanceValOne = 0;
        appleInstance1.setValue(instanceValOne);
        
        assertEquals("Must equal value one.", instanceValOne, appleInstance1.getValue());
        
        int instanceValTwo = 1;
        appleInstance2.setValue(instanceValTwo);
        assertEquals("Must equal value two.", instanceValTwo, appleInstance2.getValue());
        
        //APPLE is still one object across the program.
        assertEquals("Must now equal value two.", instanceValTwo, appleInstance1.getValue());
  
    }
    
}