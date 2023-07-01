package org.hcmus.datn.handlers;

import java.io.*;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileHandler {
    public static boolean getFileFromStream(InputStream inputStream,String destinationFolder,String fileName) throws IOException {
        File outputFolder=new File(destinationFolder);
        System.out.println("Output folder check");
        System.out.println(outputFolder.exists());
        System.out.println(outputFolder.isDirectory());
        if(!outputFolder.exists()&&!outputFolder.isDirectory())
        {
            throw new IOException("Output folder is not valid!");
        }

        FileOutputStream fout=new FileOutputStream(destinationFolder+"/"+fileName);
        int total=0;
        int count=0;
        byte[] data = new byte[1024];
        while ((count = inputStream.read(data)) != -1) {
            total += count;
            fout.write(data, 0, count);
        }
        System.out.println(total);
        fout.close();
        return true;
    }

    public static boolean deleteFile(String filePath)
    {
        File file=new File(filePath);
        if(!file.exists())
        {
            return false;
        }
        if(file.isFile())
        {
            return file.delete();
        }

        File[] files=file.listFiles();
        boolean result=true;
        //delete all children
        for(int i=0;i<files.length;i++)
        {
            File f=files[i];
            boolean fResult=false;
            if(f.isDirectory())
            {
                fResult=deleteFile(f.getPath());
            }
            else {
                fResult=f.delete();
            }
            result=result&&fResult;
        }
        //delete current folder
        return result&&file.delete();
    }

    public static ArrayList<File> getAllFileOfFolder(String folderPath)
    {
        ArrayList<File> files=new ArrayList<>();
        File file=new File(folderPath);

        if(file.exists()&&file.isDirectory())
        {
            File[] fileList=file.listFiles();
            for(int i=0;i<fileList.length;i++)
            {

                File f=fileList[i];
                if(f.isFile())
                {
                    files.add(f);
                } else if (f.isDirectory()) {
                    files.addAll(getAllFileOfFolder(f.getPath()));
                }

            }
        }

        return files;
    }



    public static String extractArchiveFile(String zipPath, String desPath) {
        File destDir = new File(desPath);
        byte[] buffer = new byte[1024];
        String filePath="";
        try {
            ZipInputStream zis = new ZipInputStream(new FileInputStream(zipPath));
            ZipEntry zipEntry = zis.getNextEntry();

            while (zipEntry != null) {
                File newFile = newFile(destDir, zipEntry);
                if(filePath.isEmpty())
                {
                    filePath= newFile.getAbsolutePath();
                }

                System.out.println("Extracting " + zipEntry.getName() + "...");
                if (zipEntry.isDirectory()) {
                    if (!newFile.isDirectory() && !newFile.mkdirs()) {
                        throw new IOException("Failed to create directory " + newFile);
                    }
                } else {
                    File parent = newFile.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new IOException("Failed to create directory " + parent);
                    }

                    // write file content
                    FileOutputStream fos = new FileOutputStream(newFile);
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
                }
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            return "";
        }

        return filePath;
    }
    public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        String fileName = convertString(zipEntry.getName());

//        File destFile = new File(destinationDir, zipEntry.getName());

        File destFile = new File(destinationDir, fileName);
        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + fileName);
        }
        return destFile;
    }

    public static String getNameOfOS()
    {
        return System.getProperty("os.name");
    }


    public static String convertString(String str){
        String nfdNormalizedString = Normalizer.normalize( str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return  pattern.matcher(nfdNormalizedString).replaceAll("");
    }

}
