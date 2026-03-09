public class RequestQueue {

    private static class QueueNode {
        BorrowRequest data;
        QueueNode next;
        QueueNode(BorrowRequest data) { this.data = data; this.next = null; }
    }

    public static class BorrowRequest {
        String userId;
        String userName;
        String bookId;
        String bookTitle;
        String requestDate;
        String role;

        BorrowRequest(String userId, String userName, String role,
                      String bookId, String bookTitle, String requestDate) {
            this.userId      = userId;
            this.userName    = userName;
            this.role        = role;
            this.bookId      = bookId;
            this.bookTitle   = bookTitle;
            this.requestDate = requestDate;
        }

        public void display(int pos) {
            System.out.printf("  [%d] %-15s (%-10s) -> %-38s | %s%n",
                pos, userName, userId, truncate(bookTitle, 38), requestDate);
        }

        private String truncate(String s, int max) {
            return s.length() > max ? s.substring(0, max - 2) + ".." : s;
        }
    }

    private QueueNode front;
    private QueueNode rear;
    private int       size;

    public RequestQueue() { front = null; rear = null; size = 0; }

    public void enqueue(BorrowRequest request) {
        QueueNode newNode = new QueueNode(request);
        if (isEmpty()) { front = rear = newNode; }
        else { rear.next = newNode; rear = newNode; }
        size++;
        System.out.printf("  Added to waitlist: %s wants \"%s\" (position %d)%n",
            request.userName, request.bookTitle, size);
    }

    public BorrowRequest dequeue() {
        if (isEmpty()) { System.out.println("  Queue is empty -- no pending requests."); return null; }
        BorrowRequest request = front.data;
        front = front.next;
        if (front == null) rear = null;
        size--;
        System.out.printf("  Approved request for: %s -> \"%s\"%n", request.userName, request.bookTitle);
        return request;
    }

    public BorrowRequest front() {
        if (isEmpty()) { System.out.println("  Queue is empty."); return null; }
        return front.data;
    }

    public void displayQueue() {
        if (isEmpty()) { System.out.println("  No pending borrow requests."); return; }
        System.out.println("  +== BORROW REQUEST QUEUE (FIFO -- served front to rear) =========================+");
        System.out.println("  | FRONT (next to be served)                                                      |");
        QueueNode curr = front;
        int pos = 1;
        while (curr != null) { curr.data.display(pos++); curr = curr.next; }
        System.out.println("  +================================================================================+");
        System.out.println("  Pending requests: " + size);
    }

    public int countForBook(String bookId) {
        int count = 0;
        QueueNode curr = front;
        while (curr != null) {
            if (curr.data.bookId.equals(bookId)) count++;
            curr = curr.next;
        }
        return count;
    }

    public boolean isEmpty() { return front == null; }
    public int     size()    { return size; }
}
