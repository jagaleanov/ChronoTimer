package main;

import chrono.Chrono;
import chrono.ChronoThread;
import timer.Timer;
import timer.TimerThread;
import gui.GUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ChronoTimer implements ActionListener {

    private Chrono chrono;
    private ChronoThread chronoThread;
    private Timer timer;
    private TimerThread timerThread;
    private GUI view;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ChronoTimer ct = new ChronoTimer();
    }

    public ChronoTimer() {
        this.view = new GUI();
        this.chrono = new Chrono();
        this.chronoThread = new ChronoThread(this.chrono, this.view.chronoClock);
        this.timer = new Timer();
        this.timerThread = new TimerThread(this.timer, this.view.timerClock, this.view.timerSetBtn, this.view.timerStartBtn, this.view.timerStopBtn);

        this.view.chronoStartBtn.addActionListener(this);
        this.view.chronoStopBtn.addActionListener(this);
        this.view.chronoResetBtn.addActionListener(this);
        this.view.chronoSaveBtn.addActionListener(this);

        this.view.timerSetBtn.addActionListener(this);
        this.view.timerStartBtn.addActionListener(this);
        this.view.timerStopBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.view.chronoStartBtn) {
            this.view.chronoStartBtn.setEnabled(false);
            this.view.chronoResetBtn.setEnabled(true);
            this.view.chronoSaveBtn.setEnabled(true);
            this.view.chronoStopBtn.setEnabled(true);
            this.chronoThread.start();
        } else if (e.getSource() == view.chronoStopBtn) {
            this.chronoThread.stop();
            this.chronoThread = new ChronoThread(this.chrono, this.view.chronoClock);
            this.view.chronoStartBtn.setEnabled(true);
            this.view.chronoResetBtn.setEnabled(true);
            this.view.chronoSaveBtn.setEnabled(false);
            this.view.chronoStopBtn.setEnabled(false);
        } else if (e.getSource() == view.chronoResetBtn) {
            this.chronoThread.stop();
            this.chronoThread = new ChronoThread(this.chrono, this.view.chronoClock);
            this.chrono.reset();
            this.view.chronoClock.setText(this.chrono.getTime());
            this.view.chronoStartBtn.setEnabled(true);
            this.view.chronoResetBtn.setEnabled(false);
            this.view.chronoSaveBtn.setEnabled(false);
            this.view.chronoStopBtn.setEnabled(false);
        } else if (e.getSource() == view.chronoSaveBtn) {
        } else if (e.getSource() == view.timerSetBtn) {
            JTextField xField = new JTextField(5);
            JTextField yField = new JTextField(5);
            JTextField zField = new JTextField(5);
            JPanel myPanel = new JPanel();
            myPanel.add(new JLabel("h"));
            myPanel.add(xField);
            myPanel.add(Box.createHorizontalStrut(15)); // a spacer
            myPanel.add(new JLabel("mm"));
            myPanel.add(yField);
            myPanel.add(Box.createHorizontalStrut(15)); // a spacer
            myPanel.add(new JLabel("ss"));
            myPanel.add(zField);

            int result = JOptionPane.showConfirmDialog(null, myPanel,
                    "Introduzca el tiempo inicial", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                this.timer.setTime(Integer.valueOf(xField.getText()), Integer.valueOf(yField.getText()), Integer.valueOf(zField.getText()));
                this.view.timerClock.setText(this.timer.getTime());
                this.view.timerSetBtn.setEnabled(false);
                this.view.timerStartBtn.setEnabled(true);
                this.view.timerStopBtn.setEnabled(false);
            }
        } else if (e.getSource() == view.timerStartBtn) {
            this.view.timerSetBtn.setEnabled(false);
            this.view.timerStartBtn.setEnabled(false);
            this.view.timerStopBtn.setEnabled(true);
            this.timerThread.start();
        } else if (e.getSource() == view.timerStopBtn) {
            this.timerThread.stop();
            this.timerThread = new TimerThread(this.timer, this.view.timerClock, this.view.timerSetBtn, this.view.timerStartBtn, this.view.timerStopBtn);
            this.view.timerSetBtn.setEnabled(true);
            this.view.timerStartBtn.setEnabled(true);
            this.view.timerStopBtn.setEnabled(false);
        }
    }

}
