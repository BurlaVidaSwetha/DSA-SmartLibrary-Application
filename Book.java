public class Book {
    String id;
    String title;
    String author;
    String category;
    String difficulty;
    double rating;
    int    borrowFee;
    int    availableCopies;
    boolean isBorrowed;

    public Book(String id, String title, String author,
                String category, String difficulty,
                double rating, int borrowFee, int availableCopies) {
        this.id              = id;
        this.title           = title;
        this.author          = author;
        this.category        = category;
        this.difficulty      = difficulty;
        this.rating          = rating;
        this.borrowFee       = borrowFee;
        this.availableCopies = availableCopies;
        this.isBorrowed      = false;
    }

    public void display() {
        System.out.printf("  %-38s | %-22s | %s | %.1f | Rs.%-3d | %d copies%n",
            truncate(title, 38), truncate(author, 22),
            padDiff(difficulty), rating, borrowFee, availableCopies);
    }

    public void displayDetailed() {
        System.out.println("  +---------------------------------------------");
        System.out.println("  | Title     : " + title);
        System.out.println("  | Author    : " + author);
        System.out.println("  | Category  : " + category);
        System.out.println("  | Difficulty: " + difficulty);
        System.out.printf ("  | Rating    : %.1f%n", rating);
        System.out.println("  | Borrow Fee: Rs." + borrowFee);
        System.out.println("  | Available : " + availableCopies + " copies");
        System.out.println("  +---------------------------------------------");
    }

    private String truncate(String s, int max) {
        return s.length() > max ? s.substring(0, max - 2) + ".." : s;
    }
    private String padDiff(String d) {
        return String.format("%-12s", d);
    }

    @Override
    public String toString() {
        return String.format("\"%s\" by %s (Rs.%d)", title, author, borrowFee);
    }
}
