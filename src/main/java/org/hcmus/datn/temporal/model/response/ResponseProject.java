package org.hcmus.datn.temporal.model.response;

import org.hcmus.datn.utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.LinkedHashMap;

public class ResponseProject {
    private String id;
    private String key;
    private String userId;
    private String submissionId;

    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;

    public ResponseProject() {
    }
    public ResponseProject(String id, String key, String userId, String submissionId, Date createdAt, Date updatedAt, Date deletedAt) {
        this.id = id;
        this.key = key;
        this.userId = userId;
        this.submissionId = submissionId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }
    public ResponseProject(String key, String userId, String submissionId) {
        this.key = key;
        this.userId = userId;
        this.submissionId = submissionId;
    }




    public ResponseProject(String key, String userId) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public static ResponseProject toResponseProject(LinkedHashMap<String, Object> map) throws ParseException {
        return new ResponseProject(
                (String) map.get("id"),
                (String) map.get("key"),
                (String) map.get("userId"),
                (String) map.get("submissionId"),
                Utils.convertDateFromString((String) map.get("createdAt")),
                Utils.convertDateFromString((String) map.get("updatedAt")),
                Utils.convertDateFromString((String) map.get("deletedAt"))
        );

    }

    @Override
    public String toString() {
        return "ResponseProject{" +
                "id='" + id + '\'' +
                ", key='" + key + '\'' +
                ", userId='" + userId + '\'' +
                ", submissionId='" + submissionId + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deletedAt=" + deletedAt +
                '}';
    }
}


