public class BookSorter {

    public static Book[] bubbleSortByRating(Book[] books) {
        Book[] arr  = books.clone();
        int    n    = arr.length;
        int    swaps = 0, comparisons = 0;

        System.out.println("\n  BUBBLE SORT -- by Rating (highest first)");
        System.out.println("  Time: O(n^2) | Space: O(1) | Stable: YES");
        System.out.println("  -----------------------------------------");

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                comparisons++;
                if (arr[j].rating < arr[j + 1].rating) {
                    Book temp  = arr[j];
                    arr[j]     = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped    = true;
                    swaps++;
                }
            }
            if (!swapped) break;
        }

        System.out.printf("  Done -- Comparisons: %d | Swaps: %d%n", comparisons, swaps);
        return arr;
    }

    public static Book[] selectionSortByAvailability(Book[] books) {
        Book[] arr  = books.clone();
        int    n    = arr.length;
        int    swaps = 0, comparisons = 0;

        System.out.println("\n  SELECTION SORT -- by Availability (most copies first)");
        System.out.println("  Time: O(n^2) | Space: O(1) | Stable: NO");
        System.out.println("  -----------------------------------------");

        for (int i = 0; i < n - 1; i++) {
            int maxIdx = i;
            for (int j = i + 1; j < n; j++) {
                comparisons++;
                if (arr[j].availableCopies > arr[maxIdx].availableCopies) maxIdx = j;
            }
            if (maxIdx != i) {
                Book temp   = arr[i];
                arr[i]      = arr[maxIdx];
                arr[maxIdx] = temp;
                swaps++;
            }
        }

        System.out.printf("  Done -- Comparisons: %d | Swaps: %d%n", comparisons, swaps);
        return arr;
    }

    public static Book[] mergeSortByTitle(Book[] books) {
        System.out.println("\n  MERGE SORT -- by Title (A to Z)");
        System.out.println("  Time: O(n log n) | Space: O(n) | Stable: YES");
        System.out.println("  -----------------------------------------");

        Book[] arr = books.clone();
        mergeSort(arr, 0, arr.length - 1);
        System.out.println("  Done -- " + arr.length + " books sorted alphabetically");
        return arr;
    }

    private static void mergeSort(Book[] arr, int left, int right) {
        if (left >= right) return;
        int mid = (left + right) / 2;
        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }

    private static void merge(Book[] arr, int left, int mid, int right) {
        int leftLen  = mid - left + 1;
        int rightLen = right - mid;
        Book[] L = new Book[leftLen];
        Book[] R = new Book[rightLen];
        System.arraycopy(arr, left,    L, 0, leftLen);
        System.arraycopy(arr, mid + 1, R, 0, rightLen);

        int i = 0, j = 0, k = left;
        while (i < leftLen && j < rightLen) {
            if (L[i].title.compareToIgnoreCase(R[j].title) <= 0) arr[k++] = L[i++];
            else arr[k++] = R[j++];
        }
        while (i < leftLen)  arr[k++] = L[i++];
        while (j < rightLen) arr[k++] = R[j++];
    }

    public static Book[] quickSortByPrice(Book[] books) {
        System.out.println("\n  QUICK SORT -- by Borrow Fee (lowest first)");
        System.out.println("  Time: O(n log n) avg, O(n^2) worst | Space: O(log n)");
        System.out.println("  -----------------------------------------");

        Book[] arr = books.clone();
        quickSort(arr, 0, arr.length - 1);
        System.out.println("  Done -- " + arr.length + " books sorted by fee");
        return arr;
    }

    private static void quickSort(Book[] arr, int low, int high) {
        if (low >= high) return;
        int pivotIdx = partition(arr, low, high);
        quickSort(arr, low, pivotIdx - 1);
        quickSort(arr, pivotIdx + 1, high);
    }

    private static int partition(Book[] arr, int low, int high) {
        int pivot = arr[high].borrowFee;
        int i     = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j].borrowFee <= pivot) {
                i++;
                Book temp = arr[i]; arr[i] = arr[j]; arr[j] = temp;
            }
        }
        Book temp = arr[i + 1]; arr[i + 1] = arr[high]; arr[high] = temp;
        return i + 1;
    }

    public static void displaySorted(Book[] arr, int topN, String sortedBy) {
        int show = Math.min(topN, arr.length);
        System.out.println("\n  +-- TOP " + show + " BOOKS sorted by " + sortedBy + " -----------------------------------------------");
        System.out.printf ("  | %-3s %-38s %-22s %-12s %s%n", "#", "TITLE", "AUTHOR", "DIFFICULTY", "RATING | FEE | AVAIL");
        System.out.println("  +------------------------------------------------------------------------------------------");
        for (int i = 0; i < show; i++) {
            System.out.printf("  | %-3d", i + 1);
            arr[i].display();
        }
        System.out.println("  +------------------------------------------------------------------------------------------");
    }

    public static void compareAlgorithms(Book[] books) {
        System.out.println("\n  +== SORTING PERFORMANCE COMPARISON (" + books.length + " books) ====================+");
        System.out.println("  | Algorithm      | Time Complexity | Space  | Stable | Used for        |");
        System.out.println("  |----------------|-----------------|--------|--------|-----------------|");
        System.out.println("  | Bubble Sort    | O(n^2)          | O(1)   | YES    | Rating (desc)   |");
        System.out.println("  | Selection Sort | O(n^2)          | O(1)   | NO     | Availability    |");
        System.out.println("  | Merge Sort     | O(n log n)      | O(n)   | YES    | Title A-Z       |");
        System.out.println("  | Quick Sort     | O(n log n) avg  | O(log) | NO     | Price ascending |");
        System.out.println("  +=================================================================+");

        long start, end;

        start = System.nanoTime();
        bubbleSortByRating(books);
        end = System.nanoTime();
        System.out.printf("  Bubble Sort:    %6d ns%n", end - start);

        start = System.nanoTime();
        selectionSortByAvailability(books);
        end = System.nanoTime();
        System.out.printf("  Selection Sort: %6d ns%n", end - start);

        start = System.nanoTime();
        mergeSortByTitle(books);
        end = System.nanoTime();
        System.out.printf("  Merge Sort:     %6d ns%n", end - start);

        start = System.nanoTime();
        quickSortByPrice(books);
        end = System.nanoTime();
        System.out.printf("  Quick Sort:     %6d ns%n", end - start);
    }
}
