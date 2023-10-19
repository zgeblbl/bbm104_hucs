import java.util.ArrayList;

public class Member {
    public Member(String type, int id) {
        this.type = type;
        this.id = id;
    }
    private final String type;
    private final int id;
    private int borrowedBook = 0;
    private ArrayList<Book> borrowedBooks, booksReadInLibrary = new ArrayList<>();
    public String getType() {
        return type;
    }
    public int getId() {
        return id;
    }

    public int getBorrowedBook() {
        return borrowedBook;
    }

    public void setBorrowedBook(int borrowedBook) {
        this.borrowedBook = borrowedBook;
    }

    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(ArrayList<Book> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public ArrayList<Book> getBooksReadInLibrary() {
        return booksReadInLibrary;
    }

    public void setBooksReadInLibrary(ArrayList<Book> booksReadInLibrary) {
        this.booksReadInLibrary = booksReadInLibrary;
    }
}
