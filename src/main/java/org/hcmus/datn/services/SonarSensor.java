package org.hcmus.datn.services;


import org.hcmus.datn.handlers.FileHandler;
import org.hcmus.datn.utils.ProjectType;

import java.io.File;
import java.util.ArrayList;


public class SonarSensor {
    /**
     * Detect type of project like:
     * C#
     * Java: Maven, Gradle
     * Others: Python, JavaScript, Go, ...
     */

    public static ProjectType getTypeOfProject(String projectPath)
    {

        ArrayList<File> files= FileHandler.getAllFileOfFolder(projectPath);
        System.out.println(files.size());
        for (File f:files) {

            if(f.getPath().contains("pom.xml"))
            {
                return ProjectType.JAVA_MAVEN;
            }
            if(f.getPath().endsWith("build.gradle")){
                return ProjectType.JAVA_GRADLE;
            }
            if(f.getPath().endsWith(".csproj"))
            {
                return ProjectType.C_SHARP;
            }
            if(f.getPath().endsWith(".cpp"))
            {
                return ProjectType.C_CPP;
            }
            if(f.getPath().endsWith(".c"))
            {
                return ProjectType.C;
            }
        }

        return ProjectType.DEFAULT;
    }

    public  static ProjectType getLanguage(String projectPath, StringBuilder rootPath) throws Exception {
        File file = new File(projectPath);

        if(file.exists()){
            if(file.isDirectory()){
                File[] fileList = file.listFiles();
                ArrayList<File> files = new ArrayList<>();
                ArrayList<File> directories = new ArrayList<>();

                for(int i = 0; i < fileList.length; i++){
                    if(fileList[i].isFile()){
                        files.add(fileList[i]);
                    }
                    else {
                        directories.add(fileList[i]);
                    }
                }
                for(int i = 0; i < files.size(); i++){
                    ProjectType type = getLanguage(files.get(i).getPath(), rootPath);
                    if (!type.equals(ProjectType.DEFAULT)){
                        return type;
                    }

                }
                for(int i = 0; i < directories.size(); i++){
                    ProjectType type = getLanguage(directories.get(i).getPath(), rootPath);
                    if (!type.equals(ProjectType.DEFAULT)){
                        return type;
                    }
                }

                return ProjectType.DEFAULT;
            }else{
                if(file.getPath().endsWith(".zip")){
                    String extractArchiveFilePath = FileHandler.extractArchiveFile(file.getPath(), file.getParent() + "/" + file.getName().substring(0, file.getName().length() - 4));
                    return getLanguage(extractArchiveFilePath, rootPath);
                }
                if(file.getPath().contains("pom.xml"))
                {
                    rootPath.append(file.getParent().toString());
                    return ProjectType.JAVA_MAVEN;
                }
                if(file.getPath().endsWith("build.gradle")){
                    rootPath.append(file.getParent().toString());
                    return ProjectType.JAVA_GRADLE;
                }
                if(file.getPath().endsWith(".csproj"))
                {
                    rootPath.append(file.getParent().toString());
                    return ProjectType.C_SHARP;
                }
                if(file.getPath().endsWith(".cpp"))
                {
                    rootPath.append(file.getParent().toString());
                    return ProjectType.C_CPP;
                }
                if(file.getPath().endsWith(".c"))
                {
                    rootPath.append(file.getParent().toString());
                    return ProjectType.C;
                }
                return ProjectType.DEFAULT;

            }
        }
//        else{
//            return ProjectType.OTHERS;
//        }
        throw new Exception("Path not exist");
    }

}
