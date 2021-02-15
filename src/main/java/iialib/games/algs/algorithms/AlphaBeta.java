package iialib.games.algs.algorithms;

import iialib.games.algs.GameAlgorithm;
import iialib.games.algs.IHeuristic;
import iialib.games.model.IBoard;
import iialib.games.model.IMove;
import iialib.games.model.IRole;

import javax.management.relation.Role;
import java.util.ArrayList;

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

    private int minMax(Board board, int depth,int alpha,int beta) {
        // System.out.println("Prof: " + depth);
        ArrayList<Move> moves = board.possibleMoves(playerMinRole);
        if (depth == depthMax || moves.isEmpty()) {
            return h.eval(board, playerMinRole);
        } else {
            for (Move move : moves) {
                beta = Math.min(beta, maxMin(board.play(move, playerMinRole), depth + 1,alpha,beta));
                if (alpha >= beta)
                    return alpha;
            }
            return beta;
        }
    }

    private int maxMin(Board board, int depth,int alpha,int beta) {
        // System.out.println("Prof: " + depth);
        ArrayList<Move> moves = board.possibleMoves(playerMaxRole);
        if (depth == depthMax || moves.isEmpty()) {
            return h.eval(board, playerMaxRole);
        } else {
            for (Move move : moves) {
                alpha = Math.max(alpha, minMax(board.play(move, playerMaxRole), depth + 1,alpha,beta));
                if (alpha >= beta)
                    return beta;
            }
            return alpha;
        }
    }

    @Override
    public Move bestMove(Board board, Role playerRole) {
        System.out.println("[AlphaBeta]");

        ArrayList<Move> moves = board.possibleMoves(playerRole);
        Move bestMove = moves.get(0);
        Board firstTry = board.play(bestMove, playerRole);
        int best = (playerRole == playerMaxRole) ? maxMin(firstTry, 1,this.alpha,this.beta) : minMax(firstTry, 1,this.alpha,this.beta);

        if (playerRole == playerMaxRole) {
            for (int i = 1; i < moves.size(); i++) {
                int newVal = maxMin(firstTry, 1,this.alpha,this.beta);
                if (newVal > best) {
                    bestMove = moves.get(i);
                    best = newVal;
                }
            }
        } else {
            for (int i = 1; i < moves.size(); i++) {
                int newVal = minMax(firstTry, 1,this.alpha,this.beta);
                if (newVal < best) {
                    bestMove = moves.get(i);
                    best = newVal;
                }
            }
        }

        return bestMove;
    }
}
