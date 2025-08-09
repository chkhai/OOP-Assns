package store_package;

import java.sql.*;
import java.util.Vector;

public class ProductCatalog {

    private final String DATABASE_URL = "jdbc:mysql://localhost:3306/db";
    private final Connection con;
    private Statement stmt;
    private ResultSet rs;

    public ProductCatalog() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(DATABASE_URL, "root", "root");
        stmt = con.createStatement();
        stmt.execute("USE db;");
    }

    public Vector<Product> executeAllProductsQuery() throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM products;";
        stmt = con.createStatement();
        rs =  stmt.executeQuery(query);
        return constructVector(rs);
    }

    private Vector<Product> constructVector(ResultSet rs) throws SQLException {
        Vector<Product> ans = new Vector<>();
        for (;rs.next();) {
            String id = rs.getString(1);
            String name = rs.getString(2);
            String img = rs.getString(3);
            double p = rs.getDouble(4);
            Product curr = new Product(id, name, img, p);
            ans.add(curr);
        }
        return ans;
    }

    public Product getProduct(String id) throws SQLException, ClassNotFoundException {
        String select = "SELECT * FROM products WHERE productid = ?";
        PreparedStatement pstmt = con.prepareStatement(select);
        pstmt.setString(1, id);
        rs = pstmt.executeQuery();
        if(!rs.next()) return null;
        return new Product(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4));
    }
}
