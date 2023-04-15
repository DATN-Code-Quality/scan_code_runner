package org.hcmus.datn.temporal.activities;

import org.hcmus.datn.temporal.model.response.ResponseObject;
import org.hcmus.datn.temporal.model.response.Submission;
import org.hcmus.datn.worker.SonarWorker;

public class ScannerActivitiesImpl implements ScannerActivities{
    @Override
    public void scanCode(Submission submission) {
        System.out.println("start activity");

        SonarWorker worker=new SonarWorker();
        worker.setUserID(submission.getUserId());
        worker.setAssignmentID(submission.getAssignmentId());
        worker.setSubmissionURL(submission.getLink());

        //call worker to run
        try {
            Boolean result = worker.run();
            for (int count = 0; result == false && count < 3; count++){
                result = worker.run();
            }

            if (result == false){
                throw new Exception("Cann't scan this project");
            }
        }
        catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
