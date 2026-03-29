package library_management_system;

public class Book {
    private String isbn;
    private String title;
    private String author;
    private boolean isAvailable;

    public Book(String isbn, String title, String author, boolean isAvailable) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.isAvailable = isAvailable;
    }

    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isAvailable() { return isAvailable; }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    @Override
    public String toString() {
        return isbn + "," + title + "," + author + "," + isAvailable;
    }
}