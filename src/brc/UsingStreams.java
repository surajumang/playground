package brc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class UsingStreams {

    public static void main(String[] args) throws IOException {

        //Map the lines to Temperature records and then perform a collect using a custom collector.
        TreeMap<String, TemperatureAggregator> result = Files.lines(Paths.get("./measurements_big.txt"))
                //.parallel()
                .map(Temperature::fromString)
                .collect( Collectors.groupingBy(Temperature::city, TreeMap::new, temperatureCollector()) );
        System.out.println(result);
    }

    private static class TemperatureAggregator {
        private int min, max, sum, count;

        public TemperatureAggregator() {
            min = Integer.MAX_VALUE;
            max = Integer.MIN_VALUE;
            sum = 0;
            count = 0;
        }

        public TemperatureAggregator(int min, int max, int sum, int count) {
            this.min = min;
            this.max = max;
            this.sum = sum;
            this.count = count;
        }

        public static void accumulate(TemperatureAggregator agg, Temperature t1) {
            agg.min = Math.min( agg.min, t1.value() );
            agg.max = Math.max( agg.max, t1.value() );
            agg.sum += t1.value();
            agg.count++;
        }

        public static TemperatureAggregator merge(TemperatureAggregator first, TemperatureAggregator second) {
            return new TemperatureAggregator(
                    Math.min(first.min, second.min),
                    Math.max(first.max, second.max),
                    first.sum + second.sum,
                    first.count + second.count
            );
        }

        public String toString() {
            return String.format("%s/%s/%s", fromInt(min), fromInt(sum/count), fromInt(max));
        }

        private static String fromInt(int number) {
            int remainder = number % 10;
            int divisor = number / 10;
            return String.format("%d.%d", divisor, remainder > 0 ? remainder : -1*remainder);
        }
    }

    private record Temperature(String city, int value) {

        static Temperature fromString(String line) {
            String[] items = line.split(";");
            return new Temperature(items[0], fromDouble(items[1]));
        }

        static int fromDouble(String number) {
            double d = Double.parseDouble(number);
            d = d * 10;
            return (int) d;
        }
    }

    // <T, R, R>
    private static Collector<Temperature, TemperatureAggregator, TemperatureAggregator> temperatureCollector() {
        //Items of type T are present in a Stream and they need to be collected.
        //Intermediate Result of accumulation/collection is stored in object of type A.
        //Final Result of the Collection/accumulation is of Type R.

        // accumulation <Type,Agg,Result>
        //in this case Result is same as Agg.
        // ()          -> Agg   supplier for the container
        // (Agg, Type) -> Agg   accumulator
        // (Agg, Agg)  -> Agg   combine intermediate results
        return Collector.of(
            TemperatureAggregator::new, TemperatureAggregator::accumulate, TemperatureAggregator::merge
        );
    }
}
