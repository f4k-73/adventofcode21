package Day4;

import java.util.*;
import java.util.stream.Collectors;

public class Second {

    public static String solver(List<String> list) {
        List<Integer> numberInput = Arrays.stream(list.remove(0).split(",")).map(Integer::valueOf).collect(Collectors.toList());

        List<List<Integer>> fieldBuildList = new LinkedList<>();
        List<Field> fields = new LinkedList<>();
        Set<Field> finishedFields = new HashSet<>();

        Integer winnerScore = null;
        Integer winnerNumber = null;

        while (!list.isEmpty()) {
            String line = list.remove(0);
            if (line.isEmpty()) {
                if (!fieldBuildList.isEmpty()) {
                    fields.add(new Field(fieldBuildList));
                }
                fieldBuildList = new LinkedList<>();
            } else {
                fieldBuildList.add(Arrays.stream(line.split(" ")).filter(s -> !s.isEmpty()).map(Integer::valueOf).collect(Collectors.toList()));
            }
        }
        fields.add(new Field(fieldBuildList));

        for (Integer number : numberInput) {
            for (Field field : fields) {
                if (field.addNumber(number) && !finishedFields.contains(field)) {
                    winnerScore = field.score();
                    winnerNumber = number;
                    finishedFields.add(field);
                }
            }
        }

        if (winnerScore != null) {
            return String.valueOf(winnerNumber * winnerScore);
        }

        return "no winner";
    }

    private Second() {}

    public static class Field {
        private List<List<Integer>> field;
        private List<List<Boolean>> marked;

        public Field(List<List<Integer>> field) {
            this.field = field;
            this.marked = new ArrayList<>(field.size());
            for (int i = 0; i < field.size(); i++) {
                List<Boolean> marks = new ArrayList<>(field.size());
                for (int j = 0; j < field.size(); j++) {
                    marks.add(false);
                }
                this.marked.add(marks);
            }

            if (this.field.size() != this.marked.size()) {
                throw new IllegalStateException();
            }
            for (int i = 0; i < this.field.size(); i++) {
                if (this.field.get(i).size() != this.marked.get(i).size()) {
                    throw new IllegalStateException();
                }
            }
        }

        public boolean addNumber(int number) {
            for (int listIndex = 0; listIndex < this.field.size(); listIndex++) {
                List<Integer> list = this.field.get(listIndex);
                for (int index = 0; index < list.size(); index++) {
                    if (list.get(index).equals(number)) {
                        this.marked.get(listIndex).set(index, true);
                    }
                }
            }
            return hasWon(false);
        }

        public boolean hasWon(boolean diagonal) {
            boolean won = false;
            for (List<Boolean> list : this.marked) {
                boolean criteriaMet = true;
                for (Boolean b : list) {
                    criteriaMet &= b;
                }
                won |= criteriaMet;
            }
            for (int index = 0; index < this.marked.size(); index++) {
                boolean criteriaMet = true;
                for (int listIndex = 0; listIndex < this.marked.size(); listIndex++) {
                    criteriaMet &= this.marked.get(listIndex).get(index);
                }
                won |= criteriaMet;
            }
            if (diagonal) {
                {
                    boolean criteriaMet = true;
                    for (int index = 0; index < this.marked.size(); index++) {
                        criteriaMet &= this.marked.get(index).get(index);
                    }
                    won |= criteriaMet;
                }
                {
                    boolean criteriaMet = true;
                    for (int index = 0; index < this.marked.size(); index++) {
                        criteriaMet &= this.marked.get(this.marked.size() - index - 1).get(index);
                    }
                    won |= criteriaMet;
                }
            }
            return won;
        }

        public int score() {
            int sum = 0;
            for (int listIndex = 0; listIndex < this.marked.size(); listIndex++) {
                for (int index = 0; index < this.marked.size(); index++) {
                    if (!this.marked.get(listIndex).get(index)) {
                        sum += this.field.get(listIndex).get(index);
                    }
                }
            }
            return sum;
        }

        public void print() {
            for (int listIndex = 0; listIndex < this.field.size(); listIndex++) {
                List<Integer> ints = this.field.get(listIndex);
                for (int index = 0; index < ints.size(); index++) {
                    Integer i = ints.get(index);
                    Boolean b = this.marked.get(listIndex).get(index);
                    System.out.printf("%s%2d ",b ? "_" : " ", i);
                }
                System.out.println();
            }
        }
    }
}
