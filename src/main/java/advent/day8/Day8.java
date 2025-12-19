package advent.day8;

import advent.Solver;
import advent.Util.Pair;

import java.util.*;


public class Day8 implements Solver {
    @Override
    public long solve(List<String> input) {

        List<Point> points = transformToPoints(input);

       return solveUsingUnionFind(points);
    }

    private long solveUsingUnionFind(List<Point> points) {
        int MAX_CONNECTION = 10;
        final long[]  circuit = new long[MAX_CONNECTION];
        int maxCircuitId = 0;

        List<Pair<Double, int[]>> distances = new ArrayList<>();
        for (int i = 0; i < points.size()-1; i++) {
            for( int j = i+1; j < points.size(); j++){
                var distance = points.get(i).distance(points.get(j));
                distances.add( new Pair<>(distance, new int[] {i, j}));
            }
        }



        Set<Integer> seen = new HashSet<>();
        Map<Integer, Integer> pointToCircuitMapping = new HashMap<>();
        distances.sort((pair1, pair2) -> Double.compare(pair1.first(), pair2.first()));
        for (int i = 0; i < 10; i++) {
            var distanceObj = distances.get(i);
            int p1 = distanceObj.second()[0];
            int p2 = distanceObj.second()[1];
            System.out.println("Closest i=" + i +" "+ points.get(p1) + ", " + points.get(p2) );
            var p1CircuitId = pointToCircuitMapping.get(p1);
            var p2CircuitId = pointToCircuitMapping.get(p2);
            if (p1CircuitId == null && p2CircuitId == null) {
                // new circuit
                circuit[maxCircuitId] += 2;
                pointToCircuitMapping.put(p1, maxCircuitId);
                pointToCircuitMapping.put(p2, maxCircuitId);
                maxCircuitId += 1;



            } else if (p1CircuitId == null ) {
                circuit[p2CircuitId] += 1;
                pointToCircuitMapping.put(p1, p2CircuitId);
            } else if (p2CircuitId == null ) {
                circuit[p1CircuitId] += 1;
                pointToCircuitMapping.put(p2, p1CircuitId);

            } else if ( p1CircuitId != p2CircuitId) {

            }
        }
        long[] sortedCircuit = Arrays.stream(circuit).sorted().toArray();
        return sortedCircuit[MAX_CONNECTION-1] * sortedCircuit[MAX_CONNECTION-2] * sortedCircuit[MAX_CONNECTION-3];
    }

    private List<Point> transformToPoints(List<String> input) {
        return input.stream().map(line -> {
            var arr = Arrays.stream(line.split(",")).mapToLong(Long::parseLong).toArray();
            return new Point(arr[0], arr[1], arr[2]);
        }).toList();
    }


    record Point(long x, long y, long z) {
        public double distance(Point othr) {
            return  Math.pow(x - othr.x(), 2) + Math.pow(y - othr.y(), 2) +  Math.pow(z - othr.z(), 2);
        }
    }

}
