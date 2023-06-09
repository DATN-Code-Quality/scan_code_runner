package org.hcmus.datn.temporal.worker;

import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import io.temporal.worker.WorkerOptions;
import org.hcmus.datn.common.Config;
import org.hcmus.datn.temporal.activities.ScannerActivitiesImpl;
import org.hcmus.datn.temporal.workflow.ScannerWorkflowImpl;

public class ScannerWorker {
    private static final String SCANNER_TASKQUEUE;

    static {
        SCANNER_TASKQUEUE = Config.get("SCANNER_TASKQUEUE");
    }

    public void setupAndStart() {
        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        WorkflowClient client = WorkflowClient.newInstance(service);
        WorkerFactory factory = WorkerFactory.newInstance(client);

        WorkerOptions.Builder optionBuilder = WorkerOptions.newBuilder();
        optionBuilder.setMaxConcurrentActivityTaskPollers(1);
        optionBuilder.setMaxConcurrentActivityExecutionSize(1);
//        optionBuilder.setMaxConcurrentWorkflowTaskExecutionSize(1);

        Worker worker = factory.newWorker(SCANNER_TASKQUEUE, optionBuilder.build());

        worker.registerWorkflowImplementationTypes(ScannerWorkflowImpl.class);
        worker.registerActivitiesImplementations(new ScannerActivitiesImpl());



        factory.start();
    }
}
