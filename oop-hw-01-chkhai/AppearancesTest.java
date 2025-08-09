import junit.framework.TestCase;

import java.util.*;

public class AppearancesTest extends TestCase {
	// utility -- converts a string to a list with one
	// elem for each char.
	private List<String> stringToList(String s) {
		List<String> list = new ArrayList<String>();
		for (int i=0; i<s.length(); i++) {
			list.add(String.valueOf(s.charAt(i)));
			// note: String.valueOf() converts lots of things to string form
		}
		return list;
	}
	
	public void testSameCount1() {
		List<String> a = stringToList("abbccc");
		List<String> b = stringToList("cccbba");
		assertEquals(3, Appearances.sameCount(a, b));
	}
	
	public void testSameCount2() {
		// basic List<Integer> cases
		List<Integer> a = Arrays.asList(1, 2, 3, 1, 2, 3, 5);
		assertEquals(1, Appearances.sameCount(a, Arrays.asList(1, 9, 9, 1)));
		assertEquals(2, Appearances.sameCount(a, Arrays.asList(1, 3, 3, 1)));
		assertEquals(1, Appearances.sameCount(a, Arrays.asList(1, 3, 3, 1, 1)));
	}
	
	// Add more tests
	public void testSameCount3() {
		List<String> a = stringToList("ermalo_magradze");
		List<String> b =  stringToList("goat");
		assertEquals(2, Appearances.sameCount(a, b)); // g o
		a = stringToList("visual studio");
		b =  stringToList("intelij");
		assertEquals(3, Appearances.sameCount(a, b)); // i t l
	}

	public void testSameCount4() {
		List<Integer> a = Arrays.asList(100, 100, 29, 127, 2, 5, 6);
		assertEquals(0, Appearances.sameCount(a, Arrays.asList(7, 9, 3, 1)));
		assertEquals(1, Appearances.sameCount(a, Arrays.asList(7, 29, 47, 3)));
		assertEquals(2, Appearances.sameCount(a, Arrays.asList(2, 7, 29, 47, 3)));
		assertEquals(3,  Appearances.sameCount(a, Arrays.asList(2, 7, 29, 47, 6)));
	}

	public void testEmpty(){
		List<String> a = stringToList("");
		List<String> b = stringToList("ascas");
		assertEquals(0, Appearances.sameCount(a, b));
		a = stringToList("blabla");
		b = stringToList("");
		assertEquals(0, Appearances.sameCount(a, b));
		a = stringToList("");
		b = stringToList("");
		assertEquals(0, Appearances.sameCount(a, b));
	}
}
