package advent;

import advent.Day6.Day6;
import advent.day1.Day1;
import advent.day2.Day2;
import advent.day3.Day3;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello and welcome! " + System.getProperty("user.dir"));

        var data = Util.readFiles("input3");
        System.out.println(data);
        Solver solver = new Day3();

        System.out.println(solver.solve(data));
    }
}
