// Account.java

/*
 Simple, thread-safe Account class encapsulates
 a balance and a transaction count.
*/
public class Account {
	private int id;
	private int balance;
	private int transactions;
	
	// It may work out to be handy for the account to
	// have a pointer to its Bank.
	// (a suggestion, not a requirement)
	private Bank bank;  
	
	public Account(Bank bank, int id, int balance) {
		this.bank = bank;
		this.id = id;
		this.balance = balance;
		transactions = 0;
	}

	synchronized  public void deposit(int amount) {
		transactions++;
		balance += amount;
	}

	@Override
	public String toString() {
		return "Account{" +
				"id=" + id +
				", balance=" + balance +
				", transactions=" + transactions +
				'}';
	}

	public int getBalance() {
		return balance;
	}
}
