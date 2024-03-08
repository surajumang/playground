import java.util.*;
import java.util.stream.IntStream;

public class Fairness {
    public static void main(String[] args) {
        //System.out.println(count(new int[]{1, 4, 2, -2, 5}, new int[]{7, -2, -2, 2, 5}));

        //System.out.println(count(new int[]{2,-2,-3,3}, new int[]{0,0,4,-4}));

        System.out.println(solution(1, ""));

    }

    public static int count(int[] A, int[] B) {

        int totalA = IntStream.of(A).sum();
        int totalB = IntStream.of(B).sum();

        int prefixA = 0, suffixA=0;
        int prefixB = 0, suffixB =0;

        int total=0;
        for (int k = 0; k<A.length-1; k++) {
            prefixA += A[k];
            prefixB += B[k];

            suffixA = totalA - prefixA;
            suffixB = totalB - prefixB;

            if(prefixA == suffixA && suffixA == prefixB && prefixB == suffixB) {
                total++;
                System.out.printf("Found fair index at %d %n", k);
            }
        }
        return total;
    }

    public static int solution(int N, String S) {
        // Implement your solution here
        //add the occupied seats in a hashmpap
        Map<Integer, Set<String>> occupied = new HashMap<>();

        if(S != null && !S.isEmpty()) {
            for (String seat : S.split(" ")) {
                int row = Integer.parseInt(seat.substring(0, seat.length()-1));
                String col = seat.substring(seat.length()-1);
                occupied.merge(row, new HashSet<>(List.of(col)), (l1, l2) -> {
                    l1.addAll(l2);
                    return l1;
                });
            }
        }
        //start with processing each row.
        int families = 0;
        for (int i = 1; i <= N; i++) {
            families += total(i, occupied);
        }
        return families;
    }

    static int total(int row, Map<Integer, Set<String>> occupied) {
        Set<String> occupiedColumns = occupied.get(row);
        if(occupiedColumns == null || occupiedColumns.isEmpty()) {
            return 2;
        }
        int total = 0;
        if(!occupiedColumns.contains("B")
                && !occupiedColumns.contains("C")
                && !occupiedColumns.contains("D")
                && !occupiedColumns.contains("E")) {
            System.out.println("Choosing left aisle for " + row);
            total++;
        }
        if(!occupiedColumns.contains("F")
                && !occupiedColumns.contains("G")
                && !occupiedColumns.contains("H")
                && !occupiedColumns.contains("K")) {
            System.out.println("Choosing right aisle for " + row);
            total++;
        }
        if(total == 0) {
            if(!occupiedColumns.contains("F")
                    && !occupiedColumns.contains("G")
                    && !occupiedColumns.contains("D")
                    && !occupiedColumns.contains("E")) {
                System.out.println("Choosing center for row " +row);
                total++;
            }
        }
        return total;
    }
}
