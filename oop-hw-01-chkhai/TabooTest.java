// TabooTest.java
// Taboo class tests -- nothing provided.

import java.util.*;

import junit.framework.TestCase;

public class TabooTest extends TestCase {

    public void testNoFollowEmpty(){
        List<Character> list = Arrays.asList('a', 'b', 'c');
        Taboo<Character> taboo = new Taboo<>(list);
        assertEquals(new HashSet<>(), taboo.noFollow('d'));
    }

    public void testNoFollow1() {
        List<Character> list = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g');
        Taboo<Character> taboo = new Taboo<>(list);

        assertEquals(new HashSet<>(List.of('b')) , taboo.noFollow('a'));
        assertEquals(new HashSet<>(List.of('c')) , taboo.noFollow('b'));
        assertEquals(new HashSet<>(List.of('d')) , taboo.noFollow('c'));
        assertEquals(new HashSet<>(List.of('e')) , taboo.noFollow('d'));
        assertEquals(new HashSet<>(List.of('f')) , taboo.noFollow('e'));
        assertEquals(new HashSet<>(List.of('g')) , taboo.noFollow('f'));
    }

    public void testNoFollow2() {
        List<Character> list = Arrays.asList('a', 'm', 'c', 'a', 'e', 'b', 'd', 'e', 'f', 'm', 'a');
        Taboo<Character> taboo = new Taboo<>(list);
        assertEquals(new HashSet<>(List.of('m', 'e')), taboo.noFollow('a'));
        assertEquals(new HashSet<>(List.of('c', 'a')), taboo.noFollow('m'));
        assertEquals(new HashSet<>(List.of('a')), taboo.noFollow('c'));
        assertEquals(new HashSet<>(List.of('b', 'f')), taboo.noFollow('e'));
        assertEquals(new HashSet<>(List.of('d')), taboo.noFollow('b'));
        assertEquals(new HashSet<>(List.of('e')), taboo.noFollow('d'));
        assertEquals(new HashSet<>(List.of('m')), taboo.noFollow('f'));
    }

    public void testNoFollow3(){
        List<Character> list = Arrays.asList('4', '1',  '2', null, '3', '6', '2', '4', null, '2', '5', null, '7', '6', '8');
        Taboo<Character> taboo = new Taboo<>(list);

        assertEquals(new HashSet<>(List.of('2')), taboo.noFollow('1'));
        assertEquals(new HashSet<>(List.of('4', '5')), taboo.noFollow('2'));
        assertEquals(new HashSet<>(List.of('6')), taboo.noFollow('3'));
        assertEquals(new HashSet<>(List.of('1')), taboo.noFollow('4'));
        assertEquals(new HashSet<>(), taboo.noFollow('5'));
        assertEquals(new HashSet<>(List.of('2', '8')), taboo.noFollow('6'));
        assertEquals(new HashSet<>(List.of('6')),taboo.noFollow('7'));
        assertEquals(new HashSet<>(), taboo.noFollow('8'));
    }

    public void testReduceEmpty(){
        List<Character> list = List.of();
        Taboo<Character> taboo = new Taboo<>(list);
        List<Character> input = new ArrayList<>(List.of('a', 'c', 'b', 'x', 'c', 'a'));
        taboo.reduce(input);

        assertEquals(List.of('a', 'c', 'b', 'x', 'c', 'a'), input);
    }

    public void testReduce1(){
        List<Character> rules = List.of('a', 'c', 'a', 'b');
        Taboo<Character> taboo = new Taboo<>(rules);

        List<Character> input = new ArrayList<>(List.of('a', 'c', 'b', 'x', 'c', 'a'));
        taboo.reduce(input);

        assertEquals(List.of('a', 'x', 'c'), input);
    }

    public void testReduce2(){
        List<Character> rules = List.of('a', 'b', 'c', 'd', 'e', 'f', 'g');
        Taboo<Character> taboo = new Taboo<>(rules);

        List<Character> input = new ArrayList<>(List.of('b', 'c', 'a', 'g', 'f', 'g', 'd', 'e', 'a', 'b'));
        taboo.reduce(input);
        assertEquals(List.of('b', 'a', 'g', 'f', 'd', 'a'), input);
    }

    public void testReduce3(){
        List<Character> rules = List.of('d' ,'a', 'b', 'a', 'f', 'm', 'b', 'd', 'a', 'c');
        Taboo<Character> taboo = new Taboo<>(rules);

        List<Character> input = new ArrayList<>(List.of('f', 'k', 'a', 'b', 'c', 'm', 'c', 'b', 'a'));
        taboo.reduce(input);
        assertEquals(List.of('f', 'k', 'a', 'm', 'c', 'b'), input);
    }
}
