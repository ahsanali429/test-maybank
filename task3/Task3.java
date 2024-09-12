package task3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Task3 {
    static Integer count1 = 0;
    static Integer count2 = 0;
    static Integer count3 = 0;

    public static void main(String[] args) {
//        throwOutOfMemoryException();
//        preventOutOfMemoryExceptionUsingGC();
        preventOutOfMemoryExceptionWithOutUsingGC();
    }

    private static void throwOutOfMemoryException() {
        try {
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < 1000000000; i++) {
                count1++;
                buffer.append("Record #: ").append(count1);
            }
            System.out.println("Successfully run program");
        } catch (OutOfMemoryError e) {
            System.out.println("Out of Memory Error occurred: " + e.getMessage());
        }
    }

    private static void preventOutOfMemoryExceptionUsingGC() {
        try {
            for (int j = 0; j < 1000; j++) {
                StringBuffer buffer = new StringBuffer();
                for (int i = 0; i < 1000000; i++) {
                    count2++;
                    buffer.append("Record #: ").append(count2);
                }
                buffer = null; // Set null, so when JVM call garbage collector it will automatically remove from memory
            }
            System.out.println("Successfully run program");
        } catch (OutOfMemoryError e) {
            System.out.println("Out of Memory Error occurred: " + e.getMessage());
        }
    }


    private static void preventOutOfMemoryExceptionWithOutUsingGC() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("task3/output.txt"))) {
            for (int j = 0; j < 10000; j++) {
                StringBuffer buffer = new StringBuffer();
                for (int i = 0; i < 100000; i++) {
                    count3++;
                    buffer.append("Record #: ").append(count3);
                    buffer.append("\n");
                }
                writer.write(buffer.toString());
            }
            System.out.println("Successfully run program");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            System.out.println("Out of Memory Error occurred: " + e.getMessage());
        }
    }
}
