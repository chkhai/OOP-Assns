// Board.java

import java.util.Arrays;

/**
 CS108 Tetris Board.
 Represents a Tetris board -- essentially a 2-d grid
 of booleans. Supports tetris pieces and row clearing.
 Has an "undo" feature that allows clients to add and remove pieces efficiently.
 Does not do any drawing or have any idea of pixels. Instead,
 just represents the abstract 2-d board.
*/
public class Board	{
	// Some ivars are stubbed out for you:
	private int width;
	private int height;
	private boolean[][] grid;
	private boolean DEBUG = true;
	boolean committed;
	private int[] widths;
	private int[] heights;
	private int max_height;
	private boolean[][] dup_grid;
	private int[] dup_widths;
	private int[] dup_heights;
	private int dup_max_height;

	// Here a few trivial methods are provided:

	/**
	 Creates an empty board of the given width and height
	 measured in blocks.
	*/
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		grid = new boolean[width][height];
		committed = true;

		dup_grid = new boolean[width][height];
		widths = new int[height];
		dup_widths = new int[height];
		heights = new int[width];
		dup_heights = new int[width];
		max_height = 0;
		dup_max_height = 0;

		// YOUR CODE HERE
		//done
	}


	/**
	 Returns the width of the board in blocks.
	*/
	public int getWidth() {
		return width;
	}


	/**
	 Returns the height of the board in blocks.
	*/
	public int getHeight() {
		return height;
	}


	/**
	 Returns the max column height present in the board.
	 For an empty board this is 0.
	*/
	public int getMaxHeight() {
		return max_height; // YOUR CODE HERE
	}


	/**
	 Checks the board for internal consistency -- used
	 for debugging.
	*/
	public void sanityCheck() {
		if(!DEBUG) return;

		int checker_max_height = Integer.MIN_VALUE;
		for(int col = 0; col < width; col++) {
			if(heights[col] > checker_max_height) checker_max_height = heights[col];
		}
		if(max_height != checker_max_height) throw new RuntimeException("Sanity check failed on max height");

		for(int row = 0; row < height; row++) {
			int cnt = 0;
			for(int col = 0; col < width; col++) {
				if(grid[col][row]) cnt++;
			}
			if(cnt != widths[row]) throw new RuntimeException("Sanity check failed on widths");
		}

		for(int col = 0; col < width; col++) {
			for(int row = height - 1; row > -1; row--) {
				if(grid[col][row]){
					if(row + 1 != heights[col]) throw new RuntimeException("Sanity check failed on heights");
					break;
				}
			}
		}
	}

	/**
	 Given a piece and an x, returns the y
	 value where the piece would come to rest
	 if it were dropped straight down at that x.

	 <p>
	 Implementation: use the skirt and the col heights
	 to compute this fast -- O(skirt length).
	*/
	public int dropHeight(Piece piece, int x) {
		int[] skirt = piece.getSkirt();
		int y = 0;
		for (int i = 0; i < piece.getWidth(); i++) {
			if (x + i >= width || x + i < 0) continue;
			if(heights[x + i] - skirt[i] > y) y = heights[x + i] - skirt[i];
		}
		return y;
		// YOUR CODE HERE
	}


	/**
	 Returns the height of the given column --
	 i.e. the y value of the highest block + 1.
	 The height is 0 if the column contains no blocks.
	*/
	public int getColumnHeight(int x) {	return heights[x]; // YOUR CODE HERE
	}


	/**
	 Returns the number of filled blocks in
	 the given row.
	*/
	public int getRowWidth(int y) { return widths[y]; // YOUR CODE HERE
	}


	/**
	 Returns true if the given block is filled in the board.
	 Blocks outside of the valid width/height area
	 always return true.
	*/
	public boolean getGrid(int x, int y) {
		if(out_of_bounds(x,y)) return true;
		return grid[x][y];
	}

	private boolean out_of_bounds(int x, int y) {	return (x < 0 || x >= width || 0 > y || y >= height);
	}


	public static final int PLACE_OK = 0;
	public static final int PLACE_ROW_FILLED = 1;
	public static final int PLACE_OUT_BOUNDS = 2;
	public static final int PLACE_BAD = 3;

	/**
	 Attempts to add the body of a piece to the board.
	 Copies the piece blocks into the board grid.
	 Returns PLACE_OK for a regular placement, or PLACE_ROW_FILLED
	 for a regular placement that causes at least one row to be filled.

	 <p>Error cases:
	 A placement may fail in two ways. First, if part of the piece may falls out
	 of bounds of the board, PLACE_OUT_BOUNDS is returned.
	 Or the placement may collide with existing blocks in the grid
	 in which case PLACE_BAD is returned.
	 In both error cases, the board may be left in an invalid
	 state. The client can use undo(), to recover the valid, pre-place state.
	*/
	public int place(Piece piece, int x, int y) {
		// flag !committed problem
		if (!committed) throw new RuntimeException("place commit problem");

		int result = PLACE_OK;

		committed = false;
		// copying data
		copy_data();

		if(out_of_bounds(x,y)) return PLACE_OUT_BOUNDS;
		if(out_of_bounds(x + piece.getWidth() - 1,y+ piece.getHeight() - 1)) return PLACE_OUT_BOUNDS;

		int xx, yy;

		for(TPoint curr: piece.getBody()) if(grid[x+curr.x][y+curr.y]) return PLACE_BAD;

		for(TPoint curr: piece.getBody()){
			xx = x+curr.x;
			yy = y+curr.y;
			grid[xx][yy] = true;
			widths[yy] += 1;

			if(yy >= heights[xx]) heights[xx] = yy + 1;
			if(widths[yy] == width) result = PLACE_ROW_FILLED;
			if(heights[xx] > max_height) max_height = heights[xx];
		}
		sanityCheck();
		// YOUR CODE HERE
		//done
		return result;
	}

	public void copy_data() {
		dup_max_height = max_height;
		System.arraycopy(widths, 0, dup_widths, 0, widths.length);
		System.arraycopy(heights, 0, dup_heights, 0, heights.length);
		for(int i = 0; i < width; i++) System.arraycopy(grid[i], 0, dup_grid[i], 0, grid[i].length);

	}

	/**
	 Deletes rows that are filled all the way across, moving
	 things above down. Returns the number of rows cleared.
	*/
	public int clearRows() {
		if(committed) copy_data();
		committed = false;
		int rowsCleared = 0;
		int new_row = 0;

		//shifting
		for(int row = 0; row < max_height; row++) {
			if(widths[row] == width) {
				rowsCleared++;
				continue;
			}
			for(int col = 0; col < width; col++) grid[col][new_row] = grid[col][row];
			widths[new_row] = widths[row];
			new_row++;
		}

		//erase rows after new_row
		for(; new_row < max_height; new_row++) {
			widths[new_row] = 0;
			for(int col = 0; col < width; col++) grid[col][new_row] = false;
		}

		max_height = max_height - rowsCleared;

		int[] new_heights= new int[width];

		for(int row = max_height-1; row > -1; row--) {
			for(int col = 0; col < width; col++){
				if(row >= new_heights[col] && grid[col][row])  new_heights[col] = row+1;
			}
		}
		heights = new_heights;
		sanityCheck();
		return rowsCleared;
	}



	/**
	 Reverts the board to its state before up to one place
	 and one clearRows();
	 If the conditions for undo() are not met, such as
	 calling undo() twice in a row, then the second undo() does nothing.
	 See the overview docs.
	*/
	public void undo() {
		// YOUR CODE HERE
		if(committed) return;

		boolean[][] tmp_grid = grid;
		int[] tmp_widths = widths;
		int[] tmp_heights = heights;
		int tmp_max_height = max_height;

		grid = dup_grid;
		dup_grid = tmp_grid;
		widths = dup_widths;
		dup_widths = tmp_widths;
		heights = dup_heights;
		dup_heights = tmp_heights;
		max_height = dup_max_height;
		dup_max_height = tmp_max_height;

		commit();
		sanityCheck();
	}


	/**
	 Puts the board in the committed state.
	*/
	public void commit() {
		committed = true;
	}



	/*
	 Renders the board state as a big String, suitable for printing.
	 This is the sort of print-obj-state utility that can help see complex
	 state change over time.
	 (provided debugging utility)
	 */
	public String toString() {
		StringBuilder buff = new StringBuilder();
		for (int y = height-1; y>=0; y--) {
			buff.append('|');
			for (int x=0; x<width; x++) {
				if (getGrid(x,y)) buff.append('+');
				else buff.append(' ');
			}
			buff.append("|\n");
		}
		for (int x=0; x<width+2; x++) buff.append('-');
		return(buff.toString());
	}
}


