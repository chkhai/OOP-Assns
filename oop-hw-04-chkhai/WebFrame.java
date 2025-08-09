import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class WebFrame extends JFrame {
    private JTable table;
    private JScrollPane scroll;
    public DefaultTableModel model;
    private JButton single_button;
    private JButton concurrent_button;
    private JButton stop_button;
    private JTextField field;
    private JLabel running;
    private JLabel completed;
    private JLabel time_elapsed;
    private JProgressBar progress;
    private Vector<WebWorker> workers;
    private volatile boolean is_interrupted;
    private Launcher l;
    long start_time;
    private final AtomicInteger running_threads_count = new AtomicInteger(0);
    private final AtomicInteger completed_threads_count = new AtomicInteger(0);
    private WebFrame fr;


    public WebFrame(String filename) throws IOException {
        super("WebLoader");
        init_comps(filename);
        add_listeners();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        this.setVisible(true);
    }

    private void add_listeners() {
        single_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                original_state();
                l = new Launcher(1);
                l.start();
            }
        });
        concurrent_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int num = Integer.parseInt(field.getText());
                original_state();
                l = new Launcher(num);
                l.start();
            }
        });
        stop_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stop_button.setEnabled(false);
                if(l == null || !l.isAlive()) return;
                l.interrupt();
                is_interrupted = true;
            }
        });
    }

    private void original_state() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < model.getRowCount(); i++) model.setValueAt("", i, 1);
                progress.setValue(0);
                running.setText("Running: ");
                completed.setText("Completed: ");
                time_elapsed.setText("Time Elapsed: ");

                single_button.setEnabled(false);
                concurrent_button.setEnabled(false);
                stop_button.setEnabled(true);
            }
        });
        is_interrupted = false;
        running_threads_count.set(0);
        completed_threads_count.set(0);
    }

    public void init_comps(String filename) throws IOException {
        fr = this;
        is_interrupted = false;
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        model = new DefaultTableModel(new String[] {"url", "status"}, 0);
        table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(600,300));
        panel.add(scroll);

        add_data_to_tables(filename);

        JPanel low_panel = new JPanel();
        low_panel.setLayout(new BoxLayout(low_panel, BoxLayout.Y_AXIS));
        low_panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        single_button = new JButton("Single Thread Fetch");
        single_button.setMaximumSize(new Dimension(200,30));

        concurrent_button = new JButton("Concurrent Fetch");
        concurrent_button.setMaximumSize(new Dimension(200,30));

        field = new JTextField();
        field.setMaximumSize(new Dimension(50,30));

        running = new JLabel("Running: ");
        completed = new JLabel("Completed: ");
        time_elapsed = new JLabel("Time Elapsed: ");
        progress = new JProgressBar();

        stop_button = new JButton("Stop");
        stop_button.setEnabled(false);
        stop_button.setMaximumSize(new Dimension(200,30));

        low_panel.add(Box.createHorizontalStrut(10));
        low_panel.add(single_button);
        low_panel.add(Box.createVerticalStrut(5));
        low_panel.add(concurrent_button);
        low_panel.add(Box.createVerticalStrut(5));
        low_panel.add(field);
        low_panel.add(Box.createVerticalStrut(5));
        low_panel.add(running);
        low_panel.add(Box.createVerticalStrut(5));
        low_panel.add(completed);
        low_panel.add(Box.createVerticalStrut(5));
        low_panel.add(time_elapsed);
        low_panel.add(Box.createVerticalStrut(5));
        low_panel.add(progress);
        low_panel.add(Box.createVerticalStrut(5));
        low_panel.add(stop_button);
        low_panel.add(Box.createVerticalStrut(5));
        panel.add(low_panel);
        this.add(panel);
    }

    private void add_data_to_tables(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String str = reader.readLine();
        while(1>0){
            if(str == null) break;
            String[] data = new String[]{str,""};
            model.addRow(data);
            str = reader.readLine();
        }
        reader.close();
    }

    public class Launcher extends Thread{
        int num_workers;
        Semaphore sem;
        public Launcher(int num_workers) {
            this.num_workers = num_workers;
            sem = new Semaphore(this.num_workers);
            workers = new Vector<>();
        }

        @Override
        public void run(){
            start_time = System.currentTimeMillis();
            increment_running_threads();
            for(int i = 0; i < model.getRowCount() && !this.isInterrupted() && !is_interrupted; i++){
                try {
                    sem.acquire();
                    if(this.isInterrupted() || is_interrupted){
                        sem.release();
                        break;
                    }
                    String url = model.getValueAt(i, 0).toString();
                    WebWorker w = new WebWorker(url, i, fr, sem);
                    workers.add(w);
                    w.start();
                } catch (InterruptedException e) {
                    model.setValueAt("interrupted", i, 1);
                    for (WebWorker worker : workers) {
                        if (worker != null && worker.isAlive()) {
                            worker.interrupt();
                        }
                    }
                    sem.release();
                    break;
                }
            }
            for(WebWorker worker : workers) {
                try {
                    worker.join();
                } catch (InterruptedException e) {
                    worker.interrupt();
                }
            }
            long time = System.currentTimeMillis() - start_time;
            upt_time(time);
            decrement_running_threads();
        }
    }

    private void upt_time(long time) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                time_elapsed.setText("Time Elapsed: " + time + "ms");
                single_button.setEnabled(true);
                concurrent_button.setEnabled(true);
                field.setEnabled(true);
                stop_button.setEnabled(false);
            }
        });
    }

    public void increment_running_threads() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                running_threads_count.incrementAndGet();
                running.setText("Running: " + running_threads_count.get());
            }
        });
    }

    public void decrement_running_threads() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                running_threads_count.decrementAndGet();
                running.setText("Running: " + running_threads_count.get());
            }
        });
    }

    public void increment_completed_threads() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                completed_threads_count.incrementAndGet();
                completed.setText("Completed: " + completed_threads_count.get());
                progress.setValue(completed_threads_count.get() * 100 / model.getRowCount());
            }
        });
    }

    public static void main(String[] args) throws IOException {
        WebFrame frame = new WebFrame("links.txt");
    }
}
