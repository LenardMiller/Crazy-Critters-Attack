package main.misc;

public class Timer {

    private final int BETWEEN_COUNTS;

    private int alarmTime;
    private int counter;
    private int betweenCounter;

    /**
     * Counts up when updated
     * @param alarmTime when the alarm will trigger
     * @param betweenCounts how many frames to wait before incrementing timer
     * @param startTriggered if the alarms starts triggered, or untriggered
     */
    public Timer(int alarmTime, int betweenCounts, boolean startTriggered) {
        BETWEEN_COUNTS = betweenCounts;
        this.alarmTime = alarmTime;
        if (startTriggered) counter = this.alarmTime;
    }

    /**
     * Counts up when updated
     * @param alarmTime when the alarm will trigger
     */
    public Timer(int alarmTime) {
        this(alarmTime, 1, false);
    }

    /**
     * Counts up when updated
     * @param alarmTime when the alarm will trigger
     * @param betweenCounts how many frames to wait before incrementing timer
     */
    public Timer(int alarmTime, int betweenCounts) {
        this(alarmTime, betweenCounts, false);
    }

    /**
     * Counts up when updated
     * @param alarmTime when the alarm will trigger
     * @param startTriggered if the alarms starts triggered, or untriggered
     */
    public Timer(int alarmTime, boolean startTriggered) {
        this(alarmTime, 1, startTriggered);
    }

    /**
     * Counts up when updated
     */
    public Timer() {
        this(0);
    }

    /**
     * Increase counter,
     * between counts will only work if this is run every frame
     */
    public void update() {
        betweenCounter++;
        if (betweenCounter >= BETWEEN_COUNTS) {
            counter++;
            betweenCounter = 0;
        }
    }

    public int getCurrentTime() {
        return counter;
    }

    /**
     * Checks counter, and resets if true and reset enabled
     * @return if the counter is >= alarm time
     */
    public boolean triggered(boolean reset) {
        if (counter >= alarmTime) {
            if (reset) reset();
            return true;
        } return false;
    }

    /**
     * Set counter and betweenCounter to 0
     */
    public void reset() {
        counter = 0;
        betweenCounter = 0;
    }

    public void setAlarmTime(int alarmTime) {
        this.alarmTime = alarmTime;
    }

    public void setCurrentTime(int time) {
        counter = time;
    }

    public int getAlarmTime() {
        return alarmTime;
    }

    public int getBETWEEN_COUNTS() {
        return BETWEEN_COUNTS;
    }

    public int getBetweenCounter() {
        return betweenCounter;
    }
}
