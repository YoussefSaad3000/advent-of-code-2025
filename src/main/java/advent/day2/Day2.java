package advent.day2;

import advent.Solver;
import advent.Util;
import advent.Util.Pair;

import java.util.*;
import java.util.stream.IntStream;

public class Day2 implements Solver {
    @Override
    public long solve(List<String> input) {
        Set<Long> results = new HashSet<>();
        // transform input into pair with equal digit size (10,90) stays (10,90) but
        // (10,111) split into (10,99) (100,111)
        List<Pair<Long, Long>> pairs = new ArrayList<>();
        Arrays.asList(input.get(0).split(",")).forEach(expr -> {
            var bounds = expr.split("-");
            pairs.addAll(constructPairWithEqualDigitCount(Long.parseLong(bounds[0]), Long.parseLong(bounds[1])));
        });

        pairs.forEach(pair -> {
            // For first part only change 8 to 3 to only account for numbers that could be split into 2 identical parts
            IntStream.range(2, 8).forEach(
                            i -> results.addAll(getPalindromesInsideRangeWithNSplit(pair.first(), pair.second(), i)));
        });
        System.out.println(results);
        return results.stream().mapToLong(ele -> ele).sum();
    }

    private List<Pair<Long, Long>> constructPairWithEqualDigitCount(long lw, long hi) {
        int lwDigit = LongUtil.digits(lw);
        int hiDigit = LongUtil.digits(hi);
        if (lwDigit == hiDigit) {
            return List.of(new Pair<>(lw, hi));
        }
        long split = (long) Math.pow(10, lwDigit);
        return List.of(new Pair<>(lw, split - 1), new Pair<>(split, hi));
    }

    private Collection<Long> getPalindromesInsideRangeWithNSplit(long lw, long hi, int N) {
        // I created this variation for the second part of the problem
        System.out.println("WE are solving {lw : " + lw + ", hi : " + hi + " } for N = " + N);
        int digits = LongUtil.digits(lw);
        if (digits % N != 0) {
            // even numbers will never be invalid
            return List.of();
        }

        Set<Long> results = new HashSet<>();
        // lw = [a,b,c,d] hi [a1,b1,c1,d1]
        // if xyxy is potential palindrom then xy >= ab xy >= cd and xy <= a1b1 xy <=
        // c1d1 lw band should 8999

        long min = LongUtil.getFirstNDigits(lw, N);
        long max = LongUtil.getFirstNDigits(hi, N);
        long potential;
        while (min <= max) {
            potential = LongUtil.evaluatePalindromeNpart(min, N);
            if (potential >= lw && potential <= hi) {
                results.add(potential);
            }
            min += 1;
        }
        System.out.printf("Solution for {lw=%d, hi=%d} ->(%d) %s %n", lw, hi, results.size(), results);
        return results;
    }

    public class LongUtil {
        private LongUtil() {
        }
        static int digits(long l) {
            return (int) Math.log10((double) l) + 1;
        }

        static Util.Pair<Long, Long> splitInTwo(long l) {
            int digits = digits(l);
            long half = (long) Math.pow(10, digits / 2);
            long q = l / half;
            long r = l % half;
            return new Util.Pair<>(q, r);
        }

        static long getFirstNDigits(long l, int N) {
            // this function assume the l could be splitted
            int digits = digits(l);
            long split = digits / N;
            long powerOf10 = (long) Math.pow(10, (int) split * (N - 1));
            long res = l / powerOf10;
            System.out.println("for l = " + l + " ,first N = " + N + " digits is: " + res);
            return res;

        }

        public static long evaluatePalindromeNpart(long l, int N) {
            int digits = digits(l);
            int i = 0;
            long res = 0L;
            long powerOf10 = (long) Math.pow(10, digits);
            while (i < N) {
                res = powerOf10 * res + l;
                i += 1;
            }
            // System.out.println("evaluate l = " + l + " N = " + N + " is: " + res);
            return res;
        }
    }
}
