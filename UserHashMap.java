import java.util.ArrayList;
import java.util.List;

public class UserHashMap {

    private static final int    DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR      = 0.75;

    private static class Entry {
        String key;
        User   value;
        Entry  next;
        Entry(String key, User value) { this.key = key; this.value = value; this.next = null; }
    }

    private Entry[] buckets;
    private int     capacity;
    private int     size;

    public UserHashMap() {
        this.capacity = DEFAULT_CAPACITY;
        this.buckets  = new Entry[capacity];
        this.size     = 0;
    }

    private int hash(String key) {
        int hash  = 0;
        int prime = 31;
        for (char c : key.toCharArray()) hash = (hash * prime + c) % capacity;
        return Math.abs(hash);
    }

    public void put(String userId, User user) {
        if ((double) size / capacity >= LOAD_FACTOR) resize();
        int   index = hash(userId);
        Entry curr  = buckets[index];
        while (curr != null) {
            if (curr.key.equals(userId)) { curr.value = user; return; }
            curr = curr.next;
        }
        Entry newEntry = new Entry(userId, user);
        newEntry.next  = buckets[index];
        buckets[index] = newEntry;
        size++;
    }

    public User get(String userId) {
        int   index = hash(userId);
        Entry curr  = buckets[index];
        while (curr != null) {
            if (curr.key.equals(userId)) return curr.value;
            curr = curr.next;
        }
        return null;
    }

    public boolean remove(String userId) {
        int   index = hash(userId);
        Entry curr  = buckets[index];
        Entry prev  = null;
        while (curr != null) {
            if (curr.key.equals(userId)) {
                if (prev == null) buckets[index] = curr.next;
                else              prev.next = curr.next;
                size--;
                System.out.println("  User removed: " + userId);
                return true;
            }
            prev = curr; curr = curr.next;
        }
        return false;
    }

    public boolean containsKey(String userId) { return get(userId) != null; }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        for (Entry bucket : buckets) {
            Entry curr = bucket;
            while (curr != null) { list.add(curr.value); curr = curr.next; }
        }
        return list;
    }

    public List<User> getUsersByRole(String role) {
        List<User> list = new ArrayList<>();
        for (User u : getAllUsers()) if (u.role.equals(role)) list.add(u);
        return list;
    }

    public void displayHashTable() {
        System.out.println("  +== HASH TABLE STATE (capacity=" + capacity + ", size=" + size + ", load=" +
            String.format("%.0f%%", (double)size/capacity*100) + ") ==+");
        int filledBuckets = 0;
        for (int i = 0; i < capacity; i++) {
            if (buckets[i] != null) {
                filledBuckets++;
                System.out.printf("  | Bucket[%2d] -> ", i);
                Entry curr = buckets[i];
                while (curr != null) {
                    System.out.printf("[%s: %s]", curr.key, curr.value.name);
                    if (curr.next != null) System.out.print(" -> ");
                    curr = curr.next;
                }
                System.out.println();
            }
        }
        System.out.println("  +== Filled buckets: " + filledBuckets + "/" + capacity + " ==+");
    }

    public void displayAllUsers() {
        List<User> all = getAllUsers();
        if (all.isEmpty()) { System.out.println("  No users registered."); return; }
        System.out.println("  +-----------------------------------------------------------");
        System.out.printf ("  | %-12s %-20s %-12s %s%n", "ID", "NAME", "ROLE", "FINE | BOOKS");
        System.out.println("  +-----------------------------------------------------------");
        for (User u : all) u.display();
        System.out.println("  +-----------------------------------------------------------");
        System.out.println("  Total users: " + size);
    }

    private void resize() {
        int     newCapacity = capacity * 2;
        Entry[] newBuckets  = new Entry[newCapacity];
        int     oldCapacity = capacity;
        capacity = newCapacity;
        for (int i = 0; i < oldCapacity; i++) {
            Entry curr = buckets[i];
            while (curr != null) {
                Entry next     = curr.next;
                int   newIndex = hash(curr.key);
                curr.next      = newBuckets[newIndex];
                newBuckets[newIndex] = curr;
                curr = next;
            }
        }
        buckets = newBuckets;
        System.out.println("  HashMap resized: " + oldCapacity + " -> " + newCapacity + " buckets");
    }

    public int     size()    { return size; }
    public boolean isEmpty() { return size == 0; }
}
