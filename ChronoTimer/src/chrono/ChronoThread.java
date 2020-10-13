package chrono;

import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;

public class ChronoThread extends Thread {

    private boolean run;
    private Chrono chrono;
    private JLabel chronoClock;

    public ChronoThread(Chrono chrono, JLabel chronoClock) {
        this.chrono = chrono;
        this.chronoClock = chronoClock;
    }

    @Override
    public void run() {
        run = true;
        while (run) {
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Chrono.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.chrono.run();
            this.chronoClock.setText(this.chrono.getTime());
        }
    }

}
