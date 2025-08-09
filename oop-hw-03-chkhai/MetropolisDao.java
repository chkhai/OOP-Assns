import java.sql.*;
import java.util.Vector;

import static java.lang.Integer.parseInt;

public class MetropolisDao {
    private final String db_name = "chkhai";
    private final String table_name = "metropolises";
    private Connection connection;
    private Vector<Metropolises>  metropolises;
    /**
     * Establishes a connection to the database and initializes the metropolises vector.
     */
    public MetropolisDao(Connection con) throws SQLException {
        this.connection = con;
        metropolises = new Vector<>();
        PreparedStatement statement = connection.prepareStatement("USE " +  db_name + ";");
    }

    /**
     * Adds a new metropolis record to the database and local cache.
     *
     * @param metropolis the name of the metropolis
     * @param continent  the continent it belongs to
     * @param p          the population
     */
    public void addMetropolis(String metropolis, String continent, int p) throws SQLException {
        String command = "INSERT INTO " + table_name + " VALUES (?, ?, ?);";
        PreparedStatement statement = connection.prepareStatement(command);
        statement.setString(1, metropolis);
        statement.setString(2, continent);
        statement.setInt(3, p);
//        statement.setString(3, String.valueOf(Integer.parseInt(String.valueOf(p))));
        statement.executeUpdate();
        metropolises.add(new Metropolises(metropolis, continent, String.valueOf(Integer.parseInt(String.valueOf(p)))));
    }

    /**
     * Searches for metropolis records in the database based on given parameters.
     *
     * @param metropolis      name to search (can be partial)
     * @param continent       continent to search (can be partial)
     * @param p               population value as a string
     * @param smaller_than_p  true if searching for population less than or equal to `p`
     * @param exact           true if exact matches are required for strings
     * @return a vector of matching metropolis records
     */
    public Vector<Metropolises> searchMetropolis(String metropolis, String continent, String p,
                                                 boolean smaller_than_p, boolean exact) throws SQLException {
        String select_all = "SELECT * FROM " + table_name;
        if(metropolis.isEmpty() && continent.isEmpty() && p.isEmpty()) {
            Statement statement = connection.createStatement();
            ResultSet result_set = statement.executeQuery(select_all  + ";");
            return get_v_from_result_set(result_set);
        } //if everythings empty select *
        String rest_command = make_command(metropolis, continent, p, smaller_than_p, exact);
        select_all += rest_command;
//        System.out.println(select_all);
        PreparedStatement statement = connection.prepareStatement(select_all);
        ResultSet result_set = statement.executeQuery();
        return get_v_from_result_set(result_set);
    }

    /**
     * Constructs the SQL WHERE clause based on search parameters.
     *
     * @param metropolis     name to search
     * @param continent      continent to search
     * @param p              population string
     * @param smaller_than_p true for <= population, false for > population
     * @param exact          true for exact string match
     * @return SQL WHERE clause as a string
     */
    private String make_command(String metropolis, String continent, String p, boolean smaller_than_p, boolean exact) {
        String res = "";
        int cond = 0;
        if(!p.isEmpty()){
            cond++;
            res += " WHERE population ";
            if(smaller_than_p) res += "<= " + p;
            else res += "> " + p;
        }
        if(!metropolis.isEmpty()){
            if(cond == 0) res += " WHERE metropolis ";
            else res += " AND metropolis ";
            cond++;
            if(exact) res += "LIKE '" + metropolis + "'";
            else res += "LIKE '%" + metropolis + "%'";
        }
        if(!continent.isEmpty()){
            if(cond == 0) res += " WHERE continent ";
            else res += " AND continent ";
            cond++;
            if(exact) res += "LIKE '" + continent + "'";
            else res += "LIKE '%" + continent + "%'";
        }
        res += ";";
        return res;
    }

    /**
     * Converts a ResultSet into a Vector<Metropolises>.
     *
     * @param result_set the result set from an SQL query
     * @return a vector of Metropolises objects
     */
    private Vector<Metropolises> get_v_from_result_set(ResultSet result_set) throws SQLException {
        Vector<Metropolises> v = new Vector<>();
        for (;result_set.next();) {
            Metropolises curr = new Metropolises(result_set.getString(1),
                    result_set.getString(2), result_set.getString(3));
            v.add(curr);
        }
        return v;
    }

    /**
     * Returns the locally cached list of metropolises added during the session.
     *
     * @return vector of all metropolises currently stored in memory
     */
    public Vector<Metropolises> get_all_metropolises() { return metropolises;   }
}


