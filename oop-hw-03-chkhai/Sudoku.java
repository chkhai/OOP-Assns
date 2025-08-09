import java.util.*;

/*
 * Encapsulates a Sudoku grid to be solved.
 * CS108 Stanford.
 */
public class Sudoku {
	// Provided grid data for main/testing
	// The instance variable strategy is up to you.
	
	// Provided easy 1 6 grid
	// (can paste this text into the GUI too)
	public static final int[][] easyGrid = Sudoku.stringsToGrid(
	"1 6 4 0 0 0 0 0 2",
	"2 0 0 4 0 3 9 1 0",
	"0 0 5 0 8 0 4 0 7",
	"0 9 0 0 0 6 5 0 0",
	"5 0 0 1 0 2 0 0 8",
	"0 0 8 9 0 0 0 3 0",
	"8 0 9 0 4 0 2 0 0",
	"0 7 3 5 0 9 0 0 1",
	"4 0 0 0 0 0 6 7 9");
	
	
	// Provided medium 5 3 grid
	public static final int[][] mediumGrid = Sudoku.stringsToGrid(
			"530070000",
			"600195000",
			"098000060",
			"800060003",
			"400803001",
			"700020006",
			"060000280",
			"000419005",
			"000080079");
	
	// Provided hard 3 7 grid
	// 1 solution this way, 6 solutions if the 7 is changed to 0
	public static final int[][] hardGrid = Sudoku.stringsToGrid(
	"3 7 0 0 0 0 0 8 0",
	"0 0 1 0 9 3 0 0 0",
	"0 4 0 7 8 0 0 0 3",
	"0 9 3 8 0 0 0 1 2",
	"0 0 0 0 4 0 0 0 0",
	"5 2 0 0 0 6 7 9 0",
	"6 0 0 0 2 1 0 4 0",
	"0 0 0 5 3 0 9 0 0",
	"0 3 0 0 0 0 0 5 1");
	
	
	public static final int SIZE = 9;  // size of the whole 9x9 puzzle
	public static final int PART = 3;  // size of each 3x3 part
	public static final int MAX_SOLUTIONS = 100;
	
	// Provided various static utility methods to
	// convert data formats to int[][] grid.
	
	/**
	 * Returns a 2-d grid parsed from strings, one string per row.
	 * The "..." is a Java 5 feature that essentially
	 * makes "rows" a String[] array.
	 * (provided utility)
	 * @param rows array of row strings
	 * @return grid
	 */
	public static int[][] stringsToGrid(String... rows) {
		int[][] result = new int[rows.length][];
		for (int row = 0; row<rows.length; row++) {
			result[row] = stringToInts(rows[row]);
		}
		return result;
	}
	
	
	/**
	 * Given a single string containing 81 numbers, returns a 9x9 grid.
	 * Skips all the non-numbers in the text.
	 * (provided utility)
	 * @param text string of 81 numbers
	 * @return grid
	 */
	public static int[][] textToGrid(String text) {
		int[] nums = stringToInts(text);
		if (nums.length != SIZE*SIZE) throw new RuntimeException("Needed 81 numbers, but got:" + nums.length);
		
		int[][] result = new int[SIZE][SIZE];
		int count = 0;
		for (int row = 0; row<SIZE; row++) {
			for (int col=0; col<SIZE; col++) {
				result[row][col] = nums[count];
				count++;
			}
		}
		return result;
	}
	
	
	/**
	 * Given a string containing digits, like "1 23 4",
	 * returns an int[] of those digits {1 2 3 4}.
	 * (provided utility)
	 * @param string string containing ints
	 * @return array of ints
	 */
	public static int[] stringToInts(String string) {
		int[] a = new int[string.length()];
		int found = 0;
		for (int i=0; i<string.length(); i++) {
			if (Character.isDigit(string.charAt(i))) {
				a[found] = Integer.parseInt(string.substring(i, i+1));
				found++;
			}
		}
		int[] result = new int[found];
		System.arraycopy(a, 0, result, 0, found);
		return result;
	}


