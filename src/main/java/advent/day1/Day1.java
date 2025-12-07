package advent.day1;

import advent.Solver;
import java.util.List;

public class Day1 implements Solver {
    @Override
    public int solve(List<String> input) {
        List<Instruction> instructions = fetchInstruction(input);
        return solvePart2(instructions);
    }

    private int solvePart1(List<Instruction> instructions) {
        int cursor = 50;
        int ans = 0;
        for (Instruction instruction : instructions) {
            switch (instruction.direction) {
                case LEFT -> cursor -= instruction.steps();
                case RIGHT -> cursor += instruction.steps();
            }
            cursor = (cursor % 100);
            if (cursor == 0) {
                ans += 1;
            }
        }
        return ans;
    }

    private int solvePart2(List<Instruction> instructions) {
        int cursor = 50;
        int newCursor;
        int ans = 0;
        for (Instruction instruction : instructions) {
            int rawSteps = instruction.steps() % 100; // this is always positive
            int circles = instruction.steps() / 100;
            ans += circles;
            newCursor = cursor;
            switch (instruction.direction) {
                case LEFT :
                    newCursor = newCursor - rawSteps;
                    if (cursor != 0 && rawSteps != 0 && newCursor <= 0) {
                        ans += 1;
                    }
                    break;
                case RIGHT :
                    newCursor = newCursor + rawSteps;
                    if (cursor != 0 && rawSteps != 0 && newCursor > 99) {
                        ans += 1;
                    }
                    break;
            }
            cursor = arithmeticReminder(newCursor, -100);
            // System.out.println(String.format("For instruction %s, cursor %d, ans
            // %d",instruction, cursor, ans));
        }
        return ans;
    }

    private List<Instruction> fetchInstruction(List<String> input) {
        return input.stream().map(line -> new Instruction(Direction.from(line.substring(0, 1)),
                        Integer.parseInt(line.substring(1)))).toList();
    }

    private int arithmeticReminder(int a, int b) {
        // for now let us force b to be positive
        int reminder = a % b;
        if (reminder < 0) {
            reminder += b;
        }
        return reminder;
    }

    record Instruction(Direction direction, int steps) {
    }

    enum Direction {
        LEFT, RIGHT;

        public static Direction from(String s) {
            return s.equals("L") ? LEFT : RIGHT;
        }
    }
}
