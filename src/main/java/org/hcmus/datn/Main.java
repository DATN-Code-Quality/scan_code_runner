package org.hcmus.datn;

import okhttp3.Response;
import org.hcmus.datn.handlers.FileHandler;
import org.hcmus.datn.services.HttpService;
import org.hcmus.datn.services.ScannerService;
import org.hcmus.datn.utils.ScanResult;
import org.hcmus.datn.worker.SonarWorker;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Main {
    static final String _HOST = "https://af2c-54-151-141-45.ap.ngrok.io";
    static final String _USERNAME = "admin";
    static final String _PASSWORD = "123456";

    public static void main(String[] args) {
        //test file template
        String userID = "19120721";
        String assignmentID = "ASS_0001";
        String submissionURL = "https://codeload.github.com/anhvinhphan659/CountReact/zip/refs/heads/master";
        //init worker and set information to run
        SonarWorker worker=new SonarWorker();
        worker.setUserID(userID);
        worker.setAssignmentID(assignmentID);
        worker.setSubmissionURL(submissionURL);
        //call worker to run
        worker.run();
    }


}