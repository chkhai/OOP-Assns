import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

public class MetropolisFrame extends JFrame {
    private final String DATABASE_URL = "jdbc:mysql://localhost:3306/chkhai";
    private final String USER = "root";
    private final String PASSWORD = "root";
    private JTextField metropolis;
    private JTextField continent;
    private JTextField p;
    private JButton add;
    private JButton search;
    private JComboBox p_combo;
    private JComboBox exact_combo;
    private final MetropolisJTable m_table;

    /**
     * Constructs the main GUI frame, initializes all components, and sets up layout and event listeners.
     */
    public MetropolisFrame() throws SQLException, ClassNotFoundException {
        super("Metropolis Viewer");
        JComponentsInit();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);

        m_table = new MetropolisJTable(connection);
        JTable table = new JTable(m_table);
        JScrollPane scroll = new JScrollPane(table);

        JPanel up_panel = new JPanel();
        up_panel.add(new JLabel("Metropolis:"));
        up_panel.add(metropolis);
        up_panel.add(new JLabel("Continent:"));
        up_panel.add(continent);
        up_panel.add(new JLabel("Population:"));
        up_panel.add(p);

        JPanel right_panel = new JPanel(new BorderLayout());
        JPanel add_search = new JPanel();
        add_search.setLayout(new GridLayout(2, 1, 10, 10));
        add_search.add(add);
        add_search.add(search);
        right_panel.add(add_search, BorderLayout.NORTH);
        JPanel combo_panel = new JPanel();
        combo_panel.setLayout(new BoxLayout(combo_panel, BoxLayout.Y_AXIS));
        combo_panel.setBorder(new TitledBorder("Search Options"));
        p_combo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        exact_combo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        combo_panel.add(p_combo);
        combo_panel.add(Box.createVerticalStrut(5)); // spacing
        combo_panel.add(exact_combo);
        right_panel.add(combo_panel, BorderLayout.CENTER);

        JPanel center_panel = new JPanel(new BorderLayout());
        center_panel.add(scroll, BorderLayout.CENTER);
        center_panel.add(right_panel, BorderLayout.EAST);

        this.add(up_panel, BorderLayout.NORTH);
        this.add(center_panel, BorderLayout.CENTER);
        m_table.search("","","", false, false);
        addListeners();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        this.setVisible(true);
    }

    /**
     * Registers action listeners for the "Add" and "Search" buttons.
     * The "Add" button inserts a new record, and the "Search" button performs a query with selected options.
     */
    private void addListeners() {
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String metr = metropolis.getText();
                String cont = continent.getText();
                String pop = p.getText();
                if(!metr.isEmpty() && !cont.isEmpty() && !pop.isEmpty()) {
                    try {
                        m_table.add(metr, cont, pop);
                        metropolis.setText("");
                        continent.setText("");
                        p.setText("");
                        m_table.search("","","", false, false);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String metr = metropolis.getText();
                String cont = continent.getText();
                String pop = p.getText();
                try {
                    m_table.search(metr, cont, pop,
                            p_combo.getSelectedIndex() == 1, exact_combo.getSelectedIndex() == 0);
                    metropolis.setText("");
                    continent.setText("");
                    p.setText("");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    /**
     * Initializes all Swing components used in the interface,
     * including text fields, buttons, and combo boxes.
     */
    private void JComponentsInit(){
        metropolis = new JTextField(20);
        continent = new JTextField(20);
        p = new JTextField(20);
        add = new JButton("Add");
        search = new JButton("Search");

        Vector<String> v_exact = new Vector<>();
        Vector<String> v_p = new Vector<>();
        v_exact.add("Exact Match");
        v_exact.add("Partial Match");
        v_p.add("Population Larger Than");
        v_p.add("Population Less Than");
        exact_combo = new JComboBox(v_exact);
        p_combo = new JComboBox(v_p);
    }

    /**
     * Launches the application by creating an instance of MetropolisFrame.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        MetropolisFrame frame = new MetropolisFrame();
    }
}
