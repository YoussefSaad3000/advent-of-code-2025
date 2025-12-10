package advent.day4;

import advent.Solver;

import java.util.*;

import static advent.Util.buildGrid;

public class Day4 implements Solver {
    private int N;
    private int M;
    private char[][] map;
    static private final int[][] DIRECTIONS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

    @Override
    public long solve(List<String> input) {
        char[][] map = buildGrid(input);
        this.N = map.length;
        this.M = map[0].length;
        this.map = map;
        return solvePart2(map);
    }

    private long solvePart1(char[][] map) {
        long res = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] == '@') {
                    if (checkNeighbors(i, j)) {
                        res += 1;
                    }
                }
            }
        }
        return res;
    }

    private boolean checkNeighbors(final int i, final int j) {
        int neighbors = Arrays.stream(DIRECTIONS).filter(tuple -> isRollPaper(i + tuple[0], j + tuple[1])).toList()
                        .size();
        return neighbors < 4;
    }

    private long solvePart2(char[][] map) {
        boolean[][] seen = new boolean[N][M];
        Deque<int[]> queue = new ArrayDeque<>();
        Set<String> shouldBeRemoved = new HashSet<>();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] == '@') {
                    if (checkNeighbors(i, j)) {
                        shouldBeRemoved.add(i + "/" + j);
                        queue.offer(new int[]{i, j});
                    }
                }
            }
        }

        while (!queue.isEmpty()) {
            var cell = queue.poll();
            int i = cell[0];
            int j = cell[1];
            if (seen[i][j]) {
                continue;
            }
            if (checkNeighbors(i, j)) {
                shouldBeRemoved.add(i + "/" + j);
                map[i][j] = '?';
                seen[i][j] = true;

                for (int[] dir : DIRECTIONS) {
                    int dx = dir[0] + i;
                    int dy = dir[1] + j;
                    if (isInsideMap(dx, dy) && map[dx][dy] == '@' && !seen[dx][dy]) {
                        queue.offer(new int[]{dx, dy});
                    }
                }
            }
        }

        return shouldBeRemoved.size();
    }

    private boolean isInsideMap(final int i, final int j) {
        return 0 <= i && i < N && 0 <= j && j < M;
    }

    private boolean isRollPaper(final int i, final int j) {
        return isInsideMap(i, j) && map[i][j] == '@';
    }
}
