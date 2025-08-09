
// Test cases for CharGrid -- a few basic tests are provided.

import junit.framework.TestCase;

public class CharGridTest extends TestCase {
	
	public void testCharArea1() {
		char[][] grid = new char[][] {
				{'a', 'y', ' '},
				{'x', 'a', 'z'},
			};
		
		
		CharGrid cg = new CharGrid(grid);
				
		assertEquals(4, cg.charArea('a'));
		assertEquals(1, cg.charArea('z'));
		assertEquals(1, cg.charArea('x'));
		assertEquals(1, cg.charArea(' '));
	}
	
	
	public void testCharArea2() {
		char[][] grid = new char[][] {
				{'c', 'a', ' '},
				{'b', ' ', 'b'},
				{' ', ' ', 'a'}
			};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(6, cg.charArea('a'));
		assertEquals(3, cg.charArea('b'));
		assertEquals(1, cg.charArea('c'));
		assertEquals(9, cg.charArea(' '));
	}

	public void testCharAreaEmpty() {
		char[][] grid = new char[][] {};
		CharGrid cg = new CharGrid(grid);
		assertEquals(0, cg.charArea('a'));
		assertEquals(0, cg.charArea('b'));
	}

	public void testCharArea3() {
		char[][] grid = new char[][] {
				{'l', 'm', 'b'},
				{'e', 'd', 'b'},
				{'d', 'e', 'm'},
		}
		;
		CharGrid cg = new CharGrid(grid);
		assertEquals(1, cg.charArea('l'));
		assertEquals(6, cg.charArea('m'));
		assertEquals(4, cg.charArea('e'));
		assertEquals(4, cg.charArea('d'));
		assertEquals(2, cg.charArea('b'));
	}

	public void testCharArea4() {
		char[][] grid = new char[][] {
				{'a', 'b', 'b'},
				{' ', 'a', 'o'},
				{'b', 'e', ' '},
				{'m', 'b', 'o'},
		};
		CharGrid cg = new CharGrid(grid);
		assertEquals(4, cg.charArea('a'));
		assertEquals(12, cg.charArea('b'));
		assertEquals(6, cg.charArea(' '));
		assertEquals(3, cg.charArea('o'));
		assertEquals(1, cg.charArea('m'));
		assertEquals(1, cg.charArea('e'));
	}

	public void testPlusEmpty(){
		char[][] grid = new char[][] {
				{'a', 'b', 'b'},
				{' ', 'a', 'o'},
		};
		CharGrid cg = new CharGrid(grid);
		assertEquals(0, cg.countPlus());
	}

	public void testPlus1(){
		char[][] grid = new char[][] {
				{' ', 'm', ' '},
				{'m', 'm', 'm'},
				{' ', 'm', ' '},
				{' ', ' ', ' '},
		};
		CharGrid cg = new CharGrid(grid);
		assertEquals(1, cg.countPlus());
	}

	public void testPlus2(){
		char[][] grid = new char[][] {
				{' ', ' ', 'p', ' ', ' ', ' ', ' ', ' ', ' '},
				{' ', ' ', 'p', ' ', ' ', ' ', ' ', 'x', ' '},
				{'p', 'p', 'p', 'p', 'p', ' ', 'x', 'x', 'x',},
				{' ', ' ', 'p', ' ', ' ', 'y', ' ', 'x', ' '},
				{' ', ' ', 'p', ' ', 'y', 'y', 'y', ' ', ' '},
				{'z', 'z', 'z', 'z', 'z', 'y', 'z', 'z', 'z'},
				{' ', ' ', 'x', 'x', ' ', 'y', ' ', ' ', ' '},
		};
		CharGrid cg = new CharGrid(grid);
		assertEquals(2, cg.countPlus());
	}

	public void testPlus3(){
		char[][] grid = new char[][] {
				{' ', '+', ' ', ' ', ' ', ' ', ' ', '-', ' '},
				{'+', '+', '+', ' ', '@', ' ', '-', '-', '-'},
				{' ', '+', ' ', ' ', '@', ' ', ' ', '-', ' ',},
				{' ', ' ', '@', '@', '@', '@', '@', '-', ' '},
				{' ', ' ', 'j', ' ', '@', ' ', ' ', 'o', ' '},
				{' ', 'j', 'j', 'j', '@', ' ', 'o', 'o', 'o'},
				{' ', ' ', 'j', ' ', ' ', ' ', ' ', 'o', ' '},
		};
		CharGrid cg = new CharGrid(grid);
		assertEquals(4, cg.countPlus());
	}
}
