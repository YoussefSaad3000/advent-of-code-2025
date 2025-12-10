package advent;

import advent.day3.Day3;
import advent.day4.Day4;
import advent.day5.Day5;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello and welcome! " + System.getProperty("user.dir"));

        var data = Util.readFiles("input5");
        System.out.println(data);
        Solver solver = new Day5();

        System.out.println(solver.solve(data));
    }
}
