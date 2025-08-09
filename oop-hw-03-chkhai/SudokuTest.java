import junit.framework.TestCase;

import static java.lang.Math.abs;

public class SudokuTest extends TestCase {

    public void test_solve_1(){
        String ans = "1 3 5 2 9 7 8 6 4\n9 8 2 4 1 6 7 5 3\n7 6 4 3 8 5 1 9 2\n2 1 8 7 3 9 6 4 5\n5 9 7 8 6 4 2 3 1\n6 4 3 1 5 2 9 7 8\n4 2 6 5 7 1 3 8 9\n3 5 9 6 2 8 4 1 7\n8 7 1 9 4 3 5 2 6";
        String sud = "0 3 5 2 9 0 8 6 4\n0 8 2 4 1 0 7 0 3\n7 6 4 3 8 0 0 9 0\n2 1 8 7 3 9 0 4 0\n0 0 0 8 0 4 2 3 0\n0 4 3 0 5 2 9 7 0\n4 0 6 5 7 1 0 0 9\n3 5 9 0 2 8 4 1 7\n8 0 0 9 0 0 5 2 6";
        int[][] g = Sudoku.stringsToGrid(
                "0 3 5 2 9 0 8 6 4",
                "0 8 2 4 1 0 7 0 3",
                "7 6 4 3 8 0 0 9 0",
                "2 1 8 7 3 9 0 4 0",
                "0 0 0 8 0 4 2 3 0",
                "0 4 3 0 5 2 9 7 0",
                "4 0 6 5 7 1 0 0 9",
                "3 5 9 0 2 8 4 1 7",
                "8 0 0 9 0 0 5 2 6");
        Sudoku sudoku = new Sudoku(g);
        assertEquals(1, sudoku.solve());
        assertTrue(sudoku.getSolutionText().contains(ans));
        assertTrue(sudoku.toString().equals(sud));
    }

    public void test_solve_2(){
        int[][] g = {
                {0, 0, 0, 0, 0, 0, 2, 0, 0},
                {0, 8, 0, 0, 0, 7, 0, 9, 0},
                {6, 0, 2, 0, 0, 0, 5, 0, 0},
                {0, 7, 0, 0, 6, 0, 0, 0, 0},
                {0, 0, 0, 9, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 2, 0, 0, 4, 0},
                {0, 0, 5, 0, 0, 0, 6, 0, 3},
                {9, 0, 0, 4, 0, 0, 0, 7, 0},
                {0, 0, 6, 0, 0, 0, 0, 0, 0}
        };
        Sudoku sudoku = new Sudoku(g);
        assertEquals(59, sudoku.solve());
        long time1 = sudoku.getElapsed();
        assertTrue(time1 > 0);
        sudoku.solve();
        long time2 = sudoku.getElapsed();
    }

    public void test_solve_3(){
        String solution =
                "5 4 6 1 8 2 7 9 3\n" +
                        "2 7 8 3 5 9 1 4 6\n" +
                        "9 1 3 4 6 7 2 5 8\n" +
                        "3 5 2 7 9 6 4 8 1\n" +
                        "4 9 7 8 1 5 3 6 2\n" +
                        "6 8 1 2 3 4 9 7 5\n" +
                        "1 2 9 6 4 8 5 3 7\n" +
                        "7 6 4 5 2 3 8 1 9\n" +
                        "8 3 5 9 7 1 6 2 4";
        int[][] g = Sudoku.stringsToGrid(
                "5 0 6 1 8 2 7 9 3",
                "2 0 8 3 5 9 1 0 6",
                "9 1 3 4 6 7 0 5 8",
                "0 5 2 7 9 6 4 8 1",
                "4 9 7 0 1 0 3 6 2",
                "6 8 1 2 3 4 9 7 0",
                "1 2 9 6 4 8 5 3 7",
                "7 6 4 0 2 3 8 1 0",
                "0 3 5 9 7 1 6 2 4");
        Sudoku sudoku = new Sudoku(g);
        assertEquals(1, sudoku.solve());
        assertTrue(sudoku.getSolutionText().contains(solution));
    }

