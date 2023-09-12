package service;

public class BooksearchResult {
    private boolean bookfound;
    private int bookId;
    private String Disponibility;
    public boolean isBookfound() {
        return bookfound;
    }

    public BooksearchResult(boolean bookfound, int bookId, String Disponibility ) {
        this.bookfound = bookfound;
        this.bookId = bookId;
        this.Disponibility = Disponibility;
    }

    public String getDisponibility() {
        return Disponibility;
    }

    public void setDisponibility(String disponibility) {
        Disponibility = disponibility;
    }

    public void setBookfound(boolean bookfound) {
        this.bookfound = bookfound;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
