package advent.day3;

import advent.Solver;
import java.util.*;

public class Day3 implements Solver {
    @Override
    public long solve(List<String> input) {
        var batteries = constructBanks(input);
        System.out.println(batteries);
        return batteries.stream().mapToLong(this::maxJoltage12).sum();
    }

    // Part 1
    private int maxJoltage(List<Integer> bank) {
        int decimal = 0, unit = 0;
        for (int num : bank) {
            if (unit > decimal) {
                decimal = unit;
                unit = num;
            } else if (num > unit) {
                unit = num;
            }
        }
        return decimal * 10 + unit;
    }

    // Part 2
    private long maxJoltage12(List<Integer> bank) {
        var big = bigguest(bank, 12);
        long res = 0;
        int i = 0;
        while (i < 12) {
            res = res * 10 + big.get(i);
            i += 1;
        }
        return res;
    }

    public static List<Integer> bigguest(List<Integer> bank, int k) {
        Deque<Integer> deque = new LinkedList<>();
        var N = bank.size();
        for (int i = 0; i < N; i++) {
            var battery = bank.get(i);
            // we pop as many elemenet as we can given that the bank still has batteries if
            // our batter is
            // big
            // k - deque size is the size to be filled
            while (!deque.isEmpty() && deque.peekLast() < battery && (k - deque.size() <= N - i - 1)) {
                deque.pollLast();
            }

            if (deque.size() < k) {
                deque.add(battery);
            }
        }
        return new ArrayList<>(deque);
    }

    private List<List<Integer>> constructBanks(List<String> input) {
        return input.stream().map(line -> Arrays.stream(line.split("")).map(Integer::parseInt).toList()).toList();
    }
}
