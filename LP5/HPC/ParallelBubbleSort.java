import java.util.Arrays;

class ParallelBubbleSort {

  static void bubble(int[] array) {
    int n = array.length;
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (array[j] > array[j + 1]) {
          int temp = array[j];
          array[j] = array[j + 1];
          array[j + 1] = temp;
        }
      }
    }
  }

  static void pBubble(int[] array) {
    int n = array.length;
    boolean sorted = false;
    while (!sorted) {
      sorted = true;
      // #pragma omp parallel for shared(array, sorted)
      for (int i = 1; i < n - 1; i += 2) {
        if (array[i] > array[i + 1]) {
          int temp = array[i];
          array[i] = array[i + 1];
          array[i + 1] = temp;
          sorted = false;
        }
      }
      // #pragma omp parallel for shared(array, sorted)
      for (int i = 0; i < n - 1; i += 2) {
        if (array[i] > array[i + 1]) {
          int temp = array[i];
          array[i] = array[i + 1];
          array[i + 1] = temp;
          sorted = false;
        }
      }
      // #pragma omp barrier
    }
  }

  static void printArray(int[] array) {
    for (int num : array) {
      System.out.print(num + " ");
    }
    System.out.println();
  }

  public static void main(String[] args) {
    int[] arr = { 10, 9, 8, 7, 6, 5, 4, 3, 2, 1 };

    // Sequential time
    long startTime = System.nanoTime();
    bubble(arr);
    long endTime = System.nanoTime();
    System.out.println("Sequential Bubble Sort took: " + (endTime - startTime) / 1e9 + " seconds");
    printArray(arr);

    // Reset array
    arr = new int[] { 10, 9, 8, 7, 6, 5, 4, 3, 2, 1 };

    // Parallel time
    startTime = System.nanoTime();
    pBubble(arr);
    endTime = System.nanoTime();
    System.out.println("Parallel Bubble Sort took: " + (endTime - startTime) / 1e9 + " seconds");
    printArray(arr);
  }
}
