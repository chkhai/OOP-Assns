
/*
 HW1 Taboo problem class.
 Taboo encapsulates some rules about what objects
 may not follow other objects.
 (See handout).
*/

import java.util.*;

public class Taboo<T> {

	private Map<T,HashSet<T>> m;
	/**
	 * Constructs a new Taboo using the given rules (see handout.)
	 * @param rules rules for new Taboo
	 */
	public Taboo(List<T> rules) {
		T curr = null;
		m = new HashMap<>();
		for(T t : rules ) {
			if(curr != null && t != null) {
				m.putIfAbsent(curr, new HashSet<>());
				m.get(curr).add(t);
			}
			curr = t;
		}
	}
	
	/**
	 * Returns the set of elements which should not follow
	 * the given element.
	 * @param elem
	 * @return elements which should not follow the given element
	 */
	public Set<T> noFollow(T elem) {
		if(m.containsKey(elem)) return m.get(elem);
		return new HashSet<T>(); // YOUR CODE HERE
	}
	
	/**
	 * Removes elements from the given list that
	 * violate the rules (see handout).
	 * @param list collection to reduce
	 */
	public void reduce(List<T> list) {
		T prev = null;
		T elem = null;
		Iterator<T> it = list.iterator();
		while(it.hasNext()) {
			elem = it.next();
			if(m.containsKey(prev) && prev != null && m.get(prev).contains(elem)) it.remove();
			else  prev = elem;
		}
	}
}
