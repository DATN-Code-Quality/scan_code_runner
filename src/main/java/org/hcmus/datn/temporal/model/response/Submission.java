package org.hcmus.datn.temporal.model.response;

import java.util.Date;

public class Submission {
    private String id;
    private String assignmentId;
    private String link;
    private String note;
    private String submitType;
    private String userId;
    private String origin;
    private Boolean status;
    private Float grade;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;

    public Submission() {
    }

    public Submission(String id, String assignmentId, String link, String note, String submitType, String userId, String origin, Boolean status, Float grade, Date createdAt, Date updatedAt, Date deletedAt) {
        this.id = id;
        this.assignmentId = assignmentId;
        this.link = link;
        this.note = note;
        this.submitType = submitType;
        this.userId = userId;
        this.origin = origin;
        this.status = status;
        this.grade = grade;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSubmitType() {
        return submitType;
    }

    public void setSubmitType(String submitType) {
        this.submitType = submitType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Float getGrade() {
        return grade;
    }

    public void setGrade(Float grade) {
        this.grade = grade;
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
}
