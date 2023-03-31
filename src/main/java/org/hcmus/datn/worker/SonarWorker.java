package org.hcmus.datn.worker;

import okhttp3.Response;
import org.hcmus.datn.common.Constant;
import org.hcmus.datn.handlers.FileHandler;
import org.hcmus.datn.services.HttpService;
import org.hcmus.datn.services.ScannerService;
import org.hcmus.datn.temporal.workflow.ProjectWorkflow;
import org.hcmus.datn.temporal.model.request.Project;
import org.hcmus.datn.utils.ScanResult;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class SonarWorker {
    private ScannerService scannerService;

    private String userID;
    private String assignmentID;
    private String submissionURL;

    ///Please ensure userID, assignmentID, submissionURL is initialize and valid
    public void run()
    {
        //TODO: Replace with other config later
        //generate service
        ScannerService scannerService = new ScannerService(Constant.SONARQUBE_HOST, Constant.SONARQUBE_USERNAME, Constant.SONARQUBE_PASSWORD);
        //create temp folder to handle
        File tempFolder = new File("temp");
        if (!tempFolder.exists()) {
            tempFolder.mkdirs();
        }
        //generate ID
        String projectId = ScannerService.generateID(userID, assignmentID);

        //download file from URL
        Response response = HttpService.excuteRequest(HttpService.newGetRequest(submissionURL, new HashMap<>(), new HashMap<>()));
        try {
            String saveFileName = projectId + ".zip";
            boolean downloaded = FileHandler.getFileFromStream(response.body().byteStream(), tempFolder.getPath(), saveFileName);
            //unzip file (if zipped)
            String extractedFolderPath = "";
            if (downloaded) {
                extractedFolderPath = FileHandler.extractArchiveFile(tempFolder.getPath() + "/" + saveFileName, tempFolder.getPath());
            }

            boolean projectCreated = scannerService.createNewProject(projectId);
            if (!projectCreated) {
                throw new Exception("Error create new project Sonarqube");
            }
            String token = scannerService.generateNewToken(projectId);
            if (token.isEmpty()) {
                throw new Exception("Error generate token Sonarqube");
            }
            //scan source code
            if (!extractedFolderPath.isEmpty()) {
                System.out.println("Extract folder path: " + extractedFolderPath);

                ScanResult result= scannerService.scanProject(extractedFolderPath, projectId, token);
                System.out.println(result);


            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Save project and result in to DataBase
        ProjectWorkflow.createProject(new Project(projectId, userID, assignmentID));

        //clean up folder
        if(tempFolder.exists())
        {
            tempFolder.delete();
        }

    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getAssignmentID() {
        return assignmentID;
    }

    public void setAssignmentID(String assignmentID) {
        this.assignmentID = assignmentID;
    }

    public String getSubmissionURL() {
        return submissionURL;
    }

    public void setSubmissionURL(String submissionURL) {
        this.submissionURL = submissionURL;
    }
}
