package org.hcmus.datn.services;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.hcmus.datn.common.Constant;
import org.hcmus.datn.temporal.model.request.Project;
import org.hcmus.datn.temporal.model.request.Result;
import org.hcmus.datn.temporal.model.response.Response;
import org.hcmus.datn.temporal.model.response.ResponseProject;
import org.hcmus.datn.temporal.model.response.ResponseResult;
import org.hcmus.datn.temporal.workflow.ProjectWorkflow;
import org.hcmus.datn.temporal.workflow.ResultWorkflow;

import java.io.IOException;
import java.text.ParseException;
import java.util.LinkedHashMap;

public class DatabaseService {
    public static void addProjectAndResult(ScannerService scannerService, Project project) throws IOException, ParseException {
        Response projectResponse =  ProjectWorkflow.createProject(project);

        if(projectResponse.getError() == 0){
            ResponseProject savedproject = ResponseProject.toResponseProject((LinkedHashMap<String, Object>) projectResponse.getData());

            Result result = scannerService.getResultOverview( savedproject.getId(), savedproject.getKey());

            Response resultResponse = ResultWorkflow.createProject(result);
            if (resultResponse.getError() == 0){
                ResponseResult savedResult = ResponseResult.toResponseResult((LinkedHashMap<String, Object>) resultResponse.getData());
                System.out.println(savedproject.toString());
                System.out.println(savedResult.toString());
            }

        }


    }
}
