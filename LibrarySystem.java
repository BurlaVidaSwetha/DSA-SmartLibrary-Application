import java.util.*;

public class LibrarySystem {

    static BookLinkedList     catalog     = new BookLinkedList();
    static UserHashMap        userDB      = new UserHashMap();
    static RequestQueue       waitlist    = new RequestQueue();
    static SuggestionBox      suggestions = new SuggestionBox();      // NEW
    static SemesterCurriculum curriculum  = new SemesterCurriculum(); // NEW
    static Map<String, BorrowStack> borrowStacks = new HashMap<>();
    static Scanner sc          = new Scanner(System.in);
    static User    currentUser = null;

    public static void main(String[] args) {
        printBanner();
        seedData();
        curriculum.seedDefaults();   // NEW
        mainMenu();
    }

    // 
    //  MAIN MENU
    // 
    static void mainMenu() {
        while (true) {
            printLine();
            System.out.println("  DIGITAL SMART LIBRARY -- MAIN MENU");
            printLine();
            System.out.println("  1. Student Login");
            System.out.println("  2. Librarian Login");
            System.out.println("  3. Lecturer Login");
            System.out.println("  4. Register New User");
            System.out.println("  5. Demo Mode");
            System.out.println("  0. Exit");
            printLine();
            int choice = inputInt("  Enter choice: ");
            switch (choice) {
                case 1: login("student");    break;
                case 2: login("librarian");  break;
                case 3: login("lecturer");   break;
                case 4: registerUser();      break;
                case 5: dsaDemoMenu();       break;
                case 0:
                    System.out.println("\n  Goodbye! Thank you for using Digital Smart Library.\n");
                    System.exit(0);
                default:
                    System.out.println("  Invalid choice.");
            }
        }
    }

    // 
    //  LOGIN
    // 
    static void login(String role) {
        printLine();
        System.out.println("  " + role.toUpperCase() + " LOGIN");
        printLine();
        System.out.print("  User ID   : ");  String id  = sc.nextLine().trim();
        System.out.print("  Password  : ");  String pwd = sc.nextLine().trim();
        User user = userDB.get(id);
        if (user == null) {
            System.out.println("  User ID not found. Use Register option to create an account.");
            return;
        }
        if (!user.role.equals(role)) {
            System.out.println("  This ID is registered as '" + user.role + "', not '" + role + "'.");
            return;
        }
        if (!user.password.equals(pwd)) {
            System.out.println("  Incorrect password.");
            return;
        }
        currentUser = user;
        System.out.println("\n  Welcome, " + user.name + "! (" + role + ")");
        if (user.fine > 0) System.out.println("  You have an outstanding fine of Rs." + user.fine);
        borrowStacks.putIfAbsent(id, new BorrowStack());

        if (role.equals("librarian") && suggestions.newCount() > 0)
            System.out.println("  ** You have " + suggestions.newCount() + " new suggestion(s) from lecturers! **");

        switch (role) {
            case "student":   studentMenu();   break;
            case "librarian": librarianMenu(); break;
            case "lecturer":  lecturerMenu();  break;
        }
        currentUser = null;
    }

    // 
    //  STUDENT MENU
    // 
    static void studentMenu() {
        while (true) {
            printLine();
            System.out.println("  STUDENT MENU -- " + currentUser.name);
            printLine();
            System.out.println("  1.  View All Books");
            System.out.println("  2.  Search Books by Keyword");
            System.out.println("  3.  Search Books by Exact Title");
            System.out.println("  4.  Browse Sorted Catalog");
            System.out.println("  5.  Borrow a Book");
            System.out.println("  6.  Return a Book");
            System.out.println("  7.  My Borrow History");
            System.out.println("  8.  My Profile & Fine");
            System.out.println("  9.  Semester Exam Prep -- Books by Year & Branch");
            System.out.println("  0.  Logout");
            printLine();
            int choice = inputInt("  Enter choice: ");
            switch (choice) {
                case 1: viewAllBooks();               break;
                case 2: searchLinear();               break;
                case 3: searchBinary();               break;
                case 4: browseSorted();               break;
                case 5: borrowBook();                 break;
                case 6: returnBook();                 break;
                case 7: viewBorrowHistory();          break;
                case 8: currentUser.displayProfile(); break;
                case 9: semesterExamPrep();           break;
                case 0: System.out.println("  Logged out."); return;
                default: System.out.println("  Invalid.");
            }
        }
    }

