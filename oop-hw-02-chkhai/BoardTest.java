import junit.framework.TestCase;


public class BoardTest extends TestCase {
	Board b;
	Piece pyr1, pyr2, pyr3, pyr4, s, sRotated;
	Piece l1, l2, sq, stick, s1, s2;

	// This shows how to build things in setUp() to re-use
	// across tests.

	// In this case, setUp() makes shapes,
	// and also a 3X6 board, with pyr placed at the bottom,
	// ready to be used by tests.

	protected void setUp() throws Exception {
		b = new Board(3, 6);

		pyr1 = new Piece(Piece.PYRAMID_STR);
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();

		s = new Piece(Piece.S1_STR);
		sRotated = s.computeNextRotation();

		l1 = new Piece(Piece.L1_STR);
		l2 = new Piece(Piece.L2_STR);
		sq = new Piece(Piece.SQUARE_STR);
		stick = new Piece(Piece.STICK_STR);
		s1 = new Piece(Piece.S1_STR);
		s2 = new Piece(Piece.S2_STR);

		b.place(pyr1, 0, 0);
	}

	// Check the basic width/height/max after the one placement
	public void testSample1() {
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(2, b.getColumnHeight(1));
		assertEquals(2, b.getMaxHeight());
		assertEquals(3, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(0, b.getRowWidth(2));
	}

	// Place sRotated into the board, then check some measures
	public void testSample2() {
		b.commit();
		int result = b.place(sRotated, 1, 1);
		assertEquals(Board.PLACE_OK, result);
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(4, b.getColumnHeight(1));
		assertEquals(3, b.getColumnHeight(2));
		assertEquals(4, b.getMaxHeight());
	}

	// Makre  more tests, by putting together longer series of
	// place, clearRows, undo, place ... checking a few col/row/max
	// numbers that the board looks right after the operations.

	public void testToString(){
		b.commit();
		b = new Board(0,0);
		assertEquals("--", b.toString());
		b = new Board(4, 4);
		b.place(sq, 0, 0);
		b.commit();
		b.place(sq, 2, 2);
		b.commit();
		b.place(sq, 0, 2);
		b.commit();
		b.place(sq, 2, 0);
		b.commit();
		assertTrue(b.toString().equals(
				"|++++|\n" +
						"|++++|\n" +
						"|++++|\n" +
						"|++++|\n" +
						"------"
		));
	}

	public void testPlaceBadCases(){
		b.commit();
		assertEquals(Board.PLACE_BAD, b.place(sRotated, 0, 0));
		b.commit();
		assertEquals(Board.PLACE_OUT_BOUNDS, b.place(stick, 0, 3));
		b.commit();
		assertEquals(Board.PLACE_OUT_BOUNDS, b.place(stick, -1, 2));
		b.commit();
		assertEquals(Board.PLACE_OUT_BOUNDS, b.place(stick, 1, -2));
	}

	public void testPlaceAndGetters(){
		assertEquals(true, b.getGrid(5,10));
		assertEquals(6, b.getHeight());
		assertEquals(3, b.getWidth());
		b.commit();
		assertEquals(Board.PLACE_BAD, b.place(stick, 0, 0));
		b.commit();
		assertEquals(Board.PLACE_OK, b.place(stick, 0, 1));
		assertEquals(5, b.getColumnHeight(0));
		assertEquals(2, b.getColumnHeight(1));
		assertEquals(1, b.getColumnHeight(2));
		assertEquals(5, b.getMaxHeight());
		assertEquals(3, b.getRowWidth(0));
		b.commit();
		assertEquals(Board.PLACE_ROW_FILLED, b.place(pyr2, 1, 1));
		assertEquals(3, b.getColumnHeight(1));
		assertEquals(4, b.getColumnHeight(2));
		assertEquals(3, b.getRowWidth(1));
		assertEquals(3, b.clearRows());
		b.commit();
		assertEquals(Board.PLACE_OUT_BOUNDS, b.place(l1, 5, -2));
		b.commit();
		l1 = l1.computeNextRotation();
		assertEquals(Board.PLACE_ROW_FILLED, b.place(l1, 0, 2));
		assertEquals(4, b.getMaxHeight());
		assertEquals(3, b.getColumnHeight(0));
		assertEquals(3, b.getColumnHeight(1));
		assertEquals(4, b.getColumnHeight(2));
		assertEquals(3, b.getRowWidth(2));
		assertEquals(1, b.clearRows());
	}

	public void testClear(){
		assertEquals(1, b.clearRows());
		b.commit();
		assertEquals(0, b.getColumnHeight(0));
		assertEquals(0, b.getColumnHeight(0));
		assertEquals(1, b.getColumnHeight(1));
		assertEquals(0, b.clearRows());
		b.commit();
		s1 = s1.computeNextRotation();
		assertEquals(Board.PLACE_OK, b.place(s1, 1, 0));
		assertEquals(3, b.getColumnHeight(1));
		assertEquals(2, b.getColumnHeight(2));
		assertEquals(3, b.getMaxHeight());
		b.commit();
		assertEquals(Board.PLACE_ROW_FILLED, b.place(stick, 0, 0));
		assertEquals(4, b.getColumnHeight(0));
		assertEquals(4, b.getMaxHeight());
		assertEquals(2, b.clearRows());
		b.commit();
		assertEquals(Board.PLACE_ROW_FILLED, b.place(s1, 1, 0));
		assertEquals(2, b.clearRows());
		b.commit();
		assertEquals(Board.PLACE_OK, b.place(pyr2, 1, 0));
		b.commit();
		for(int i = 0; i < 2; i++) l2 = l2.computeNextRotation();
		assertEquals(Board.PLACE_ROW_FILLED, b.place(l2, 0,0));
		assertEquals(3, b.getRowWidth(2));
		assertEquals(3, b.clearRows());
		assertEquals(0, b.getMaxHeight());
		assertEquals(0, b.getRowWidth(0));
		for(int i = 0; i< b.getWidth(); i++) for(int j = 0; j< b.getHeight(); j++) assertEquals(false, b.getGrid(i,j));
		b.commit();
		l1 = l1.computeNextRotation().computeNextRotation();
		assertEquals(Board.PLACE_OK, b.place(sq,0,0));
		assertEquals(0, b.clearRows());
		b.commit();
		assertEquals(Board.PLACE_ROW_FILLED, b.place(l1,1,0));
		assertEquals(3, b.getMaxHeight());
		assertEquals(2, b.getColumnHeight(0));
		assertEquals(2, b.clearRows());
		b.commit();
		assertEquals(2, b.getRowWidth(0));
		assertEquals(0, b.getRowWidth(1));
	}

	public void testDropHeight(){
		assertEquals(2, b.dropHeight(sq, 0));
		assertEquals(1, b.dropHeight(stick, 2));
		assertEquals(1, b.clearRows());
		b.commit();
		s1 = s1.computeNextRotation();
		assertEquals(Board.PLACE_OK, b.place(s1, 1, 0));
		assertEquals(3, b.dropHeight(sq, 0));
		assertEquals(0, b.dropHeight(stick, 0));
		l2 = l2.computeNextRotation();
		assertEquals(1, b.dropHeight(l2, 2));
		assertEquals(0, b.dropHeight(l2, -7));
		b.commit();
	}

	public void testUndo(){
		b.undo();
		assertTrue(b.toString().equals(
				"|   |\n" +
				"|   |\n" +
				"|   |\n" +
				"|   |\n" +
				"|   |\n" +
				"|   |\n" +
				"-----"
		));
		b.place(pyr1, 0, 0);
		b.clearRows();
		b.commit();
		b.place(s1.computeNextRotation(), 1, 0);
		b.commit();
		b.place(stick, 0, 0);
		b.undo();
		assertTrue(b.toString().equals(
				"|   |\n" +
				"|   |\n" +
				"|   |\n" +
				"| + |\n" +
				"| ++|\n" +
				"| ++|\n" +
				"-----"
		));
		b.place(stick, 0, 0);
		b.commit();
		b.clearRows();
		b.undo();
		assertTrue(b.toString().equals(
				"|   |\n" +
				"|   |\n" +
				"|+  |\n" +
				"|++ |\n" +
				"|+++|\n" +
				"|+++|\n" +
				"-----"
		));
	}

	public void testCommit(){
		b.commit();
		assertTrue(b.toString().equals(
				"|   |\n" +
						"|   |\n" +
						"|   |\n" +
						"|   |\n" +
						"| + |\n" +
						"|+++|\n" +
						"-----"
		));
		b.clearRows();
		b.commit();
		assertTrue(b.toString().equals(
				"|   |\n" +
						"|   |\n" +
						"|   |\n" +
						"|   |\n" +
						"|   |\n" +
						"| + |\n" +
						"-----"
		));
		b.place(pyr2,1,0);
		b.commit();
		b.place(l2.computeNextRotation().computeNextRotation(), 0, 0);
		b.commit();
		assertTrue(b.toString().equals(
				"|   |\n" +
				"|   |\n" +
				"|   |\n" +
				"|+++|\n" +
				"|+++|\n" +
				"|+++|\n" +
				"-----"
		));
		b.clearRows();
		assertTrue(b.toString().equals(
				"|   |\n" +
						"|   |\n" +
						"|   |\n" +
						"|   |\n" +
						"|   |\n" +
						"|   |\n" +
						"-----"
		));
		b.undo();
		assertTrue(b.toString().equals(
				"|   |\n" +
						"|   |\n" +
						"|   |\n" +
						"|+++|\n" +
						"|+++|\n" +
						"|+++|\n" +
						"-----"
		));
		b.commit();
	}
}