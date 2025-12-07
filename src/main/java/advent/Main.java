package advent;

import advent.Day6.Day6;
import advent.day1.Day1;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello and welcome! " + System.getProperty("user.dir"));

        var data = Util.readFiles("input1");
        System.out.println(data);
        Solver solver = new Day1();

        System.out.println(solver.solve(data));
    }
}
