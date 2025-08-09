// HW1 2-d array Problems
// CharGrid encapsulates a 2-d grid of chars and supports
// a few operations on the grid.

public class CharGrid {
	private char[][] grid;

	/**
	 * Constructs a new CharGrid with the given grid.
	 * Does not make a copy.
	 * @param grid
	 */
	public CharGrid(char[][] grid) {
		this.grid = grid;
	}
	
	/**
	 * Returns the area for the given char in the grid. (see handout).
	 * @param ch char to look for
	 * @return area for given char
	 */
	public int charArea(char ch) {
		if (grid.length == 0 || grid[0].length == 0) return 0;
		int area = 0;
		boolean first_occur_not_found = true;
		int row1 = -1;
		int row2 = -1;
		int col1 = grid[0].length;
		int col2 = -1;
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[0].length; j++) {
				if(grid[i][j] == ch) {
					if(first_occur_not_found) {
						first_occur_not_found = false;
						row1 = i;
					}
					row2 = i;
					if(j < col1) col1 = j;
					if(j > col2) col2 = j;
				}
			}
		}
		if(first_occur_not_found) return 0;
		area = (row2 - row1 + 1)*(col2-col1+1);
		return area; // YOUR CODE HERE
	}
	
	/**
	 * Returns the count of '+' figures in the grid (see handout).
	 * @return number of + in grid
	 */
	public int countPlus() {
		int plus_cnt = 0;
		for(int i = 1; i < grid.length - 1; i++) {
			for(int j = 1; j < grid[0].length - 1; j++) {
				char c =  grid[i][j];
				int up_arm = arm_len(c, i, j, -1, 0);
				int down_arm = arm_len(c, i, j, 1, 0);
				int left_arm = arm_len(c, i, j, 0, -1);
				int right_arm = arm_len(c, i, j, 0, 1);
				if(up_arm != left_arm || up_arm != right_arm || up_arm != down_arm) continue;
				if(up_arm < 2) continue;
				plus_cnt++;
			}
		}

		return plus_cnt; // YOUR CODE HERE
	}

	private boolean in_bounds(int i, int j) {
		return (i >= 0) && (i < grid.length) && (j >= 0) && (j < grid[0].length);
	}

	private int arm_len(char c, int i, int j, int i_diff, int j_diff) {
		int len = 1;
		int new_i = i + i_diff;
		int new_j = j + j_diff;
		while(in_bounds(new_i, new_j) && grid[new_i][new_j] == c) {
			len++;
			new_i += i_diff;
			new_j += j_diff;
		}
		return len;
	}
	
}
