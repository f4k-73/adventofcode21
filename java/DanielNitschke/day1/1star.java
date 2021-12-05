public static String solver(List<String> list) {
    List<Integer> intList = list.stream().map(Integer::valueOf).collect(Collectors.toList());
    int depthIncrements = 0;
    for (int i = 1; i < list.size(); i++) {
        if (intList.get(i-1) < intList.get(i)) {
            depthIncrements++;
        }
    }
    return String.valueOf(depthIncrements);
}