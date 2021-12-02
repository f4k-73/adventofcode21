public static String solver(List<String> list) {
    List<Integer> intList = list.stream().map(Integer::valueOf).collect(Collectors.toList());
    int depthIncrements = 0;
    for (int i = 3; i < list.size(); i++) {
        if (intList.get(i-3) < intList.get(i)) {
            depthIncrements++;
        }
    }
    return String.valueOf(depthIncrements);
}