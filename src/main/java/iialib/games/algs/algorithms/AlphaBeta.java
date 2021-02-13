package iialib.games.algs.algorithms;

import iialib.games.algs.GameAlgorithm;
import iialib.games.algs.IHeuristic;
import iialib.games.model.IBoard;
import iialib.games.model.IMove;
import iialib.games.model.IRole;

import javax.management.relation.Role;

public class AlphaBeta<Move extends IMove, Role extends IRole, Board extends IBoard<Move, Role, Board>> implements GameAlgorithm<Move, Role, Board> {
    private final static int DEPTH_MAX_DEFAUT = 4;
    private final Role playerMaxRole;
    private final Role playerMinRole;
    private int depthMax = DEPTH_MAX_DEFAUT;
    private int nbNodes;
    private int nbLeaves;
    private IHeuristic<Board, Role> h;

    private int alpha;
    private int beta;

    public AlphaBeta(Role playerMaxRole, Role playerMinRole, IHeuristic<Board, Role> h) {
        this.playerMaxRole = playerMaxRole;
        this.playerMinRole = playerMinRole;
        this.h = h;
        alpha = IHeuristic.MIN_VALUE;
        beta = IHeuristic.MAX_VALUE;
    }

    public AlphaBeta(Role playerMaxRole, Role playerMinRole, IHeuristic<Board, Role> h, int depthMax) {
        this(playerMaxRole, playerMinRole, h);
        this.depthMax = depthMax;
    }

    @Override
    public Move bestMove(Board board, Role playerRole) {
        return null;
    }
}
