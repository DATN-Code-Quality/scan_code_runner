package org.hcmus.datn.temporal.activities;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;
import org.hcmus.datn.temporal.model.response.ResponseObject;
import org.hcmus.datn.temporal.model.response.Submission;

import java.io.IOException;
import java.text.ParseException;

@ActivityInterface
public interface ScannerActivities {
    @ActivityMethod
    public ResponseObject scanCode(Submission submission) throws IOException, ParseException, InterruptedException;
}
