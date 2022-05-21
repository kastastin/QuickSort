import java.util.concurrent.RecursiveAction;

public class ForkJoinTask extends RecursiveAction {
    private int[] array;
    private int left;
    private int right;

    public ForkJoinTask(int[] array, int left, int right) {
        this.array = array;
        this.left = left;
        this.right = right;
    }

    public ForkJoinTask(int[] array) {
        this.array = array;
        this.left = 0;
        this.right = array.length - 1;
    }

    private void swap(int[] array, int x, int y) {
        int temp = array[x];
        array[x] = array[y];
        array[y] = temp;
    }

    private int partition(int[] array, int left, int right) {
        int pivotIndex = (left + right) / 2;
        swap(array, pivotIndex, right);
        int low = left;

        for (int i = left; i <= right - 1 ; i++) {
            if (array[i] <= array[right]) {
                swap (array, i, low);
                low++;
            }
        }

        swap (array, low, right);
        return low;
    }

    @Override
    protected void compute() {
        if(left < right){
            int pivote = partition(array, left, right);
            invokeAll(new ForkJoinTask(array, left, pivote), new ForkJoinTask(array, pivote + 1, right));
        }
    }
}