package org.hcmus.datn.temporal.workflow;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.client.WorkflowStub;
import io.temporal.serviceclient.WorkflowServiceStubs;
import org.hcmus.datn.common.Constant;
import org.hcmus.datn.temporal.model.request.Project;
import org.hcmus.datn.temporal.model.response.Response;

import java.time.Duration;
import java.util.Date;
import java.util.LinkedHashMap;

public class ProjectWorkflow {
    static public Response createProject(Project project){
        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        WorkflowClient client = WorkflowClient.newInstance(service);
        WorkflowStub workflow =
                client.newUntypedWorkflowStub(
                        "createProject",
                        WorkflowOptions.newBuilder()
                                .setTaskQueue(Constant.TASKQUEUE_PROJECT)
                                .setWorkflowId("database-service-workflow" + new Date().getTime())
                                .setWorkflowRunTimeout(Duration.ofMinutes(5))
                                .build());

        workflow.start(project);
        LinkedHashMap<String, Object> response = workflow.getResult(LinkedHashMap.class);

        return  Response.toResponse(response);

//        System.out.println(result.get(Constant.DATABASE_SERVICE_RESPONSE_ERROR));
//        System.out.println(result.get(Constant.DATABASE_SERVICE_RESPONSE_DATA).getClass());
    }
}
