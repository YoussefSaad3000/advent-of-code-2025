package advent.day_10;

import java.io.File;
import java.util.*;

public class Solver {
    final private int N;
    final private int M;

    public Solver(){
        System.out.println("solver");
        var map = createMapFromFile();
        this.N = map.size();
        this.M = map.get(0).length;
        Map<Character, List<Point>> groups = new HashMap<>();
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < M; j++) {
                if (map.get(i)[j] == '.') {
                    continue;
                }
                groups.computeIfAbsent(map.get(i)[j], k -> new ArrayList<>()).add(new Point(i,j));
            }
        }
        System.out.println(groups);

        Set<Point> antennas = new HashSet<>();
        groups.forEach((key, value) -> antennas.addAll(solve_2(value)));
        System.out.println("sol is " + antennas.size());

    }

    public boolean inMap(Point point) {
        return point.x() < N && point.x() >= 0 && point.y() < M && point.y() >= 0;
    }


    private Collection<Point> solve_2(List<Point> points) {

        Set<Point> antennas = new HashSet<>();
        for (Point p1 : points) {
            for (Point p2 : points) {
                if (p1.equals(p2)) {
                    continue;
                }
                antennas.add(p1);

                antennas.add(p2);

                Point diff = p1.minus(p2);
                Point p3 =  p1.add(diff);
                while (inMap(p3)) {
                    antennas.add(p3);
                    p3 = p3.add(diff);
                }

                while (inMap(p3)) {
//                    if (inBorder(p3)) {
//                        antennas.add(p3);
//                        break;
//                    }
                    antennas.add(p3);
                    p3 = p3.minus(diff);
                }
            }
        }
        return antennas;
    }

    private Collection<Point> solve(List<Point> points) {

        Set<Point> antennas = new HashSet<>();
        for (Point p1 : points) {
            for (Point p2 : points) {
                if (p1.equals(p2)) {
                    continue;
                }



                Point diff = p1.minus(p2);
                Point p3 =  p1.add(diff);
                Point p4 = p2.minus(diff);
                if (inMap(p3)) {
                    antennas.add(p3);
                }
                if (inMap(p4)) {
                    antennas.add(p4);
                }

            }
        }
        return antennas;
    }

//    private Collection<Point> horizontalIntersection(Point p1, Point p2) {
//        Point diff = p1.minus(p2);
//        if (p1.x() != 0 && p2.x() != ) {
//
//        }
//    }


    public record Point(int x, int y) {
        public Point minus(Point other) {
            return new Point(x - other.x(), y - other.y());
        }
        public Point add(Point other) {
            return new Point(x + other.x(), y + other.y());
        }
    }

    public int solve() {
        return 0;
    }
    public List<char[]> createMapFromFile(){
        List<char[]> map = new ArrayList<>();
        File file;
        try {
            file = new File(this.getClass().getResource("/input.txt").toURI());
        } catch (Exception e) {
            System.out.println("zabb ");
            e.printStackTrace();
            return new ArrayList<>();
        }


        // try-with-resources: Scanner will be closed automatically
        try (Scanner myReader = new Scanner(file)) {
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                var length = data.length();
                char[] chars =  new char[length];
                data.getChars(0, length, chars, 0);
                map.add(chars);
            }
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return map;
    }

    public static void main(String[] args){
        System.out.println("We are solving that shit again ...");
        var s = new Solver();
        System.out.println(s.solve());
    }

}
