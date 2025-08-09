import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store_package.Product;
import store_package.ProductCatalog;

import java.sql.SQLException;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

public class ProductCatalogTest {
    private ProductCatalog pc;
    private Vector<Product> v;
    private Product p;

    @BeforeEach
    public void setUp() throws SQLException, ClassNotFoundException {
        pc = new ProductCatalog();
        v = pc.executeAllProductsQuery();
    }

    @Test
    public void testOne(){
        assertEquals(14, v.size());
        assertEquals("HC", v.get(0).getId());
        assertEquals("Classic Hoodie", v.get(0).getName());
        assertEquals(40.0, v.get(0).getPrice());
        assertEquals("HLE",  v.get(1).getId());
        assertEquals("Limited Edition Hood", v.get(1).getName());
    }

    @Test
    public void testTwo() throws SQLException, ClassNotFoundException {
        p = pc.getProduct("TLKo");
        assertEquals(17.95, p.getPrice());
        assertEquals("Korean Tee", p.getName());
        assertEquals("KoreanTShirt.jpg", p.getImg());
        p = pc.getProduct("TS");
        assertEquals("Seal Tee", p.getName());
        assertEquals("SealTShirt.jpg", p.getImg());
        assertEquals(19.95, p.getPrice());
    }
}
