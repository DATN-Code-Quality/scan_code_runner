package org.hcmus.datn;

import org.hcmus.datn.temporal.worker.ScannerWorker;
import org.hcmus.datn.worker.SonarWorker;

import java.io.IOException;
import java.text.ParseException;

public class Main {


    public static void main(String[] args) throws IOException, ParseException, InterruptedException {
        //test file template
//        String userID = "01f5d8de-a0c8-4b19-aa10-9e15f9a9a29c";
//        String assignmentID = "0293aa94-3e86-4312-b19c-a618131b4fd8";
//        String submissionURL = "https://codeload.github.com/anhvinhphan659/CountReact/zip/refs/heads/master";
//        //init worker and set information to run
//        SonarWorker worker=new SonarWorker();
//        worker.setUserID(userID);
//        worker.setAssignmentID(assignmentID);
//        worker.setSubmissionURL(submissionURL);
//        //call worker to run
//        worker.run();

        ScannerWorker scannerWorker = new ScannerWorker();
        scannerWorker.setupAndStart();

    }
}
