package games.awale;

import iialib.games.algs.IHeuristic;

public class AwaleHeuristics {

    public static IHeuristic<AwaleBoard, AwaleRole> hTop = (board, role) -> board.nbSeedRest - board.nbSeedTop;


    public static IHeuristic<AwaleBoard, AwaleRole> hDown = (board, role) -> board.nbSeedRest - board.nbSeedDown;

}
