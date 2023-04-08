package org.hcmus.datn.temporal.activities;

import org.hcmus.datn.temporal.model.response.ResponseObject;
import org.hcmus.datn.temporal.model.response.Submission;
import org.hcmus.datn.worker.SonarWorker;

import java.io.IOException;
import java.text.ParseException;

public class ScannerActivitiesImpl implements ScannerActivities{

    @Override
    public ResponseObject scanCode(Submission submission) {
        //test file template
//        String userID = "01f5d8de-a0c8-4b19-aa10-9e15f9a9a29c";
//        String assignmentID = "0293aa94-3e86-4312-b19c-a618131b4fd8";
//        String submissionURL = "https://codeload.github.com/anhvinhphan659/CountReact/zip/refs/heads/master";
        //init worker and set information to run

        SonarWorker worker=new SonarWorker();
        worker.setUserID(submission.getUserId());
        worker.setAssignmentID(submission.getAssignmentId());
        worker.setSubmissionURL(submission.getLink());
        //call worker to run
        ResponseObject result = worker.run();

        return result;

    }
}