    // 
    //  LIBRARIAN MENU
    // 
    static void librarianMenu() {
        while (true) {
            printLine();
            System.out.println("  LIBRARIAN MENU -- " + currentUser.name);
            printLine();
            System.out.println("  1.  View All Books");
            System.out.println("  2.  Add Book to Catalog");
            System.out.println("  3.  Remove Book from Catalog");
            System.out.println("  4.  Edit Book Price");
            System.out.println("  5.  View All Users");
            System.out.println("  6.  View Borrow Waitlist");
            System.out.println("  7.  Approve Next Waitlist Request");
            System.out.println("  8.  View All Fines");
            System.out.println("  9.  Show User Database Internals");
            System.out.println("  10. View Lecturer Suggestions");
            System.out.println("  11. Manage Semester Curriculum (Branch / Year)");
            System.out.println("  0.  Logout");
            printLine();
            int choice = inputInt("  Enter choice: ");
            switch (choice) {
                case 1:  viewAllBooks();             break;
                case 2:  libAddBook();               break;
                case 3:  libRemoveBook();            break;
                case 4:  libEditPrice();             break;
                case 5:  userDB.displayAllUsers();   break;
                case 6:  waitlist.displayQueue();    break;
                case 7:  libApproveRequest();        break;
                case 8:  viewAllFines();             break;
                case 9:  userDB.displayHashTable();  break;
                case 10: libViewSuggestions();       break;
                case 11: libManageCurriculum();      break;
                case 0:  System.out.println("  Logged out."); return;
                default: System.out.println("  Invalid.");
            }
        }
    }

    // 
    //  LECTURER MENU
    // 
    static void lecturerMenu() {
        while (true) {
            printLine();
            System.out.println("  LECTURER MENU -- " + currentUser.name);
            printLine();
            System.out.println("  1.  View All Books");
            System.out.println("  2.  Search Books");
            System.out.println("  3.  Browse Sorted Catalog");
            System.out.println("  4.  Borrow a Book");
            System.out.println("  5.  Return a Book");
            System.out.println("  6.  My Borrow History");
            System.out.println("  7.  My Profile & Fine");
            System.out.println("  8.  Send Book Suggestion to Librarian");
            System.out.println("  0.  Logout");
            printLine();
            int choice = inputInt("  Enter choice: ");
            switch (choice) {
                case 1: viewAllBooks();               break;
                case 2: searchLinear();               break;
                case 3: browseSorted();               break;
                case 4: borrowBook();                 break;
                case 5: returnBook();                 break;
                case 6: viewBorrowHistory();          break;
                case 7: currentUser.displayProfile(); break;
                case 8: lecturerSendSuggestion();     break;
                case 0: System.out.println("  Logged out."); return;
                default: System.out.println("  Invalid.");
            }
        }
    }

    // 
    //  NEW FEATURE 1 -- LECTURER: SEND SUGGESTION TO LIBRARIAN
    // 
    static void lecturerSendSuggestion() {
        printLine();
        System.out.println("  SEND BOOK SUGGESTION TO LIBRARIAN");
        printLine();
        System.out.println("  Suggest a book to be added / highlighted in the library.");
        System.out.print("  Book Title  : "); String title  = sc.nextLine().trim();
        if (title.isEmpty()) { System.out.println("  Title cannot be empty."); return; }
        System.out.print("  Author Name : "); String author = sc.nextLine().trim();
        System.out.print("  Reason / Notes (why students need this book): ");
        String reason = sc.nextLine().trim();
        if (reason.isEmpty()) reason = "No reason provided.";

        SuggestionBox.Suggestion s = new SuggestionBox.Suggestion(
            currentUser.id, currentUser.name,
            title, author, reason,
            getCurrentDate()
        );
        suggestions.enqueue(s);
        System.out.println("\n  Suggestion submitted successfully!");
        System.out.println("  The librarian will review it shortly.");
    }

    // 
    //  NEW FEATURE 2 -- LIBRARIAN: VIEW SUGGESTIONS
    // 
    static void libViewSuggestions() {
        printLine();
        System.out.println("  LECTURER SUGGESTIONS INBOX");
        printLine();
        suggestions.displayAll();
    }

    // 
    //  NEW FEATURE 2b -- LIBRARIAN: MANAGE SEMESTER CURRICULUM
    // 
    static void libManageCurriculum() {
        while (true) {
            printLine();
            System.out.println("  MANAGE SEMESTER CURRICULUM");
            printLine();
            System.out.println("  1. View Full Curriculum");
            System.out.println("  2. Add Subject to a Branch/Year");
            System.out.println("  3. Remove Subject from a Branch/Year");
            System.out.println("  4. Add Book Keyword to a Subject");
            System.out.println("  5. Remove Book Keyword from a Subject");
            System.out.println("  0. Back");
            printLine();
            int ch = inputInt("  Enter choice: ");
            switch (ch) {
                case 1: curriculum.displayAllCurriculum(); break;
                case 2: libCurriculumAddSubject();         break;
                case 3: libCurriculumRemoveSubject();      break;
                case 4: libCurriculumAddKeyword();         break;
                case 5: libCurriculumRemoveKeyword();      break;
                case 0: return;
                default: System.out.println("  Invalid.");
            }
        }
    }

