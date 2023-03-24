package org.hcmus.datn.services;

import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;
import org.hcmus.datn.utils.ScanResultStatus;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;

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

    public ScanResultStatus scanProject(String projectPath, String projectKey, String token) {
        ScanResultStatus result = ScanResultStatus.UNKNOWN;
        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", String.format("cd %s && sonar-scanner.bat -D\"sonar.projectKey=%s\" -D\"sonar.sources=.\" -D\"sonar.host.url=%s\" -D\"sonar.login=%s\"", projectPath, projectKey, hostURL, token));
        builder.redirectErrorStream(true);

        Process p = null;
        BufferedReader r=null;
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
                    if(line.contains(ERROR_EXCUTION_MSG))
                    {
                        result = ScanResultStatus.ERROR;
                        break;
                    }


                }
                if (line.contains(SUCCESS_MSG)) {
                    result = ScanResultStatus.SUCCESS;

                }
                System.out.println(line);
                if(r!=null)
                {
                    try {
                        r.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
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

    public static String generateID(String userID, String assignmentID) {

        return userID + "_" + assignmentID + "_" + new Date().getTime();
    }

    public String getHostURL() {
        return hostURL;
    }

    public void setHostURL(String hostURL) {
        this.hostURL = hostURL;
    }
}

