package org.hcmus.datn.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static String getFileNameFromUrl(String fileUrl){
      String[] names=  fileUrl.split("/");
      return names[names.length-1];
    }

    public static Date convertDateFromString(String day) throws ParseException {
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if(day != null){
            return formatter.parse((day).replace('T', ' '));
        }
        return null;
    }
}