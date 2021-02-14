package iialib.games.algs.algorithms;

import iialib.games.algs.GameAlgorithm;
import iialib.games.algs.IHeuristic;
import iialib.games.model.IBoard;
import iialib.games.model.IMove;
import iialib.games.model.IRole;

import java.util.ArrayList;

public class MiniMax<Move extends IMove, Role extends IRole, Board extends IBoard<Move, Role, Board>> implements GameAlgorithm<Move, Role, Board> {

    // Constants
    /**
     * Defaut value for depth limit
     */
    private final static int DEPTH_MAX_DEFAUT = 4;

    // Attributes
    /**
     * Role of the max player
     */
    private final Role playerMaxRole;

    /**
     * Role of the min player
     */
    private final Role playerMinRole;

    /**
     * Algorithm max depth
     */
    private int depthMax = DEPTH_MAX_DEFAUT;


    /**
     * Heuristic used by the max player
     */
    private final IHeuristic<Board, Role> h;

    //
    /**
     * number of internal visited (developed) nodes (for stats)
     */
    private int nbNodes;

    /**
     * number of leaves nodes nodes (for stats)
     */
    private int nbLeaves;

    // --------- Constructors ---------

    public MiniMax(Role playerMaxRole, Role playerMinRole, IHeuristic<Board, Role> h) {
        this.playerMaxRole = playerMaxRole;
        this.playerMinRole = playerMinRole;
        this.h = h;
        nbLeaves = 0;
        nbNodes = 0;
    }

    //
    public MiniMax(Role playerMaxRole, Role playerMinRole, IHeuristic<Board, Role> h, int depthMax) {
        this(playerMaxRole, playerMinRole, h);
        this.depthMax = depthMax;
    }

    /*
     * IAlgo METHODS =============
     */

    @Override
    public Move bestMove(Board board, Role playerRole) {
        System.out.println("[MiniMax]");

        ArrayList<Move> moves = board.possibleMoves(playerRole);
        Move bestMove = moves.get(0);
        Board firstTry = board.play(bestMove, playerRole);
        int best = (playerRole == playerMaxRole) ? maxMin(firstTry, 1) : minMax(firstTry, 1);

        if (playerRole == playerMaxRole) {
            for (int i = 1; i < moves.size(); i++) {
                int newVal = maxMin(firstTry, 1);
                if (newVal > best) {
                    System.out.println("CHECK1");
                    bestMove = moves.get(i);
                    best = newVal;
                }
            }
        } else {
            for (int i = 1; i < moves.size(); i++) {
                int newVal = minMax(firstTry, 1);
                if (newVal < best) {
                    System.out.println("CHECK2");
                    bestMove = moves.get(i);
                    best = newVal;
                }
            }
        }

        return bestMove;
    }

    /*
     * PUBLIC METHODS ==============
     */

    public String toString() {
        return "MiniMax(ProfMax=" + depthMax + ")";
    }

    /*
     * PRIVATE METHODS ===============
     */

    private int minMax(Board board, int depth) {
        // System.out.println("Prof: " + depth);
        ArrayList<Move> moves = board.possibleMoves(playerMinRole);
        if (depth == depthMax || moves.isEmpty()) {
            return h.eval(board, playerMinRole);
        } else {
            int min = IHeuristic.MAX_VALUE;
            for (Move move : moves) {
                min = Math.min(min, maxMin(board.play(move, playerMinRole), depth + 1));
            }
            return min;
        }
    }

    private int maxMin(Board board, int depth) {
        // System.out.println("Prof: " + depth);
        ArrayList<Move> moves = board.possibleMoves(playerMaxRole);
        if (depth == depthMax || moves.isEmpty()) {
            return h.eval(board, playerMaxRole);
        } else {
            int max = IHeuristic.MIN_VALUE;
            for (Move move : moves) {
                max = Math.max(max, minMax(board.play(move, playerMaxRole), depth + 1));
            }
            return max;
        }
    }
}
