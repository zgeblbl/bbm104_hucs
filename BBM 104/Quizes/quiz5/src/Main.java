import java.util.ArrayList;
import java.util.Arrays;
public class Main {
    public static void main(String[] args) {
        String[] inputArray = ioOperations.reader(args[0]);
        ArrayList<ArrayList<String>> lines = new ArrayList<>();
        assert inputArray != null;
        for (String line: inputArray){
            String[] elements = line.split("\t");
            ArrayList<String> myList = new ArrayList<>(Arrays.asList(elements));
            lines.add(myList);
        }
        for (ArrayList<String> lineList: lines){
            switch (lineList.get(0)){
                case "Convert from Base 10 to Base 2:":
                    ioOperations.writer(args[1], "Equivalent of "+lineList.get(1)+
                            " (base 10) in base 2 is: "+Commands.binaryConverter(lineList.get(1)), true, true);
                    break;
                case "Count from 1 up to n in binary:":
                    ioOperations.writer(args[1], "Counting from 1 up to "+
                            lineList.get(1)+" in binary:\t"+Commands.binaryCounter(lineList.get(1)), true, true);
                    break;
                case "Check if following is palindrome or not:":
                    ioOperations.writer(args[1], Commands.palindromeChecker(lineList.get(1)), true, true);
                    break;
                case "Check if following expression is valid or not:":
                    boolean correctExpression = Commands.parenthesisChecker(lineList.get(1));
                    if (correctExpression){
                        ioOperations.writer(args[1], "\""+ lineList.get(1)+"\"" +" is a valid expression.", true, true);
                    }else{
                        ioOperations.writer(args[1], "\""+lineList.get(1)+"\"" +" is not a valid expression.", true, true);
                    }
                    break;
            }
        }
    }
}