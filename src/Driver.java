import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.stream;

public class Driver {

    public static void main(String[] args) {
//
        //stringHashCode(new String[]{ "abcd", "java", "dcba", "ajav", "xyz", "epam", "pame", "aepm", "AAAAAAAAAAAAAA", "FFFFFFFFFFFFF" });
        //rapaNui(105);

        reverseInGroups(new int[]{1,2,3,4,5}, 4);
    }

    public static void linkedHashMap() {
        SimpleLinkedHashMap map = new SimpleLinkedHashMap();
        map.put("1", "1");

        map.put("2", "2");
        map.put("3", "3"); map.put("4", "4");
        map.iterate();
    }

    static void stringHashCode(String[] arr) {
        Map<String, Set<Pair>> map =  Arrays.stream(arr)
                .map(s -> {
            char[] chars = s.toCharArray();
            Arrays.sort(chars);
            return new Pair(s, new String(chars));

        })
                .collect(Collectors.groupingBy(pair -> pair.sorted, Collectors.toSet()));


    }

    static class Pair{
        String s;
        String sorted;

        Pair(String s, String sorted) {
            this.s = s;
            this.sorted = sorted;
        }

        public String toString() {
            return s;
        }
    }

    public static void lruCache(){
        LRUCache lruCache = new LRUCache(3);

        System.out.println(lruCache.get("dfkj"));
        lruCache.put("1", "1");

        lruCache.put("2", "2");
        lruCache.put("3", "3");
        System.out.println("Got value for 1: " + lruCache.get("1"));
        lruCache.put("4", "4");
    }

    private static void temp(int[] ans) {
        String val = Arrays.stream(ans)
                .boxed()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    static class Conditional {
        Predicate<Integer> predicate;
        Function<Integer, String> function;

        Conditional(Predicate<Integer> p, Function<Integer, String> f) {
            this.predicate = p;
            this.function = f;
        }
    }

    static Predicate<Integer> modulo(int number) {
        return (value) -> value % number == 0;
    }

    private static void rapaNui(int n) {

        Function<Integer, String> mapper = (number) -> {
            StringBuilder sb = new StringBuilder();
            if(number % 3 == 0) sb.append("Rapa");
            if(number % 5 == 0) sb.append("Nui");
            if(number % 7 == 0) sb.append("Seven");
            if(number % 9 == 0) sb.append("Nine");

            if(sb.isEmpty()) sb.append(number);

            return sb.toString();
            //else case
        };
        List<Conditional> conditionals = List.of(
                new Conditional(modulo(3), (index) -> "Rapa"),
                new Conditional(modulo(5), index -> "Nui"),
                new Conditional(modulo(7), index -> "Seven")
        );



        Map<Integer, String> map = new LinkedHashMap<>();
        map.put(3, "Rapa");
        map.put(5, "Nui");
        map.put(7, "Seven");
        map.put(9, "Nine");

        Function<Integer, String> mapper2 = (number) -> {
            //StringBuilder sb = new StringBuilder();
            String ans = map.entrySet()
                    .stream()
                    .filter(entry -> number % entry.getKey() == 0)
                    .map(Map.Entry::getValue)
                    .reduce((a,b) -> a+b)
                    .orElse(String.valueOf(number));
            return ans;
        };

        String ans = IntStream.range(1, n+1)
                .boxed()
                .map(mapper2)
                .collect(Collectors.joining(","));
        System.out.println(ans);

    }


    //[1,2,3,4,5,6]
    private static void reverseInGroups(int[] nums, int k) {
        for (int i=0; i<nums.length; i+=k ) {
            int left = i, right = Math.min(i+k-1, nums.length-1);

            while(left < right) {
                swap(nums, left++, right--);
            }
        }
        System.out.println( Arrays.stream(nums).boxed()
                .map(String::valueOf).collect(Collectors.joining(", ")) );

    }

    private static void swap(int[] arr, int start, int end) {
        int temp = arr[start];
        arr[start] = arr[end];
        arr[end] = temp;
    }
}
