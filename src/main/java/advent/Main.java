package advent;

import advent.day3.Day3;
import advent.day4.Day4;
import advent.day5.Day5;
import advent.day6.Day6;
import advent.day7.Day7;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello and welcome! " + System.getProperty("user.dir"));

        var data = Util.readFiles("input7");
        System.out.println(data);
        Solver solver = new Day7();

        System.out.println(solver.solve(data));
    }
}
