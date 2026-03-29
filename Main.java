package library_management_system;

import java.util.Scanner;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Library library = new Library();
        Scanner sc = new Scanner(System.in);

        library.loadBooks();
        library.loadMembers();

        while (true) {
            System.out.println("\n===== Library Menu =====");
            System.out.println("1. Add Book");
            System.out.println("2. Register Member");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. Search Book");
            System.out.println("6. Show Statistics");
            System.out.println("7. Exit");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("ISBN: ");
                    String isbn = sc.nextLine();
                    System.out.print("Title: ");
                    String title = sc.nextLine();
                    System.out.print("Author: ");
                    String author = sc.nextLine();

                    library.addBook(new Book(isbn, title, author, true));
                    break;

                case 2:
                    System.out.print("Member ID: ");
                    String id = sc.nextLine();
                    System.out.print("Name: ");
                    String name = sc.nextLine();

                    library.registerMember(new Member(id, name));
                    break;

                case 3:
                    System.out.print("Member ID: ");
                    String mId = sc.nextLine();
                    System.out.print("ISBN: ");
                    String bIsbn = sc.nextLine();

                    library.borrowBook(mId, bIsbn);
                    break;

                case 4:
                    System.out.print("Member ID: ");
                    String rmId = sc.nextLine();
                    System.out.print("ISBN: ");
                    String rIsbn = sc.nextLine();

                    library.returnBook(rmId, rIsbn);
                    break;

                case 5:
                    System.out.print("Enter title: ");
                    String search = sc.nextLine();
                    library.searchByTitle(search);
                    break;

                case 6:
                    library.showStatistics();
                    break;

                case 7:
                    library.saveBooks();
                    library.saveMembers();
                    System.out.println("Data saved. Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}