package Day2;

import java.util.List;
import java.util.stream.Collectors;

public class First {

    public static String solver(List<String> list) {
        int horizontalPosition = 0;
        int depth = 0;

        for (String line : list) {
            String[] split = line.split(" ");
            int value = Integer.parseInt(split[1]);
            if ("forward".equals(split[0])) {
                horizontalPosition += value;
            } else if ("up".equals(split[0])) {
                depth -= value;
            } else if ("down".equals(split[0])) {
                depth += value;
            }
        }
        return String.valueOf(horizontalPosition * depth);
    }

    private First() {}
}