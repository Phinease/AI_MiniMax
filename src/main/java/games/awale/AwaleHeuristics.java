package games.awale;

import iialib.games.algs.IHeuristic;

public class AwaleHeuristics {

    public static IHeuristic<AwaleBoard, AwaleRole> hTop = (board, role) -> {
        System.out.println("rest-top"+(board.nbSeedRest-board.nbSeedTop));
        return board.nbSeedRest-board.nbSeedTop;
    };


    public static IHeuristic<AwaleBoard, AwaleRole> hDown = (board, role) -> {
        System.out.println("rest-down"+(board.nbSeedRest-board.nbSeedTop));
        return board.nbSeedRest-board.nbSeedDown;
    };

}