    public void test_solve_4(){
        String ans = "1 6 4 7 9 5 3 8 2\n2 8 7 4 6 3 9 1 5\n9 3 5 2 8 1 4 6 7\n3 9 1 8 7 6 5 2 4\n5 4 6 1 3 2 7 9 8\n7 2 8 9 5 4 1 3 6\n8 1 9 6 4 7 2 5 3\n6 7 3 5 2 9 8";
        int[][] easyGrid = Sudoku.stringsToGrid(
                "1 6 4 0 0 0 0 0 2",
                "2 0 0 4 0 3 9 1 0",
                "0 0 5 0 8 0 4 0 7",
                "0 9 0 0 0 6 5 0 0",
                "5 0 0 1 0 2 0 0 8",
                "0 0 8 9 0 0 0 3 0",
                "8 0 9 0 4 0 2 0 0",
                "0 7 3 5 0 9 0 0 1",
                "4 0 0 0 0 0 6 7 9");
        Sudoku sudoku = new Sudoku(easyGrid);
        assertEquals(1, sudoku.solve());
        assertTrue(sudoku.getSolutionText().contains(ans));
    }

    public void test_solve_5(){
        String ans = "5 3 4 6 7 8 9 1 2\n6 7 2 1 9 5 3 4 8\n1 9 8 3 4 2 5 6 7\n8 5 9 7 6 1 4 2 3\n4 2 6 8 5 3 7 9 1\n7 1 3 9 2 4 8 5 6\n9 6 1 5 3 7 2 8 4\n2 8 7 4 1 9 6 3 5\n3 4 5 2 8 6 1 7 9";
        int[][] mediumGrid = Sudoku.stringsToGrid(
                "530070000",
                "600195000",
                "098000060",
                "800060003",
                "400803001",
                "700020006",
                "060000280",
                "000419005",
                "000080079");
        Sudoku sudoku = new Sudoku(mediumGrid);
        assertEquals(1, sudoku.solve());
        assertTrue(sudoku.getSolutionText().contains(ans));
    }

    public void test_solve_6(){
        String ans = "3 7 5 1 6 2 4 8 9\n8 6 1 4 9 3 5 2 7\n2 4 9 7 8 5 1 6 3\n4 9 3 8 5 7 6 1 2\n7 1 6 2 4 9 8 3 5\n5 2 8 3 1 6 7 9 4\n6 5 7 9 2 1 3 4 8\n1 8 2 5 3 4 9 7 6\n9 3 4 6 7 8 2 5 1";
        int[][]  hardGrid = Sudoku.stringsToGrid(
                "3 7 0 0 0 0 0 8 0",
                "0 0 1 0 9 3 0 0 0",
                "0 4 0 7 8 0 0 0 3",
                "0 9 3 8 0 0 0 1 2",
                "0 0 0 0 4 0 0 0 0",
                "5 2 0 0 0 6 7 9 0",
                "6 0 0 0 2 1 0 4 0",
                "0 0 0 5 3 0 9 0 0",
                "0 3 0 0 0 0 0 5 1");
        Sudoku sudoku = new Sudoku(hardGrid);
        assertEquals(1, sudoku.solve());
        assertTrue(sudoku.getSolutionText().contains(ans));
    }

    public void test_text_to_grid(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int value = (i + j) % 9 + 1;  // Values 1-9
                sb.append(value);
                if (i != 8 || j != 8) {
                    sb.append(" ");
                }
            }
        }
        String input = sb.toString();
        int[][] g = Sudoku.textToGrid(input);
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                int expectedValue = (i + j) % 9 + 1;
                assertEquals(expectedValue, g[i][j]);
            }
        }
    }

}
