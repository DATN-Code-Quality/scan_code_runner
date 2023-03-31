package org.hcmus.datn.temporal.workflow;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.client.WorkflowStub;
import io.temporal.serviceclient.WorkflowServiceStubs;
import org.hcmus.datn.common.Constant;
import org.hcmus.datn.temporal.model.request.Result;
import org.hcmus.datn.temporal.model.response.Response;

import java.time.Duration;
import java.util.Date;
import java.util.LinkedHashMap;

public class ResultWorkflow {
    static public Response createProject(Result result){
        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        WorkflowClient client = WorkflowClient.newInstance(service);
        WorkflowStub workflow =
                client.newUntypedWorkflowStub(
                        "createResult",
                        WorkflowOptions.newBuilder()
                                .setTaskQueue(Constant.TASKQUEUE_RESULT)
                                .setWorkflowId("database-service-workflow" + new Date().getTime())
                                .setWorkflowRunTimeout(Duration.ofMinutes(5))
                                .build());

        workflow.start(result);
        LinkedHashMap<String, Object> response = workflow.getResult(LinkedHashMap.class);
        return  Response.toResponse(response);
    }

}
