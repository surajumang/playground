import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StreamCollect {
    public static void main(String[] args) {
         Map<String, Long> freq = Arrays.stream(new String[] {"red", "blue", "white", "white", "red"})
                .collect( Collectors.groupingBy( Function.identity(), Collectors.counting() ));

//        freq.forEach((color, count) -> {
//            System.out.printf("Color: %s is present %d times%n", color, count);
//        });

        List<List<Integer>> list = new ArrayList<>();
        list.add(Arrays.asList(1,2,3));
        list.add(Arrays.asList(4,5,6));
        list.add(Arrays.asList(7,8,9));

        //also try flatMaptoInt
        int total = list.stream()
                .flatMapToInt(item -> item.stream().mapToInt(Integer::intValue))
                .sum();

        System.out.println(total);
        String s = "ababcdddd";

    }
}
