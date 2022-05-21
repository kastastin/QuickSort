import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class ParallelAlgorithm {

    public static void main(String[] args) {
        // demonstrateAlgorithm(200000, true);
        sizeExperiment(5);
    }

    private static void sizeExperiment(int nExperiments) {
        int[] arraySizes = new int[] {100000, 200000, 300000, 400000, 500000, 600000, 700000, 800000, 900000, 1000000};
        Map<Integer, Long> times = new HashMap<>();

        for (int size : arraySizes) {            
            long allTime = 0;
            for (int i = 0; i < nExperiments; i++) {
                int[] initialArray = new int[size];
                for (int j = 0; j < size; j++) 
                    initialArray[j] = j;
                shuffle(initialArray);

                long time = System.nanoTime();
                ForkJoinTask quickSort = new ForkJoinTask(initialArray);
                ForkJoinPool pool = new ForkJoinPool();
                pool.invoke(quickSort);
                pool.shutdown();
                allTime += (System.nanoTime() - time) / 1000000;
            }
            allTime /= nExperiments;                       
            times.put(size, allTime);   
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

    private static void demonstrateAlgorithm(int size, boolean isShowArray) {
        int[] initialArray = new int[size];
        for (int i = 0; i < size; i++) 
            initialArray[i] = i;
        shuffle(initialArray);

        long time = System.nanoTime();
        ForkJoinTask quickSort = new ForkJoinTask(initialArray);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(quickSort);
        pool.shutdown();

        System.out.printf("\nSize: %d | Time: %d ms\n", size, (System.nanoTime() - time) / 1000000);

        if (isShowArray) {
            System.out.print("\nFirst 5 elements sorted array: ");
            for (int i = 0; i < 5; i++) {
                System.out.printf(initialArray[i] + " | ");
            }

            System.out.print("\n\nLast 5 elements sorted array: ");
            for (int i = initialArray.length - 5; i < initialArray.length; i++) {
                System.out.printf(initialArray[i] + " | ");
            }

            System.out.println("\n");
        }
    }

    private static void shuffle(int[] array) {
        Random random = ThreadLocalRandom.current();
        for (int i = array.length - 1; i > 0; i--) {
            int randomIndex = random.nextInt(i + 1);
            int temp = array[randomIndex];
            array[randomIndex] = array[i];
            array[i] = temp;
        }
    }
}