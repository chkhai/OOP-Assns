import java.util.HashSet;
import java.util.Set;

// CS108 HW1 -- String static methods

public class StringCode {

	/**
	 * Given a string, returns the length of the largest run.
	 * A a run is a series of adajcent chars that are the same.
	 * @param str
	 * @return max run length
	 */
	public static int maxRun(String str) {
		if (str.isEmpty()) return 0; // YOUR CODE HERE
		int ans = 0;
		int cnt = 1;
		int curr = str.charAt(0);
		for (int i = 1; i < str.length(); i++) {
			if (str.charAt(i) != curr) {
				ans = Math.max(ans, cnt);
				curr = str.charAt(i);
				cnt = 1;
			}else cnt++;
		}
		return ans;
	}

	
	/**
	 * Given a string, for each digit in the original string,
	 * replaces the digit with that many occurrences of the character
	 * following. So the string "a3tx2z" yields "attttxzzz".
	 * @param str
	 * @return blown up string
	 */
	public static String blowup(String str) {
		if (str.isEmpty()) return "";
//		return null; // YOUR CODE HERE
		String res = "";
		for(int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c >= '0' && c <= '9') {
				if (i + 1 < str.length()) {
					int num = c - '0';

					for (int j = 0; j < num; j++) res += str.charAt(i + 1);
				}
			} else {
				res += c;
			}
		}
		return res;
	}
	
	/**
	 * Given 2 strings, consider all the substrings within them
	 * of length len. Returns true if there are any such substrings
	 * which appear in both strings.
	 * Compute this in linear time using a HashSet. Len will be 1 or more.
	 */
	public static boolean stringIntersect(String a, String b, int len) {
		if(a.isEmpty() || b.isEmpty()) return false;
		HashSet<String> set = new HashSet<>();
		int iter_num_1 = a.length() - len + 1;
		for (int i = 0; i < iter_num_1; i++) {
//			System.out.println("Adding to set: " + sub);
			set.add(a.substring(i, i + len));
		}
		int iter_num_2 = b.length() - len + 1;
		for (int i = 0; i < iter_num_2; i++) {
//			System.out.println("Checking substring: " + b.substring(i, i + len));
			if(set.contains(b.substring(i, i + len))) return true;
		}

		return false; // YOUR CODE HERE
	}
}
