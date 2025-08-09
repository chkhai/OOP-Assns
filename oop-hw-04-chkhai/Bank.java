// Bank.java

/*
 Creates a bunch of accounts and uses threads
 to post transactions to the accounts concurrently.
*/

import java.io.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

public class Bank {
	public static final int ACCOUNTS = 20;	 // number of accounts
	private BlockingQueue<Transaction> bq;
	private static final Transaction nullTrans = new Transaction(-1,0,0);
	private static int transaction_cnt = 0;
	private CountDownLatch latch;
	private final Vector<Account> accs;
	private static StringBuilder output = new StringBuilder();


	public Bank(int numWorkers) throws InterruptedException {
		accs = new Vector<>();
		for(int i = 0; i <  ACCOUNTS; i++) {
			Account curr = new Account(this, i, 1000);
			accs.add(curr);
		}
		latch = new CountDownLatch(numWorkers);
		bq = new LinkedBlockingQueue<>();
	}

	public Vector<Account> getAccs() {
		return new Vector<>(accs);
	}

	/*
	 Reads transaction data (from/to/amt) from a file for processing.
	 (provided code)
	 */
	public void readFile(String file) {
			try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			// Use stream tokenizer to get successive words from file
			StreamTokenizer tokenizer = new StreamTokenizer(reader);
			
			while (true) {
				transaction_cnt++;
				int read = tokenizer.nextToken();
				if (read == StreamTokenizer.TT_EOF) break;  // detect EOF
				int from = (int)tokenizer.nval;
				
				tokenizer.nextToken();
				int to = (int)tokenizer.nval;
				
				tokenizer.nextToken();
				int amount = (int)tokenizer.nval;
				
				// Use the from/to/amount
				
				// YOUR CODE HERE
				Transaction t = new Transaction(from, to, amount);
				bq.put(t);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/*
	 Processes one file of transaction data
	 -fork off workers
	 -read file into the buffer
	 -wait for the workers to finish
	*/
	public void processFile(String file, int numWorkers) throws InterruptedException {
		readFile(file);
		for(int i = 0; i < numWorkers; i++) {
			Worker w = new Worker(bq);
			w.start();
		}
		for(int i = 0; i < numWorkers; i++) {
			bq.put(nullTrans);
		}
		latch.await();
		for(Account acc : accs) {
			System.out.println(acc.toString());
			output.append(acc.toString() + "\n");
		}
	}

	public static String getOutput() {
		return output.toString();
	}

	/*
         Looks at commandline args and calls Bank processing.
        */
	public static void main(String[] args) throws InterruptedException {
		// deal with command-lines args
		if (args.length == 0) {
			System.out.println("Args: transaction-file [num-workers [limit]]");
			System.exit(1);
		}
		
		String file = args[0];
		
		int numWorkers = 1;
		if (args.length >= 2) numWorkers = Integer.parseInt(args[1]);
		Bank b = new Bank(numWorkers);
		b.processFile(file, numWorkers);
	}

	private class Worker extends Thread {
		private final BlockingQueue<Transaction> bq;

		public Worker(BlockingQueue<Transaction> bq) {
			this.bq = bq;
		}

		@Override
		public void run() {
			Transaction dummy = nullTrans;
			while(true){
                try {
                    dummy = bq.take();
					if(dummy == nullTrans){
						latch.countDown();
						return;
					}
					int money = dummy.amount;
					int from = dummy.from;
					int to = dummy.to;
					Account src = accs.get(from);
					Account dest = accs.get(to);
					src.deposit(-money);
					dest.deposit(money);
//					latch.countDown();
                } catch (InterruptedException e) {
                    return;
                }
            }
		}
	}
}

