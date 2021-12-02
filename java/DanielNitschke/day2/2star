public static String solver(List<String> list) {
    int aim = 0;
    int horizontalPosition = 0;
    int depth = 0;

    for (String line : list) {
        String[] split = line.split(" ");
        int value = Integer.parseInt(split[1]);
        if ("forward".equals(split[0])) {
            horizontalPosition += value;
            depth += aim * value;
        } else if ("up".equals(split[0])) {
            aim -= value;
        } else if ("down".equals(split[0])) {
            aim += value;
        }
    }
    return String.valueOf(horizontalPosition * depth);
}