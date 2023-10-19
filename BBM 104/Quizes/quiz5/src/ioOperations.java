import java.io.*;
import java.nio.file.*;
/**
 * ioOperations is the reader/writer class that contains the file i/o related code segments (methods).
 */
public class ioOperations {
    /**
     * Writes given content to file at given path.
     *
     * @param path    Path for the file content is going to be written.
     * @param content Content that is going to be written to file.
     * @param append  Append status, true if wanted to append to file if it exists, false if wanted to create file from zero.
     * @param newLine True if wanted to append a new line after content, false if vice versa.
     */
    public static void writer(String path, String content, boolean append, boolean newLine) {
        PrintStream ps = null;
        try {
            ps = new PrintStream(new FileOutputStream(path, append));
            ps.print(content + (newLine ? "\n" : ""));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.flush();
                ps.close();
            }
        }
    }
    /**
     * Reads the file at the given path and returns contents of it in a string array.
     *
     * @param path    Path for the file content is going to be read.
     */
    public static String[] reader(String path) {
        int i = 0;
        try {
            int length = Files.readAllLines(Paths.get(path)).size();
            String[] results = new String[length];
            for (String line : Files.readAllLines(Paths.get(path))) {
                results[i++] = line;
            }
            return results;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
