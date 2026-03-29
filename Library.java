package library_management_system;

import java.util.*;
import java.io.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Library {

    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<Member> members = new ArrayList<>();

    // ---------------- BOOK METHODS ----------------

    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book added!");
    }

    public void removeBook(String isbn) {
        books.removeIf(b -> b.getIsbn().equals(isbn));
        System.out.println("Book removed!");
    }

    public Book findBook(String isbn) {
        for (Book b : books) {
            if (b.getIsbn().equals(isbn)) return b;
        }
        return null;
    }

    // ---------------- MEMBER METHODS ----------------

    public void registerMember(Member member) {
        members.add(member);
        System.out.println("Member registered!");
    }

    public Member findMember(String id) {
        for (Member m : members) {
            if (m.getMemberId().equals(id)) return m;
        }
        return null;
    }

    // ---------------- BORROW / RETURN ----------------

    public void borrowBook(String memberId, String isbn) {
        Book book = findBook(isbn);
        Member member = findMember(memberId);

        if (book == null || member == null) {
            System.out.println("Invalid book or member!");
            return;
        }

        if (!book.isAvailable()) {
            System.out.println("Book not available!");
            return;
        }

        LocalDate dueDate = LocalDate.now().plusDays(7);

        member.borrowBook(isbn, dueDate);
        book.setAvailable(false);

        System.out.println("Book borrowed! Due date: " + dueDate);
    }

    public void returnBook(String memberId, String isbn) {
        Member member = findMember(memberId);
        Book book = findBook(isbn);

        if (member == null || book == null) {
            System.out.println("Invalid data!");
            return;
        }

        LocalDate dueDate = member.getBorrowedBooks().get(isbn);

        if (dueDate != null) {
            double fine = calculateFine(dueDate);
            if (fine > 0) {
                System.out.println("Late return! Fine: ₹" + fine);
            }
        }

        member.returnBook(isbn);
        book.setAvailable(true);

        System.out.println("Book returned!");
    }

    public double calculateFine(LocalDate dueDate) {
        long daysLate = ChronoUnit.DAYS.between(dueDate, LocalDate.now());
        return (daysLate > 0) ? daysLate * 5 : 0;
    }

    // ---------------- SEARCH ----------------

    public void searchByTitle(String title) {
        for (Book b : books) {
            if (b.getTitle().toLowerCase().contains(title.toLowerCase())) {
                System.out.println(b.getTitle() + " by " + b.getAuthor());
            }
        }
    }

    // ---------------- STATISTICS ----------------

    public void showStatistics() {
        long available = books.stream().filter(Book::isAvailable).count();
        long borrowed = books.size() - available;

        System.out.println("Total Books: " + books.size());
        System.out.println("Available: " + available);
        System.out.println("Borrowed: " + borrowed);
    }

    // ---------------- FILE HANDLING ----------------

    public void saveBooks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("books.txt"))) {
            for (Book b : books) {
                writer.write(b.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving books!");
        }
    }

    public void loadBooks() {
        try (BufferedReader reader = new BufferedReader(new FileReader("books.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] d = line.split(",");
                books.add(new Book(d[0], d[1], d[2], Boolean.parseBoolean(d[3])));
            }
        } catch (IOException e) {
            System.out.println("No book data found.");
        }
    }

    public void saveMembers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("members.txt"))) {
            for (Member m : members) {
                writer.write(m.serialize());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving members!");
        }
    }

    public void loadMembers() {
        try (BufferedReader reader = new BufferedReader(new FileReader("members.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Member m = new Member(parts[0], parts[1]);

                for (int i = 2; i < parts.length; i++) {
                    String[] bookData = parts[i].split(":");
                    m.borrowBook(bookData[0], LocalDate.parse(bookData[1]));
                }

                members.add(m);
            }
        } catch (IOException e) {
            System.out.println("No member data found.");
        }
    }
}