    static void libCurriculumAddSubject() {
        printLine();
        System.out.println("  ADD SUBJECT");
        printLine();
        String branch = pickBranch();
        int    year   = pickYear();
        System.out.print("  Subject Code (e.g. ML, OS): "); String subCode = sc.nextLine().trim();
        System.out.print("  Subject Name : "); String subName = sc.nextLine().trim();
        if (subName.isEmpty()) { System.out.println("  Name cannot be empty."); return; }
        System.out.print("  Icon (emoji, press Enter to skip): "); String icon = sc.nextLine().trim();
        if (icon.isEmpty()) icon = "";
        System.out.print("  Description  : "); String desc = sc.nextLine().trim();
        System.out.print("  Book Keywords (comma-separated, e.g. Python,Algorithm): ");
        String raw = sc.nextLine().trim();
        String[] kwArr = raw.isEmpty() ? new String[0] : raw.split(",");
        for (int i = 0; i < kwArr.length; i++) kwArr[i] = kwArr[i].trim();
        SemesterCurriculum.Subject sub = new SemesterCurriculum.Subject(subCode, subName, icon, desc, kwArr);
        curriculum.add(branch, year, sub);
        System.out.println("  Subject [" + subCode + "] \"" + subName + "\" added to " + branch + " Year " + year + ".");
    }

    static void libCurriculumRemoveSubject() {
        printLine();
        System.out.println("  REMOVE SUBJECT");
        printLine();
        String branch = pickBranch();
        int    year   = pickYear();
        showSubjectList(branch, year);
        System.out.print("  Enter subject code to remove (e.g. ML, OS, DBMS): ");
        String name = sc.nextLine().trim();
        if (curriculum.removeSubject(branch, year, name))
            System.out.println("  Subject removed.");
        else
            System.out.println("  Subject not found.");
    }

    static void libCurriculumAddKeyword() {
        printLine();
        System.out.println("  ADD BOOK KEYWORD TO SUBJECT");
        printLine();
        String branch = pickBranch();
        int    year   = pickYear();
        showSubjectList(branch, year);
        System.out.print("  Subject code : "); String sub = sc.nextLine().trim();
        System.out.print("  New keyword  : "); String kw  = sc.nextLine().trim();
        if (curriculum.addKeywordToSubject(branch, year, sub, kw))
            System.out.println("  Keyword added.");
        else
            System.out.println("  Subject not found.");
    }

    static void libCurriculumRemoveKeyword() {
        printLine();
        System.out.println("  REMOVE BOOK KEYWORD FROM SUBJECT");
        printLine();
        String branch = pickBranch();
        int    year   = pickYear();
        showSubjectList(branch, year);
        System.out.print("  Subject code : "); String sub = sc.nextLine().trim();
        System.out.print("  Keyword      : "); String kw  = sc.nextLine().trim();
        if (curriculum.removeKeywordFromSubject(branch, year, sub, kw))
            System.out.println("  Keyword removed.");
        else
            System.out.println("  Subject or keyword not found.");
    }

    static void showSubjectList(String branch, int year) {
        LinkedList<SemesterCurriculum.Subject> list = curriculum.getSubjects(branch, year);
        if (list.isEmpty()) { System.out.println("  No subjects defined yet."); return; }
        System.out.println("  Subjects for " + branch + " Year " + year + " (" + list.size() + " subjects):");
        int i = 1;
        for (SemesterCurriculum.Subject s : list)
            System.out.println("    " + i++ + ". [" + s.code + "] " + s.icon + " " + s.name
                + "  [keywords: " + s.bookKeywords + "]");
        System.out.println();
    }

    // 
    //  NEW FEATURE 3 -- STUDENT: SEMESTER EXAM PREP
    // 
    static void semesterExamPrep() {
        printLine();
        System.out.println("  SEMESTER EXAM PREP -- BOOKS BY YEAR & BRANCH");
        printLine();
        System.out.println("  1. Use my registered Branch & Year (" +
            currentUser.branch + " | Year " + currentUser.year + ")");
        System.out.println("  2. Choose Branch & Year manually");
        int opt = inputInt("  Enter choice: ");

        String branch;
        int    year;
        if (opt == 1) {
            branch = currentUser.branch;
            year   = currentUser.year;
        } else {
            branch = pickBranch();
            year   = pickYear();
        }

        LinkedList<SemesterCurriculum.Subject> subjects = curriculum.getSubjects(branch, year);
        if (subjects.isEmpty()) {
            System.out.println("  No curriculum data found for " + branch + " Year " + year + ".");
            System.out.println("  Ask the librarian to update the curriculum.");
            return;
        }

        Book[] allBooks = catalog.toArray();
        printLine();
        System.out.println("  EXAM PREP: " + branch + " | YEAR " + year);
        System.out.println("  Total subjects this semester: " + subjects.size());
        printLine();

        for (SemesterCurriculum.Subject subject : subjects) {
            System.out.println("\n  [" + subject.code + "] " + subject.icon + "  " + subject.name);
            System.out.println("  " + subject.desc);
            System.out.println("  " + "-".repeat(60));

            // Collect matched books (no duplicates)
            List<Book> matched = new ArrayList<>();
            for (String kw : subject.bookKeywords) {
                for (Book b : allBooks) {
                    if (!matched.contains(b) &&
                        (b.title.toLowerCase().contains(kw.toLowerCase())    ||
                         b.category.toLowerCase().contains(kw.toLowerCase()) ||
                         b.author.toLowerCase().contains(kw.toLowerCase()))) {
                        matched.add(b);
                    }
                }
            }

            if (matched.isEmpty()) {
                System.out.println("  No matching books in catalog yet.");
            } else {
                System.out.printf("  %-38s %-14s %s%n",
                    "TITLE", "DIFFICULTY", "RATING | FEE | COPIES");
                System.out.println("  " + "-".repeat(72));
                for (Book b : matched) {
                    System.out.print("  ");
                    b.display();
                }
            }
        }

        printLine();
        System.out.println("  Tip: Use option 5 (Borrow a Book) to borrow any book above!");
        printLine();
    }

