package Day5;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Second {

    public static String solver(List<String> list) {
        Field field = new Field();
        for (String line : list) {
            String[] positions = line.split(" -> ");
            String[] pos1Split = positions[0].split(",");
            String[] pos2Split = positions[1].split(",");
            Point2I pos1 = new Point2I(Integer.parseInt(pos1Split[0]), Integer.parseInt(pos1Split[1]));
            Point2I pos2 = new Point2I(Integer.parseInt(pos2Split[0]), Integer.parseInt(pos2Split[1]));
            Point2I dir = Point2I.direction(pos1, pos2);
            field.add(pos1);
            while (!pos1.equals(pos2)) {
                field.add(pos1 = Point2I.add(pos1, dir));
            }
        }
        return String.valueOf(field.score());
    }

    public static class Point2I {
        private int x;
        private int y;

        public Point2I (int x, int y) {
            this.x = x;
            this.y = y;
        }

        public static Point2I add(Point2I point1, Point2I point2) {
            return new Point2I(point1.x + point2.x, point1.y + point2.y);
        }

        public static Point2I direction(Point2I point1, Point2I point2) {
            int dx = point2.x - point1.x;
            int dy = point2.y - point1.y;
            int length = Math.max(Math.abs(dx), Math.abs(dy));
            dx /= length;
            dy /= length;
            return new Point2I(dx, dy);
        }

        public boolean isDiagonal() {
            return this.x != 0 && this.y != 0;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Point2I point2I = (Point2I) o;

            return x == point2I.x && y == point2I.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }

        @Override
        public String toString() {
            return "x=" + x + ", y=" + y;
        }
    }

    public static class Field {
        Map<Point2I, Integer> map;

        public Field() {
            this.map = new HashMap<>();
        }

        public void add(Point2I point) {
            Integer value = this.map.getOrDefault(point, 0);
            this.map.put(point, ++value);
        }

        public Integer getValue(Point2I point) {
            return this.map.getOrDefault(point, 0);
        }

        public int score() {
            int sum = 0;
            for (int value : this.map.values()) {
                sum += Math.min(1, value - 1);
            }
            return sum;
        }
    }
}
