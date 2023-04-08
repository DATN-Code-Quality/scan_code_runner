package org.hcmus.datn.temporal.model.response;

import jakarta.persistence.*;
import org.hcmus.datn.utils.Utils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashMap;

@Entity()
@Table(name = "result")
public class Result {
    @Id
    @UuidGenerator()
    private String id;
    private String projectId;
    private int smells;
    private int  bugs;
    private  int vulnerabilities;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
//    @ManyToOne(targetEntity = ResponseProject.class,fetch = FetchType.LAZY)
//    @JoinColumn(name="projectId",referencedColumnName="id")
//    private ResponseProject project;

    public Result() {
    }

    public Result(String id, String projectId, int smells, int bugs, int vulnerabilities, Date createdAt, Date updatedAt, Date deletedAt) {
        this.id = id;
        this.projectId = projectId;
        this.bugs = bugs;
        this.vulnerabilities = vulnerabilities;
        this.smells = smells;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public Result(String projectId, int bugs, int vulnerabilities, int smells) {
        this.projectId = projectId;
        this.bugs = bugs;
        this.vulnerabilities = vulnerabilities;
        this.smells = smells;
    }
    public Result(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public int getBugs() {
        return bugs;
    }

    public void setBugs(int bugs) {
        this.bugs = bugs;
    }

    public int getVulnerabilities() {
        return vulnerabilities;
    }

    public void setVulnerabilities(int vulnerabilities) {
        this.vulnerabilities = vulnerabilities;
    }

    public int getSmells() {
        return smells;
    }

    public void setSmells(int smells) {
        this.smells = smells;
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

//    public ResponseProject getProject() {
//        return project;
//    }
//
//    public void setProject(ResponseProject project) {
//        this.project = project;
//    }

    public static Result toResponseResult(LinkedHashMap<String, Object> map) throws ParseException {
        return new Result(
                (String) map.get("id"),
                (String) map.get("projectId"),
                (Integer) map.get("smells"),
                (Integer) map.get("bugs"),
                (Integer) map.get("vulnerabilities"),
                Utils.convertDateFromString((String) map.get("createdAt")),
                Utils.convertDateFromString((String) map.get("updatedAt")),
                Utils.convertDateFromString((String) map.get("deletedAt"))
        );

    }

    @Override
    public String toString() {
        return "ResponseResult{" +
                "id='" + id + '\'' +
                ", projectId='" + projectId + '\'' +
                ", smells=" + smells +
                ", bugs=" + bugs +
                ", vulnerabilities=" + vulnerabilities +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deletedAt=" + deletedAt +
                "}\n";
    }
}