    // 
    //  HELPERS
    // 
    static String pickBranch() {
        System.out.println("  Select Branch:");
        System.out.println("    1. CSE    2. CSIT    3. ECE    4. AIDS");
        int b = inputInt("  Enter choice: ");
        switch (b) {
            case 1: return "CSE";
            case 2: return "CSIT";
            case 3: return "ECE";
            case 4: return "AIDS";
            default: System.out.println("  Invalid, defaulting to CSE."); return "CSE";
        }
    }

    static int pickYear() {
        int y = inputInt("  Enter Year (1-4): ");
        if (y < 1 || y > 4) { System.out.println("  Invalid, defaulting to Year 1."); return 1; }
        return y;
    }

    // 
    //  ORIGINAL BOOK / BORROW METHODS (UNCHANGED)
    // 
    static void viewAllBooks() {
        printLine();
        System.out.println("  BOOK CATALOG");
        printLine();
        catalog.displayAll();
    }

    static void searchLinear() {
        System.out.print("\n  Enter keyword (title / author / category): ");
        String kw = sc.nextLine().trim();
        Book[] books = catalog.toArray();
        List<Book> results = BookSearch.linearSearch(books, kw);
        BookSearch.displayResults(results);
    }

    static void searchBinary() {
        System.out.print("\n  Enter EXACT book title: ");
        String title = sc.nextLine().trim();
        Book[] books  = catalog.toArray();
        Book[] sorted = BookSorter.mergeSortByTitle(books);
        Book   result = BookSearch.binarySearch(sorted, title);
        if (result != null) {
            System.out.println("\n  Book found:");
            result.displayDetailed();
        }
    }

    static void browseSorted() {
        printLine();
        System.out.println("  SORT CATALOG BY:");
        System.out.println("  1. Rating");
        System.out.println("  2. Availability");
        System.out.println("  3. Title A-Z");
        System.out.println("  4. Price ascending");
        System.out.println("  5. Compare all sorting algorithms");
        printLine();
        int choice = inputInt("  Enter choice: ");
        Book[] books = catalog.toArray();
        Book[] sorted;
        switch (choice) {
            case 1:
                sorted = BookSorter.bubbleSortByRating(books);
                BookSorter.displaySorted(sorted, sorted.length, "Rating (highest first)");
                break;
            case 2:
                sorted = BookSorter.selectionSortByAvailability(books);
                BookSorter.displaySorted(sorted, sorted.length, "Availability (most copies first)");
                break;
            case 3:
                sorted = BookSorter.mergeSortByTitle(books);
                BookSorter.displaySorted(sorted, sorted.length, "Title (A to Z)");
                break;
            case 4:
                sorted = BookSorter.quickSortByPrice(books);
                BookSorter.displaySorted(sorted, sorted.length, "Price (lowest first)");
                break;
            case 5:
                BookSorter.compareAlgorithms(books);
                break;
            default:
                System.out.println("  Invalid choice.");
        }
    }

