import java.util.*;
import java.io.*;

public class Task2 {
    public static void main(String[] args) {
        System.out.println(System.getProperty("user.home"));
        try {
            Scanner scObj = new Scanner (new File("input.txt"));
            int no1 = scObj.nextInt();
            int no2 = scObj.nextInt();
            System.out.println("The two nos are : " + no1 + ", " + no2);
            // Write to a File
            Formatter outObj = new Formatter(new File("output.txt"));
            int totalSum = no1 + no2;
            System.out.println("The total sum value is " + totalSum);
            outObj.format("%d", totalSum);
            outObj.close();
        } catch(Exception ee) {
            System.out.println("Error "+ee.toString());
        }
    }
}


/*
    After JDK 17 java security manager is depricated, for me also it's not working but if I will use JDK below 17 and use security manager,
    It will print my home directory that is /Users/ahsan and then give an error while reading an input file.

    Note: Currently this code is working and doing sum and printing the output in another file
 */
