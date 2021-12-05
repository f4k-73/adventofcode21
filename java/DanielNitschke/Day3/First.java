package Day3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class First {

    public static String solver(List<String> list) {
        int gamma = gamma(list);
        int epsilon = epsilon(list);
        return String.valueOf(gamma * epsilon);
    }


    public static int gamma(List<String> list) {
        return bitMeasure(list, '1');
    }

    public static int epsilon(List<String> list) {
        return bitMeasure(list, '0');
    }

    public static int bitMeasure(List<String> list, char compare) {
        Map<Integer, Integer> bitmap = new HashMap<>();
        int length = list.get(0).length();
        for (String line : list) {
            for (int i = 0; i < length; i++) {
                int count = bitmap.getOrDefault(i, 0);
                bitmap.put(i, count + (line.charAt(i) == compare ? 1 : 0));
            }
        }
        int value = 0;
        for (int i = length - 1; i >= 0; i--) {
            value <<= 1;
            value += bitmap.getOrDefault(length - i - 1, 0) >= (list.size() / 2) ? 1 : 0;
        }
        return value;
    }

    private First() {}
}
