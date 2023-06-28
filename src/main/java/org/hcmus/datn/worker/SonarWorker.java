package org.hcmus.datn.worker;

import okhttp3.Response;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.hcmus.datn.common.Config;
import org.hcmus.datn.handlers.FileHandler;
import org.hcmus.datn.services.DatabaseService;
import org.hcmus.datn.services.HttpService;
import org.hcmus.datn.services.ScannerService;
import org.hcmus.datn.services.SonarSensor;
import org.hcmus.datn.temporal.model.response.Project;
import org.hcmus.datn.utils.ProjectType;
import org.hcmus.datn.utils.ScanResult;
import org.hcmus.datn.utils.SubmissionStatus;
import org.hcmus.datn.utils.Utils;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;

public class SonarWorker {
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
        ScannerService scannerService = null;// new ScannerService(Config.get("SONARQUBE_HOST"), Config.get("SONARQUBE_USERNAME"), Config.get("SONARQUBE_PASSWORD"));
        ProjectType projectType = null;
        String token = null;
                //generate ID
        String projectId = ScannerService.generateID(userID, assignmentID);

    //create temp folder to handle
        File tempFolder = new File("temp");
        if (!tempFolder.exists()) {

            System.out.println("Generate temp folder");
            tempFolder.mkdirs();
            System.out.println("Temp folder path: "+tempFolder.getPath());
        }
        System.out.println("Handle file from URL: "+submissionURL);

        try {
            String extractedFolderPath = "";

            // Download and Extract File
            if (submissionURL.contains("https://github.com/")) {
                if(!submissionURL.endsWith(".git")){
                    submissionURL = submissionID + ".git";
                }
                CloneCommand cloneGitCmd = Git.cloneRepository().setURI(submissionURL).setDirectory(Paths.get("temp/" + submissionID + "/").toFile());

                Git git = cloneGitCmd.call();
                //TODO: please close clone process although it is autoclosable
                git.close();
                //DONE: handle git clone process
                extractedFolderPath = tempFolder.getPath() + "/" + submissionID;
            } else {
                if (submissionURL.contains("http")) {
                    Response response = HttpService.excuteRequest(HttpService.newGetRequest(submissionURL, new HashMap<>(), new HashMap<>()));
                    String saveFileName = projectId + ".zip";

                    String courseId = DatabaseService.getCourseId(assignmentID);
                    String newLink =  Config.get("PATH") + courseId + "/" + assignmentID +  "/"+ userID;
                    File newFolder = new File(newLink);
                    if (!newFolder.exists()) {
                        newFolder.mkdirs();
                    }
//                    boolean downloaded = FileHandler.getFileFromStream(response.body().byteStream(), tempFolder.getPath(), saveFileName);
                    boolean downloaded = FileHandler.getFileFromStream(response.body().byteStream(), newLink, saveFileName);
                    //unzip file (if zipped)
                    if (downloaded) {
//                        extractedFolderPath = FileHandler.extractArchiveFile(tempFolder.getPath() + "/" + saveFileName, tempFolder.getPath() + "/" + submissionID);
                        extractedFolderPath = FileHandler.extractArchiveFile(newLink + "/" + saveFileName, tempFolder.getPath() + "/" + submissionID);
                    }
                    newLink = "/" +  courseId + "/" + assignmentID +  "/"+ userID + "/" + saveFileName;
                    DatabaseService.updateSubmisionLink(submissionID, newLink);
                } else {
                    submissionURL = Config.get("PATH") + submissionURL;
                    extractedFolderPath = FileHandler.extractArchiveFile(submissionURL, tempFolder.getPath() + "/" + submissionID);
                }
            }

            projectType = SonarSensor.getTypeOfProject(extractedFolderPath);

            if (projectType.equals(ProjectType.C_CPP)){
                scannerService = new ScannerService(Config.get("SONARCLOUD_URL"));
            }else{
                scannerService = new ScannerService(Config.get("SONARQUBE_HOST"), Config.get("SONARQUBE_USERNAME"), Config.get("SONARQUBE_PASSWORD"));

                try {
                    scannerService.createNewProject(projectId);
                }catch (Exception e){}
                try {
                    scannerService.addProjectIntoGate(assignmentID, projectId);
                }catch (Exception e){}

                token = scannerService.generateNewToken(projectId);
                if (token.isEmpty()) {
                    throw new Exception("Error generate token Sonarqube");
                }
            }

            Project project = DatabaseService.findProjectByKey(projectId);
            if (project == null) {
                DatabaseService.createProject(new Project(projectId, userID, submissionID, projectType));
            }
            else{
                if (!projectType.equals(project.getType())){
                    DatabaseService.updateProjectType(project.getId(), projectType);
                }
            }

            //scan source code
            if (!extractedFolderPath.isEmpty()) {
                System.out.println("Extract folder path: " + extractedFolderPath);
                ScanResult result = scannerService.scanProject(extractedFolderPath, projectId, token);

                System.out.println("Scan result: " + result);
                if (!result.equals(ScanResult.SUCCESS)) {
                    System.err.println("Scan project result: FAILED");
                    return false;

                } else {
                    String status = null;

                    if(projectType.equals(ProjectType.C_CPP)){
                        status = scannerService.getResultFromCloud(projectId, assignmentID);
                    }
                    else{
                        status = scannerService.getResult(projectId);

                    }

                    if (status.equals("ERROR")) {
                        DatabaseService.updateSubmisionStatus(submissionID, SubmissionStatus.FAIL);
                    } else if (status.equals("OK")) {
                        DatabaseService.updateSubmisionStatus(submissionID, SubmissionStatus.PASS);
                    } else {
                        System.err.println("Scan project result: UNKNOWN");
                        return false;
                    }



                }
            } else {
                System.err.println("Error while extracting file");
                return false;
            }

        }
//        catch (IOException e) {
//            e.printStackTrace();
//            return false;
////            throw new RuntimeException(e);
//        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
//            throw new RuntimeException(e);
        } finally {
            if (tempFolder.exists()) {
                Utils.deleteDir(tempFolder);
            }

        }
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
