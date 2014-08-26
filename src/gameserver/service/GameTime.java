package gameserver.service;

import gameserver.config.SystemConfig;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameTime {

    final static Logger logger = LoggerFactory.getLogger(GameTime.class);
    protected long currentTimeMillis = 0;
    protected long currentTimeSecond = currentTimeMillis / 1000;
    protected int curSecond = 0;
    protected int curMinute = 0;
    protected int curHour = 0;
    protected int curWeekOfYear = 0;
    protected int curDayOfWeek = 0;
    protected int curDayOfMonth = 0;
    protected int curMonthOfYear = 0;
    protected int curDayOfYear = 0;
    protected int curYear = 0;
    protected final static GameTime gameTime = new GameTime();

    static {
        Calendar cal = Calendar.getInstance();
        gameTime.set(cal);
    }

    public static GameTime getInstance() {  
        return gameTime;
    }

    public void setTime(int timeSecond) {
        Calendar cal = Calendar.getInstance();
        int millis = cal.get(Calendar.MILLISECOND);
        cal.setTimeInMillis((long) timeSecond + millis * 1000);
        this.set(cal);
    }

    private void set(int year, int month, int day, int hour, int minute, int second) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);

        set(cal);
    }

    private void set(Calendar cal) {
        currentTimeMillis = cal.getTimeInMillis();
        currentTimeSecond = currentTimeMillis / 1000;

        curSecond = cal.get(Calendar.SECOND);
        curMinute = cal.get(Calendar.MINUTE);
        curHour = cal.get(Calendar.HOUR_OF_DAY);
        curDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        curDayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        curMonthOfYear = cal.get(Calendar.MONTH) + 1;
        curDayOfYear = cal.get(Calendar.DAY_OF_YEAR);
        curYear = cal.get(Calendar.YEAR);

        int dow = cal.get(Calendar.DAY_OF_WEEK);
        curDayOfWeek = dow == Calendar.SUNDAY ? 7 : dow - 1; // Java的周默认是从周日开始，这里做个转换，把周一~周日换成 1-7
    }

    public long currentTimeMillis() {
        return currentTimeMillis;
    }

    public int currentTimeSecond() {
        return (int) currentTimeSecond;
    }

    /**
     * Gets the year in the game
     *
     * @return Year
     */
    public int getYear() {
        return this.curYear;
    }

    /**
     * Gets the month in the game, 1-12
     *
     * @return Month 1-12
     */
    public int getMonth() {
        return this.curMonthOfYear;
    }

    /**
     * Gets the day in the game, 1-31
     *
     * @return Day 1-31
     */
    public int getDay() {
        return this.curDayOfMonth;
    }

    /**
     * @return the curWeekOfYear
     */
    public int getWeekOfYear() {
        return curWeekOfYear;
    }

    public int getDayOfWeek() {
        return curDayOfWeek;
    }

    /**
     * Gets the hour in the game, 0-23
     *
     * @return Hour 0-23
     */
    public int getHour() {
        return this.curHour;
    }

    /**
     * Gets the minute in the game, 0-59
     *
     * @return Minute 0-59
     */
    public int getMinute() {
        return this.curMinute;
    }

    /**
     * @return the curOnlineRewardTime
     */
    public int getDayOfYear() {
        return this.curDayOfYear;
    }

    public int getMinuteOfDay() {
        return curHour * 60 + curMinute;
    }

    public int getSecondOfDay() {
        return curHour * 3600 + curMinute * 60 + curSecond;
    }

    public int getSecondOfWeek() {
        return (curDayOfWeek - 1) * 86400 + getSecondOfDay();
    }

    public int getSecondOfMonth() {
        return (curDayOfMonth - 1) * 86400 + getSecondOfDay();
    }

    public Calendar now() {
        Calendar cal = Calendar.getInstance();
        cal.set(curYear, curMonthOfYear - 1, curDayOfMonth, curHour, curMinute, curSecond);
        cal.set(Calendar.MILLISECOND, 0);

        return cal;
    }

    public int getLaunchedDays() {
        int days = 0;
        int launchcedTimeInSecond = (int) SystemConfig.LAUNCH_DATE.getTimeInMillis() / 1000;
        int diff = (int) currentTimeSecond - launchcedTimeInSecond;
        days = diff / 86400;

        return days;
    }

    @Override
    public String toString() {
        return String.format("{%d-%02d-%02d %02d:%02d:%02d}", curYear, curMonthOfYear, curDayOfMonth, curHour, curMinute, curSecond);
    }
}