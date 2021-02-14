package games.awale;

import iialib.games.algs.IHeuristic;

public class AwaleHeuristics {

    public static IHeuristic<AwaleBoard, AwaleRole> hTop = (board, role) -> {
        return board.nbSeedRest-board.nbSeedTop;
        //return 0;
    };


    public static IHeuristic<AwaleBoard, AwaleRole> hDown = (board, role) -> {
        return board.nbSeedRest-board.nbSeedDown;
        //return 0;
    };

}
