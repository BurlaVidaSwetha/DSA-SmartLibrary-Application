/**
 * SuggestionBox -- Custom Linked-List Queue
 * DSA concept: Queue (FIFO) via singly-linked nodes
 * Lecturers enqueue book suggestions; librarian dequeues / views them.
 */
public class SuggestionBox {

    //  inner node 
    private static class SNode {
        Suggestion data;
        SNode      next;
        SNode(Suggestion d) { this.data = d; this.next = null; }
    }

    //  Suggestion record 
    public static class Suggestion {
        String lecturerId;
        String lecturerName;
        String bookTitle;
        String bookAuthor;
        String reason;
        String date;
        boolean seen;

        public Suggestion(String lecturerId, String lecturerName,
                          String bookTitle, String bookAuthor,
                          String reason, String date) {
            this.lecturerId   = lecturerId;
            this.lecturerName = lecturerName;
            this.bookTitle    = bookTitle;
            this.bookAuthor   = bookAuthor;
            this.reason       = reason;
            this.date         = date;
            this.seen         = false;
        }

        public void display(int pos) {
            String tag = seen ? "[SEEN]   " : "[NEW]    ";
            System.out.printf("  %s [%d] From: %-18s | Book: %-28s | Author: %-18s%n",
                tag, pos,
                truncate(lecturerName, 18),
                truncate(bookTitle,    28),
                truncate(bookAuthor,   18));
            System.out.printf("         Reason : %s%n", reason);
            System.out.printf("         Date   : %s%n", date);
            System.out.println("         " + "-".repeat(75));
        }

        private String truncate(String s, int max) {
            if (s == null) return "";
            return s.length() > max ? s.substring(0, max - 2) + ".." : s;
        }
    }

    //  Queue internals 
    private SNode front;
    private SNode rear;
    private int   size;

    public SuggestionBox() { front = null; rear = null; size = 0; }

    /** Enqueue a new suggestion (rear) */
    public void enqueue(Suggestion s) {
        SNode node = new SNode(s);
        if (rear == null) { front = rear = node; }
        else              { rear.next = node; rear = node; }
        size++;
    }

    /** View and mark all suggestions seen */
    public void displayAll() {
        if (front == null) {
            System.out.println("  No suggestions received yet.");
            return;
        }
        System.out.println("  +== LECTURER SUGGESTIONS INBOX (" + size + " total) =====================================+");
        SNode curr = front;
        int   pos  = 1;
        while (curr != null) {
            curr.data.display(pos++);
            curr.data.seen = true;          // mark seen as we display
            curr = curr.next;
        }
        System.out.println("  +==================================================================================+");
    }

    /** Count unseen (new) suggestions */
    public int newCount() {
        int   c    = 0;
        SNode curr = front;
        while (curr != null) { if (!curr.data.seen) c++; curr = curr.next; }
        return c;
    }

    public boolean isEmpty() { return front == null; }
    public int     size()    { return size; }
}
