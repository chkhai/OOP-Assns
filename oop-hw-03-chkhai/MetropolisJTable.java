import javax.swing.table.AbstractTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

public class MetropolisJTable extends AbstractTableModel {
    private MetropolisDao db;
    private Vector<Metropolises> v;
    Connection connection;

    public MetropolisJTable(Connection con) throws SQLException, ClassNotFoundException {
        this.connection = con;

        db = new MetropolisDao(connection);
        v = db.get_all_metropolises();
    }

    /**
     * Returns the name of a column at a specified index.
     *
     * @param column the column index
     * @return the name of the column ("Metropolis", "Continent", or "Population")
     */
    @Override
    public String getColumnName(int column){
        if(column==0) return "Metropolis";
        else if(column==1) return "Continent";
        else if(column==2) return "Population";
        return null;
    }

    /**
     * Returns the number of rows in the table, which is the number of metropolis entries.
     *
     * @return the row count
     */
    @Override
    public int getRowCount() {
        return v.size();
    }

    /**
     * Returns the number of columns in the table (always 3).
     *
     * @return the column count
     */
    @Override
    public int getColumnCount() {
        return 3;
    }

    /**
     * Returns the value at a specific cell in the table.
     *
     * @param rowIndex the row index
     * @param columnIndex the column index
     * @return the value at the specified cell, or null if indices are out of bounds
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(rowIndex<0 || rowIndex>=v.size()) return null;
        Metropolises m = v.get(rowIndex);
        if(columnIndex==0) return m.getMetropolis();
        if(columnIndex==1) return m.getContinent();
        if(columnIndex==2) return m.getPopulation();
        return null;
    }

    /**
     * Adds a new metropolis to the database and updates the table model.
     *
     * @param metropolis the name of the metropolis
     * @param continent  the continent it belongs to
     * @param p          the population as a string
     */
    public void add(String metropolis, String continent, String p) throws SQLException {
        db.addMetropolis(metropolis, continent, Integer.parseInt(p));
       // v = db.get_all_metropolises();
        fireTableRowsInserted(v.size()-1, v.size()-1);
    }

    /**
     * Searches for metropolis entries based on the given parameters and updates the table.
     *
     * @param metropolis      name to search
     * @param continent       continent to search
     * @param p               population as a string
     * @param smaller_than_p  if true, population should be ≤ p; otherwise, > p
     * @param exact           if true, exact match is required; otherwise, use LIKE
     */
    public void search(String metropolis, String continent, String p,
                       boolean smaller_than_p, boolean exact) throws SQLException {
        v = db.searchMetropolis(metropolis, continent, p, smaller_than_p, exact);
        fireTableDataChanged();
    }

    public Vector<Metropolises> get_all_metropolises() {
        return v;
    }
}
