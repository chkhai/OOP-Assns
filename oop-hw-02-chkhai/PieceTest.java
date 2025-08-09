import junit.framework.TestCase;

import java.util.*;

/*
  Unit test for Piece class -- starter shell.
 */
public class PieceTest extends TestCase {
	// You can create data to be used in the your
	// test cases like this. For each run of a test method,
	// a new PieceTest object is created and setUp() is called
	// automatically by JUnit.
	// For example, the code below sets up some
	// pyramid and s pieces in instance variables
	// that can be used in tests.
	private Piece pyr1, pyr2, pyr3, pyr4;
	private Piece s, sRotated;

	protected void setUp() throws Exception {
		super.setUp();

		pyr1 = new Piece(Piece.PYRAMID_STR);
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();

		s = new Piece(Piece.S1_STR);
		sRotated = s.computeNextRotation();
	}

	public void testGetters(){
		assertEquals(3, pyr1.getWidth());
		assertEquals(2, pyr1.getHeight());
		assertEquals(3, s.getWidth());
		assertEquals(2, s.getHeight());
		Piece l =  new Piece(Piece.L1_STR);
		assertEquals(2, l.getWidth());
		assertEquals(3, l.getHeight());
		l = new Piece(Piece.L2_STR);
		assertEquals(2, l.getWidth());
		assertEquals(3, l.getHeight());
		l = new Piece(Piece.STICK_STR);
		assertEquals(1, l.getWidth());
		assertEquals(4, l.getHeight());
		l = new Piece(Piece.SQUARE_STR);
		assertEquals(2, l.getWidth());
		assertEquals(2, l.getHeight());
		TPoint[] arr = l.getBody();
		TPoint[] dum = new TPoint[4];
		TPoint d1= new TPoint(0,0);
		dum[0] = d1;
		TPoint d2= new TPoint(0,1);
		dum[1] = d2;
		TPoint d3= new TPoint(1,0);
		dum[2] = d3;
		TPoint d4= new TPoint(1,1);
		dum[3] = d4;
		assertTrue(Arrays.equals(arr,dum));
	}

	public void testEquals1(){
		Piece stick = new Piece(Piece.STICK_STR);
		assertTrue(stick.equals(new Piece("0 0  0 1  0 3  0 2")));
		assertTrue(stick.equals(new Piece("0 0  0 3  0 2  0 1")));
		assertTrue(stick.equals(new Piece("0 3  0 0  0 1  0 2")));
		assertTrue(stick.equals(new Piece("0 0  0 2  0 1  0 3")));
		assertTrue(stick.equals(new Piece("0 0  0 3  0 1  0 2")));
		assertTrue(stick.equals(new Piece("0 0  0 1  0 3  0 2")));
		assertTrue(stick.equals(new Piece("0 3  0 2  0 1  0 0")));
		Piece s = new Piece(Piece.S1_STR);
		assertFalse(s.equals(sRotated));
		sRotated = sRotated.computeNextRotation();
		assertTrue(sRotated.equals(s));
	}

	public void testEquals2(){
		Piece p = new Piece("1 0  0 0  2 0  1 1"); //pyramid but different order
		assertTrue(p.equals(pyr1));
		p = p.computeNextRotation();
		assertFalse(p.equals(pyr1));
		assertTrue(p.equals(new Piece("1 0  1 1  1 2  0 1")));
		p = p.computeNextRotation();
		assertFalse(p.equals(pyr1));
		assertTrue(p.equals(new Piece("0 1  1 1  2 1  1 0")));
		p = p.computeNextRotation();
		assertFalse(p.equals(pyr1));
		assertTrue(p.equals(new Piece("0 2  1 1  0 0  0 1")));
		p = p.computeNextRotation();
		assertTrue(p.equals(pyr1));// came back to pyr1 form after 4 rotates
	}