    static void borrowBook() {
        printLine();
        System.out.println("  BORROW A BOOK");
        printLine();
        System.out.print("  Enter book name (or part of it): ");
        String keyword = sc.nextLine().trim();
        java.util.List<Book> matches = catalog.findAllByTitle(keyword);
        if (matches.isEmpty()) {
            System.out.println("  No book found matching \"" + keyword + "\". Use View All Books to browse.");
            return;
        }
        Book book;
        if (matches.size() == 1) {
            book = matches.get(0);
        } else {
            System.out.println("  Multiple matches found:");
            for (int i = 0; i < matches.size(); i++)
                System.out.printf("  %d. %s by %s%n", i + 1, matches.get(i).title, matches.get(i).author);
            int pick = inputInt("  Enter number to select: ") - 1;
            if (pick < 0 || pick >= matches.size()) { System.out.println("  Invalid selection."); return; }
            book = matches.get(pick);
        }
        String bookId = book.id;
        if (currentUser.borrowedBookIds.contains(bookId)) {
            System.out.println("  You already have this book borrowed.");
            return;
        }
        if (book.availableCopies <= 0) {
            System.out.println("  No copies available. Adding you to the waitlist...");
            RequestQueue.BorrowRequest req = new RequestQueue.BorrowRequest(
                currentUser.id, currentUser.name, currentUser.role,
                bookId, book.title, getCurrentDate()
            );
            waitlist.enqueue(req);
            System.out.println("  Waitlist position: " + waitlist.countForBook(bookId));
            return;
        }
        System.out.println("\n  Book details:");
        book.displayDetailed();
        System.out.printf("  Borrow fee: Rs.%d | Your current fine: Rs.%d%n", book.borrowFee, currentUser.fine);
        System.out.print("  Confirm borrow? (y/n): ");
        if (!sc.nextLine().trim().equalsIgnoreCase("y")) { System.out.println("  Borrow cancelled."); return; }
        System.out.print("  Due date (YYYY-MM-DD, press Enter for default 14 days): ");
        String dueDate = sc.nextLine().trim();
        if (dueDate.isEmpty()) dueDate = getDateAfterDays(14);
        BorrowStack.BorrowRecord record = new BorrowStack.BorrowRecord(
            bookId, book.title, getCurrentDate(), dueDate, book.borrowFee
        );
        BorrowStack stack = borrowStacks.get(currentUser.id);
        stack.push(record);
        catalog.updateCopies(bookId, -1);
        currentUser.borrowedBookIds.add(bookId);
        currentUser.fine += book.borrowFee;
        userDB.put(currentUser.id, currentUser);
        System.out.println("\n  Book borrowed successfully!");
        System.out.printf("  \"%s\" | Due: %s | Fee: Rs.%d | Total Fine: Rs.%d%n",
            book.title, dueDate, book.borrowFee, currentUser.fine);
    }

    static void returnBook() {
        printLine();
        System.out.println("  RETURN A BOOK");
        printLine();
        BorrowStack stack = borrowStacks.get(currentUser.id);
        if (stack == null || stack.isEmpty()) {
            System.out.println("  You have no borrowed books to return.");
            return;
        }
        System.out.println("  Your most recent borrow:");
        BorrowStack.BorrowRecord top = stack.peek();
        System.out.printf("  \"%s\" | Due: %s | Fee: Rs.%d%n", top.bookTitle, top.dueDate, top.feePaid);
        System.out.print("  Return this book? (y/n): ");
        if (!sc.nextLine().trim().equalsIgnoreCase("y")) { System.out.println("  Return cancelled."); return; }
        BorrowStack.BorrowRecord record = stack.pop();
        record.status = "RETURNED";
        catalog.updateCopies(record.bookId, +1);
        currentUser.borrowedBookIds.remove(record.bookId);
        currentUser.fine = Math.max(0, currentUser.fine - record.feePaid);
        userDB.put(currentUser.id, currentUser);
        System.out.println("\n  Book returned successfully!");
        System.out.printf("  Fine cleared: Rs.%d | Remaining fine: Rs.%d%n", record.feePaid, currentUser.fine);
        int waiting = waitlist.countForBook(record.bookId);
        if (waiting > 0)
            System.out.println("  Note: " + waiting + " student(s) waiting for this book. Librarian can approve from waitlist.");
    }

    static void viewBorrowHistory() {
        printLine();
        System.out.println("  BORROW HISTORY");
        printLine();
        BorrowStack stack = borrowStacks.get(currentUser.id);
        if (stack == null || stack.isEmpty()) { System.out.println("  No borrow history yet."); return; }
        stack.displayHistory();
        System.out.printf("  Total active fine: Rs.%d%n", stack.computeTotalFine());
    }

    static void libAddBook() {
        printLine();
        System.out.println("  ADD BOOK TO CATALOG");
        printLine();
        String id = "B" + String.format("%03d", catalog.size() + 1);
        System.out.print("  Title         : "); String title  = sc.nextLine().trim();
        System.out.print("  Author        : "); String author = sc.nextLine().trim();
        System.out.print("  Category (Programming/Academic/Personality/Novels/Career): ");
        String cat  = sc.nextLine().trim();
        System.out.print("  Difficulty (Beginner/Intermediate/Advanced): ");
        String diff = sc.nextLine().trim();
        double rating = inputDouble("  Rating (0.0-5.0): ");
        int    fee    = inputInt   ("  Borrow Fee (Rs.): ");
        int    copies = inputInt   ("  Available Copies: ");
        Book newBook = new Book(id, title, author, cat, diff, rating, fee, copies);
        catalog.addBook(newBook);
    }

