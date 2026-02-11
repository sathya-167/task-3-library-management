import java.util.ArrayList;
import java.util.Scanner;

class Book {
    private String isbn;
    private String title;
    private String author;
    private String genre;
    private boolean isAvailable;

    public Book(String isbn, String title, String author, String genre) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isAvailable = true;
    }

    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getGenre() { return genre; }
    public boolean isAvailable() { return isAvailable; }

    public void setAvailable(boolean available) { isAvailable = available; }

    public void displayInfo() {
        System.out.println("ISBN: " + isbn);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Genre: " + genre);
        System.out.println("Status: " + (isAvailable ? "Available" : "Borrowed"));
        System.out.println("----------------------------------------");
    }
}

class Member {
    private String memberId;
    private String name;
    private String contact;
    private ArrayList<Book> borrowedBooks;

    public Member(String memberId, String name, String contact) {
        this.memberId = memberId;
        this.name = name;
        this.contact = contact;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getMemberId() { return memberId; }

    public boolean borrowBook(Book book) {
        if (book != null && book.isAvailable()) {
            borrowedBooks.add(book);
            book.setAvailable(false);
            return true;
        }
        return false;
    }

    public boolean returnBook(Book book) {
        if (book != null && borrowedBooks.contains(book)) {
            borrowedBooks.remove(book);
            book.setAvailable(true);
            return true;
        }
        return false;
    }

    public void displayInfo() {
        System.out.println("Member ID: " + memberId);
        System.out.println("Name: " + name);
        System.out.println("Contact: " + contact);
        System.out.println("Books Borrowed: " + borrowedBooks.size());
        if (!borrowedBooks.isEmpty()) {
            System.out.println("Borrowed Books:");
            for (Book b : borrowedBooks) {
                System.out.println(" - " + b.getTitle());
            }
        }
        System.out.println("----------------------------------------");
    }
}

class Library {
    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<Member> members = new ArrayList<>();

    public void addBook(Book book) { books.add(book); }
    public void addMember(Member member) { members.add(member); }

    public Book findBookByIsbn(String isbn) {
        for (Book b : books) {
            if (b.getIsbn().equals(isbn)) return b;
        }
        return null;
    }

    public Member findMemberById(String id) {
        for (Member m : members) {
            if (m.getMemberId().equals(id)) return m;
        }
        return null;
    }

    public ArrayList<Book> searchBooks(String keyword) {
        ArrayList<Book> results = new ArrayList<>();
        keyword = keyword.toLowerCase();
        for (Book b : books) {
            if (b.getTitle().toLowerCase().contains(keyword) ||
                    b.getAuthor().toLowerCase().contains(keyword) ||
                    b.getGenre().toLowerCase().contains(keyword)) {
                results.add(b);
            }
        }
        return results;
    }

    public void displayAllBooks() {
        System.out.println("\n=== ALL BOOKS ===");
        if (books.isEmpty()) { System.out.println("No books in library!"); return; }
        for (Book b : books) b.displayInfo();
    }

    public void displayAvailableBooks() {
        System.out.println("\n=== AVAILABLE BOOKS ===");
        boolean found = false;
        for (Book b : books) {
            if (b.isAvailable()) {
                b.displayInfo();
                found = true;
            }
        }
        if (!found) System.out.println("No books available at the moment!");
    }
}

public class LibrarySystem {

    public static void main(String[] args) {

        Library library = new Library();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== LIBRARY MANAGEMENT SYSTEM ===");
            System.out.println("1. Add New Book");
            System.out.println("2. Register New Member");
            System.out.println("3. Display All Books");
            System.out.println("4. Display Available Books");
            System.out.println("5. Search Books");
            System.out.println("6. Borrow Book");
            System.out.println("7. Return Book");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            switch (choice) {

                case 1: // Add Book
                    System.out.print("Enter ISBN: ");
                    String isbn = scanner.nextLine();
                    System.out.print("Enter Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter Genre: ");
                    String genre = scanner.nextLine();
                    library.addBook(new Book(isbn, title, author, genre));
                    System.out.println("✅ Book added successfully!");
                    break;

                case 2: // Register Member
                    System.out.print("Enter Member ID: ");
                    String memberId = scanner.nextLine();
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Contact: ");
                    String contact = scanner.nextLine();
                    library.addMember(new Member(memberId, name, contact));
                    System.out.println("✅ Member registered successfully!");
                    break;

                case 3: // Display All Books
                    library.displayAllBooks();
                    break;

                case 4: // Display Available Books
                    library.displayAvailableBooks();
                    break;

                case 5: // Search Books
                    System.out.print("Enter search keyword: ");
                    String keyword = scanner.nextLine();
                    ArrayList<Book> results = library.searchBooks(keyword);
                    System.out.println("\nSearch Results:");
                    if (results.isEmpty()) {
                        System.out.println("No books found for '" + keyword + "'");
                    } else {
                        for (Book b : results) b.displayInfo();
                    }
                    break;

                case 6: // Borrow Book
                    System.out.print("Enter Member ID: ");
                    String mId = scanner.nextLine();
                    System.out.print("Enter Book ISBN: ");
                    String bIsbn = scanner.nextLine();
                    Member member = library.findMemberById(mId);
                    Book book = library.findBookByIsbn(bIsbn);
                    if (member != null && book != null && member.borrowBook(book)) {
                        System.out.println("✅ Book borrowed successfully!");
                        System.out.println("Member: " + member.getMemberId());
                        System.out.println("Book: " + book.getTitle());
                    } else {
                        System.out.println("❌ Borrow failed! Check member ID or book availability.");
                    }
                    break;

                case 7: // Return Book
                    System.out.print("Enter Member ID: ");
                    String mId2 = scanner.nextLine();
                    System.out.print("Enter Book ISBN: ");
                    String bIsbn2 = scanner.nextLine();
                    Member member2 = library.findMemberById(mId2);
                    Book book2 = library.findBookByIsbn(bIsbn2);
                    if (member2 != null && book2 != null && member2.returnBook(book2)) {
                        System.out.println("✅ Book returned successfully!");
                    } else {
                        System.out.println("❌ Return failed! Check member ID or borrowed book.");
                    }
                    break;

                case 8:
                    System.out.println("Exiting system...");
                    break;

                default:
                    System.out.println("Invalid choice! Try again.");
            }

        } while (choice != 8);

        scanner.close();
    }
}
