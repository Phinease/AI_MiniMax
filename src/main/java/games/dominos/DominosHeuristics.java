package games.dominos;

import iialib.games.algs.IHeuristic;

public class DominosHeuristics {
	
	public static IHeuristic<DominosBoard,DominosRole>  hVertical = (board,role) -> {
        /* TODO */
		if(board.nbVerticalMoves()==0){
			return IHeuristic.MIN_VALUE;
		}
		if(board.nbHorizontalMoves()==0){
			return IHeuristic.MAX_VALUE;
		}
		return board.nbHorizontalMoves()-board.nbVerticalMoves();
    };
    

	public static IHeuristic<DominosBoard,DominosRole> hHorizontal = (board,role) -> {
	       /* TODO */
		if(board.nbHorizontalMoves()==0){
			return IHeuristic.MIN_VALUE;
		}
		if(board.nbVerticalMoves()==0){
			return IHeuristic.MAX_VALUE;
		}
		return board.nbVerticalMoves()- board.nbHorizontalMoves();
	};
   
}
	