	// Provided -- the deliverable main().
	// You can edit to do easier cases, but turn in
	// solving hardGrid.
	public static void main(String[] args) {
		Sudoku sudoku = new Sudoku(hardGrid);
		System.out.println(sudoku); // print the raw problem
		int count = sudoku.solve();
		System.out.println("solutions:" + count);
		System.out.println("elapsed:" + sudoku.getElapsed() + "ms");
		System.out.println(sudoku.getSolutionText());
	}
	
	//inner spot class
	//done
	private class spot implements Comparable<spot> {
		private final int x_cord;
		private final int y_cord;
		private int size_of_available_nums;
		private final List<Integer> lst;

		public spot(int x, int y){
			this.x_cord = x;
			this.y_cord = y;
			this.lst = new Vector<>();
			find_legal_nums();
		}

		private void find_legal_nums(){
			lst.clear();
			for(int i=1; i<10; i++) lst.add(i);

			for(int i = 0; i < SIZE; i++){
				lst.remove((Integer)grid[i][y_cord]);
				lst.remove((Integer)grid[x_cord][i]);
			}

			int part_x = x_cord / PART;
			int part_y = y_cord / PART;
			for(int i = 0; i < PART; i++){
				for(int j = 0; j < PART; j++){
					int xx = part_x * PART + i;
					int yy = part_y * PART + j;
					if(xx == x_cord && yy == y_cord) continue;
					lst.remove((Integer)grid[xx][yy]);
				}
			}
			size_of_available_nums = lst.size();
		}

		public void set(int val){
			grid[x_cord][y_cord] = val;
		}

		@Override
		public int compareTo(spot o) {
			return Integer.compare(this.size_of_available_nums, o.size_of_available_nums);
		}
	}


	//private variables
	private final int[][] grid;
	private final List<spot> spots;
	private int sols;
	private final int[][] dup_grid;
	private long time;

	/**
	 * Sets up based on the given ints.
	 */
	//done
	public Sudoku(int[][] ints) {
		spots = new ArrayList<>();
		this.grid = ints;
		sols = 0;
		dup_grid = new int[SIZE][SIZE];
		make_spots();
	}

	//done
	private void make_spots() {
		for(int i = 0; i < SIZE; i++){
			for(int j = 0; j < SIZE; j++){
				if(grid[i][j] != 0) continue;
				spot s = new spot(i, j);
				spots.add(s);
			}
		}
		Collections.sort(spots);
	}

	//done
	private String to_string_helper(int[][] grid){
		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                sb.append(grid[i][j]);
                if (j < grid[i].length - 1) sb.append(" ");
            }
			if (i < grid.length - 1) sb.append("\n");
        }
		return sb.toString();
	}

	//done
	@Override
	public String toString(){
		return to_string_helper(grid);
	}

	//done
	private void copy_data(){
		for(int i = 0; i < SIZE; i++) {
			System.arraycopy(grid[i], 0, dup_grid[i], 0, SIZE);
		}
	}

	/**
	 * Solves the puzzle, invoking the underlying recursive search.
	 */
	//done
	public int solve() {
		long time_start = System.currentTimeMillis();
		helper(0);
		time = System.currentTimeMillis() - time_start;
		return sols; // YOUR CODE HERE
	}

	private void helper(int idx){
		if(sols >= MAX_SOLUTIONS) return;
		if(idx == spots.size()) {
			sols++;
			if(sols == 1) copy_data();
			return;
		}
		spot s = spots.get(idx);
		s.find_legal_nums();
		for(int i : s.lst){
			s.set(i);
			helper(idx+1);
			s.set(0);
		}
	}

	public String getSolutionText() {
		if(dup_grid == null) return "";
		return to_string_helper(dup_grid);
		 // YOUR CODE HERE
	}
	
	public long getElapsed() {
		return time; // YOUR CODE HERE
	}

}
