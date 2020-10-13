package chrono;

import main.Clock;
import javax.swing.JOptionPane;

public class Chrono {

    private Clock clock;
    private Clock memoryList[];
    private int memCounter;

    public Chrono() {
        this.memoryList = new Clock[10];
        this.reset();
    }

    public void run() {
        this.clock.counterAsc();
    }

    public String getTime() {
        return this.clock.getTime();
    }

    public void save() {
        if (this.memCounter < 9) {
            this.memCounter++;
            this.memoryList[memCounter] = clock.clone();
        } else {
            JOptionPane.showMessageDialog(null, "La memoria esta llena!");
        }
    }

    public void reset() {
        this.clock = new Clock();
        for (int i = 0; i < 10; i++) {
            this.memoryList[i] = null;
        }

        this.memCounter = 0;
    }
}
