package org.hcmus.datn.temporal.activities;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;
import org.hcmus.datn.temporal.model.response.Submission;

@ActivityInterface
public interface ScannerActivities {
    @ActivityMethod()
    void scanCode(Submission submission) ;
}
