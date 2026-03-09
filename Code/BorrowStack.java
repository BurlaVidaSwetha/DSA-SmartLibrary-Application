public class BorrowStack {

    private static class StackNode {
        BorrowRecord data;
        StackNode next;
        StackNode(BorrowRecord data) { this.data = data; this.next = null; }
    }

    public static class BorrowRecord {
        String bookId;
        String bookTitle;
        String borrowDate;
        String dueDate;
        int    feePaid;
        String status;

        BorrowRecord(String bookId, String bookTitle,
                     String borrowDate, String dueDate, int feePaid) {
            this.bookId     = bookId;
            this.bookTitle  = bookTitle;
            this.borrowDate = borrowDate;
            this.dueDate    = dueDate;
            this.feePaid    = feePaid;
            this.status     = "ACTIVE";
        }

        public void display(int pos) {
            System.out.printf("  [%d] %-38s | Borrowed: %-12s Due: %-12s | Rs.%d | %s%n",
                pos, truncate(bookTitle, 38), borrowDate, dueDate, feePaid, status);
        }

        private String truncate(String s, int max) {
            return s.length() > max ? s.substring(0, max - 2) + ".." : s;
        }
    }

    private StackNode top;
    private int       size;

    public BorrowStack() { top = null; size = 0; }

    public void push(BorrowRecord record) {
        StackNode newNode = new StackNode(record);
        newNode.next = top;
        top = newNode;
        size++;
        System.out.println("  Borrowed: " + record.bookTitle + " (Rs." + record.feePaid + ")");
    }

    public BorrowRecord pop() {
        if (isEmpty()) {
            System.out.println("  Stack is empty -- no borrow history to undo.");
            return null;
        }
        BorrowRecord record = top.data;
        top = top.next;
        size--;
        System.out.println("  Returned: " + record.bookTitle);
        return record;
    }

    public BorrowRecord peek() {
        if (isEmpty()) { System.out.println("  Stack is empty."); return null; }
        return top.data;
    }

    public void displayHistory() {
        if (isEmpty()) { System.out.println("  No borrow history yet."); return; }
        System.out.println("  +== BORROW HISTORY (most recent first) ==========================================+");
        StackNode curr = top;
        int pos = 1;
        while (curr != null) { curr.data.display(pos++); curr = curr.next; }
        System.out.println("  +================================================================================+");
        System.out.println("  Total borrow records: " + size);
    }

    public int computeTotalFine() {
        int total = 0;
        StackNode curr = top;
        while (curr != null) {
            if ("ACTIVE".equals(curr.data.status)) total += curr.data.feePaid;
            curr = curr.next;
        }
        return total;
    }

    public void markTopReturned() { if (top != null) top.data.status = "RETURNED"; }

    public boolean isEmpty() { return top == null; }
    public int     size()    { return size; }
}
