import java.util.ArrayList;
import java.util.List;

public class BookSearch {

    public static List<Book> linearSearch(Book[] books, String keyword) {
        System.out.println("\n  LINEAR SEARCH for: \"" + keyword + "\"");
        System.out.println("  Scanning every book from index 0 to " + (books.length - 1));
        System.out.println("  Time complexity: O(n) -- worst case checks all " + books.length + " books");
        System.out.println("  -------------------------------------------------");

        List<Book> results = new ArrayList<>();
        String kw = keyword.toLowerCase();
        int comparisons = 0;

        for (int i = 0; i < books.length; i++) {
            comparisons++;
            Book b = books[i];
            if (b.title.toLowerCase().contains(kw)
             || b.author.toLowerCase().contains(kw)
             || b.category.toLowerCase().contains(kw)
             || b.difficulty.toLowerCase().contains(kw)) {
                results.add(b);
                System.out.printf("  Match at index %2d: %s%n", i, b.title);
            }
        }

        System.out.println("  -------------------------------------------------");
        System.out.printf("  Comparisons made: %d | Matches found: %d%n", comparisons, results.size());
        if (results.isEmpty()) System.out.println("  No books found matching \"" + keyword + "\"");
        return results;
    }

    public static Book binarySearch(Book[] sortedBooks, String exactTitle) {
        System.out.println("\n  BINARY SEARCH for exact title: \"" + exactTitle + "\"");
        System.out.println("  Repeatedly halving the search space");
        System.out.println("  Time complexity: O(log n) -- max " +
            (int)(Math.log(sortedBooks.length) / Math.log(2) + 1) + " steps for " + sortedBooks.length + " books");
        System.out.println("  Requirement: array must be sorted by title");
        System.out.println("  -------------------------------------------------");

        int low  = 0;
        int high = sortedBooks.length - 1;
        int step = 0;

        while (low <= high) {
            step++;
            int mid = (low + high) / 2;
            int cmp = sortedBooks[mid].title.compareToIgnoreCase(exactTitle);
            System.out.printf("  Step %2d: low=%2d  mid=%2d  high=%2d  -> checking \"%s\"%n",
                step, low, mid, high, sortedBooks[mid].title);

            if (cmp == 0) {
                System.out.println("  -------------------------------------------------");
                System.out.printf("  FOUND in %d step(s)! vs Linear Search worst-case %d steps%n",
                    step, sortedBooks.length);
                return sortedBooks[mid];
            } else if (cmp < 0) {
                System.out.printf("  -> \"%s\" comes BEFORE target -- search RIGHT half%n", sortedBooks[mid].title);
                low = mid + 1;
            } else {
                System.out.printf("  -> \"%s\" comes AFTER target -- search LEFT half%n", sortedBooks[mid].title);
                high = mid - 1;
            }
        }

        System.out.println("  -------------------------------------------------");
        System.out.println("  NOT FOUND after " + step + " step(s). Title must match exactly.");
        return null;
    }

    public static List<Book> searchByCategory(Book[] books, String category) {
        List<Book> results = new ArrayList<>();
        for (Book b : books) {
            if (b.category.equalsIgnoreCase(category)) results.add(b);
        }
        return results;
    }

    public static List<Book> searchByPriceRange(Book[] books, int minFee, int maxFee) {
        System.out.println("\n  PRICE RANGE SEARCH: Rs." + minFee + " - Rs." + maxFee);
        List<Book> results = new ArrayList<>();
        for (Book b : books) {
            if (b.borrowFee >= minFee && b.borrowFee <= maxFee) results.add(b);
        }
        System.out.println("  Found " + results.size() + " book(s) in range.");
        return results;
    }

    public static void displayResults(List<Book> results) {
        if (results.isEmpty()) return;
        System.out.println("\n  +-- SEARCH RESULTS (" + results.size() + " book(s)) -----------------------------------------------");
        for (Book b : results) {
            System.out.print("  | ");
            b.display();
        }
        System.out.println("  +--------------------------------------------------------------------------------");
    }
}
