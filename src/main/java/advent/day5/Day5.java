package advent.day5;

import advent.Solver;
import advent.Util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.sort;

public class Day5 implements Solver {
    @Override
    public long solve(List<String> input) {
        var data = constructIntervalsAndIDs(input);

        // return solvePart1(data);
        return solvePart2(data.first());
    }
    private long solvePart1(Pair<List<List<Long>>, List<Long>> data) {
        List<List<Long>> intervals = data.first();
        List<Long> ingredients = data.second();
        long res = 0;
        for (long ingredient : ingredients) {
            for (List<Long> interval : intervals) {
                if (interval.get(0) <= ingredient && ingredient <= interval.get(1)) {
                    res += 1;
                    break;
                }
            }
        }
        return res;
    }

    private long solvePart2(List<List<Long>> intervals) {
        intervals.sort((l1, l2) -> {
            if (l1.get(0) > l2.get(0)) {
                return 1;
            } else if (l1.get(0) < l2.get(0)) {
                return -1;
            }
            return l1.get(1).compareTo(l2.get(1));
        });
        long ans = 0;
        long lw = intervals.get(0).get(0);
        long hi = intervals.get(0).get(1);
        int n = intervals.size();
        int i = 1;

        while (i < n) {
            long left = intervals.get(i).get(0);
            long right = intervals.get(i).get(1);
            if (left > hi) {
                ans += (hi - lw + 1);
                lw = left;
                hi = right;
            } else {
                hi = Math.max(hi, right);
            }
            i++;
        }
        ans += (hi - lw + 1);
        return ans;
    }

    private Pair<List<List<Long>>, List<Long>> constructIntervalsAndIDs(List<String> input) {
        int i = 0;
        int n = input.size();
        boolean intervalsFinished = false;
        List<List<Long>> intervals = new ArrayList<>();
        List<Long> ingredients = new ArrayList<>();
        while (i < n) {
            var line = input.get(i);
            if (line.isBlank()) {
                intervalsFinished = true;
            } else if (!intervalsFinished) {
                var ranges = line.split("-");
                intervals.add(List.of(Long.parseLong(ranges[0]), Long.parseLong(ranges[1])));
            } else {
                ingredients.add(Long.parseLong(line));
            }
            i++;
        }
        return new Pair<>(intervals, ingredients);
    }
}
