import junit.framework.TestCase;

import java.sql.*;
import java.util.Vector;

public class MetropolisDaoAndJTableTest extends TestCase {

    private MetropolisDao m_dao;
    private Connection con;
    private final String h2_db_str = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    private final String db_username = "sa";
    private final String db_password = "";
    private MetropolisJTable m_table;

    public void setUp() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(h2_db_str, db_username, db_password);
        m_dao = new MetropolisDao(con);
//              System.out.println("Connection Done!");
        Statement statement = con.createStatement();
        statement.execute("DROP TABLE IF EXISTS metropolises;");
        statement.execute("CREATE TABLE metropolises\n" +
                "(\n" +
                "    metropolis VARCHAR(255) NOT NULL,\n" +
                "    continent  VARCHAR(255),\n" +
                "    population INT NOT NULL DEFAULT 0\n" +
                ");");


//        m_dao.addMetropolis("Tbilisi", "Europe", 1200000);
//        m_dao.addMetropolis("Paris", "Europe", 2000000);
//        m_dao.addMetropolis("Washington", "North America", 45000000);
//        m_dao.addMetropolis("Rio De Janeiro", "South America", 6000000);
//        m_dao.addMetropolis("Beijing", "Asia", 22000000);
//        m_dao.addMetropolis("Mumbai", "Asia", 12000000);
//        System.out.println("success");
        m_table = new MetropolisJTable(con);
        m_table.add("Tbilisi", "Europe", "1200000");
        m_table.add("Paris", "Europe", "2000000");
        m_table.add("Washington", "North America", "45000000");
        m_table.add("Rio De Janeiro", "South America", "6000000");
        m_table.add("Beijing", "Asia", "22000000");
        m_table.add("Mumbai", "Asia", "12000000");
    }

    public void test_cities_are_added() throws SQLException {
        //checking that all metropolises where added
        //assertEquals(6, m_dao.get_all_metropolises().size());
        Vector<Metropolises> v = m_dao.searchMetropolis("", "","", false, true);
        assertEquals(6, v.size());
        Metropolises m = v.get(0);
        assertTrue(m.getContinent().equals("Europe"));
        assertTrue(m.getMetropolis().equals("Tbilisi"));
        assertEquals(1200000, Integer.parseInt(m.getPopulation()));
        m = v.get(1);
        assertTrue(m.getContinent().equals("Europe"));
        assertTrue(m.getMetropolis().equals("Paris"));
        assertEquals(2000000, Integer.parseInt(m.getPopulation()));
        m = v.get(2);
        assertTrue(m.getContinent().equals("North America"));
        assertTrue(m.getMetropolis().equals("Washington"));
        assertEquals(45000000, Integer.parseInt(m.getPopulation()));
        m = v.get(3);
        assertTrue(m.getContinent().equals("South America"));
        assertTrue(m.getMetropolis().equals("Rio De Janeiro"));
        assertEquals(6000000, Integer.parseInt(m.getPopulation()));
        m = v.get(4);
        assertTrue(m.getContinent().equals("Asia"));
        assertTrue(m.getMetropolis().equals("Beijing"));
        assertEquals(22000000, Integer.parseInt(m.getPopulation()));
        m= v.get(5);
        assertTrue(m.getContinent().equals("Asia"));
        assertTrue(m.getMetropolis().equals("Mumbai"));
        assertEquals(12000000, Integer.parseInt(m.getPopulation()));
    }

    public void test_query_1() throws SQLException {
        Vector<Metropolises> v = m_dao.searchMetropolis("i","si","", false, false);
        assertEquals(2, v.size());
        assertTrue(v.get(0).getContinent().equals("Asia"));
        assertTrue(v.get(0).getMetropolis().equals("Beijing"));
        assertEquals(22000000, Integer.parseInt(v.get(0).getPopulation()));
        assertTrue(v.get(1).getContinent().equals("Asia"));
        assertTrue(v.get(1).getMetropolis().equals("Mumbai"));
        assertEquals(12000000, Integer.parseInt(v.get(1).getPopulation()));
    }

    public void test_query_2() throws SQLException {
        Vector<Metropolises> v = m_dao.searchMetropolis("", "", "10000000", true, false);
        assertEquals(3, v.size());
        assertTrue(v.get(0).getMetropolis().equals("Tbilisi"));
        assertTrue(v.get(1).getMetropolis().equals("Paris"));
        assertTrue(v.get(2).getMetropolis().equals("Rio De Janeiro"));
    }

    public void test_query_3() throws SQLException {
        Vector<Metropolises> v = m_dao.searchMetropolis("e%j", "s%i", "1000", false, false);
        assertEquals(1, v.size());
        Metropolises m = v.getFirst();
        assertTrue(m.getContinent().equals("Asia"));
        assertTrue(m.getMetropolis().equals("Beijing"));
        assertEquals(22000000, Integer.parseInt(m.getPopulation()));
    }

    public void test_query_4() throws SQLException {
        Vector<Metropolises> v = m_dao.searchMetropolis("","","7000000", false, true);
        assertEquals(3, v.size());
        assertTrue(v.get(0).getMetropolis().equals("Washington"));
        assertTrue(v.get(1).getMetropolis().equals("Beijing"));
        assertTrue(v.get(2).getMetropolis().equals("Mumbai"));
    }

    public void test_query_5() throws SQLException {
        Vector<Metropolises> v = m_dao.searchMetropolis("", "%er%ca", "", false, false);
        assertEquals(2,v.size());
        assertTrue(v.get(0).getMetropolis().equals("Washington"));
        assertTrue(v.get(1).getMetropolis().equals("Rio De Janeiro"));
    }

    public void test_query_6() throws SQLException {
        Vector<Metropolises> v = m_dao.searchMetropolis("%%i%s", "%E%pe", "", false, false);
        assertEquals(2, v.size());
        assertTrue(v.get(0).getMetropolis().equals("Tbilisi"));
        assertTrue(v.get(1).getMetropolis().equals("Paris"));
    }

    public void test_query_empty() throws SQLException {
        Vector<Metropolises> v = m_dao.searchMetropolis("%k%p%ad", "", "", false, false);
        assertEquals(0, v.size());
    }

    public void test_table_1(){
        assertTrue(m_table.getColumnName(0).equals("Metropolis"));
        assertTrue(m_table.getColumnName(1).equals("Continent"));
        assertTrue(m_table.getColumnName(2).equals("Population"));
        assertEquals(3, m_table.getColumnCount());
        assertEquals(6, m_table.getRowCount());
        assertNull(m_table.getValueAt(-1, -1));
        assertNull(m_table.getColumnName(9));
    }

    public void test_table_2() throws SQLException {
        m_table.search("","","", false, false);
        Vector<Metropolises> v = m_table.get_all_metropolises();
        assertEquals(6, v.size());
        assertEquals("Tbilisi", m_table.getValueAt(0,0));
        assertEquals("Europe", m_table.getValueAt(0,1));
        assertEquals("1200000", m_table.getValueAt(0,2));
        assertEquals("Paris", m_table.getValueAt(1,0));
        assertEquals("Europe", m_table.getValueAt(1,1));
        assertNull(m_table.getValueAt(1, 29));
    }
}
