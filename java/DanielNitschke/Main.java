import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Main {
    public static void main(String[] args) {
        final String DATA_PATH = "./data/";

        List<DayInputFile> days2021 = Stream.of(
                new DayInputFile("2021, Day 5 B", DATA_PATH + "day5.txt", Day5.Second::solver),
                new DayInputFile("2021, Day 5 A", DATA_PATH + "day5.txt", Day5.First::solver),
                new DayInputFile("2021, Day 4 B", DATA_PATH + "day4.txt", Day4.Second::solver),
                new DayInputFile("2021, Day 4 A", DATA_PATH + "day4.txt", Day4.First::solver),
                new DayInputFile("2021, Day 3 B", DATA_PATH + "day3.txt", Day3.Second::solver),
                new DayInputFile("2021, Day 3 A", DATA_PATH + "day3.txt", Day3.First::solver),
                new DayInputFile("2021, Day 2 B", DATA_PATH + "day2.txt", Day2.Second::solver),
                new DayInputFile("2021, Day 2 A", DATA_PATH + "day2.txt", Day2.First::solver),
                new DayInputFile("2021, Day 1 B", DATA_PATH + "day1.txt", Day1.Second::solver),
                new DayInputFile("2021, Day 1 A", DATA_PATH + "day1.txt", Day1.First::solver)
        ).collect(Collectors.toList());


        System.out.println("== 2021 ==");
        for (DayInputFile day : days2021) {
            System.out.printf("%s : %s%n", day.getName(), day.solve());
        }
    }
}