package org.hcmus.datn.services;

import com.google.gson.JsonObject;
import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;
import org.hcmus.datn.common.Config;
import org.hcmus.datn.common.Constant;
import org.hcmus.datn.handlers.FileHandler;
import org.hcmus.datn.temporal.model.response.Result;
import org.hcmus.datn.utils.JsonUtils;
import org.hcmus.datn.utils.ProjectType;
import org.hcmus.datn.utils.ScanResult;
import org.hcmus.datn.worker.SonarConfig;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ScannerService {
    static String osName;
    private ProjectType projectType;
    private String hostURL = "";
    private String username = "";
    private String password = "";

    static final String EXECUTION_SUCCESS_MSG = "EXECUTION SUCCESS";
    static final String BUILD_SUCCESS_MSG = "BUILD SUCCESS";
    static final String ERROR_EXCUTION_MSG = "Error during SonarScanner execution";
    static final String ERROR = "ERROR";

    private HashMap<String, String> headers = new HashMap<>();

    public ScannerService(String hostURL, String username, String password, ProjectType projectType) {
        this.hostURL = hostURL;
        this.username = username;
        this.password = password;
        //set default project type
        this.projectType = projectType;
        this.osName = FileHandler.getNameOfOS().toLowerCase();

        headers.put("Authorization", Credentials.basic(username, password));
    }

    public ScannerService(String hostURL) {
        this.hostURL = hostURL;

        this.projectType = ProjectType.C_CPP;
        this.osName = FileHandler.getNameOfOS().toLowerCase();
//        headers.put("Authorization", Credentials.basic(username, password));
    }

    public ScanResult scanProject(String projectPath, String projectKey, String token) {
        ScanResult result = ScanResult.UNKNOWN;
        ProcessBuilder builder = null;
        //generate command to run
        try{
            CompletableFuture<String> command=buildTerminalCommand(projectPath,projectKey,token);
            CompletableFuture.allOf(command).join();
            builder = getProcessBuilder(projectPath, command.get());

            builder.redirectErrorStream(true);
        }catch (IOException e){
            return  ScanResult.ERROR;
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //call terminal to run
//        ProcessBuilder builder = getProcessBuilder(projectPath, command);
//
//        builder.redirectErrorStream(true);

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
                if (line.contains(EXECUTION_SUCCESS_MSG) || line.contains(BUILD_SUCCESS_MSG)) {
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
        formBuilder.add("name", tokenName + new Date().getTime());
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

    public String getResult(String projectKey) throws InterruptedException {
        Thread.sleep(10000);
        HashMap<String, String> params = new HashMap<>();

        params.put("projectKey", projectKey);
        /** START SCOPE **/
        Request getResult = HttpService.newGetRequest(
                hostURL + "/api/qualitygates/project_status", params, headers);
        Response res = HttpService.excuteRequest(getResult);
        /** END SCOPE **/


        int statusCode = res.code();
        try {
            if (statusCode == 200) {
                JSONObject projectStatusObj = new JSONObject(res.body().string());
                String status = projectStatusObj.getJSONObject("projectStatus").getString("status");
                if (status == null) {
                    return "";
                }
                return status;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "";
    }

    public String getResultFromCloud(String projectKey, String assignmentId) throws InterruptedException {
        Thread.sleep(5000);
        HashMap<String, String> params = new HashMap<>();

        params.put("component", projectKey);
        params.put("metrics", "bugs,vulnerabilities,code_smells,duplicated_lines_density,coverage,violations,blocker_violations,critical_violations,major_violations,minor_violations,info_violations");

        Request getResult = HttpService.newGetRequest(
                hostURL + "/api/measures/search_history", params, headers);
        Response res = HttpService.excuteRequest(getResult);

        int statusCode = res.code();
        try {
            if (statusCode == 200) {
                JSONObject configObj = new JSONObject(DatabaseService.getConfigOfAssignment(assignmentId));
                JSONObject projectStatusObj = new JSONObject(res.body().string());

                int length = projectStatusObj.getJSONObject("paging").getInt("total");
                JSONArray measures = projectStatusObj.getJSONArray("measures");
                HashMap<String, Double> measuresMap = new HashMap<>();
                for(int i = 0; i < measures.length(); i++){
                    Double value = measures.getJSONObject(i).getJSONArray("history").getJSONObject(length -1).getDouble("value");
                    String metric = measures.getJSONObject(i).getString("metric");
                    try {
                        if(configObj.get(metric) != null ){
                            if( (configObj.getDouble(metric) > value && metric.equals("coverage")) || (configObj.getDouble(metric) < value && !metric.equals("coverage"))){
                                return "ERROR";
                            }
                        }
                    }catch (Exception e){}
                }
                return "OK";
            }
            else{
                System.out.println(res);
            }
        } catch (IOException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
        return "";
    }

    public boolean addProjectIntoGate(String gateName, String projectKey) {
        HashMap<String, String> params = new HashMap<>();
        params.put("gateName", gateName);
        params.put("projectKey", projectKey);

        Request createProject = HttpService.newPostRequest(
                hostURL + "/api/qualitygates/select", new FormBody.Builder().build(), params, headers);
        Response res = HttpService.excuteRequest(createProject);
        int statusCode = res.code();
        return statusCode == 204;
    }

    private CompletableFuture<String> buildTerminalCommand(String projectPath, String projectKey, String token) throws IOException {
        String command = "";
//        projectType = SonarSensor.getTypeOfProject(projectPath);
        switch (projectType) {
            case C_CPP:
                String build_wrapper_command = "";
                if (osName.contains("win")) {
                    build_wrapper_command = "build-wrapper-win-x86-64.exe";
                } else if (osName.contains("linux")) {
                    build_wrapper_command = "build-wrapper-linux-x86-64";
                } else if (osName.contains("mac")) {
                    build_wrapper_command = "build-wrapper-macosx-x86";
                }
                command += build_wrapper_command + " --out-dir bw-output g++ *.cpp";
                System.out.println("Wrapper command: " + command);
                //TODO: change later and set up to config file
                String sonarCloudOrganization = Config.get("SONARCLOUD_ORGANIZATION");
//                String sonarCloudProjKey = "cpp_test";
                String sonarCloudProjKey = projectKey;
                String sonarCloudToken = Config.get("SONARCLOUD_TOKEN");

//                command += " && sonar-scanner -D\"sonar.organization=" + sonarCloudOrganization + "\""
//                        + " -D\"sonar.projectKey=" + sonarCloudProjKey + "\""
//                        + " -D\"sonar.login=" + sonarCloudToken + "\""
//                        + " -D\"sonar.sources=.\" -D\"sonar.verbose=true\" -D\"sonar.cfamily.build-wrapper-output=bw-output\" -D\"sonar.host.url=https://sonarcloud.io\" ";
                command += " && sonar-scanner -D\"sonar.organization=" + sonarCloudOrganization + "\""
                        + " -D\"sonar.projectKey=" + sonarCloudProjKey + "\""
                        + " -D\"sonar.login=" + sonarCloudToken + "\""
                        + " -D\"sonar.sources=.\" -D\"sonar.verbose=true\" -D\"sonar.cfamily.build-wrapper-output=bw-output\" -D\"sonar.host.url=" + hostURL +"\" ";

                System.out.println("Final command: "+command);
                break;
            case C_SHARP:
                command += "dotnet-sonarscanner begin /k:\"" + projectKey + "\" /d:sonar.host.url=\"" + hostURL + "\"  /d:sonar.login=\"" + token + "\" ";
                command += "&& dotnet build ";
                command += "&& dotnet-sonarscanner end /d:sonar.login=\"" + token + "\"";
                break;
            case JAVA_MAVEN:
                command = "mvn sonar:sonar" + "  -Dsonar.projectKey=" + projectKey +
                        " -Dsonar.java.binaries=" + "./target/classes" +
                        " -Dsonar.host.url=" + hostURL +
                        " -Dsonar.login=" + token + " -X";

                // Compile project
                ProcessBuilder processBuilder = getProcessBuilder(projectPath, "mvn compile");
                processBuilder.redirectErrorStream(true).start();

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
//                command="/root/datn/scan_code_runner/sonar-scanner/bin/sonar-scanner";
                command = "sonar-scanner -D\"sonar.projectKey=" + projectKey + "\" -D\"sonar.sources=.\" -D\"sonar.host.url=" + hostURL + "\" -D\"sonar.login=" + token + "\"";
                System.out.println("Command: " + command);
                break;
        }
        return CompletableFuture.completedFuture(command);
    }

    public static String generateID(String userID, String assignmentID) {
//        return userID + "_" + assignmentID + "_" + new Date().getTime();
        return userID + "_" + assignmentID;
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

    private static ProcessBuilder getProcessBuilder(String projectPath, String command) {
        ProcessBuilder processBuilder = null;

        osName = FileHandler.getNameOfOS().toLowerCase();
        if (osName.contains("win")) {
            processBuilder = new ProcessBuilder(
                    "cmd.exe", "/c", String.format("cd \"%s\" && %s", projectPath, command));
            ;
        } else if (osName.contains("linux")) {

            processBuilder = new ProcessBuilder(
                    "bash", "-c", String.format("cd \"%s\" && %s", projectPath, command));
            ;
//            shellScript="bash -c";

        } else if (osName.contains("mac"))
        {

            processBuilder= new ProcessBuilder(
                    "/bin/sh", "-c"  , String.format("cd %s && %s", projectPath,command));;
//            shellScript="/bin/sh -c";
        }
        System.out.println(processBuilder.command());

        return  processBuilder;

    }
}