	public void testComputeNextRotation(){
		Piece l =  new Piece(Piece.L1_STR);
		assertTrue(l.computeNextRotation().equals(new Piece("0 0  1 0  2 0  2 1")));
		l = new Piece(Piece.STICK_STR);
		assertTrue(l.computeNextRotation().equals(new Piece("0 0  1 0  2 0  3 0")));
		l = new Piece(Piece.SQUARE_STR);
		assertTrue(l.computeNextRotation().equals(l));
		l = new Piece(Piece.S1_STR);
		assertTrue(l.computeNextRotation().equals(new Piece("1 0  1 1  0 1  0 2")));
		l = l.computeNextRotation();
		l = l.computeNextRotation();
		assertTrue(new Piece(Piece.S1_STR).equals(l));
		l = new Piece(Piece.L1_STR);
		for(int i = 0; i  < 4; i++)	l = l.computeNextRotation();
		assertTrue(new Piece(Piece.L1_STR).equals(l));
	}

	public void testGetPiecesAndFastRotation1(){
		//tested symmetric pieces
		Piece[] p = Piece.getPieces();
		Piece stick = p[Piece.STICK];
		Piece stick_one_rot = stick.fastRotation();
		Piece stick_two_rot = stick_one_rot.fastRotation();

		assertTrue(stick_one_rot.equals(new Piece("0 0  1 0  2 0  3 0")));
		assertFalse(stick.equals(stick_one_rot));
		assertTrue(stick.equals(stick_two_rot));

		Piece s = p[Piece.S1];
		Piece s_one_rot = s.fastRotation();
		Piece s_two_rot = s_one_rot.fastRotation();

		assertTrue(s_one_rot.equals(new Piece("0 1  0 2  1 1  1 0")));
		assertFalse(s.equals(s_one_rot));
		assertTrue(s.equals(s_two_rot));

		Piece square = p[Piece.SQUARE];
		assertTrue(square.equals(square.fastRotation()));
	}
	// Here are some sample tests to get you started

	public void testGetPiecesAndFastRotation2(){
		Piece[] p = Piece.getPieces();
		Piece l = p[Piece.L1];
		Piece l_one_rot = l.fastRotation();
		Piece l_two_rot = l_one_rot.fastRotation();
		Piece l_three_rot = l_two_rot.fastRotation();
		assertTrue(l_one_rot.equals(new Piece("0 0  1 0  2 0  2 1")));
		assertTrue(l_two_rot.equals(new Piece("0 2  1 0  1 1  1 2")));
		assertTrue(l_three_rot.equals(new Piece("0 0  0 1  1 1  2 1")));
		assertTrue(l.equals(l_three_rot.fastRotation())); //four rotate

		pyr1 = p[Piece.PYRAMID];
		pyr2 = pyr1.fastRotation();
		pyr3 = pyr2.fastRotation();
		pyr4 = pyr3.fastRotation();

		assertTrue(pyr2.equals(new Piece("0 1  1 0  1 1  1 2")));
		assertTrue(pyr3.equals(new Piece("0 1  1 1  2 1  1 0")));
		assertTrue(pyr4.equals(new Piece("0 0  0 1  0 2  1 1")));
		assertTrue(pyr4.fastRotation().equals(pyr1));
	}
	
	public void testSampleSize() {
		// Check size of pyr piece
		assertEquals(3, pyr1.getWidth());
		assertEquals(2, pyr1.getHeight());
		
		// Now try after rotation
		// Effectively we're testing size and rotation code here
		assertEquals(2, pyr2.getWidth());
		assertEquals(3, pyr2.getHeight());
		
		// Now try with some other piece, made a different way
		Piece l = new Piece(Piece.STICK_STR);
		assertEquals(1, l.getWidth());
		assertEquals(4, l.getHeight());
	}
	
	
	// Test the skirt returned by a few pieces
	public void testSampleSkirt() {
		// Note must use assertTrue(Arrays.equals(... as plain .equals does not work
		// right for arrays.
		assertTrue(Arrays.equals(new int[] {0, 0, 0}, pyr1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0, 1}, pyr3.getSkirt()));
		
		assertTrue(Arrays.equals(new int[] {0, 0, 1}, s.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0}, sRotated.getSkirt()));
	}
	
	
}
