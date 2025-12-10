package advent.day6;

import advent.Solver;
import advent.Util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static advent.Util.buildGrid;

public class Day6 implements Solver {
    @Override
    public long solve(List<String> input) {
        // var data = constructData(input);
        // return solvePart1(constructData().first(), data.second())

        return solvePart2(input);
    }
    private long solvePart1(List<List<Long>> numbers, List<String> operations) {
        int n = operations.size();
        long ans = 0;
        for (int i = 0; i < n; i++) {
            long res;
            if (operations.get(i).equals("+")) {
                res = 0;
                for (int j = 0; j < numbers.size(); j++) {
                    res += numbers.get(j).get(i);
                }
            } else {
                res = 1;
                for (int j = 0; j < numbers.size(); j++) {
                    res *= numbers.get(j).get(i);
                }
            }
            ans += res;
        }

        return ans;
    }

    private long solvePart2(List<String> input) {
        char[][] grid = buildGrid(input.subList(0, input.size() - 1));
        int size = input.get(input.size() - 1).length();
        char[] operations = new char[size];
        input.get(input.size() - 1).getChars(0, size, operations, 0);
        int from = 0;
        int to;
        int i = 1;
        long ans = 0;
        while (i < size) {
            if (operations[i] != ' ') {
                to = i - 2;
                long eval = evaluate(from, to, operations[from], grid);
                ans += eval;
                from = i;
            }
            i += 1;
        }
        long eval = evaluate(from, grid[0].length - 1, operations[from], grid);
        ans += eval;
        return ans;
    }
    private long evaluate(int from, int to, char operation, char[][] grid) {
        List<Long> numbers = new ArrayList<>();

        for (int i = from; i <= to; i++) {
            String s = "";
            for (int j = 0; j < grid.length; j++) {
                if (grid[j][i] != ' ') {
                    s = s.concat(String.valueOf(grid[j][i]));
                }

            }
            numbers.add(Long.parseLong(s));
        }
        return numbers.stream().reduce(operation == '+' ? 0L : 1L,
                        (prec, acc) -> (operation == '+' ? prec + acc : prec * acc));
    }
    private long evaluate(List<String[]> data, String operation) {
        List<Long> numbers = new ArrayList<>();
        int i = 0;
        while (i < 5) {
            String num = "";
            for (String[] s : data) {
                if (i < s.length) {
                    num = num.concat(s[i]);
                }
            }
            if (num.isBlank()) {
                break;
            }
            numbers.add(Long.parseLong(num));
            i += 1;
        }
        return numbers.stream().reduce(operation.equals("+") ? 0L : 1L,
                        (prec, acc) -> (operation.equals("+") ? prec + acc : prec * acc));

    }

    private Pair<List<List<Long>>, List<String>> constructData(List<String> input) {
        List<String> operations = List.of(input.get(input.size() - 1).split("\\s+"));
        List<List<Long>> numbers = input.subList(0, input.size() - 1).stream()
                        .map(ele -> Arrays.stream(ele.split("\\s+")).mapToLong(Long::parseLong).boxed().toList())
                        .toList();
        return new Pair<>(numbers, operations);
    }
}
