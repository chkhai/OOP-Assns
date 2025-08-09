import javax.swing.*;
import java.awt.*;

public class JBrainTetris extends JTetris{
    private final Brain brain;
    private int cnt;
    private JCheckBox brainMode;
    private JSlider adversary;
    private JLabel ok_label;
    private Brain.Move move;
    private final int limit_h = board.getHeight() - TOP_SPACE;

    JBrainTetris(int pixels) {
        super(pixels);
        cnt = 0;
        brain = new DefaultBrain();
    }

    @Override
    public JComponent createControlPanel() {
        JPanel panel = (JPanel) super.createControlPanel();

        //brain panel
        JPanel brain_panel = new JPanel();
        brain_panel.add(new JLabel("Brain:"));
        brainMode = new JCheckBox("Brain active");
        brain_panel.add(brainMode);

        //adversary
        JPanel adversary_panel = new JPanel();
        adversary_panel.add(new JLabel("Adversary:"));
        adversary = new JSlider(0, 100, 0);
        adversary.setPreferredSize(new Dimension(100, 15));
        adversary_panel.add(adversary);
        ok_label = new JLabel("ok");
        adversary_panel.add(ok_label);

        panel.add(brain_panel);
        panel.add(adversary_panel);

        return panel;
    }

    @Override
    public Piece pickNextPiece(){
        int random_num = random.nextInt(99) + 1;
        int res_ind = -1;
        double res = Integer.MIN_VALUE;
        if(random_num < adversary.getValue()){
            ok_label.setText("*ok*");
            Brain.Move curr_move;
            for(int i = 0; i < super.pieces.length; i++){
                curr_move = new Brain.Move();
                brain.bestMove(board, super.pieces[i], limit_h , curr_move);
                if(curr_move.score <= res) continue;
                res = curr_move.score;
                res_ind = i;
            }
            return super.pieces[res_ind];
        }else{
            ok_label.setText("ok");
            return super.pickNextPiece();
        }
    }

    @Override
    public void tick(int verb){
        super.tick(verb);
        if(verb != DOWN || !brainMode.isSelected()) return;
        if (cnt != super.count) {
            board.undo();
            cnt = super.count;
            move = brain.bestMove(board, currentPiece, limit_h, null);
        }
        if(move == null) return;

        if (!move.piece.equals(currentPiece)) super.tick(ROTATE);
        if (move.x < currentX) super.tick(LEFT);
        if (move.x > currentX) super.tick(RIGHT);
    }

    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) { }

        JBrainTetris tetris = new JBrainTetris(16);
        JFrame frame = JTetris.createFrame(tetris);
        frame.setVisible(true);
    }

}
