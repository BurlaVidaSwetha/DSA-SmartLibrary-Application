public class BookLinkedList {

    private Node head;

    public BookLinkedList() { this.head = null; }

    public void addBook(Book book) {
        Node newNode = new Node(book);
        newNode.next = head;
        head = newNode;
        System.out.println("  Book added to catalog: " + book.title);
    }

    public void addBookAtTail(Book book) {
        Node newNode = new Node(book);
        if (head == null) { head = newNode; return; }
        Node curr = head;
        while (curr.next != null) curr = curr.next;
        curr.next = newNode;
    }

    public boolean removeBook(String bookId) {
        if (head == null) return false;
        if (head.data.id.equals(bookId)) {
            System.out.println("  Removed: " + head.data.title);
            head = head.next;
            return true;
        }
        Node prev = head;
        Node curr = head.next;
        while (curr != null) {
            if (curr.data.id.equals(bookId)) {
                System.out.println("  Removed: " + curr.data.title);
                prev.next = curr.next;
                return true;
            }
            prev = curr;
            curr = curr.next;
        }
        System.out.println("  Book ID not found: " + bookId);
        return false;
    }

    public Book findById(String bookId) {
        Node curr = head;
        while (curr != null) {
            if (curr.data.id.equals(bookId)) return curr.data;
            curr = curr.next;
        }
        return null;
    }

    // Find by partial title match (case-insensitive), returns first match
    public Book findByTitle(String titleKeyword) {
        Node curr = head;
        String kw = titleKeyword.toLowerCase();
        while (curr != null) {
            if (curr.data.title.toLowerCase().contains(kw)) return curr.data;
            curr = curr.next;
        }
        return null;
    }

    // Find all books matching a partial title
    public java.util.List<Book> findAllByTitle(String titleKeyword) {
        java.util.List<Book> results = new java.util.ArrayList<>();
        Node curr = head;
        String kw = titleKeyword.toLowerCase();
        while (curr != null) {
            if (curr.data.title.toLowerCase().contains(kw)) results.add(curr.data);
            curr = curr.next;
        }
        return results;
    }

    public boolean removeByTitle(String titleKeyword) {
        if (head == null) return false;
        String kw = titleKeyword.toLowerCase();
        if (head.data.title.toLowerCase().contains(kw)) {
            System.out.println("  Removed: " + head.data.title);
            head = head.next;
            return true;
        }
        Node prev = head;
        Node curr = head.next;
        while (curr != null) {
            if (curr.data.title.toLowerCase().contains(kw)) {
                System.out.println("  Removed: " + curr.data.title);
                prev.next = curr.next;
                return true;
            }
            prev = curr;
            curr = curr.next;
        }
        System.out.println("  Book not found: " + titleKeyword);
        return false;
    }

    public boolean updatePriceByTitle(String titleKeyword, int newPrice) {
        Book b = findByTitle(titleKeyword);
        if (b != null) {
            int old = b.borrowFee;
            b.borrowFee = newPrice;
            System.out.printf("  Price updated: %s -- Rs.%d -> Rs.%d%n", b.title, old, newPrice);
            return true;
        }
        return false;
    }

    public Book[] toArray() {
        int n = size();
        Book[] arr = new Book[n];
        Node curr = head;
        for (int i = 0; i < n; i++) { arr[i] = curr.data; curr = curr.next; }
        return arr;
    }

    public int size() {
        int count = 0;
        Node curr = head;
        while (curr != null) { count++; curr = curr.next; }
        return count;
    }

    public void displayAll() {
        if (head == null) { System.out.println("  No books in catalog."); return; }
        System.out.println("  +---------------------------------------------------------------------------------------------");
        System.out.printf ("  | %-38s %-22s %-12s %s%n", "TITLE", "AUTHOR", "DIFFICULTY", "RATING | FEE | AVAIL");
        System.out.println("  +---------------------------------------------------------------------------------------------");
        Node curr = head;
        int i = 1;
        while (curr != null) {
            System.out.print("  | " + i++ + ". ");
            curr.data.display();
            curr = curr.next;
        }
        System.out.println("  +---------------------------------------------------------------------------------------------");
        System.out.println("  Total books in catalog: " + (i - 1));
    }

    public boolean updatePrice(String bookId, int newPrice) {
        Book b = findById(bookId);
        if (b != null) {
            int old = b.borrowFee;
            b.borrowFee = newPrice;
            System.out.printf("  Price updated: %s -- Rs.%d -> Rs.%d%n", b.title, old, newPrice);
            return true;
        }
        return false;
    }

    public boolean updateCopies(String bookId, int delta) {
        Book b = findById(bookId);
        if (b != null) { b.availableCopies = Math.max(0, b.availableCopies + delta); return true; }
        return false;
    }

    public boolean isEmpty() { return head == null; }
    public Node getHead()    { return head; }
}
