/* 
 * Copyright 2017 Rene Bylander at WITC
 */

package edu.witc.recgen.utility;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * @author Rene Bylander
 * @created Mar 1, 2017
 */
public class DateUtil {
    
    public enum DateFormats{
        ISO_LOCAL_DATE("YYYY-MM-dd"),
        CALENDAR_DATE("yyyy/MM/dd"),
        LONG_MONTH_DAY_YEAR("MMMM dd yyyy"),
        SHORT_MONTH_DAY_YEAR("MMM dd yyyy"),
        MONTH_DAY_YEAR_NUMBERS("MM/dd/yyy");

        private final String displayValue;
        
        private DateFormats(final String value){
            displayValue = value;
        }
        @Override
        public String toString(){
            return displayValue;
        }
    }
    
    public static LocalDate LocalDateFromString(String date, DateFormats pattern){
        if(date.contains("-")){
            date = date.replace('-', '/');
        }
        String usePattern = pattern.toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(usePattern);
        LocalDate localDate = LocalDate.parse(date, formatter);
        return localDate;
    }
    
    public static LocalDate getCurrentDate(){
        //yyyy-mm-dd ex: 2017-03-28
        return LocalDate.now();
    }
    public static LocalDateTime getCurrentDateTime(){
        
        return LocalDateTime.now();
    }
    public static boolean isLeapYear(LocalDate date){
        return date.isLeapYear();  
    }
    public static Month getDateMonth(LocalDate date){
        return date.getMonth();
    }
    public static int getDateDayOfMonth(LocalDate date){
        return date.getDayOfMonth();
    }
    public static int getDateYear(LocalDate date){
        return date.getYear();
    }
    public static String getFormattedDate(LocalDate date, DateFormats pattern){
        //ISO_LOCAL_DATE: YYYY-MM-dd all numbers; YYYY-MMM-dd give short month
        String usePattern = pattern.toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(usePattern);
        return date.format(formatter);
    }
    public static LocalDate getDateFrom(int yearDate, Month monthDate, int dayDate ){
        return LocalDate.of(yearDate, monthDate,dayDate);
    }
    public static LocalDate getDateFrom(int yearDate, int monthDate, int dayDate ){
        return LocalDate.of(yearDate, monthDate,dayDate);
    }
    public static long getDaysBetween(LocalDate date1, LocalDate date2){
        long daysBetween = ChronoUnit.DAYS.between(date1, date2);
        return daysBetween;
    }
    public static LocalDate addDaysToDate(LocalDate date, int days){
        LocalDate plusDays = date.plus(days, ChronoUnit.DAYS);
        return plusDays;
    }
    public static LocalDate addWeeksToDate(LocalDate date, int weeks){
        LocalDate plusWeeks = date.plus(weeks, ChronoUnit.WEEKS);
        return plusWeeks;
    }
    public static LocalDate addMonthsToDate(LocalDate date, int months){
        LocalDate plusMonths = date.plus(months, ChronoUnit.MONTHS);
        return plusMonths;
    }
    public static LocalDate addYearsToDate(LocalDate date, int years){
        LocalDate plusYears = date.plus(years, ChronoUnit.YEARS);
        return plusYears;
    }
    public static LocalDate addDecadesToDate(LocalDate date, int decades){
        LocalDate plusDecades = date.plus(decades, ChronoUnit.DECADES);
        return plusDecades;
    }
    /*substract timeframe from date*/
    public static LocalDate minusDaysFromDate(LocalDate date, int days){
        LocalDate minusDays = date.minus(days, ChronoUnit.DAYS);
        return minusDays;
    }
    public static LocalDate minusWeeksFromDate(LocalDate date, int weeks){
        LocalDate minusWeeks = date.minus(weeks, ChronoUnit.WEEKS);
        return minusWeeks;
    }
    public static LocalDate minusMonthsFromDate(LocalDate date, int months){
        LocalDate minusMonths = date.minus(months, ChronoUnit.MONTHS);
        return minusMonths;
    }
    public static LocalDate minusYearsFromDate(LocalDate date, int years){
        LocalDate minusYears = date.minus(years, ChronoUnit.YEARS);
        return minusYears;
    }
    public static LocalDate minusDecadesFromDate(LocalDate date, int decades){
        LocalDate minusDecades = date.minus(decades, ChronoUnit.DECADES);
        return minusDecades;
    }
    public static int getMonthValueInt(LocalDate date){
        return date.getMonthValue();
    }
    public static String getTwoDigitMonth(String month){
        String monthValue = null;
        switch(month.toLowerCase()){
             case "january":
                monthValue = "01";
                break;
            case "february":
                monthValue = "02";
                break;
            case "march":
                monthValue = "03";
                break;
            case "april":
                monthValue = "04";
                break;
            case "may":
                monthValue = "05";
                break;
            case "june":
                monthValue = "06";
                break;
            case "july":
                monthValue = "07";
                break;
            case "august":
                monthValue = "08";
                break;
            case "september":
                monthValue = "09";
                break;
            case "october":
                monthValue = "10";
                break;
            case "november":
                monthValue = "11";
                break;
            case "december":
                monthValue = "12";
                break;
            default:
                break;
        
        }
        return monthValue;
    }
    public static Month getMonthValueString(String monthString){
        //an example of using this method is if the end-user picks a month
        //from a combobox (e.g. June) and you needed to get its LocalDate value
        Month month = null;
        switch(monthString.toLowerCase()){
            case "january":
                month = Month.JANUARY;
                break;
            case "february":
                month = Month.FEBRUARY;
                break;
            case "march":
                month = Month.MARCH;
                break;
            case "april":
                month = Month.APRIL;
                break;
            case "may":
                month = Month.MAY;
                break;
            case "june":
                month = Month.JUNE;
                break;
            case "july":
                month = Month.JULY;
                break;
            case "august":
                month = Month.AUGUST;
                break;
            case "september":
                month = Month.SEPTEMBER;
                break;
            case "october":
                month = Month.OCTOBER;
                break;
            case "november":
                month = Month.NOVEMBER;
                break;
            case "december":
                month = Month.DECEMBER;
                break;
            default:
                break;
        }
        return month;
    }
    /**
     * Use if you want a SQL date based on current date
     * @return current java.sql.Date
     */
    public static java.sql.Date getSqlDate() {
        LocalDate today = getCurrentDate();
        java.sql.Date sqlDate = java.sql.Date.valueOf(today);
        return sqlDate;
    }
     /**
     * Use if you want a SQL date based on a specific LocalDate
     * @param date of type LocalDate
     * @return java.sql.Date
     */
    public static java.sql.Date getSqlDate(LocalDate date){ 
        java.sql.Date sqlDate = null;
        if(date != null){
            sqlDate = java.sql.Date.valueOf(date);
        } 
      return sqlDate;   
    }
    /**
     * Use if you have a SQL Date but need a LocalDate
     * @param sqlDate
     * @return LocalDate
     */
    public static LocalDate getLocalDateFromSqlDate(java.sql.Date sqlDate){
        LocalDate date = null;
        if(sqlDate != null){
            date = sqlDate.toLocalDate();
        }
        return date;
    }


}//end of class
