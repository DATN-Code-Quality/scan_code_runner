package org.hcmus.datn.temporal.activities;

import org.hcmus.datn.services.DatabaseService;
import org.hcmus.datn.temporal.model.request.Submission;
import org.hcmus.datn.utils.SubmissionStatus;
import org.hcmus.datn.worker.SonarWorker;

public class ScannerActivitiesImpl implements ScannerActivities{
    @Override
    public void scanCode(Submission submission) {
        SonarWorker worker=new SonarWorker();
        worker.setUserID(submission.getUserId());
        worker.setAssignmentID(submission.getAssignmentId());
        worker.setSubmissionURL(submission.getLink());
        worker.setSubmissionID(submission.getId());
//        worker.setSubmission(submission);
        //call worker to run
        try {
            DatabaseService.updateSubmisionStatus(submission.getId(), SubmissionStatus.SCANNING);

            Boolean result = worker.run();
//            for (int count = 0; result == false && count < 3; count++){
//                result = worker.run();
//
//
//            }

            if (result == false){
                DatabaseService.updateSubmisionStatus(submission.getId(), SubmissionStatus.SCANNING_FALI);
                throw new Exception("Cannot scan this project");
            }
        }
        catch (Exception e){
            DatabaseService.updateSubmisionStatus(submission.getId(), SubmissionStatus.SCANNING_FALI);

            throw new RuntimeException(e.getMessage());
        }
    }
}
