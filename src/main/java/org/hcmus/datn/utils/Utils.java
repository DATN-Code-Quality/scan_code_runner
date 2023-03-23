package org.hcmus.datn.utils;

public class Utils {
    public static String getFileNameFromUrl(String fileUrl){
      String[] names=  fileUrl.split("/");
      return names[names.length-1];
    }
}