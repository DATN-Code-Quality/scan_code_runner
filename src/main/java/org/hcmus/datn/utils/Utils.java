package org.hcmus.datn.utils;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static String getFileNameFromUrl(String fileUrl) {
        String[] names = fileUrl.split("/");
        return names[names.length - 1];
    }

    public static Date convertDateFromString(String day) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (day != null) {
            return formatter.parse((day).replace('T', ' '));
        }
        return null;
    }

    public static String formatdate(Date day) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (day != null) {
            return formatter.format(day);
        }
        return null;
    }

    //make sure driveURL is link of specified file
    public static String getLinkFileFromGGDriveShare(String driveURL) {
        String url = "https://drive.google.com/uc?id=";
        String fileID = "";
        String[] elements = url.split("/");

        for (int i = 0; i < elements.length; i++) {
            if (elements[i].compareTo("d") == 0) {
                fileID = elements[i + 1];
                break;
            }
        }
        return url + fileID;
    }

    public static void deleteDir(File file) {

        File[] list = file.listFiles();
        if (list != null) {
            for (File temp : list) {
                deleteDir(temp);
            }
        }
        file.delete();
    }
}