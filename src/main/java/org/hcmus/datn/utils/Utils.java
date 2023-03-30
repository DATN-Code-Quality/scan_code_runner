package org.hcmus.datn.utils;

public class Utils {
    public static String getFileNameFromUrl(String fileUrl){
      String[] names=  fileUrl.split("/");
      return names[names.length-1];
    }
    //make sure driveURL is link of specified file
    public static String getLinkFileFromGGDriveShare(String driveURL)
    {
        String url="https://drive.google.com/uc?id=";
        String fileID="";
        String[] elements=url.split("/");

        for(int i=0;i<elements.length;i++)
        {
            if(elements[i].compareTo("d")==0)
            {
                fileID=elements[i+1];
                break;
            }
        }
        return url+fileID;
    }
}