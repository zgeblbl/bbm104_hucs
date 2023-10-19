//Özge Bülbül 2220765008
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        String[] inputArray = ioOperations.reader(args[0]);
        ArrayList<ArrayList<String>> lines = new ArrayList<>();
        assert inputArray != null;
        for (String line: inputArray){
            if(!(Objects.equals(line, ""))){
                String[] elements = line.split("\t");
                ArrayList<String> myList = new ArrayList<>(Arrays.asList(elements));
                lines.add(myList);
            }
        }
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<Member> members = new ArrayList<>();
        for (ArrayList<String>line:lines){
            switch(line.get(0)){
                case"addBook":
                    Commands.AddBook(line, books, args[1]);
                    break;
                case "addMember":
                    Commands.AddMember(line, members, args[1]);
                    break;
                case "borrowBook":
                    Commands.Borrow(line, books, args[1], members);
                    break;
                case "extendBook":
                    Commands.ExtendDueDate(line, books, args[1], members);
                    break;
                case "readInLibrary":
                    Commands.ReadInLibrary(line, books, args[1], members);
                    break;
                case "returnBook":
                    Commands.ReturnBook(line, books, args[1], members);
                    break;
                case "getTheHistory":
                    Commands.GetHistory(books, args[1], members);
                    break;
            }
        }
    }
}