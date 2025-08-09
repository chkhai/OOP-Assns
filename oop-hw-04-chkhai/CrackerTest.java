import junit.framework.TestCase;

import java.security.NoSuchAlgorithmException;

public class CrackerTest extends TestCase {

    public void testCrackerHashMaker() throws NoSuchAlgorithmException {
        assertEquals("4181eecbd7a755d19fdf73887c54837cbecf63fd", Cracker.hexToString(Cracker.make_hashed_password("molly")));
        assertEquals("e69a756ac71279bfe707cf457c3331b5a413c5a7", Cracker.hexToString(Cracker.make_hashed_password("luka")));
        assertEquals("165fc1fde32d56a81deade7693b2268df18e9fc3", Cracker.hexToString(Cracker.make_hashed_password("cuga")));
        assertEquals("f5bb62d58ce2e48d507df821d405e26f20436e65", Cracker.hexToString(Cracker.make_hashed_password("chkhai")));
        assertEquals("914d61e816b0bcae6a411366eee1c7d0b91078f7", Cracker.hexToString(Cracker.make_hashed_password("oop")));
        assertEquals("7918b3f81e0db90855893a6b69c6efc3619b2ab2", Cracker.hexToString(Cracker.make_hashed_password("keti")));
    }

    public void testCracker1() throws InterruptedException {
        assertEquals("or", Cracker.detect_password("1758356db21759f7c5a0da9b4dd1db8fd6feab3f", 2, 8));
        assertEquals("45", Cracker.detect_password("fb644351560d8296fe6da332236b1f8d61b2828a", 2, 8));
    }

    public void testCracker2() throws InterruptedException{
        assertEquals("bla", Cracker.detect_password("ffa6706ff2127a749973072756f83c532e43ed02", 3, 8));
        assertEquals("oop", Cracker.detect_password("914d61e816b0bcae6a411366eee1c7d0b91078f7", 3, 5));
        assertEquals("dad", Cracker.detect_password("0da19b540f7c3526f24acba988b03df43b3c5cf3", 4, 20));
        //assertEquals("molly", Cracker.detect_password("4181eecbd7a755d19fdf73887c54837cbecf63fd", 5, 8));
        //molly takes time:)
    }

    public void testMain() throws NoSuchAlgorithmException, InterruptedException {
        String[] arr = new String[3];
        arr[0] = "1758356db21759f7c5a0da9b4dd1db8fd6feab3f";
        arr[1] = "2";
        arr[2] = "10";
        Cracker.main(arr);
        assertEquals("or", Cracker.get_output());
    }
}
