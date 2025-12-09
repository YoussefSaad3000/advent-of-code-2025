package advent.day4;

import advent.Solver;

import java.util.Arrays;
import java.util.List;

import static advent.Util.buildGrid;

public class Day4 implements Solver  {
    private int N;
    private int M;
    private char[][] map;
    static private final int[][] DIRECTIONS = {{1,0}, {-1,0},{0,1}, {0, -1}, {1, 1}, {1,-1}, {-1, 1}, {-1,-1}};

    @Override
    public long solve(List<String> input) {
        char[][] map = buildGrid(input);
        this.N = map.length;
        this.M = map[0].length;
        this.map = map;
        return solve(map);
    }

    private long solve(char[][] map) {
        long res = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
               if (map[i][j] == '@') {
                   int finalI = i;
                   int finalJ = j;

                   int neighbors = Arrays.stream(DIRECTIONS)
                           .filter(tuple -> isRollPaper(finalI + tuple[0], finalJ + tuple[1]))
                           .toList()
                           .size();
                   if (neighbors < 4) {
                       res += 1;
                   }
               }
            }
        }
        return res;
    }

    private boolean isRollPaper(final int i, final int j) {
        return 0 <= i && i < N && 0 <= j && j < M && map[i][j] == '@';
    }



}
