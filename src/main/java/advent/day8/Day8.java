package advent.day8;

import advent.Solver;
import advent.Util.Pair;

import java.util.*;

public class Day8 implements Solver {
    @Override
    public long solve(List<String> input) {

        List<Point> points = transformToPoints(input);

        // return solveUsingUnionFind(points);
        return solvePart2(points);
    }

    int find(int x, int[] circuit) {
        while (x != circuit[x]) {
            x = circuit[x];
        }
        return x;
    }

    void unite(int a, int b, int[] circuit, long[] size) {
        int representA = find(a, circuit);
        int representB = find(b, circuit);
        if (representA == representB) {
            return;
        }
        if (size[representA] <= size[representB]) {
            circuit[representA] = representB;
            size[representB] += size[representA];
            size[representA] = 0;
        } else {
            circuit[representB] = representA;
            size[representA] += size[representB];
            size[representB] = 0;
        }
    }

    private long solveUsingUnionFind(List<Point> points) {
        int MAX_CONNECTION = 1000;
        int N = points.size();

        List<Pair<Double, int[]>> distances = new ArrayList<>();
        for (int i = 0; i < points.size() - 1; i++) {
            for (int j = i + 1; j < points.size(); j++) {
                var distance = points.get(i).distance(points.get(j));
                distances.add(new Pair<>(distance, new int[]{i, j}));
            }
        }

        distances.sort(Comparator.comparingDouble(Pair::first));

        // circuit for each element contain the next elements as a chain
        final int[] circuit = new int[N];

        // for each represantative the size of its set
        final long[] size = new long[N];
        for (int i = 0; i < N; i++)
            circuit[i] = i;
        for (int i = 0; i < N; i++)
            size[i] = 1;

        for (int i = 0; i < MAX_CONNECTION; i++) {
            var distanceObj = distances.get(i);
            int p1 = distanceObj.second()[0];
            int p2 = distanceObj.second()[1];
            System.out.println("Closest i=" + i + " " + points.get(p1) + ", " + points.get(p2));
            unite(p1, p2, circuit, size);
        }
        long[] sortedsize = Arrays.stream(size).sorted().toArray();
        return sortedsize[N - 1] * sortedsize[N - 2] * sortedsize[N - 3];
    }

    private long solvePart2(List<Point> points) {
        int N = points.size();

        List<Pair<Double, int[]>> distances = new ArrayList<>();
        for (int i = 0; i < points.size() - 1; i++) {
            for (int j = i + 1; j < points.size(); j++) {
                var distance = points.get(i).distance(points.get(j));
                distances.add(new Pair<>(distance, new int[]{i, j}));
            }
        }

        distances.sort(Comparator.comparingDouble(Pair::first));
        Set<Integer> seen = new HashSet<>();

        long ans = -1;
        for (int i = 0; i < distances.size(); i++) {
            if (seen.size() == N) {
                break;
            }
            var distanceObj = distances.get(i);
            int p1 = distanceObj.second()[0];
            int p2 = distanceObj.second()[1];
            if (seen.contains(p1) && seen.contains(p2)) {
                continue;
            }
            ans = points.get(p1).x() * points.get(p2).x();
            seen.add(p1);
            seen.add(p2);
        }

        return ans;
    }

    private List<Point> transformToPoints(List<String> input) {
        return input.stream().map(line -> {
            var arr = Arrays.stream(line.split(",")).mapToLong(Long::parseLong).toArray();
            return new Point(arr[0], arr[1], arr[2]);
        }).toList();
    }

    record Point(long x, long y, long z) {
        public double distance(Point othr) {
            return Math.pow(x - othr.x(), 2) + Math.pow(y - othr.y(), 2) + Math.pow(z - othr.z(), 2);
        }
    }

}
