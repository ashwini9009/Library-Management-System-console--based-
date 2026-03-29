package library_management_system;

import java.time.LocalDate;
import java.util.HashMap;

public class Member {
    private String memberId;
    private String name;
    private HashMap<String, LocalDate> borrowedBooks;

    public Member(String memberId, String name) {
        this.memberId = memberId;
        this.name = name;
        this.borrowedBooks = new HashMap<>();
    }

    public String getMemberId() { return memberId; }
    public String getName() { return name; }
    public HashMap<String, LocalDate> getBorrowedBooks() { return borrowedBooks; }

    public void borrowBook(String isbn, LocalDate dueDate) {
        borrowedBooks.put(isbn, dueDate);
    }

    public void returnBook(String isbn) {
        borrowedBooks.remove(isbn);
    }

    public String serialize() {
        StringBuilder sb = new StringBuilder();
        sb.append(memberId).append(",").append(name);

        for (String isbn : borrowedBooks.keySet()) {
            sb.append(",").append(isbn).append(":").append(borrowedBooks.get(isbn));
        }
        return sb.toString();
    }
}