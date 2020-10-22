import java.util.Arrays;

public class Magic {

    public static void main(String[] args) {
        int[] arr = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};

        System.out.println(Arrays.toString(MyMagic(arr, 10)));
    }

    public static int[] MyMagic(int[] arr, int n) {
        boolean done = true;
        int j = 0;
        while (j <= n-2) {
            if (arr[j] > arr[j+1]) {
                int temp = arr[j];
                arr[j] = arr[j+1];
                arr[j+1] = temp;
                done = false;
            }
            j++;
        }

        j = n-1;
        while (j >= 1) {
            if (arr[j] < arr[j-1]) {
                int temp = arr[j-1];
                arr[j-1] = arr[j];
                arr[j] = temp;
                done = false;
            }
            j--;
        }

        if (!done) {
            System.out.println("aaaaa");
            return MyMagic(arr, n);
        }
        else {
            return arr;
        }
    }
}
