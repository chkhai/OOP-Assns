// Cracker.java
/*
 Generates SHA hashes of short strings in parallel.
*/

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

public class Cracker {
	// Array of chars used to produce strings
	public static final char[] CHARS = "abcdefghijklmnopqrstuvwxyz0123456789.,-!".toCharArray();
	private static CountDownLatch latch;
	private static final int chars_size = CHARS.length;
	private static String result = "";
	private static StringBuilder output = new StringBuilder();
	
	/*
	 Given a byte[] array, produces a hex String,
	 such as "234a6f". with 2 chars for each byte in the array.
	 (provided code)
	*/
	public static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
	
	/*
	 Given a string of hex byte values such as "24a26f", creates
	 a byte[] array of those values, one byte value -128..127
	 for each 2 chars.
	 (provided code)
	*/
	public static byte[] hexToArray(String hex) {
		byte[] result = new byte[hex.length()/2];
		for (int i=0; i<hex.length(); i+=2) {
			result[i/2] = (byte) Integer.parseInt(hex.substring(i, i+2), 16);
		}
		return result;
	}
	
	
	
	public static void main(String[] args) throws NoSuchAlgorithmException, InterruptedException {
		if(args.length == 0) System.exit(1);
		if (args.length < 2) {
//			System.out.println("Args: target length [workers]");
			System.out.println(hexToString(make_hashed_password(args[0])));
			System.exit(1);
		}
		String targ = args[0];
		int len = Integer.parseInt(args[1]);
		int n_threads = 1;
		n_threads = Integer.parseInt(args[2]);
		String users_pass = detect_password(targ, len, n_threads);
		System.out.println(users_pass);
		output.append(users_pass);
	}

	public static String get_output(){
		return output.toString();
	}

	public static String detect_password(String targ, int len, int n_threads) throws InterruptedException {
		latch = new CountDownLatch(n_threads);
		int start_idx = 0;
		int end_idx;
		int num_in_each_block = chars_size / n_threads;
		for(int i = 0; i < n_threads; i++, start_idx = end_idx + 1) {
			end_idx = start_idx + num_in_each_block - 1;
			if(i ==  n_threads - 1) end_idx += chars_size % n_threads;
			Worker w =  new Worker(start_idx, end_idx, len, hexToArray(targ));
			w.start();
		}
		latch.await();
		return result;
	}

	public static byte[] make_hashed_password(String str) throws NoSuchAlgorithmException {
		byte[] arr = str.getBytes();
		MessageDigest m = MessageDigest.getInstance("SHA");
		m.update(arr);
		return m.digest();
	}

	private static class Worker extends Thread {
		int length;
		int start;
		int end;
		byte[] hash;

		public Worker(int start, int end, int length, byte[] h) {
			this.start = start;
			this.end = end;
			this.length = length;
			this.hash = h;
		}

		@Override
		public void run() {
			try {
				for(int i = start; i <= end; i++) helper(String.valueOf(CHARS[i]));
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
			latch.countDown();
		}

		private void helper(String str) throws NoSuchAlgorithmException {
			if(str.length() > length) return;
			if(Arrays.equals(hash, make_hashed_password(str))) result = str;
			else for(int i = 0; i < chars_size; i++) helper(str + CHARS[i]);
		}
	}
}
