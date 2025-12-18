package advent.day7;

import advent.Solver;

import java.util.*;

import static advent.Util.buildGrid;

public class Day7 implements Solver {
    @Override
    public long solve(List<String> input) {
        char[][] grid = buildGrid(input);
        return solvePart2(grid);

    }

    private long solvePart1(char[][] grid) {
        Set<String> solutions = new HashSet<>();
        int N = grid.length;
        int M = grid[0].length;
        int s = findStartingPointIndex(grid);

        Deque<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{0, s});
        Set<String> seen = new HashSet<>();

        while (!queue.isEmpty()) {
            var cell = queue.poll();
            int i = cell[0];
            int j = cell[1];
            if (grid[i][j] == '^') {
                // There is a split
                solutions.add(i + "/" + j);
                int dj = j + 1;
                if (dj < M && !seen.contains(i + "+" + dj)) {
                    queue.offer(new int[]{i, dj});
                    seen.add(i + "+" + dj);
                }
                dj = j - 1;
                if (j - 1 >= 0 && !seen.contains(i + "+" + dj)) {
                    queue.offer(new int[]{i, dj});
                    seen.add(i + "+" + dj);
                }
            } else if (i + 1 < N) {
                cell[0] += 1;
                queue.offer(cell);
            }

        }
        return solutions.size();
    }

    private long solvePart2(char[][] grid) {
        int N = grid.length;
        int M = grid[0].length;
        long[][] timelines = new long[N][M];
        int s = findStartingPointIndex(grid);
        timelines[0][s] += 1L;
        for (int i = 1; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (grid[i][j] != '^') {
                    timelines[i][j] += timelines[i - 1][j];
                }
                if (j > 0 && grid[i][j - 1] == '^') {
                    timelines[i][j] += timelines[i - 1][j - 1];
                }
                if ((j + 1) < M && grid[i][j + 1] == '^') {
                    timelines[i][j] += timelines[i - 1][j + 1];
                }
            }
        }
        return Arrays.stream(timelines[N - 1]).sum();
    }

    private int findStartingPointIndex(char[][] grid) {
        for (int i = 0; i < grid[0].length; i++) {
            if (grid[0][i] == 'S') {
                return i;
            }
        }
        return -1;
    }

}
