import java.util.*;

public class Appearances {
	
	/**
	 * Returns the number of elements that appear the same number
	 * of times in both collections. Static method. (see handout).
	 * @return number of same-appearance elements
	 */
	public static <T> int sameCount(Collection<T> a, Collection<T> b) {
		if(a.size() * b.size() == 0) return 0;
		HashMap<T, Integer> a_occurs = new HashMap<>();
		for(T curr : a){
			if(a_occurs.containsKey(curr)) a_occurs.put(curr, a_occurs.get(curr) + 1);
			else a_occurs.put(curr, 1);
		}
		HashMap<T, Integer> b_occurs = new HashMap<>();
		for(T curr : b){
			if(b_occurs.containsKey(curr)) b_occurs.put(curr, b_occurs.get(curr) + 1);
			else b_occurs.put(curr, 1);
		}
		int cnt = 0;
		for(T  curr : a_occurs.keySet()){
			if(!b_occurs.containsKey(curr)) continue;
			if(a_occurs.get(curr).equals(b_occurs.get(curr))) cnt++;
		}
		return cnt; // YOUR CODE HERE
	}
	
}
