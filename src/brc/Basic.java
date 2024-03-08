package brc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Basic {

    public static void main(String[] args) {
        //read all lines in memory and process each of them one at a time.
        String fileName = "./measurements_big.txt";
        Path filePath = Path.of(fileName);
        //for each line, split on ; and get city and temperature value
        //Store the city as a key in a HashMap

        Map<String, TemperatureRecord> map = new TreeMap<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String l;
            while( (l = reader.readLine()) != null ) {
                String[] line = l.split(";");
                String city = line[0];
                int temp = fromDouble(line[1]);
                map.merge(city, new TemperatureRecord().update(temp), (TemperatureRecord::merge));
            }
            System.out.println(map.toString());

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static int fromDouble(String number) {
        double d = Double.parseDouble(number);
        d = d * 10;
        return (int) d;
    }

    static class TemperatureRecord {
        int min = Integer.MAX_VALUE;
        int max;
        int sum;
        int total;

        TemperatureRecord update(int num) {
            min = Math.min(num, min);
            max = Math.max(num, max);
            total++;
            sum += num;
            return this;
        }

        public String toString() {
            return String.format("%s/%s/%s", fromInt(min), fromInt(sum/total), fromInt(max));
        }

        private static String fromInt(int number) {
            int remainder = number % 10;
            int divisor = number / 10;
            return String.format("%d.%d", divisor, remainder > 0 ? remainder : -1*remainder);
        }

        static TemperatureRecord merge(TemperatureRecord first, TemperatureRecord second) {
            TemperatureRecord merged = new TemperatureRecord();
            merged.min = Math.min(first.min, second.min);
            merged.max = Math.max(first.max, second.max);
            merged.sum = first.sum + second.sum;
            merged.total = first.total + second.total;
            return merged;
        }
    }


}

/*
*  Perform the same with basic Bash tools
*
*   */
