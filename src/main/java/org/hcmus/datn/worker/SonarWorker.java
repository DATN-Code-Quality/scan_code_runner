package org.hcmus.datn.worker;

import okhttp3.Response;
import org.eclipse.jgit.api.Git;
import org.hcmus.datn.common.Config;
import org.hcmus.datn.handlers.FileHandler;
import org.hcmus.datn.services.DatabaseService;
import org.hcmus.datn.services.HttpService;
import org.hcmus.datn.services.ScannerService;
import org.hcmus.datn.temporal.model.response.Project;
import org.hcmus.datn.temporal.model.request.Submission;
import org.hcmus.datn.utils.ScanResult;
import org.hcmus.datn.utils.SubmissionStatus;
import org.hcmus.datn.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;

public class SonarWorker {
    private ScannerService scannerService;

    private String userID;
    private String assignmentID;
    private String submissionURL;
    private String submissionID;
//    private Submission submission;

    ///Please ensure userID, assignmentID, submissionURL is initialize and valid
    public boolean run() {
        //TODO: Replace with other config later
        //Set submission status = SCANNING
        //generate service
        ScannerService scannerService = new ScannerService(Config.get("SONARQUBE_HOST"), Config.get("SONARQUBE_USERNAME"), Config.get("SONARQUBE_PASSWORD"));
        //create temp folder to handle
        File tempFolder = new File("temp");
        if (!tempFolder.exists()) {
            tempFolder.mkdirs();
        }
        //generate ID
        String projectId = ScannerService.generateID(userID, assignmentID);

        try
        {
            Project project = DatabaseService.findProjectByKey(projectId);
            if(project == null){
                if(scannerService.createNewProject(projectId)){
                    if(scannerService.addProjectIntoGate(assignmentID, projectId)){
                        DatabaseService.createProject(new Project(projectId, userID, submissionID));
                    }
                    else{
                        return false;
                    }
                }
                else{
                    return false;
                }

            }
        }catch (Exception e){
            return false;
        }

        try {
            String extractedFolderPath = "";

            if (submissionURL.contains("https://github.com/")){
                Git.cloneRepository()
                        .setURI(submissionURL)
                        .setDirectory(Paths.get("temp/"+submissionID+"/").toFile())
                        .call();

                extractedFolderPath = tempFolder.getPath() + "/" + submissionID;
            }
            else{
                if(submissionURL.contains("http")){
                    Response response = HttpService.excuteRequest(HttpService.newGetRequest(submissionURL, new HashMap<>(), new HashMap<>()));

                    String saveFileName = projectId + ".zip";
                    boolean downloaded = FileHandler.getFileFromStream(response.body().byteStream(), tempFolder.getPath() + "/zipFile", saveFileName);
                    //unzip file (if zipped)
                    if (downloaded) {
                        extractedFolderPath = FileHandler.extractArchiveFile(tempFolder.getPath() + "/zipFile/" + saveFileName, tempFolder.getPath() + "/" +submissionID);
                    }
                }
                else{
                    extractedFolderPath = FileHandler.extractArchiveFile(submissionURL, tempFolder.getPath() + "/" +submissionID);
                }
            }

            System.out.println(extractedFolderPath);



            String token = scannerService.generateNewToken(projectId);
            if (token.isEmpty()) {
                throw new Exception("Error generate token Sonarqube");
            }
            System.out.println(extractedFolderPath);

            //scan source code
            if (!extractedFolderPath.isEmpty()) {
                System.out.println("Extract folder path: " + extractedFolderPath);
                ScanResult result= scannerService.scanProject(extractedFolderPath, projectId, token);

                System.out.println("Scan result: " + result);
                if (!result.equals(ScanResult.SUCCESS)){
                    return false;
                }
                else{
                    String status = scannerService.getResult(projectId);

                    if(status.equals("ERROR")){
                        DatabaseService.updateSubmisionStatus(submissionID, SubmissionStatus.FAIL);
                    } else if (status.equals("OK")) {
                        DatabaseService.updateSubmisionStatus(submissionID, SubmissionStatus.PASS);
                    }else {
                        return false;
                    }

                }
            }
            else{
                return false;
            }

        } catch (IOException e) {
            return false;
//            throw new RuntimeException(e);
        } catch (Exception e) {
            return false;
//            throw new RuntimeException(e);
        }
        //TODO: nêm xóa dir sau khi scanner xong
//        if(tempFolder.exists())
//        {
//            System.out.println(tempFolder.getPath());
//            Utils.deleteDir(tempFolder);
//        }


        //clean up folder

        return true;

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

    public String getSubmissionID() {
        return submissionID;
    }

    public void setSubmissionID(String submissionID) {
        this.submissionID = submissionID;
    }

//    public Submission getSubmission() {
//        return submission;
//    }
//
//    public void setSubmission(Submission submission) {
//        this.submission = submission;
//    }
}
