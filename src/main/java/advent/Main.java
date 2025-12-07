package advent;

import advent.Day6.Day6;

public class Main {
    public static void main(String[] args) {
        // TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the
        // highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.println("Hello and welcome! " + System.getProperty("user.dir"));
        // System.out.println("let us run my programm" +
        // Day3.bigguest(List.of(2,3,4,9,8,9,9),3));

        var data = Util.readFiles("input6");
        Solver solver = new Day6();

        System.out.println(solver.solve(data));
    }
}
