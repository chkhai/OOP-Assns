// JCount.java

/*
 Basic GUI/Threading exercise.
*/

import javax.swing.*;
import java.awt.event.*;

public class JCount extends JPanel {
	private JTextField field;
	private JLabel label;
	private JButton start;
	private JButton stop;
	private Worker w;

	public void init_comps(){
		field = new JTextField("100000000");
		label = new JLabel("0");
		start = new JButton("Start");
		stop = new JButton("Stop");
		add(field);
		add(label);
		add(start);
		add(stop);
	}

	private void add_listeners() {
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String num = field.getText();
				if(w != null) w.interrupt();
				w = new Worker(num);
				w.start();
			}
		});

		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(w.isAlive()) w.interrupt();
			}
		});
	}

	public JCount() {
		// Set the JCount to use Box layout
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		// YOUR CODE HERE
		init_comps();
		add_listeners();
	}

	static public void main(String[] args)  {
		// Creates a frame with 4 JCounts in it.
		// (provided)
		JFrame frame = new JFrame("The Count");
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		
		frame.add(new JCount());
		frame.add(new JCount());
		frame.add(new JCount());
		frame.add(new JCount());

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	private class Worker extends Thread{
		private int counter;
		public Worker(String str) {
			this.counter = Integer.parseInt(str);
		}

		@Override
		public void run() {
			try{
				for(int i = 0; i <= counter; i++){
					if(i % 10000 != 0) continue;
					sleep(100);
					int copy = i;
					SwingUtilities.invokeLater(new Runnable(){
						public void run(){
								label.setText(Integer.toString(copy));
							}
					});
//						System.out.println(Integer.toString(i));
				}
			} catch (InterruptedException e) {
				System.out.println("Counter was interrupted");
				return;
			}
		}
	}
}