    static void libRemoveBook() {
        printLine();
        System.out.println("  REMOVE BOOK FROM CATALOG");
        printLine();
        System.out.print("  Enter book name to remove: ");
        String keyword = sc.nextLine().trim();
        java.util.List<Book> matches = catalog.findAllByTitle(keyword);
        if (matches.isEmpty()) { System.out.println("  No book found matching \"" + keyword + "\""); return; }
        Book book;
        if (matches.size() == 1) {
            book = matches.get(0);
        } else {
            System.out.println("  Multiple matches found:");
            for (int i = 0; i < matches.size(); i++)
                System.out.printf("  %d. %s by %s%n", i + 1, matches.get(i).title, matches.get(i).author);
            int pick = inputInt("  Enter number to select: ") - 1;
            if (pick < 0 || pick >= matches.size()) { System.out.println("  Invalid selection."); return; }
            book = matches.get(pick);
        }
        catalog.removeBook(book.id);
    }

    static void libEditPrice() {
        printLine();
        System.out.println("  EDIT BOOK BORROW FEE");
        printLine();
        System.out.print("  Enter book name: ");
        String keyword = sc.nextLine().trim();
        java.util.List<Book> matches = catalog.findAllByTitle(keyword);
        if (matches.isEmpty()) { System.out.println("  No book found matching \"" + keyword + "\""); return; }
        Book book;
        if (matches.size() == 1) {
            book = matches.get(0);
        } else {
            System.out.println("  Multiple matches found:");
            for (int i = 0; i < matches.size(); i++)
                System.out.printf("  %d. %s by %s%n", i + 1, matches.get(i).title, matches.get(i).author);
            int pick = inputInt("  Enter number to select: ") - 1;
            if (pick < 0 || pick >= matches.size()) { System.out.println("  Invalid selection."); return; }
            book = matches.get(pick);
        }
        int newPrice = inputInt("  New borrow fee (Rs.): ");
        if (!catalog.updatePrice(book.id, newPrice)) System.out.println("  Book not found.");
    }

    static void libApproveRequest() {
        printLine();
        System.out.println("  APPROVE NEXT BORROW REQUEST");
        printLine();
        waitlist.displayQueue();
        if (waitlist.isEmpty()) return;
        System.out.print("\n  Approve next request? (y/n): ");
        if (!sc.nextLine().trim().equalsIgnoreCase("y")) return;
        RequestQueue.BorrowRequest req = waitlist.dequeue();
        if (req != null) {
            System.out.printf("  Approved: %s can now borrow \"%s\"%n", req.userName, req.bookTitle);
            System.out.println("  Please notify the student to collect their book.");
        }
    }

    static void viewAllFines() {
        printLine();
        System.out.println("  ALL OUTSTANDING FINES");
        printLine();
        List<User> users = userDB.getAllUsers();
        int total = 0; boolean anyFine = false;
        System.out.printf("  %-12s %-20s %-12s %s%n", "USER ID", "NAME", "ROLE", "FINE DUE");
        System.out.println("  ---------------------------------------------------------");
        for (User u : users) {
            if (u.fine > 0) {
                System.out.printf("  %-12s %-20s %-12s Rs.%d%n", u.id, u.name, u.role, u.fine);
                total += u.fine; anyFine = true;
            }
        }
        if (!anyFine) System.out.println("  No outstanding fines.");
        else System.out.println("  ---------------------------------------------------------\n  Total outstanding: Rs." + total);
    }

    static void registerUser() {
        printLine();
        System.out.println("  REGISTER NEW USER");
        printLine();
        System.out.print("  Role (student/lecturer): ");
        String role = sc.nextLine().trim().toLowerCase();
        if (!role.equals("student") && !role.equals("lecturer")) {
            System.out.println("  Only students and lecturers can self-register."); return;
        }
        System.out.print("  User ID   : "); String id = sc.nextLine().trim().toUpperCase();
        if (userDB.containsKey(id)) { System.out.println("  ID already exists."); return; }
        System.out.print("  Full Name : "); String name = sc.nextLine().trim();
        System.out.print("  Password  : "); String pwd  = sc.nextLine().trim();
        System.out.print("  Branch (CSE/CSIT/ECE/AIDS): "); String branch = sc.nextLine().trim().toUpperCase();
        int year = inputInt("  Year (1-4): ");
        User user = new User(id, name, pwd, role);
        user.branch = branch; user.year = year;
        userDB.put(id, user);
        borrowStacks.put(id, new BorrowStack());
        System.out.println("  User registered successfully! You can now login.");
    }

    // 
    //  DSA DEMO MENU (UNCHANGED)
    // 
    static void dsaDemoMenu() {
        while (true) {
            printLine();
            System.out.println("  DEMO MODE -- Data Structures in Action");
            printLine();
            System.out.println("  1. Linked List  -- add / remove / traverse");
            System.out.println("  2. Stack        -- push / pop / peek");
            System.out.println("  3. Queue        -- enqueue / dequeue");
            System.out.println("  4. HashMap      -- hash function + collision");
            System.out.println("  5. Linear Search -- step-by-step traversal");
            System.out.println("  6. Binary Search -- step-by-step halving");
            System.out.println("  7. All Sorting   -- 4 algorithms + timing");
            System.out.println("  0. Back to Main Menu");
            printLine();
            int ch = inputInt("  Enter choice: ");
            switch (ch) {
                case 1: demoLinkedList();   break;
                case 2: demoStack();        break;
                case 3: demoQueue();        break;
                case 4: demoHashMap();      break;
                case 5: demoLinearSearch(); break;
                case 6: demoBinarySearch(); break;
                case 7: BookSorter.compareAlgorithms(catalog.toArray()); break;
                case 0: return;
                default: System.out.println("  Invalid.");
            }
        }
    }

