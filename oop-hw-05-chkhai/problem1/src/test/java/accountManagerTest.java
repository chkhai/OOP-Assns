import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import user_package.accountManager;

import static org.junit.Assert.*;

public class accountManagerTest {

    private accountManager am;

    @Before
    public void setUp(){
        am = new accountManager();
    }

    @Test
    public void testOne(){
        assertTrue(am.accountExists("Patrick"));
        assertTrue(am.accountExists("Molly"));
        assertFalse(am.accountExists("Luka"));
        assertFalse(am.accountExists("Alice"));
    }

    @Test
    public void testTwo(){
        assertTrue(am.passwordCheck("Patrick", "1234"));
        assertTrue(am.passwordCheck("Molly", "FloPup"));
        assertFalse(am.passwordCheck("Luka", "1234"));
        assertFalse(am.passwordCheck("Alice", "anvkdjnajkcnv"));
    }

    @Test
    public void testThree(){
        am.createAccount("Luka", "lukito");
        assertTrue(am.accountExists("Luka"));
        assertFalse(am.passwordCheck("Luka", "1234"));
        assertTrue(am.passwordCheck("Luka", "lukito"));
        am.createAccount("Patrick", "patrika");
        assertTrue(am.accountExists("Patrick"));
        assertTrue(am.passwordCheck("Patrick", "1234"));
        assertFalse(am.passwordCheck("Patrick", "patrika"));
    }

}
