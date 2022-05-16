import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

public class ParallelAlgorithm {

    public static void main(String[] args) {
        // demonstrateAlgorithm(500000, true);
        sizeExperiment(5);
    }

    private static void sizeExperiment(int nExperiments) {
        int[] arraySizes = new int[] {100000, 200000, 300000, 400000, 500000, 600000, 700000, 800000, 900000, 1000000};
        Map<Integer, Long> times = new HashMap<>();

        for (int size : arraySizes) {
            int[] initialArray = new int[size];
            for (int i = 0; i < size; i++) 
                initialArray[i] = (int) Math.round((Math.random() * 10000));
            
            long time = 0;
            for (int i = 0; i < nExperiments; i++) {
                long currTime = System.nanoTime();
                ForkJoinTask quickSort = new ForkJoinTask(initialArray);
                ForkJoinPool pool = new ForkJoinPool();
                pool.invoke(quickSort);
                pool.shutdown();
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

    private static void demonstrateAlgorithm(int size, boolean isShowArray) {
        int[] initialArray = new int[size];

        for (int i = 0; i < size; i++) 
            initialArray[i] = (int) Math.round((Math.random() * 10000));

        ForkJoinTask quickSort = new ForkJoinTask(initialArray);
        long time = System.nanoTime();
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(quickSort);
        pool.shutdown();

        if (isShowArray) {
            System.out.print("\n\nFirst 5 elements sorted array: ");
            for (int i = 0; i < 5; i++) {
                System.out.printf(initialArray[i] + " | ");
            }

            System.out.print("\n\nLast 5 elements sorted array: ");
            for (int i = initialArray.length - 5; i < initialArray.length; i++) {
                System.out.printf(initialArray[i] + " | ");
            }
        }

        System.out.printf("\n\nSize: %d | Time: %d ms\n\n", size, (System.nanoTime() - time) / 1000000);
    }
}