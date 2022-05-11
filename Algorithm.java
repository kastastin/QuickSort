import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Algorithm {

    public static void main(String [] args) {
        // demonstrateQuickSort(true);
        sizeExperiment(5);
    }

    private static void sizeExperiment(int nExperiments) {
        int[] arraySizes = new int[] {100000, 200000, 300000, 400000, 500000, 600000, 700000, 800000, 900000, 1000000};
        Map<Integer, Long> times = new HashMap<>();

        for (int size : arraySizes) {
            int[] initialArray = new int[size];
            for (int i = 0; i < size; i++) 
                initialArray[i] = (int) Math.round((Math.random() * 100));
            
            long time = 0;
            for (int i = 0; i < nExperiments; i++) {
                long currTime = System.nanoTime();
                quickSort(initialArray, 0, initialArray.length - 1);
                time += System.nanoTime() - currTime;
            }
            time = time / nExperiments;
            times.put(size, time / 1000000);       
        }

        List<Integer> keys = times.keySet().stream().sorted().collect(Collectors.toList());
        System.out.printf("\tArray size: ");
        for (int key : keys) 
            System.out.printf("  %5d ", key);
        System.out.println();
        System.out.printf("\tTime [ms]:");
        for (int key : keys)
            System.out.printf("  %5d ", times.get(key));
        System.out.println();
    }

    private static void demonstrateQuickSort(boolean isShowTime) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter array size: ");
        int size = in.nextInt(); 
        in.close();

        int[] initialArray = new int[size];
        String initialArrayStr = "| ";
        
        if (!isShowTime)
            System.out.println("Initial array: ");

        for (int i = 0; i < initialArray.length; i++) {
            initialArray[i] = (int) Math.round((Math.random() * 100) + 1);
            initialArrayStr += initialArray[i] + " | ";
        }

        if (!isShowTime)
            System.out.println(initialArrayStr + "\n");

        long time = System.nanoTime();
        quickSort(initialArray, 0, initialArray.length - 1);
        time = System.nanoTime() - time;

        if (isShowTime) 
            System.out.printf("\nSize: %d  -  %d ms\n\n", size, time / 1000000);

        String sortedArrayStr = "| ";
        if (!isShowTime)
            System.out.println("Sorted array: ");

        for (int i : initialArray) {
            sortedArrayStr += i + " | ";
        }

        if (!isShowTime)
            System.out.println(sortedArrayStr + "\n");
    }

    private static void swap(int[] array, int x, int y) {
        int temp = array[x];
        array[x] = array[y];
        array[y] = temp;
    }

    private static int partition(int[] array, int left, int right) {  
        int pivotIndex = (left + right) / 2;
        swap(array, pivotIndex, right);
        int low = left;
        pivotIndex = right;

        for (int i = left; i <= right - 1 ; i++) {
            if (array[i] <= array[pivotIndex]) {
                swap (array, i, low);
                low++;
            }
        }

        swap (array, low, pivotIndex);
        pivotIndex = low;
        return pivotIndex;
    }

    private static void quickSort(int[] array, int left, int right) {
        if (left >= right) {
            return;
        } else {
            int pivotIndex = partition(array, left, right);
            quickSort(array, left, pivotIndex - 1);
            quickSort(array, pivotIndex + 1, right);
        }       
    }
}