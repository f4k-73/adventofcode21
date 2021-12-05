import java.nio.charset.Charset;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.List;
import java.util.LinkedList;

public class DayInputFile {
    private String name;
    private String path;
    private Function<List<String>, String> solver;

    public DayInputFile(String name, String path, Function<List<String>, String> solver) {
        this.name = name;
        this.path = path;
        this.solver = solver;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public String solve() {
        List<String> list = new LinkedList<>();
        try {
            list =  Files.readAllLines(Paths.get(this.path), Charset.defaultCharset());
        } catch (IOException e) {
            System.err.printf("Error: Invalid path %s in %s%n", this.path, this.name);
        }
        return this.solver.apply(list);
    }
}