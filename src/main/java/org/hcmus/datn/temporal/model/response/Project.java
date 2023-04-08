package org.hcmus.datn.temporal.model.response;

import jakarta.persistence.*;
import org.hcmus.datn.utils.Utils;
import org.hibernate.annotations.UuidGenerator;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashMap;

@Entity()
@Table(name = "`project`")
public class Project {
    @Id
    @UuidGenerator()
    private String id;
    @Column(name = "`key`")
    private String key;
    @Column(name = "`userId`")
    private String userId;
    @Column(name = "`submissionId`")
    private String submissionId;

    @Column(name = "`createdAt`")
    private Date createdAt;
    @Column(name = "`updatedAt`")
    private Date updatedAt;
    @Column(name = "`deletedAt`")
    private Date deletedAt;

//    @OneToMany(
//            mappedBy = "project",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true
//    )
//    private List<ResponseResult> results = new ArrayList<>();

    public Project() {
    }
    public Project(String id, String key, String userId, String submissionId, Date createdAt, Date updatedAt, Date deletedAt) {
        this.id = id;
        this.key = key;
        this.userId = userId;
        this.submissionId = submissionId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
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

//    public List<ResponseResult> getResults() {
//        return results;
//    }
//
//    public void setResults(List<ResponseResult> results) {
//        this.results = results;
//    }

    public static Project toResponseProject(LinkedHashMap<String, Object> map) throws ParseException {
        return new Project(
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
                "id='" + id + "'\n" +
                ", key='" + key + "'\n" +
                ", userId='" + userId + "'\n" +
                ", submissionId='" + submissionId + "'\n" +
                ", createdAt=" + createdAt + "\n"+
                ", updatedAt=" + updatedAt + "\n"+
                ", deletedAt=" + deletedAt +
                "}\n";
    }
}


