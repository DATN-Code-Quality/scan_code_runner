package org.hcmus.datn.temporal.model.request;

import org.json.JSONObject;

import java.util.Date;

public class Project {
//    private String id;
    private String key;
    private String userId;
    private String submissionId;

//    private Date createdAt;
//    private Date updatedAt;
//    private Date deletedAt;

    public Project() {
    }
    public Project(String id, String key, String userId, String submissionId, Date createdAt, Date updatedAt, Date deletedAt) {
//        this.id = id;
        this.key = key;
        this.userId = userId;
        this.submissionId = submissionId;
//        this.createdAt = createdAt;
//        this.updatedAt = updatedAt;
//        this.deletedAt = deletedAt;
    }
    public Project(String key, String userId, String submissionId) {
        this.key = key;
        this.userId = userId;
        this.submissionId = submissionId;
    }




    public Project(String key, String userId) {
        this.key = key;
        this.userId = userId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(String submissionId) {
        this.submissionId = submissionId;
    }

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public Date getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(Date createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public Date getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(Date updatedAt) {
//        this.updatedAt = updatedAt;
//    }
//
//    public Date getDeletedAt() {
//        return deletedAt;
//    }
//
//    public void setDeletedAt(Date deletedAt) {
//        this.deletedAt = deletedAt;
//    }

    public JSONObject toJson(){
        JSONObject projectJson = new JSONObject();

        projectJson.put("key", this.key);
        projectJson.put("userId", this.userId);
        projectJson.put("submissionId", this.submissionId);
        return projectJson;
    }
}


