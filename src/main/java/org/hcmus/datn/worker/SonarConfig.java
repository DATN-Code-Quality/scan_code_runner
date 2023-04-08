package org.hcmus.datn.worker;

import java.io.*;

public class SonarConfig {
    private String projectKey;
    private String hostUrl;
    private String login;
    private String sources;

    public static final String CONFIG_FILE_NAME  = "sonar-project.properties";

    public void writeConfigToFile(String outFolder) throws IOException {
        File outputFolder=new File(outFolder);
        //check folder
        if(!outputFolder.exists()||outputFolder.isFile())
        {
            throw new IOException("Folder is not valid");
        }
        File outFile=new File(outputFolder.getPath()+"/"+CONFIG_FILE_NAME);
        BufferedWriter bw=new BufferedWriter(new FileWriter(outFile));
        System.out.println(sources==null);
        //write data to config file
        bw.write(writeConfig(SonarFieldConstant.PROJECT_KEY,projectKey));
        bw.write(writeConfig(SonarFieldConstant.LOGIN,login));
        bw.write(writeConfig(SonarFieldConstant.HOST_URL,hostUrl));
        bw.write(writeConfig(SonarFieldConstant.SOURCE,sources));

        //finish write file
        bw.flush();
        bw.close();

    }

    private static String writeConfig(String constant,String value)
    {

        //not write empty
        if(value==null)
        {
            return "";
        }
        if(value.isEmpty())
        {
            return "";
        }
        return constant+" = "+value+"\n";
    }

    public String getProjectKey() {
        return projectKey;
    }

    public void setProjectKey(String projectKey) {
        this.projectKey = projectKey;
    }

    public String getHostUrl() {
        return hostUrl;
    }

    public void setHostUrl(String hostUrl) {
        this.hostUrl = hostUrl;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSources() {
        return sources;
    }

    public void setSources(String sources) {
        this.sources = sources;
    }
}