    static void demoLinkedList() {
        printLine();
        System.out.println("  LINKED LIST DEMO");
        System.out.println("  Structure: HEAD -> [Node1] -> [Node2] -> [Node3] -> null");
        System.out.println("  addBook() at head: O(1) | removeBook(): O(n) | findById(): O(n)");
        printLine();
        BookLinkedList demo = new BookLinkedList();
        System.out.println("\n  Step 1: addBook() -- insert at head");
        demo.addBook(new Book("D001","DSA Made Easy","Narasimha","Academic","Intermediate",4.5,55,50));
        demo.addBook(new Book("D002","CLRS","Cormen","Academic","Advanced",4.9,79,30));
        demo.addBook(new Book("D003","Cracking Interviews","Gayle","Career","Intermediate",4.8,59,45));
        System.out.println("\n  Step 2: displayAll() -- traverse head to tail");
        demo.displayAll();
        System.out.println("\n  Step 3: removeBook('D002') -- traverse and unlink");
        demo.removeBook("D002");
        System.out.println("\n  Step 4: After removal:");
        demo.displayAll();
        System.out.println("\n  Step 5: findById('D001')");
        Book found = demo.findById("D001");
        if (found != null) found.displayDetailed();
    }

    static void demoStack() {
        printLine();
        System.out.println("  STACK DEMO -- LIFO (Last In, First Out)");
        System.out.println("  push(): O(1) | pop(): O(1) | peek(): O(1)");
        System.out.println("  Visual: TOP -> [most recent] -> [older] -> [oldest] -> BOTTOM");
        printLine();
        BorrowStack demo = new BorrowStack();
        System.out.println("\n  push('Clean Code')   -- student borrows");
        demo.push(new BorrowStack.BorrowRecord("B001","Clean Code","2025-01-01","2025-01-15",49));
        System.out.println("  push('Atomic Habits') -- student borrows next");
        demo.push(new BorrowStack.BorrowRecord("B002","Atomic Habits","2025-01-05","2025-01-19",29));
        System.out.println("  push('The Alchemist') -- most recent borrow");
        demo.push(new BorrowStack.BorrowRecord("B003","The Alchemist","2025-01-10","2025-01-24",19));
        System.out.println("\n  peek() -- shows top without removing:");
        BorrowStack.BorrowRecord top = demo.peek();
        System.out.println("  TOP: " + top.bookTitle + " (fee Rs." + top.feePaid + ")");
        System.out.println("\n  Stack state (top to bottom):");
        demo.displayHistory();
        System.out.println("\n  pop() -- student returns most recent book:");
        demo.pop();
        System.out.println("  Stack after pop:");
        demo.displayHistory();
    }

    static void demoQueue() {
        printLine();
        System.out.println("  QUEUE DEMO -- FIFO (First In, First Out)");
        System.out.println("  enqueue(): add at REAR | dequeue(): remove from FRONT");
        System.out.println("  Visual: FRONT -> [first] -> [second] -> [third] <- REAR");
        printLine();
        RequestQueue demo = new RequestQueue();
        System.out.println("\n  enqueue: Alice requests 'Design Patterns'");
        demo.enqueue(new RequestQueue.BorrowRequest("STU001","Alice","student","B008","Design Patterns","2025-01-10"));
        System.out.println("  enqueue: Bob joins waitlist next");
        demo.enqueue(new RequestQueue.BorrowRequest("STU002","Bob","student","B008","Design Patterns","2025-01-11"));
        System.out.println("  enqueue: Carol joins last");
        demo.enqueue(new RequestQueue.BorrowRequest("LEC001","Carol","lecturer","B008","Design Patterns","2025-01-12"));
        System.out.println("\n  Queue state:");
        demo.displayQueue();
        System.out.println("\n  front() -- who's next without removing:");
        RequestQueue.BorrowRequest f = demo.front();
        System.out.println("  NEXT: " + f.userName + " (requested " + f.requestDate + ")");
        System.out.println("\n  dequeue() -- approve Alice first (FIFO):");
        demo.dequeue();
        System.out.println("  Queue after approving Alice:");
        demo.displayQueue();
    }

    static void demoHashMap()      { userDB.displayHashTable(); }
    static void demoLinearSearch() {
        Book[] books = catalog.toArray();
        List<Book> results = BookSearch.linearSearch(books, "python");
        BookSearch.displayResults(results);
    }
    static void demoBinarySearch() {
        Book[] books  = catalog.toArray();
        Book[] sorted = BookSorter.mergeSortByTitle(books);
        System.out.println("  (Array sorted by title first so Binary Search can work)");
        BookSearch.binarySearch(sorted, "Clean Code");
    }

