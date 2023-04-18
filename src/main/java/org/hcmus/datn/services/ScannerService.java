package org.hcmus.datn.services;

import com.google.gson.JsonObject;
import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;
import org.hcmus.datn.common.Constant;
import org.hcmus.datn.handlers.FileHandler;
import org.hcmus.datn.temporal.model.response.Result;
import org.hcmus.datn.utils.JsonUtils;
import org.hcmus.datn.utils.ScanResult;
import org.hcmus.datn.worker.SonarConfig;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ScannerService {
    private String hostURL = "";
    private String username = "";
    private String password = "";

    static final String SUCCESS_MSG = "EXECUTION SUCCESS";
    static final String ERROR_EXCUTION_MSG = "Error during SonarScanner execution";
    static final String ERROR = "ERROR";

    private HashMap<String, String> headers = new HashMap<>();

    public ScannerService(String hostURL, String username, String password) {
        this.hostURL = hostURL;
        this.username = username;
        this.password = password;

        headers.put("Authorization", Credentials.basic(username, password));
    }

    public ScanResult scanProject(String projectPath, String projectKey, String token) {
        ScanResult result = ScanResult.UNKNOWN;
        //generate command to run
        String command=buildTerminalCommand(projectPath,projectKey,token);
        //call terminal to run
        ProcessBuilder builder = new ProcessBuilder(
                "bash","-c", String.format("cd \"%s\" && %s", projectPath, command));
        builder.redirectErrorStream(true);

        Process p = null;
        BufferedReader r = null;
        try {
            p = builder.start();

            r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while (true) {
                line = r.readLine();
                if (line == null) {
                    break;
                }
                if (line.contains(ERROR)) {
                    if (line.contains(ERROR_EXCUTION_MSG)) {
                        result = ScanResult.ERROR;
                        break;
                    }

                }
                if (line.contains(SUCCESS_MSG)) {
                    result = ScanResult.SUCCESS;

                }
                System.out.println(line);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (r != null) {
            try {
                r.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        return result;
    }

    public boolean createNewProject(String projectName) {
        HashMap<String, String> params = new HashMap<>();
        params.put("project", projectName);
        params.put("name", projectName);

        Request createProject = HttpService.newPostRequest(
                hostURL + "/api/projects/create", new FormBody.Builder().build(), params, headers);
        Response res = HttpService.excuteRequest(createProject);
        int statusCode = res.code();
        try {
            if (statusCode == 200) {
                JSONObject resBody = new JSONObject(res.body().string());
                //invalid respond json
                if (resBody.get("project") == null) {
                    return false;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return statusCode == 200;
    }

    public String generateNewToken(String tokenName) {
        HashMap<String, String> params = new HashMap<>();

        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("name", tokenName);
        formBuilder.add("login", username);

        Request createToken = HttpService.newPostRequest(
                hostURL + "/api/user_tokens/generate", formBuilder.build(), params, headers);
        Response res = HttpService.excuteRequest(createToken);
        int statusCode = res.code();
        try {
            if (statusCode == 200) {
                JSONObject resBody = new JSONObject(res.body().string());
                String token = resBody.getString("token");
                //invalid respond json
                if (token == null) {
                    return "";
                }
                return token;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "";
    }

    private String buildTerminalCommand(String projectPath, String projectKey, String token) {
        String command = "";
        ProjectType projectType = SonarSensor.getTypeOfProject(projectPath);
        switch (projectType) {
            case C_SHARP:
                command += "dotnet sonarscanner begin /k:\"" + projectKey + "\" /d:sonar.host.url=\"" + hostURL + "\"  /d:sonar.login=\"" + token + "\" ";
                command += "&& dotnet build ";
                command += "&& dotnet sonarscanner end /d:sonar.login=\"" + token + "\"";
                break;
            case JAVA_MAVEN:
                command = "mvn sonar:sonar" + "  -Dsonar.projectKey=" + projectKey +
                        "  -Dsonar.host.url=" + hostURL +
                        "  -Dsonar.login=" + token;
                break;
                //TODO: implement later
//            case JAVA_GRADLE:
//
//                break;
            default:
                //create config file
                SonarConfig sonarConfig = new SonarConfig();
                sonarConfig.setProjectKey(projectKey);
                sonarConfig.setLogin(token);
                sonarConfig.setHostUrl(hostURL);
                try {
                    System.out.println("Current folder path: " + projectPath);
                    sonarConfig.writeConfigToFile(projectPath);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                command="/home/lap15454/Downloads/sonar-scanner-cli-4.8.0.2856-linux/sonar-scanner-4.8.0.2856-linux/bin/sonar-scanner";
                break;
        }
        return command;
    }

    public static String generateID(String userID, String assignmentID) {

        return userID + "_" + assignmentID + "_" + new Date().getTime();
    }

    public String getHostURL() {
        return hostURL;
    }

    public void setHostURL(String hostURL) {
        this.hostURL = hostURL;
    }


    public Result getResultOverview(String projectId, String projectKey) throws IOException {
        Result result = new Result(projectId);

        Map<String, Integer> map = new HashMap<>();

        HashMap<String, String> params = new HashMap<>();
        params.put("componentKeys", projectKey);

        for (int i = 0; i < Constant.ISSUE_TYPES.length; i++) {
            params.put("types", Constant.ISSUE_TYPES[i]);

            Request getResult = HttpService.newGetRequest(Constant.GET_RESULT_API, params, headers);
            System.out.println(getResult);
            Response res = HttpService.excuteRequest(getResult);

            int statusCode = res.code();
            if (statusCode == 200) {
                JsonObject obj = JsonUtils.toObject(res.body().string());
                map.put(Constant.ISSUE_TYPES[i], obj.get("total").getAsInt());
            }
        }
        result.setSmells(map.get(Constant.ISSUE_TYPES[0]));
        result.setBugs(map.get(Constant.ISSUE_TYPES[1]));
        result.setVulnerabilities(map.get(Constant.ISSUE_TYPES[2]));

        return result;
    }

    public static String getShellScript()
    {
        String shellScript="";
        String osName=FileHandler.getNameOfOS().toLowerCase();
        if(osName.contains("win"))
        {
            shellScript="cmd.exe /c";
        }

        else if(osName.contains("linux"))
        {
            shellScript="/bin/bash && -c && ";

        }else if (osName.contains("mac"))
        {
            shellScript="/bin/sh -c";
        }

        return  shellScript;

    }
}

