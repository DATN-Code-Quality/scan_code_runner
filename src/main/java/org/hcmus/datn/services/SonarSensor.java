package org.hcmus.datn.services;


import org.hcmus.datn.handlers.FileHandler;

import java.io.File;
import java.util.ArrayList;

enum ProjectType{

    JAVA_MAVEN,
    JAVA_GRADLE,
    C_SHARP,
    C_CPP,
    OTHERS,

}
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
            if(f.getPath().endsWith(".csproj"))
            {
                return ProjectType.C_SHARP;
            }
            if(f.getPath().endsWith(".cpp")||f.getPath().endsWith(".c"))
            {
                return ProjectType.C_CPP;
            }
        }

        return ProjectType.OTHERS;
    }

}
