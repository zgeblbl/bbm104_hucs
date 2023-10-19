import java.time.LocalDate;

public class Book {
    public Book(String type, int id) {
        this.type = type;
        this.id = id;
    }
    private final String type;
    private final int id;
    private int numberOfExtensions = 0;
    private LocalDate initialDate, dueDate;
    private boolean isBorrowed, readInLib=false;
    private Integer ReaderID;

    public String getType() {
        return type;
    }
    public int getId() {
        return id;
    }

    public LocalDate getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(LocalDate initialDate) {
        this.initialDate = initialDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public int getNumberOfExtensions() {
        return numberOfExtensions;
    }

    public void setNumberOfExtensions(int numberOfExtensions) {
        this.numberOfExtensions = numberOfExtensions;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }

    public Integer getReaderID() {
        return ReaderID;
    }

    public void setReaderID(Integer ReaderID) {
        this.ReaderID = ReaderID;
    }

    public boolean isReadInLib() {
        return readInLib;
    }

    public void setReadInLib(boolean readInLib) {
        this.readInLib = readInLib;
    }
}
