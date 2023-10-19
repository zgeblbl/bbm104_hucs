import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Objects;
/**
 * Contains methods for the necessary commands of the library system.
 *
 */
public class Commands {
    /**
     * Adds book to the system.
     *
     * @param list    one line of the command list, a list of words that make the command
     * @param books list that contains all books of the system
     * @param path  path for the file content is going to be written
     */
    public static void AddBook(ArrayList<String> list, ArrayList<Book> books, String path){
        int id = 1;
        if(books.size() != 0){
            id = books.size()+1;
        }
        Book book = new Book(list.get(1), id);
        books.add(book);
        String type = null;
        if(Objects.equals(book.getType(), "H")){
            type = "Handwritten";
        }else if(Objects.equals(book.getType(), "P")){
            type = "Printed";

        }
        ioOperations.writer(path, "Created new book: "+type+" [id: "+book.getId()+"]", true, true);
    }
    /**
     * Adds member to the system.
     *
     * @param list    one line of the command list, a list of words that make the command
     * @param members list that contains all members of the system
     * @param path  path for the file content is going to be written
     */
    public static void AddMember(ArrayList<String> list, ArrayList<Member> members, String path){
        int id = 1;
        if(members.size() != 0){
            id = members.size()+1;
        }
        Member member = new Member(list.get(1), id);
        members.add(member);
        String type = null;
        if(Objects.equals(member.getType(), "S")){
            type = "Student";
        }else if(Objects.equals(member.getType(), "A")){
            type = "Academic";
        }
        ioOperations.writer(path, "Created new member: "+type+" [id: "+member.getId()+"]", true, true);
    }
    /**
     * Lets members borrow books.
     *
     * @param list    one line of the command list, a list of words that make the command
     * @param books list that contains all books of the system
     * @param path  path for the file content is going to be written
     * @param members list that contains all members of the system
     */
    public static void Borrow(ArrayList<String> list, ArrayList<Book> books, String path, ArrayList<Member> members){
        for(Member member:members){
            if (Integer.toString(member.getId()).equals(list.get(2))){
                if (member.getType().equals("A") && member.getBorrowedBook()==4 || member.getType().equals("S")
                        && member.getBorrowedBook()==2){
                    ioOperations.writer(path, "You have exceeded the borrowing limit!", true, true);
                }else{
                    for(Book book:books){
                        if (Integer.toString(book.getId()).equals(list.get(1))){
                            if(book.getType().equals("H")){
                                ioOperations.writer(path, "You cannot borrow this book!", true, true);
                            }else{
                                member.setBorrowedBook(member.getBorrowedBook()+1);
                                ArrayList<Book> temp = member.getBorrowedBooks();
                                temp.add(book);
                                member.setBorrowedBooks(temp);
                                book.setInitialDate(LocalDate.parse(list.get(3)));
                                book.setBorrowed(true);
                                book.setReaderID(member.getId());
                                if (member.getType().equals("A")){
                                    book.setDueDate(LocalDate.parse(list.get(3)).plusWeeks(2));
                                }else{
                                    book.setDueDate(LocalDate.parse(list.get(3)).plusWeeks(1));
                                }ioOperations.writer(path, "The book ["+book.getId()+"] was borrowed by member ["
                                        +member.getId()+"] at "+list.get(3), true, true);
                            }
                        }
                    }
                }
            }
        }
    }
    /**
     * Lets members extend the due date of borrowed book.
     *
     * @param list    one line of the command list, a list of words that make the command
     * @param books list that contains all books of the system
     * @param path  path for the file content is going to be written
     * @param members list that contains all members of the system
     */
    public static void ExtendDueDate(ArrayList<String> list, ArrayList<Book> books, String path, ArrayList<Member> members){
        boolean bookIsBorrowed = false;
        boolean bookExists = false;
        boolean memberExists = false;
        for (Book book : books){
            if (Integer.toString(book.getId()).equals(list.get(1))){
                bookExists = true;
                for (Member member : members){
                    if (Integer.toString(member.getId()).equals(list.get(2))){
                        memberExists = true;
                        for (Book borrowed : member.getBorrowedBooks()){
                            if (borrowed.getId()==book.getId()) {
                                bookIsBorrowed = true;
                                if (book.getNumberOfExtensions()==0) {
                                    if (!(LocalDate.parse(list.get(3)).isAfter(book.getDueDate()))) {
                                        if (member.getType().equals("A")) {
                                            book.setDueDate(LocalDate.parse(list.get(3)).plusWeeks(2));
                                        } else {
                                            book.setDueDate(LocalDate.parse(list.get(3)).plusWeeks(1));
                                        }ioOperations.writer(path, "The deadline of book ["+book.getId()
                                                + "] was extended by member ["+member.getId()+"] at "+list.get(3)
                                                , true, true);
                                        ioOperations.writer(path, "New deadline of book ["+book.getId()+"] is "
                                                + book.getDueDate(), true, true);
                                        book.setNumberOfExtensions(book.getNumberOfExtensions()+1);
                                    }
                                }else{
                                    ioOperations.writer(path, "You cannot extend the deadline!", true, true);
                                }
                            }
                        }
                    }
                }
            }
        }if (!bookExists){
            ioOperations.writer(path, "This book does not exist!", true, true);
        }if (!memberExists){
            ioOperations.writer(path, "This member does not exist!", true, true);
        }if (!bookIsBorrowed){
            ioOperations.writer(path, "You have to borrow the book first!", true, true);
        }
    }
    /**
     * Lets members read a book in the library.
     *
     * @param list    one line of the command list, a list of words that make the command
     * @param books list that contains all books of the system
     * @param path  path for the file content is going to be written
     * @param members list that contains all members of the system
     */
    public static void ReadInLibrary(ArrayList<String> list, ArrayList<Book> books, String path, ArrayList<Member> members) {
        boolean bookExists = false;
        boolean memberExists = false;
        for (Book book : books) {
            if (Integer.toString(book.getId()).equals(list.get(1))){
                bookExists = true;
                for (Member member : members) {
                    if (Integer.toString(member.getId()).equals(list.get(2))) {
                        memberExists = true;
                        if (book.isBorrowed()) {
                            ioOperations.writer(path, "You can not read this book!", true, true);
                        } else if (book.getType().equals(("H")) && member.getType().equals("S")) {
                            ioOperations.writer(path, "Students can not read handwritten books!", true, true);
                        } else{
                            ArrayList<Book> temp = member.getBooksReadInLibrary();
                            temp.add(book);
                            member.setBooksReadInLibrary(temp);
                            book.setReadInLib(true);
                            book.setInitialDate(LocalDate.parse(list.get(3)));
                            book.setReaderID(member.getId());
                            ioOperations.writer(path, "The book ["+book.getId()+"] was read in library by member ["
                                    +member.getId()+"] at "+list.get(3), true, true);
                        }
                    }
                }
            }
        }
        if (!bookExists){
            ioOperations.writer(path, "This book does not exist!", true, true);
        }if (!memberExists){
            ioOperations.writer(path, "This member does not exist!", true, true);
        }
    }
    /**
     * Returns the borrowed book to the system.
     *
     * @param list    one line of the command list, a list of words that make the command
     * @param books list that contains all books of the system
     * @param path  path for the file content is going to be written
     * @param members list that contains all members of the system
     */
    public static void ReturnBook(ArrayList<String> list, ArrayList<Book> books, String path, ArrayList<Member> members) {
        boolean bookExists = false;
        boolean memberExists = false;
        boolean bookIsBorrowed = false;
        long fee = 0;
        for (Book book : books) {
            if (Integer.toString(book.getId()).equals(list.get(1))) {
                bookExists = true;
                for (Member member : members) {
                    if (Integer.toString(member.getId()).equals(list.get(2))) {
                        memberExists = true;
                        for (Book book1 : member.getBorrowedBooks()){
                            if (Integer.toString(book.getId()).equals(Integer.toString(book1.getId()))) {
                                bookIsBorrowed = true;
                                break;
                            }
                        }
                        if (bookIsBorrowed){
                            if (book.getDueDate().isBefore(LocalDate.parse(list.get(3)))){
                                fee = Period.between(book.getDueDate(), LocalDate.parse(list.get(3))).getDays();
                            }ioOperations.writer(path, "The book ["+book.getId()+"] was returned by member ["
                                    +member.getId()+"] at "+list.get(3)+" Fee: "+fee, true, true);
                            ArrayList<Book> myList = member.getBorrowedBooks();
                            myList.remove(book);
                            member.setBorrowedBooks(myList);
                            book.setBorrowed(false);
                            book.setReaderID(null);
                        }for (Book book2 : member.getBooksReadInLibrary()) {
                            if (Integer.toString(book.getId()).equals(Integer.toString(book2.getId()))) {
                                bookIsBorrowed = true;
                                ioOperations.writer(path, "The book ["+book.getId()+"] was returned by member ["
                                        +member.getId()+"] at "+list.get(3)+" Fee: "+fee, true, true);
                                ArrayList<Book> myList = member.getBorrowedBooks();
                                myList.remove(book);
                                member.setBorrowedBooks(myList);
                                book.setReadInLib(false);
                                book.setReaderID(null);
                                book.setNumberOfExtensions(0);
                            }
                        }
                    }
                }
            }
        }if (!bookExists){
            ioOperations.writer(path, "This book does not exist!", true, true);
        }if (!memberExists){
            ioOperations.writer(path, "This member does not exist!", true, true);
        }if (!bookIsBorrowed){
            ioOperations.writer(path, "You must borrow the book first!", true, true);
        }
    }
    /**
     * Gives the history of activities of the library (books read in library by students etc.).
     *
     * @param books list that contains all books of the system
     * @param path  path for the file content is going to be written
     * @param members list that contains all members of the system
     */
    public static void GetHistory(ArrayList<Book> books, String path, ArrayList<Member> members) {
        int student = 0;
        for (Member member : members) {
            if (member.getType().equals("S")){
                student++;
            }
        }
        ioOperations.writer(path, "History of library:\n\nNumber of students: "+student, true, true);
        for (Member member:members){
            if (member.getType().equals("S")){
                ioOperations.writer(path, "Student [id: "+member.getId()+"]", true, true);
            }
        }ioOperations.writer(path, "\nNumber of academics: "+(members.size()-student), true, true);
        for (Member member:members){
            if (member.getType().equals("A")){
                ioOperations.writer(path, "Academic [id: "+member.getId()+"]", true, true);
            }
        }int printed = 0;
        for (Book book : books) {
            if (book.getType().equals("P")){
                printed++;
            }
        }
        ioOperations.writer(path, "\nNumber of printed books: "+printed, true, true);
        for (Book book:books) {
            if (book.getType().equals("P")) {
                ioOperations.writer(path, "Printed [id: " + book.getId() + "]", true, true);
            }
        }ioOperations.writer(path, "\nNumber of handwritten books: "+(books.size()-printed), true, true);
        for (Book book:books){
            if (book.getType().equals("H")){
                ioOperations.writer(path, "Handwritten [id: "+book.getId()+"]", true, true);
            }
        }int borrowed=0;
        for (Book book:books){
            if (book.isBorrowed()){
                borrowed++;
            }
        }
        ioOperations.writer(path, "\nNumber of borrowed books: "+borrowed, true, true);
        for (Book book:books){
            if (book.isBorrowed()){
                ioOperations.writer(path, "The book ["+book.getId()+"] was borrowed by member ["+book.getReaderID()
                        +"] at "+book.getInitialDate(), true, true);
            }
        }
        int readInLib=0;
        for (Book book:books){
            if (book.isReadInLib()){
                readInLib++;
            }
        }
        ioOperations.writer(path, "\nNumber of books read in library: "+readInLib, true, true);
        for (Book book:books){
            if (book.isReadInLib()){
                ioOperations.writer(path, "The book ["+book.getId()+"] was read in library by member ["
                        +book.getReaderID() +"] at "+book.getInitialDate(), true, true);
            }
        }
    }
}
