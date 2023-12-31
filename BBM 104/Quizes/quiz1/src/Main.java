import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.io.*;

public class Main {
    public static void writer(String path, String content, boolean append, boolean newLine) {
        PrintStream ps = null;
        try {
            ps = new PrintStream(new FileOutputStream(path, append));
            ps.print(content + (newLine ? "\n" : ""));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) { //Flushes all the content and closes the stream if it has been successfully created.
                ps.flush();
                ps.close();
            }
        }
    }

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

    public static void main(String[] args) {
        String[] lines = reader("input.txt");
        int i = 0;
        assert lines != null;
        for (String line : lines) {
            i++;
            if (Objects.equals(line, "Armstrong numbers up to:")) {
                int armstrong = Integer.parseInt(lines[i]);
                int num = 1;
                ArrayList<Integer> armstrongNumbers = new ArrayList<>();
                while (num < armstrong) {
                    int number = num;
                    int sum = 0;
                    String s = Integer.toString(number);
                    int numberLength = s.length();
                    while (number > 0) {
                        int digit = number % 10;
                        sum += Math.pow(digit, numberLength);
                        number = number / 10;
                    }
                    if (sum == num) {
                        armstrongNumbers.add(num);
                    }
                    num++;
                }
                writer("output.txt", "Armstrong numbers up to " + armstrong + ":", true, true);
                for (Object element : armstrongNumbers) {
                    writer("output.txt", element + " ", true, false);
                }
                writer("output.txt", "\n", true, true);


            } else if (Objects.equals(line, "Emirp numbers up to:")) {
                ArrayList<String> emirpNumbers = new ArrayList<>();
                int emirp = Integer.parseInt(lines[i]);
                int number = 13;
                while (number < emirp + 1) {
                    int half = number / 2;
                    int remainders = 0;
                    for (int j = 2; j < half + 1; j++) {
                        if (number % j == 0) {
                            break;
                        } else {
                            remainders++;
                        }
                    }
                    if (remainders == half - 1) {
                        String str = Integer.toString(number);
                        StringBuilder reversed = new StringBuilder();
                        for (int n = 0; n < str.length(); n++) {
                            char chr = str.charAt(n);
                            reversed.insert(0, chr);
                        }
                        int reverse = Integer.parseInt(reversed.toString());
                        int half2 = reverse / 2;
                        int reverseRemainder = 0;
                        for (int k = 2; k < half2 + 1; k++) {
                            if (reverse % k == 0) {
                                break;
                            } else {
                                reverseRemainder++;
                            }
                        if (reverseRemainder == half2 - 1 && reverse != number) {
                            String numb = Integer.toString(number);
                            emirpNumbers.add(numb);
                            }
                        }
                    }number++;
                }
                writer("output.txt", "Emirp numbers up to " + emirp + ":", true, true);
                for (Object prime : emirpNumbers) {
                    writer("output.txt", prime + " ", true, false);
                }writer("output.txt", "\n", true, true);

            }else if (Objects.equals(line, "Abundant numbers up to:")){
                int number = Integer.parseInt(lines[i]);
                ArrayList<Integer> abundantNumbers = new ArrayList<>();
                for(int num = 1; num < number+1; num++) {
                    int half = num / 2;
                    int sum = 0;
                    for (int j = 1; j < half + 1; j++) {
                        if (num % j == 0) {
                            sum += j;
                        }
                    }
                    if (sum > num) {
                        abundantNumbers.add(num);
                    }
                }writer("output.txt", "Abundant numbers up to " + number + ":", true, true);
                for (Object element: abundantNumbers) {
                    writer("output.txt", element + " ", true, false);
                }writer("output.txt", "\n", true, true);
            }else if (Objects.equals(line, "Ascending order sorting:")){
                writer("output.txt", "Ascending order sorting:", true, true);
                ArrayList<Integer> ascendingNumbers = new ArrayList<>();
                int row = i;
                do {
                    int number = Integer.parseInt(lines[row]);
                    ascendingNumbers.add(number);
                    Collections.sort(ascendingNumbers);
                    for(Object x : ascendingNumbers){
                        writer("output.txt",  x + " ", true, false);
                    }writer("output.txt", "\n", true, false);
                    row++;
                }while(Integer.parseInt(lines[row]) != -1);
                writer("output.txt", "\n", true, false);
            }else if (Objects.equals(line, "Descending order sorting:")){
                writer("output.txt", "Descending order sorting:", true, true);
                ArrayList<Integer> descendingNumbers = new ArrayList<>();
                int row = i;
                do {
                    int number = Integer.parseInt(lines[row]);
                    descendingNumbers.add(number);
                    descendingNumbers.sort(Collections.reverseOrder());
                    for(Object x : descendingNumbers){
                        writer("output.txt",  x + " ", true, false);
                    }writer("output.txt", "\n", true, false);
                    row++;
                }while(Integer.parseInt(lines[row]) != -1);
                writer("output.txt", "\n", true, false);
            }else if (Objects.equals(line, "Exit")) {
                writer("output.txt", "Finished...", true, false);
            }
        }
    }
}
