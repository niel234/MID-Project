import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

public class LibrarySystem {
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Member> members = new ArrayList<>();
    static ArrayList<Book> books = new ArrayList<>();
    static ArrayList<Borrowing> borrowings = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("=== Sistem Perpustakaan Sederhana ===");
            System.out.println("1. Registrasi anggota baru");
            System.out.println("2. Tambah data buku baru");
            System.out.println("3. Pinjam buku");
            System.out.println("4. Kembalikan buku");
            System.out.println("5. Cari buku");
            System.out.println("6. Tampilkan daftar buku yang tersedia");
            System.out.println("7. Tampilkan riwayat peminjaman anggota");
            System.out.println("8. Tampilkan laporan buku yang sering dipinjam");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Clear the buffer
            switch (choice) {
                case 1: registerMember(); break;
                case 2: addNewBook(); break;
                case 3: borrowBook(); break;
                case 4: returnBook(); break;
                case 5: searchBook(); break;
                case 6: showAvailableBooks(); break;
                case 7: showBorrowingHistory(); break;
                case 8: showFrequentlyBorrowedBooks(); break;
                case 0: System.exit(0);
                default: System.out.println("Pilihan tidak valid!");
            }
        }
    }

    public static void registerMember() {
        System.out.println("=== Registrasi Anggota Baru ===");
        System.out.print("Nama: ");
        String name = scanner.nextLine();
        System.out.print("Alamat: ");
        String address = scanner.nextLine();
        System.out.print("No. Telepon: ");
        String phone = scanner.nextLine();

        Member newMember = new Member(name, address, phone);
        members.add(newMember);
        System.out.println("Anggota berhasil diregistrasi!\n");
    }

    public static void addNewBook() {
        System.out.println("=== Tambah Data Buku Baru ===");
        System.out.print("Judul: ");
        String title = scanner.nextLine();
        System.out.print("Pengarang: ");
        String author = scanner.nextLine();
        System.out.print("Penerbit: ");
        String publisher = scanner.nextLine();
        System.out.print("Tahun Terbit: ");
        int year = scanner.nextInt();
        System.out.print("Jumlah Stok: ");
        int stock = scanner.nextInt();
        scanner.nextLine();  // Clear the buffer

        Book newBook = new Book(title, author, publisher, year, stock);
        books.add(newBook);
        System.out.println("Buku berhasil ditambahkan!\n");
    }

    public static void borrowBook() {
        System.out.println("=== Pinjam Buku ===");
        System.out.print("ID Anggota: ");
        int memberId = scanner.nextInt();
        scanner.nextLine();  // Clear the buffer
        System.out.print("ID Buku: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();  // Clear the buffer
        System.out.print("Tanggal Pinjam (yyyy-mm-dd): ");
        String borrowDate = scanner.nextLine();

        Member member = findMemberById(memberId);
        Book book = findBookById(bookId);

        if (member != null && book != null && book.getStock() > 0) {
            Borrowing borrowing = new Borrowing(member, book, borrowDate);
            borrowings.add(borrowing);
            book.reduceStock();
            System.out.println("Buku berhasil dipinjam!\n");
        } else {
            System.out.println("Peminjaman gagal! Pastikan ID valid dan stok tersedia.\n");
        }
    }

    public static void returnBook() {
        System.out.println("=== Kembalikan Buku ===");
        System.out.print("ID Peminjaman: ");
        int borrowId = scanner.nextInt();
        scanner.nextLine();  // Clear the buffer
        System.out.print("Tanggal Kembali (yyyy-mm-dd): ");
        String returnDate = scanner.nextLine();

        Borrowing borrowing = findBorrowingById(borrowId);
        if (borrowing != null) {
            borrowing.setReturnDate(returnDate);
            borrowing.getBook().increaseStock();
            double fine = borrowing.calculateFine();
            if (fine > 0) {
                System.out.println("Denda keterlambatan: Rp " + fine);
            }
            System.out.println("Buku berhasil dikembalikan!\n");
        } else {
            System.out.println("ID Peminjaman tidak ditemukan!\n");
        }
    }

    public static void searchBook() {
        System.out.println("=== Cari Buku ===");
        System.out.print("Masukkan judul/pengarang/ID buku: ");
        String query = scanner.nextLine().toLowerCase();

        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(query) ||
                    book.getAuthor().toLowerCase().contains(query) ||
                    Integer.toString(book.getId()).contains(query)) {
                System.out.println(book);
            }
        }
        System.out.println();
    }

    public static void showAvailableBooks() {
        System.out.println("=== Daftar Buku yang Tersedia ===");
        for (Book book : books) {
            if (book.getStock() > 0) {
                System.out.println(book);
            }
        }
        System.out.println();
    }

    public static void showBorrowingHistory() {
        System.out.println("=== Riwayat Peminjaman Anggota ===");
        System.out.print("ID Anggota: ");
        int memberId = scanner.nextInt();
        scanner.nextLine();  // Clear the buffer

        for (Borrowing borrowing : borrowings) {
            if (borrowing.getMember().getId() == memberId) {
                System.out.println(borrowing);
            }
        }
        System.out.println();
    }

    public static void showFrequentlyBorrowedBooks() {
        System.out.println("=== Laporan Buku yang Sering Dipinjam ===");
        // Logic to show books frequently borrowed
        // You can implement this based on how frequently each book is borrowed
        System.out.println("Fitur ini belum diimplementasikan.\n");
    }

    public static Member findMemberById(int id) {
        for (Member member : members) {
            if (member.getId() == id) {
                return member;
            }
        }
        return null;
    }

    public static Book findBookById(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    public static Borrowing findBorrowingById(int id) {
        for (Borrowing borrowing : borrowings) {
            if (borrowing.getId() == id) {
                return borrowing;
            }
        }
        return null;
    }
}

class Member {
    private static int idCounter = 1;
    private int id;
    private String name;
    private String address;
    private String phone;

    public Member(String name, String address, String phone) {
        this.id = idCounter++;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Nama: " + name + ", Alamat: " + address + ", No. Telepon: " + phone;
    }
}

class Book {
    private static int idCounter = 1;
    private int id;
    private String title;
    private String author;
    private String publisher;
    private int year;
    private int stock;

    public Book(String title, String author, String publisher, int year, int stock) {
        this.id = idCounter++;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getStock() {
        return stock;
    }

    public void reduceStock() {
        if (stock > 0) {
            stock--;
        }
    }

    public void increaseStock() {
        stock++;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Judul: " + title + ", Pengarang: " + author + ", Stok: " + stock;
    }
}

class Borrowing {
    private static int idCounter = 1;
    private int id;
    private Member member;
    private Book book;
    private String borrowDate;
    private String returnDate;

    public Borrowing(Member member, Book book, String borrowDate) {
        this.id = idCounter++;
        this.member = member;
        this.book = book;
        this.borrowDate = borrowDate;
        this.returnDate = null;
    }

    public int getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Book getBook() {
        return book;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public double calculateFine() {
        if (returnDate != null) {
            LocalDate borrow = LocalDate.parse(borrowDate);
            LocalDate returnD = LocalDate.parse(returnDate);
            long daysLate = ChronoUnit.DAYS.between(borrow, returnD) - 7; // 7 days borrowing period
            if (daysLate > 0) {
                return daysLate * 1000; // Fine 1000 IDR per day late
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return "ID Peminjaman: " + id + ", Anggota: " + member.getName() + ", Buku: " + book.getTitle() +
                ", Tanggal Pinjam: " + borrowDate + ", Tanggal Kembali: " + (returnDate != null ? returnDate : "Belum dikembalikan");
    }
}
