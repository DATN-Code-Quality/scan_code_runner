package org.hcmus.datn.temporal.model.response;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hcmus.datn.utils.ProjectType;
import org.hcmus.datn.utils.Utils;
import org.hibernate.annotations.UuidGenerator;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashMap;

@Entity()
@Table(name = "`assignment`")
public class Assignment {

    @Id
    @UuidGenerator()
    private String id;
    @Column(name = "`name`")
    private String name;
    @Column(name = "`assignmentMoodleId`")
    private String assignmentMoodleId;
    @Column(name = "`dueDate`")
    private Date dueDate;

    @Column(name = "`status`")
    private int status;

    @Column(name = "`courseId`")
    private String courseId;
    @Column(name = "`description`")
    private String description;
    @Column(name = "`attachmentFileLink`")
    private String attachmentFileLink;

    @Column(name = "`config`")
    private String config;

    @Column(name = "`createdAt`")
    private Date createdAt;
    @Column(name = "`updatedAt`")
    private Date updatedAt;
    @Column(name = "`deletedAt`")
    private Date deletedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssignmentMoodleId() {
        return assignmentMoodleId;
    }

    public void setAssignmentMoodleId(String assignmentMoodleId) {
        this.assignmentMoodleId = assignmentMoodleId;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttachmentFileLink() {
        return attachmentFileLink;
    }

    public void setAttachmentFileLink(String attachmentFileLink) {
        this.attachmentFileLink = attachmentFileLink;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
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
