import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;
import javax.swing.text.Document;

import java.awt.*;
import java.awt.event.*;


 public class SudokuFrame extends JFrame {

	 private JTextArea input;
	 private JTextArea output;
	 private JButton check;
	 private JCheckBox check_box;
	
	public SudokuFrame() {
		super("Sudoku Solver");
		setLocationByPlatform(true);

		this.setLayout(new BorderLayout(4,4));
		add_parts();

		check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 	{ solve();	}
		});

		listening_input();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	private void solve(){
		String inp = input.getText();
		Sudoku s;
		try{ s = new Sudoku(Sudoku.textToGrid(inp)); }
		catch(Exception e){
			output.setText("Parsing problem!");
			return;
		}
		int cnt = s.solve();
		if(cnt == 0) {
			output.setText("This sudoku doesn't have solution!");
			return;
		}
		long time = s.getElapsed();
		String out = s.getSolutionText() + "\n" +
					"Solutions: " + cnt + "\n" +
					"Elapsed time: " + time + "ms/n";
		output.setText(out);
	}


	private void listening_input(){
		Document d = input.getDocument();
		d.addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				if(check_box.isSelected()) solve();

			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				if(check_box.isSelected()) solve();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				if(check_box.isSelected()) solve();
			}
		});
	}

	//done
	private void add_parts(){
		input = new JTextArea(15, 20);
		input.setBorder(new TitledBorder("Puzzle"));
		this.add(input, BorderLayout.WEST);

		output = new JTextArea(15, 20);
		output.setBorder(new TitledBorder("Solution"));
		output.setEditable(false);
		this.add(output, BorderLayout.EAST);


		JPanel south_panel = new JPanel();
		this.add(south_panel, BorderLayout.SOUTH);

		check = new JButton("Check");
		south_panel.add(check);

		check_box = new JCheckBox("Auto Check");
		south_panel.add(check_box);
	}
	
	
	public static void main(String[] args) {
		// GUI Look And Feel
		// Do this incantation at the start of main() to tell Swing
		// to use the GUI LookAndFeel of the native platform. It's ok
		// to ignore the exception.
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) { }
		
		SudokuFrame frame = new SudokuFrame();
	}

}
