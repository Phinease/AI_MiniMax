package games.awale;

import games.dominos.DominosBoard;
import games.dominos.DominosRole;
import iialib.games.algs.IHeuristic;

public class AwaleHeuristics {

    public static IHeuristic<DominosBoard, DominosRole> hTop = (board, role) -> {
        if (board.nbVerticalMoves() == 0) {
            return IHeuristic.MIN_VALUE;
        }
        if (board.nbHorizontalMoves() == 0) {
            return IHeuristic.MAX_VALUE;
        }
        return board.nbVerticalMoves();
    };


    public static IHeuristic<DominosBoard, DominosRole> hDown = (board, role) -> {
        if (board.nbHorizontalMoves() == 0) {
            return IHeuristic.MIN_VALUE;
        }
        if (board.nbVerticalMoves() == 0) {
            return IHeuristic.MAX_VALUE;
        }
        return board.nbHorizontalMoves();
    };

}
