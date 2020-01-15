package HistoryLogs;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DateLog : Class used to store a date with the Year, Month, Day, Hour and Mins.
 * Used to use dates easier.
 */
public class DateLog {

    private Integer year;

    private Integer month;

    private Integer day;

    private Integer hour;

    private Integer mins;

    /**
     * Constructor
     * @param date The date at which the message was sent (format : yyyy-mm-dd)
     * @param time The time at which the message was sent (format : hh:mm)
     */
    public DateLog(String date, String time){
        String[] dateArray = date.split("-");
        String[] timeArray = time.split(":");

        Integer y = Integer.parseInt(dateArray[0]);
        if (y > 2018 && y < 2100){
            this.year = y;
        }
        else{
            throw new IllegalArgumentException("The year should be a number between 2018 and 2100 !!");
        }

        Integer mth = Integer.parseInt(dateArray[1]);
        if (mth > 0 && mth < 13){
            this.month = mth;
        }
        else{
            throw new IllegalArgumentException("The month should be a number between 1 and 12 !!");
        }

        Integer d = Integer.parseInt(dateArray[2]);
        if (d > 0 && d < 32){
            this.day = d;
        }
        else{
            throw new IllegalArgumentException("The day should be a number between 1 and 31 !!");
        }

        Integer h = Integer.parseInt(timeArray[0]);
        if (h > -1 && h < 24){
            this.hour = h;
        }
        else{
            throw new IllegalArgumentException("The hour should be a number between 0 and 23 !!");
        }

        Integer min = Integer.parseInt(timeArray[1]);
        if (min > -1 && min < 60){
            this.mins = min;
        }
        else{
            throw new IllegalArgumentException("The minute should be a number between 0 and 59 !!");
        }
    }
    
    public DateLog (int yr, int mth, int day, int hr, int min){
        this.year = yr;
        this.day = day;
        this.hour= hr;
        this.month = mth;
        this.mins = min;
    }
    
    public static DateLog getCurrentDate(){
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
        Date date1 = new Date();
        String[] sDate = dateFormat1.format(date1).split("-");
        String day = sDate[0] + "-" + sDate[1] + "-" + sDate[2];
        String time = sDate[3] + ":" + sDate[4];
        DateLog dateMsg = new DateLog(day, time);
        return dateMsg;
    }

    /* Getters */
    /**
     * Get the year in which the message was sent
     * @return The year in which the message was sent
     */
    public Integer getYear() { return this.year; }

    /**
     * Get the month in which the message was sent
     * @return The month in which the message was sent
     */
    public Integer getMonth() { return this.month; }

    /**
     * Get the day in which the message was sent
     * @return The day in which the message was sent
     */
    public Integer getDay() { return this.day; }

    /**
     * Get the hour at which the message was sent
     * @return The hour at which the message was sent
     */
    public Integer getHour() { return this.hour; }

    /**
     * Get the minute at which the message was sent
     * @return The minute at which the message was sent
     */
    public Integer getMinute() { return this.mins; }

    private String fillWithZeros(String s, Integer nbChars) throws IOException{
        String ret;
        if (s.length() == nbChars){
            ret = s;
        }
        else if (s.length() < nbChars){
            ret = s;
            while (ret.length() < nbChars){
                ret = "0" + ret;
            }
        }
        else {
            throw new IOException("The string already has more characters than it should have !!");
        }
        return ret;
    }

    /**
     * Changes a DateLog into a printable string
     * @return A String containing all the information of the DateLog
     */
    public String toString(){
        String date = "";
        String time = "";
        try{
            date = fillWithZeros(this.day.toString(), 2) + "/" + fillWithZeros(this.month.toString(), 2) + "/" + this.year;
            time = fillWithZeros(this.hour.toString(), 2) + ":" + fillWithZeros(this.mins.toString(), 2);
        } catch (IOException e){
            e.printStackTrace();
        }
        return (date + " at " + time);
    }
}