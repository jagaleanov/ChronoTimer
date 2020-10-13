package main;

public class Clock {

    private int hours;
    private int minutes;
    private int seconds;

    public Clock() {
        hours = 0;
        minutes = 0;
        seconds = 0;
    }

    public void counterAsc() {
        if (seconds == 59) {
            seconds = 0;

            if (minutes == 59) {
                minutes = 0;
                hours++;
            } else {
                minutes++;
            }
        } else {
            seconds++;
        }
    }

    public boolean counterDesc() {
        if (seconds > 0 || minutes > 0 || hours > 0) {
            if (seconds == 0) {
                seconds = 59;

                if (minutes == 0) {
                    minutes = 59;
                    hours--;
                } else {
                    minutes--;
                }
            } else {
                seconds--;
            }
            return true;
        }
        return false;
    }

    public String getTime() {
        String seconds;
        String minutes;
        String hours = String.valueOf(this.hours);

        if (this.seconds < 10) {
            seconds = "0" + this.seconds;
        } else {
            seconds = String.valueOf(this.seconds);
        }

        if (this.minutes < 10) {
            minutes = "0" + this.minutes;
        } else {
            minutes = String.valueOf(this.minutes);
        }

        return hours + ":" + minutes + ":" + seconds;
    }

    public void setTime(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    @Override
    public Clock clone() {
        Clock clon = new Clock();
        clon.setTime(this.hours, this.minutes, this.seconds);

        return clon;
    }
}
