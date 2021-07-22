package com.example.usermanagement.ultil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Validator {

    private static List<SimpleDateFormat> dateFormats = new ArrayList<SimpleDateFormat>() {
        {
            add(new SimpleDateFormat("dd/MM/yyyy"));
            add(new SimpleDateFormat("MM/dd/yyyy"));
            add(new SimpleDateFormat("dd.MM.yyyy"));
            add(new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a"));
            add(new SimpleDateFormat("dd.MM.yyyy hh:mm:ss a"));
            add(new SimpleDateFormat("dd.MMM.yyyy"));
            add(new SimpleDateFormat("dd-MMM-yyyy"));
            add(new SimpleDateFormat("yyyy-MM-dd"));
        }
    };
    public static boolean isDate(String dateStr) {
        Date date = null;
        if (dateStr == null) {
            return false;
        }
        for (SimpleDateFormat format : dateFormats) {
            try {
                format.setLenient(false);
                date = format.parse(dateStr);
            } catch (Exception ex) {
                System.out.println(ex);
            }
            if (date != null) {
                return true;
            }
        }

        return false;
    }
    public static Date stringToDate(String dateStr){
        Date date= null;
        if(dateStr.equals("") || dateStr==null){
            return null;
        }
        for(SimpleDateFormat format: dateFormats){
            try{
                format.setLenient(false);
                date = format.parse(dateStr);
            }catch (Exception ex){
//                System.out.println(ex);
            }
            if(date != null){
                break;
            }
        }
        return date;

    }
    //            String date[] = dateStr.split("/");
//        Calendar instance = Calendar.getInstance();
//        instance.set(Calendar.YEAR,Integer.parseInt(date[2]));
//        instance.set(Calendar.MONTH, Integer.parseInt(date[1]) -1);
//        instance.set(Calendar.DAY_OF_MONTH,Integer.parseInt(date[0]));
//        return instance.getTime();
}
