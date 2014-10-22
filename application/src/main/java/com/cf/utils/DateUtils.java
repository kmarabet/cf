package com.cf.utils;

import com.cf.core.exeption.TaControllerException;
import org.slf4j.Logger;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Time;
import java.sql.Date;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;


public final class DateUtils {

    private static final String DATE_FORMAT_TEST_DATA = "MM/dd/yyyy";
    private static final String DATE_FORMAT_UI = "dd/MM/yyyy";
    private static final String TIME_FORMAT = "HH:mm";

    private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private static final SimpleDateFormat dateFormatTestData = new SimpleDateFormat(DATE_FORMAT_TEST_DATA);
    private static final SimpleDateFormat dateFormatUi = new SimpleDateFormat(DATE_FORMAT_UI);
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT);

    //Java calendar in default timezone and default locale
    private static final Calendar cal = Calendar.getInstance();
    static {
        //cal.setTimeZone(TimeZone.getTimeZone("GMT"));
        cal.setFirstDayOfWeek(Calendar.MONDAY);
    }

    public static Date addOneYear(Date startDate) {
        //final Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        cal.add(Calendar.YEAR, 1);
        return new Date(cal.getTimeInMillis());
    }

    public static Date addDays(final Date startDate, final int days) {
        /*final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(startDate.getTime());
        cal.add(Calendar.DAY_OF_MONTH, days);
        return new Date(cal.getTimeInMillis());*/
        return new Date(org.apache.commons.lang3.time.DateUtils.addDays(startDate, days).getTime());
    }

    public static int countDaysBetweenDates(final Date startDate, final Date endDate) {
        //with joda-time
        //Interval interval = new Interval(startDate, endDate);
        /*Days d = Days.daysBetween(startDate, endDate);
        int days = d.getDays();*/
        long diff = endDate.getTime() - startDate.getTime();
        return (int)TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        //return  Maths.round( (startDate.getTime() - endDate.getTime())/ (1000 * 60 * 60 * 24) );
    }

    public static int getDayOfWeek(Date date) {
        cal.setTime(date);
        //Get dayOfTheWeek in american order (1-Sunday, 2-Monday ...)
        int dayOfTheWeek = cal.get(Calendar.DAY_OF_WEEK);
        //Recalculate dayOfTheWeek in european order (7-Sunday, 1-Monday ...)
        if (dayOfTheWeek == 1) return 7;
        else return dayOfTheWeek - (cal.getFirstDayOfWeek()-1);
    }

    public static Date getDateOfLastWeekDay(Date date) {
        int dayOfWeek = getDayOfWeek(date);
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_WEEK, 7 - dayOfWeek);
        return new Date(cal.getTimeInMillis());
    }

    public static String formatDate(final Date date) {

        return dateFormatUi.format(date);
    }
    public static String formatTime(final Time time) {

        return timeFormat.format(time);
    }
    public static String formatDateTime(final java.util.Date date) {

        return dateTimeFormat.format(date);
    }

    public static Date parseDate(final String date) throws ParseException {

        return new Date(dateFormatUi.parse(date).getTime());
    }

    public static Date parseDate(final String date, final String dateFormat) throws ParseException {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(dateFormat);

        return new Date(sDateFormat.parse(date).getTime());
    }

    public static java.util.Date parseDateTime(final String date) throws ParseException {

        return new java.util.Date(dateTimeFormat.parse(date).getTime());
    }
    public static java.util.Date parseDateTime(final String date, final String dateFormat) throws ParseException {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(dateFormat);

        return new java.util.Date(sDateFormat.parse(date).getTime());
    }

    @Deprecated
    public static Date parseDateTestData(final String date) throws ParseException {
        if (date == null)
            return null;
        return new Date(dateFormatTestData.parse(date).getTime());
    }

    public static Time parseTime(final String time) throws ParseException {
        if (time == null)
            return null;
        return new Time(timeFormat.parse(time).getTime());
    }
    public static Time parseTime(final String time, final String timeFormat) throws ParseException {
        SimpleDateFormat sTimeFormat = new SimpleDateFormat(timeFormat);
        if (time == null)
            return null;
        return new Time(sTimeFormat.parse(time).getTime());
    }

    public static Date validateParseDate(final String date, final String dateFormat, final Logger logger){
        try {
            return dateFormat==null?DateUtils.parseDate(date):DateUtils.parseDate(date, dateFormat);
        } catch (ParseException e) {
            ValidationDtoUtils.logAndThrowControllerException("Not valid date: " + date, logger, e);
        }
        return null;
    }

    public static DateRange validateAndParseDateRange(final String startDate, final String endDate, final Logger logger) {

        final String rangDateFormat = "yyyy-MM-dd";

        Date startParsedDate = validateParseDate(startDate, rangDateFormat, logger);
        Date endParsedDate = validateParseDate(endDate, rangDateFormat, logger);

        if (startParsedDate.after(endParsedDate)) {
            logger.error("start date is after end date");
            throw new TaControllerException(HttpServletResponse.SC_BAD_REQUEST, "start date is after end date");
        }
        return new DateRange(startParsedDate, endParsedDate);
    }

    public static class DateRange {
        public Date startDate;
        public Date endDate;

        DateRange(Date startDate, Date endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }

}
