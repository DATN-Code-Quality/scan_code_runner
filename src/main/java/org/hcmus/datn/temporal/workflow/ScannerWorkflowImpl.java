package org.hcmus.datn.temporal.workflow;

import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import org.hcmus.datn.temporal.activities.ScannerActivities;
import org.hcmus.datn.temporal.model.response.ResponseObject;
import org.hcmus.datn.temporal.model.response.Submission;

import java.io.IOException;
import java.text.ParseException;
import java.time.Duration;

public class ScannerWorkflowImpl implements ScannerWorkflow{
    private final RetryOptions retryoptions = RetryOptions.newBuilder()
            .setInitialInterval(Duration.ofSeconds(1))
            .setMaximumInterval(Duration.ofSeconds(100))
            .setBackoffCoefficient(2)
            .setMaximumAttempts(500)
            .build();
    private final ActivityOptions defaultActivityOptions = ActivityOptions.newBuilder()
            // Timeout options specify when to automatically timeout Activities if the process is taking too long.
            .setStartToCloseTimeout(Duration.ofSeconds(5))
            // Optionally provide customized RetryOptions.
            // Temporal retries failures by default, this is simply an example.
            .setRetryOptions(retryoptions)
            .build();
    private final ScannerActivities scannerActivities = Workflow.newActivityStub(ScannerActivities.class, defaultActivityOptions);

    @Override
    public ResponseObject scanCode(Submission submission)  {
        return scannerActivities.scanCode(submission);
    }
}
