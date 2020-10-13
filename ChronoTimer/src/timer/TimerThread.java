package timer;

import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JButton;
import javax.swing.JLabel;

public class TimerThread extends Thread {

    private boolean run;
    private Timer timer;
    private JLabel timerClock;
    private JButton setBtn;
    private JButton startBtn;
    private JButton stopBtn;
            

    public TimerThread(Timer timer, JLabel timerClock, JButton setBtn, JButton startBtn, JButton stopBtn) {
        this.timer = timer;
        this.timerClock = timerClock;
        this.setBtn = setBtn;
        this.startBtn = startBtn;
        this.stopBtn = stopBtn;
    }

    @Override
    public void run() {
        run = true;
        while (run) {
            //System.out.println("TIMER THREAD");

            try {
                sleep(1000);
                
                if(! this.timer.run()) {
            this.setBtn.setEnabled(true);
            this.startBtn.setEnabled(false);
            this.stopBtn.setEnabled(false);
                    this.stop();
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Timer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (LineUnavailableException ex) {
                Logger.getLogger(TimerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.timerClock.setText(this.timer.getTime());
        }
    }

}
