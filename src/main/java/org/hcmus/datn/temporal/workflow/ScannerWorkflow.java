package org.hcmus.datn.temporal.workflow;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;
import org.hcmus.datn.temporal.model.response.ResponseObject;
import org.hcmus.datn.temporal.model.response.Submission;

import java.io.IOException;
import java.text.ParseException;

@WorkflowInterface
public interface ScannerWorkflow {
    @WorkflowMethod()
    ResponseObject scanCode(Submission submission);
}
