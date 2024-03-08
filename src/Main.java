import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!%n");
        Integer[] ints = new Integer[]{1,2,3,4,5,6};
        Double[] dbs = {1.1, 2.3, 4.0};
        Float[] fls = {1.3f, 3.4f, 5.6f};
        String[] str = {"suraj", "kumar"};

        characters("I am the king of mahismati");
    }

    private static <T> void printArray(T[] numbers) {
        System.out.println("Printing Array");
        String cs = Arrays.stream(numbers).map(Object::toString).collect(Collectors.joining(", "));
        System.out.println(cs);
    }

    private static void characters(String str) {
        //no of chars
        //largest word in a sentence
        long length = str.chars().count();
        String longestWord = Arrays.stream(str.split(" "))
                //.max(Comparator.comparingInt(String::length))
                .sorted(Comparator.comparingInt(String::length).reversed())
                .skip(1).findFirst().get();

        System.out.println(longestWord);

    }

    private static void lockCondition() {
        //
        Lock lock = new ReentrantLock();
        lock.newCondition();
    }
}