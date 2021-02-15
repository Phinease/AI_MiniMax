package games.awale;

import iialib.games.algs.IHeuristic;

public class AwaleHeuristics {

    public static IHeuristic<AwaleBoard, AwaleRole> hTop = (board, role) -> board.nbSeedTop - board.nbSeedDown;


    public static IHeuristic<AwaleBoard, AwaleRole> hDown = (board, role) -> board.nbSeedDown - board.nbSeedTop;

}
