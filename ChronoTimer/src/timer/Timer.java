package timer;

import java.nio.ByteBuffer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import main.Clock;

public class Timer  {

    private Clock clock;

    public Timer() {
        this.clock = new Clock();
    }

    public boolean run() throws LineUnavailableException, InterruptedException {
        if(! this.clock.counterDesc()){
            this.soundAlarm();
            return false;
        }
        return true;
    }

    public String getTime() {
        return this.clock.getTime();
    }

    public void setTime(int hours, int minutes, int seconds) {
        
        this.clock.setTime(hours, minutes, seconds);
    }
    
    public void soundAlarm() throws LineUnavailableException, InterruptedException {
        double fFreq = 440;
        final int SAMPLING_RATE = 44100;              // Audio sampling rate
        final int SAMPLE_SIZE = 2;                    // Audio sample size in bytes

        SourceDataLine line;

        double fCyclePosition = 0;

        AudioFormat format = new AudioFormat(SAMPLING_RATE, 16, 1, true, true);
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

        if (!AudioSystem.isLineSupported(info)) {
            System.out.println("Line matching " + info + " is not supported.");
            throw new LineUnavailableException();
        }

        line = (SourceDataLine) AudioSystem.getLine(info);
        line.open(format);
        line.start();

        ByteBuffer cBuf = ByteBuffer.allocate(line.getBufferSize());

        int ctSamplesTotal = SAMPLING_RATE * 1;         // Output for roughly 5 seconds

        while (ctSamplesTotal > 0) {
            double fCycleInc = fFreq / SAMPLING_RATE;    // Fraction of cycle between samples

            cBuf.clear();                              // Discard the samples from the last pass

            int ctSamplesThisPass = line.available() / SAMPLE_SIZE;
            for (int i = 0; i < ctSamplesThisPass; i++) {
                cBuf.putShort((short) (Short.MAX_VALUE * Math.sin(2 * Math.PI * fCyclePosition)));

                fCyclePosition += fCycleInc;
                if (fCyclePosition > 1) {
                    fCyclePosition -= 1;
                }
            }

            line.write(cBuf.array(), 0, cBuf.position());
            ctSamplesTotal -= ctSamplesThisPass;     // Update total number of samples written 

            while (line.getBufferSize() / 2 < line.available()) {
                Thread.sleep(1);
            }
        }
        
        line.drain();
        line.close();
    }
}