import junit.framework.TestCase;

import java.util.Vector;

public class BankTest extends TestCase {

    public void test_small() throws InterruptedException {
        int numWorkers = 10;
        Bank b = new Bank(numWorkers);
        b.processFile("small.txt", numWorkers);
        Vector<Account> accs = b.getAccs();
        for(int i = 0; i < Bank.ACCOUNTS; i++) {
            int mon = accs.get(i).getBalance();
            if(i % 2 == 0) assertEquals(999, mon);
            else assertEquals(1001, mon);
        }
    }

    public void test_fivek() throws InterruptedException {
        int numWorkers = 20;
        Bank b = new Bank(numWorkers);
        b.processFile("5k.txt", numWorkers);
        Vector<Account> accs = b.getAccs();
        for(Account a : accs) {
            assertEquals(1000, a.getBalance());
        }
    }

    public void test_hundredk() throws InterruptedException {
        int numWorkers = 50;
        Bank b = new Bank(numWorkers);
        b.processFile("100k.txt", numWorkers);
        Vector<Account> accs = b.getAccs();
        for(Account a : accs) {
            assertEquals(1000, a.getBalance());
        }
    }

    public void test_main() throws InterruptedException {
        String[] args = new String[2];
        args[0] = "small.txt";
        args[1] = "5";
        Bank.main(args);
        for(int i = 0; i < Bank.ACCOUNTS; i++) {
            if(i %2 ==0) assertTrue(Bank.getOutput().contains("id=" + i + ", balance=999, transactions=1"));
            else assertTrue(Bank.getOutput().contains("id=" + i + ", balance=1001, transactions=1"));

        }
        assertEquals("from:-1 to:0 amt:0", new Transaction(-1, 0,0).toString());
    }
}
