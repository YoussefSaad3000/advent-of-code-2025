package advent;

import advent.day3.Day3;
import advent.day4.Day4;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello and welcome! " + System.getProperty("user.dir"));

        var data = Util.readFiles("input4");
        System.out.println(data);
        Solver solver = new Day4();

        System.out.println(solver.solve(data));
    }
}
