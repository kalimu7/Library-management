package service;

public class BooksearchResult {
    private boolean bookfound;
    private int bookId;

    public boolean isBookfound() {
        return bookfound;
    }

    public BooksearchResult(boolean bookfound, int bookId) {
        this.bookfound = bookfound;
        this.bookId = bookId;
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
