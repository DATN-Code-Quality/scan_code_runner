package org.hcmus.datn.handlers;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileHandler {
    public static boolean getFileFromStream(InputStream inputStream,String destinationFolder,String fileName) throws IOException {
        File outputFolder=new File(destinationFolder);
        if(!outputFolder.exists()&&!outputFolder.isDirectory())
        {
            throw new IOException("Output folder is not valid!");
        }

        FileOutputStream fout=new FileOutputStream(new File(destinationFolder+"/"+fileName));
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
        if(file.exists()){
            return file.delete();
        }
        return false;
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
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }
        return destFile;
    }

}
