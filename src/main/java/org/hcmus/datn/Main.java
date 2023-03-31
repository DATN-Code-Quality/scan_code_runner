package org.hcmus.datn;

import com.google.gson.JsonObject;
import org.hcmus.datn.common.Constant;
import org.hcmus.datn.services.DatabaseService;
import org.hcmus.datn.services.ScannerService;
import org.hcmus.datn.temporal.model.request.Project;
import org.hcmus.datn.temporal.workflow.ProjectWorkflow;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

public class Main {


    public static void main(String[] args) throws IOException, ParseException {
        //test file template
        String userID = "01f5d8de-a0c8-4b19-aa10-9e15f9a9a29c";
        String assignmentID = "ASS_0001";
//        String submissionURL = "https://codeload.github.com/anhvinhphan659/CountReact/zip/refs/heads/master";
//        //init worker and set information to run
//        SonarWorker worker=new SonarWorker();
//        worker.setUserID(userID);
//        worker.setAssignmentID(assignmentID);
//        worker.setSubmissionURL(submissionURL);
//        //call worker to run
//        worker.run();
        ScannerService scannerService = new ScannerService(Constant.SONARQUBE_HOST, Constant.SONARQUBE_USERNAME, Constant.SONARQUBE_PASSWORD);


        ProjectWorkflow.createProject(new Project(userID + "-" + assignmentID, userID));
//        JsonObject result =  scannerService.getResultOverview("FirstTest");
//        System.out.println(result);

        DatabaseService.addProjectAndResult(scannerService,new Project("FirstTest", userID) );
    }


}