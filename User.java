import java.util.ArrayList;
import java.util.List;

public class User {
    String id;
    String name;
    String password;
    String role;
    String branch;
    int    year;
    int    fine;
    List<String> borrowedBookIds;
    List<String> historyBookIds;

    public User(String id, String name, String password, String role) {
        this.id              = id;
        this.name            = name;
        this.password        = password;
        this.role            = role;
        this.branch          = "CSE";
        this.year            = 1;
        this.fine            = 0;
        this.borrowedBookIds = new ArrayList<>();
        this.historyBookIds  = new ArrayList<>();
    }

    public void display() {
        System.out.printf("  %-12s | %-20s | %-10s | Fine: Rs.%d | Books: %d%n",
            id, name, role, fine, borrowedBookIds.size());
    }

    public void displayProfile() {
        System.out.println("  +---------------------------------------------");
        System.out.println("  | ID       : " + id);
        System.out.println("  | Name     : " + name);
        System.out.println("  | Role     : " + role);
        System.out.println("  | Branch   : " + branch + "  Year: " + year);
        System.out.println("  | Fine Due : Rs." + fine);
        System.out.println("  | Borrowed : " + borrowedBookIds.size() + " book(s) -> " + borrowedBookIds);
        System.out.println("  +---------------------------------------------");
    }

    @Override
    public String toString() {
        return String.format("%s (%s) [%s]", name, id, role);
    }
}
