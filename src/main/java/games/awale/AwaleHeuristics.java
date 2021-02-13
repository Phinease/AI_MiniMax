package games.awale;

import iialib.games.algs.IHeuristic;

public class AwaleHeuristics {

    public static IHeuristic<AwaleBoard, AwaleRole> hTop = (board, role) -> {
        return 0;
    };


    public static IHeuristic<AwaleBoard, AwaleRole> hDown = (board, role) -> {
        return 0;
    };

}
