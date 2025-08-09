//
// TetrisGrid encapsulates a tetris board and has
// a clearRows() capability.

public class TetrisGrid {

	private boolean[][] g;
	/**
	 * Constructs a new instance with the given grid.
	 * Does not make a copy.
	 * @param grid
	 */
	public TetrisGrid(boolean[][] grid) {
		g = grid;
	}

	/**
	 * Does row-clearing on the grid (see handout).
	 */
	public void clearRows() {
		boolean[][] new_g =  new boolean[g.length][g[0].length];
		int new_row_cnt = 0;

		for(int i = 0; i < g[0].length; i++){
			if(col_is_full(i)) continue;
			// here col is not full, so we have to copy
			for (int row = 0; row < g.length; row++) {
				new_g[row][new_row_cnt] = g[row][i];
			}
			new_row_cnt++;
		}
		g = new_g;
	}

	private boolean col_is_full(int col){
		for(int i = 0; i < g.length; i++){
			if(!g[i][col]) return false;
		}
		return  true;
	}

	/**
	 * Returns the internal 2d grid array.
	 * @return 2d grid array
	 */
	boolean[][] getGrid() {
		return g; // YOUR CODE HERE
	}
}
