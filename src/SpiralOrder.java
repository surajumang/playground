public class SpiralOrder {

    public static void main(String[] args) {
        spiralOrder(new int[][]{
                {1,2,3,4},
                {5,6,7,8},
                {9,10,11,12},
                {13,14,15,16}
        });
    }
    public static void spiralOrder(int[][] array) {

        if(array == null) {
            return;
        }

        helper(array, 0, 0, array.length-1, array[0].length-1);

    }

    private static void helper(int [][] array, int i, int j, int k, int n){

        if(i > k || j > n) {
            return;
        }

        for( int tempJ=j; tempJ <= n; tempJ++ ) {
            System.out.print(array[i][tempJ] + " ");
        }

        for(int tempI=i+1; tempI <= k; tempI++) {
            System.out.print(array[tempI][n] + " ");
        }

        for(int tempN=n-1; tempN >= j; tempN--) {
            System.out.print(array[k][tempN] + " ");
        }

        for(int tempK=k-1; tempK >= i+1; tempK--) {
            System.out.print(array[tempK][j] + " ");
        }

        helper(array, i+1, j+1, k-1, n-1);
    }
}
