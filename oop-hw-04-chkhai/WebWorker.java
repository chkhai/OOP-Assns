import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Semaphore;
import javax.swing.*;

public class WebWorker extends Thread {
    private String urlString;
    private int row;
    private WebFrame webframe;
    private Semaphore launcher_sem;
    private String status;
    private final String ERROR = "err";
    private final String INTERRUPTED = "interrupted";

    public WebWorker(String url, int row, WebFrame webframe, Semaphore lanch_sem) {
        urlString = url;
        this.row = row;
        this.webframe = webframe;
        this.launcher_sem = lanch_sem;
    }

    @Override
    public void run() {
        webframe.increment_running_threads();
        download();
        SwingUtilities.invokeLater(() ->
                {webframe.model.setValueAt(status,row,1);}
        );
        webframe.decrement_running_threads();
        webframe.increment_completed_threads();
        launcher_sem.release();
    }

  //This is the core web/download i/o code...
 	public void download() {
        InputStream input = null;
        StringBuilder contents = null;
        try {
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            // Set connect() to throw an IOException
            // if connection does not succeed in this many msecs.
            connection.setConnectTimeout(5000);

            connection.connect();
            input = connection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            long start = System.currentTimeMillis();
            char[] array = new char[1000];
            int len;
            contents = new StringBuilder(1000);
            while ((len = reader.read(array, 0, array.length)) > 0) {
                contents.append(array, 0, len);
                Thread.sleep(100);
            }
            long t = System.currentTimeMillis() - start;
            String date = (new SimpleDateFormat("HH:mm:ss").format(new Date(start)));
            status = date + " " + t + "ms " + contents.length() + " bytes";
            // Successful download if we get here
        }
        // Otherwise control jumps to a catch...
        catch (InterruptedException exception) {
            // YOUR CODE HERE
            status = INTERRUPTED;
            // deal with interruption
        } catch (IOException ignored) {
            status = ERROR;
        }
        // "finally" clause, to close the input stream
        // in any case
        finally {
            try {
                if (input != null) input.close();
            } catch (IOException ignored) {
                status = ERROR;
            }
        }
    }
}
