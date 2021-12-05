package Day3;

import java.util.*;
import java.util.stream.Collectors;

public class Second {

    public static String solver(List<String> list) {
        Set<Integer> intSet = list.stream().map(line -> convert(line, '1')).collect(Collectors.toSet());
        final int LENGTH = list.get(0).length();
        int gamma = gamma(list);
        int epsilon = epsilon(list);

        int oxygenRating = -1;
        int co2ScrubberRating = -1;

        List<String> oxygenList = new LinkedList<>(list);

        int oxygenBitMask = 1 << (list.get(0).length() - 1);
        while (oxygenList.size() > 1 && oxygenBitMask > 0) {
            final int MATCH = bitMeasure(oxygenList, '1');
            final int BIT_MASK = oxygenBitMask;
            oxygenList.removeIf(oxy -> (convert(oxy, '1') & BIT_MASK) != (MATCH & BIT_MASK));
            oxygenBitMask >>= 1;
        }
        for (String oxygen : oxygenList) {
            oxygenRating = convert(oxygen, '1');
        }

        List<String> co2List = new LinkedList<>(list);

        int co2BitMask = 1 << (list.get(0).length() - 1);
        while (co2List.size() > 1 && co2BitMask > 0) {
            final int MATCH = bitMeasure(co2List, '0', -1);
            int BIT_MASK = co2BitMask;
            co2List.removeIf(co2 -> (convert(co2, '1') & BIT_MASK) != (MATCH & BIT_MASK));
            co2BitMask >>= 1;
        }
        for (String co2 : co2List) {
            co2ScrubberRating = convert(co2, '1');
        }

        return String.valueOf(oxygenRating * co2ScrubberRating);
    }


    public static int gamma(List<String> list) {
        return bitMeasure(list, '1');
    }

    public static int epsilon(List<String> list) {
        return bitMeasure(list, '0');
    }

    public static int bitMeasure(List<String> list, char compare) {
        return bitMeasure(list, compare, 0);
    }

    public static int bitMeasure(List<String> list, char compare, int compareOffset) {
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
            value += 2 * bitmap.getOrDefault(length - i - 1, 0) + compareOffset >= list.size() ? 1 : 0;
        }
        return value;
    }

    public static int convert(String line, char match) {
        int value = 0;
        for (int i = 0; i < line.length(); i++) {
            value <<= 1;
            value += line.charAt(i) == match ? 1 : 0;
        }
        return value;
    }

    private Second() {}
}