    // 
    //  SEED DATA (UNCHANGED)
    // 
    static void seedData() {
        catalog.addBookAtTail(new Book("B001","Clean Code",               "Robert C. Martin",   "Programming",  "Intermediate", 4.8, 49, 181));
        catalog.addBookAtTail(new Book("B002","The Pragmatic Programmer", "Hunt & Thomas",       "Programming",  "Intermediate", 4.7, 49, 114));
        catalog.addBookAtTail(new Book("B003","Introduction to Algorithms","Cormen et al",       "Academic",     "Advanced",     4.6, 79, 103));
        catalog.addBookAtTail(new Book("B004","Cracking the Coding Interview","Gayle McDowell",  "Career",       "Intermediate", 4.8, 59, 194));
        catalog.addBookAtTail(new Book("B005","Python Crash Course",      "Eric Matthes",        "Programming",  "Beginner",     4.6, 39, 135));
        catalog.addBookAtTail(new Book("B006","System Design Interview",  "Alex Xu",             "Programming",  "Advanced",     4.7, 69, 128));
        catalog.addBookAtTail(new Book("B007","Design Patterns",          "Gang of Four",        "Programming",  "Advanced",     4.5, 79, 117));
        catalog.addBookAtTail(new Book("B008","Atomic Habits",            "James Clear",         "Personality",  "Beginner",     4.9, 29, 175));
        catalog.addBookAtTail(new Book("B009","Deep Work",                "Cal Newport",         "Personality",  "Beginner",     4.7, 29, 154));
        catalog.addBookAtTail(new Book("B010","Rich Dad Poor Dad",        "Robert T. Kiyosaki",  "Personality",  "Beginner",     4.4, 25, 127));
        catalog.addBookAtTail(new Book("B011","The Alchemist",            "Paulo Coelho",        "Novels",       "Beginner",     4.7, 19, 129));
        catalog.addBookAtTail(new Book("B012","1984",                     "George Orwell",       "Novels",       "Intermediate", 4.8, 19, 177));
        catalog.addBookAtTail(new Book("B013","Harry Potter",             "J.K. Rowling",        "Novels",       "Beginner",     4.9, 19, 103));
        catalog.addBookAtTail(new Book("B014","AI: A Modern Approach",    "Russell & Norvig",    "Academic",     "Advanced",     4.7, 89, 111));
        catalog.addBookAtTail(new Book("B015","Database System Concepts", "Silberschatz",        "Academic",     "Intermediate", 4.5, 55, 113));

        User lib = new User("LIB001", "Ms. Priya",   "lib123",  "librarian");
        User s1  = new User("STU001", "Manasvi",     "pass123", "student");
        User s2  = new User("STU002", "Arjun",       "pass456", "student");
        User l1  = new User("LEC001", "Dr. Sharma",  "lec123",  "lecturer");
        User l2  = new User("LEC002", "Prof. Reddy", "lec456",  "lecturer");
        s1.branch = "CSE";  s1.year = 2;
        s2.branch = "CSIT"; s2.year = 3;
        l1.branch = "CSE";  l2.branch = "ECE";
        userDB.put(lib.id, lib);
        userDB.put(s1.id,  s1);
        userDB.put(s2.id,  s2);
        userDB.put(l1.id,  l1);
        userDB.put(l2.id,  l2);
        for (User u : userDB.getAllUsers()) borrowStacks.put(u.id, new BorrowStack());
    }

    // 
    //  UTILITIES (UNCHANGED)
    // 
    static int inputInt(String prompt) {
        while (true) {
            try { System.out.print(prompt); return Integer.parseInt(sc.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("  Please enter a valid number."); }
        }
    }
    static double inputDouble(String prompt) {
        while (true) {
            try { System.out.print(prompt); return Double.parseDouble(sc.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("  Please enter a valid decimal number."); }
        }
    }
    static String getCurrentDate()           { return java.time.LocalDate.now().toString(); }
    static String getDateAfterDays(int days) { return java.time.LocalDate.now().plusDays(days).toString(); }
    static void printLine() { System.out.println("  ======================================================"); }
    static void printBanner() {
        System.out.println();
        System.out.println("  +--------------------------------------------------+");
        System.out.println("  |        DIGITAL SMART LIBRARY                     |");
        System.out.println("  |        Welcome! Please login to continue.        |");
        System.out.println("  +--------------------------------------------------+");
        System.out.println("  |  Default Accounts:                               |");
        System.out.println("  |   Librarian : LIB001 / lib123                   |");
        System.out.println("  |   Student   : STU001 / pass123  (Manasvi)        |");
        System.out.println("  |   Student   : STU002 / pass456  (Arjun)          |");
        System.out.println("  |   Lecturer  : LEC001 / lec123   (Dr. Sharma)     |");
        System.out.println("  +--------------------------------------------------+");
        System.out.println();
    }
}
