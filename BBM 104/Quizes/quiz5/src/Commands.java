import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Commands {
    public static String palindromeChecker(String line){
        String temp = line.replace(" ","");
        temp = temp.replaceAll("\\p{P}", "");
        temp = temp.toLowerCase();
        Stack<Character> lineStack = new Stack<>();
        for (char letter : temp.toCharArray()) {
            lineStack.push(letter);
        }
        for (char letter : temp.toCharArray()) {
            if (lineStack.pop() != letter) {
                return "\""+line +"\""+" is not a palindrome.";
            }
        }return "\""+line +"\""+ " is a palindrome.";
    }
    public static String binaryConverter(String line){
        int number = Integer.parseInt(line);
        Stack<Integer> binaryNumberStack = new Stack<>();
        if (number == 0) {
            binaryNumberStack.push(0);
        }
        while (number > 0) {
            int remainder = number % 2;
            binaryNumberStack.push(remainder);
            number = number/2;
        }
        StringBuilder sb = new StringBuilder();
        while (!binaryNumberStack.isEmpty()) {
            sb.append(binaryNumberStack.pop());
        }
        return sb.toString();
    }
    public static String binaryCounter(String line){
        int number = Integer.parseInt(line);
        Queue<String> binaryQueue = new LinkedList<>();
        binaryQueue.offer("1");
        StringBuilder sb = new StringBuilder();
        while (number > 0) {
            String binaryNumberStr = binaryQueue.poll();
            sb.append(binaryNumberStr).append("\t");
            binaryQueue.offer(binaryNumberStr + "0");
            binaryQueue.offer(binaryNumberStr + "1");
            number--;
        }
        return sb.toString().trim();
    }

    public static boolean  parenthesisChecker(String line){
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (ch == '(' || ch == '{' || ch == '[') {
                stack.push(ch);
            } else if (ch == ')' || ch == '}' || ch == ']') {
                if (stack.isEmpty()) {
                    return false;
                }
                char top = stack.pop();
                if (!((top == '(' && ch == ')') ||(top == '{' && ch == '}') ||(top == '[' && ch == ']'))) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
}
