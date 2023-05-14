package org.hcmus.datn.temporal.model.request;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hcmus.datn.utils.ScanResult;
import org.hcmus.datn.utils.SubmissionStatus;

import java.util.Date;


@Entity()
@Table(name = "submission")
public class Submission {
    @Id
    private String id;
    @Column(name = "`assignmentId`")
    private String assignmentId;
    @Column(name = "`link`")
    private String link;
    @Column(name = "`note`")
    private String note;
    @Column(name = "`submitType`")
    private String submitType;
    @Column(name = "`timemodified`")
    private Date timemodified;
    @Column(name = "`userId`")
    private String userId;
    @Column(name = "`origin`")
    private String origin;
    @Column(name = "`status`")
    private SubmissionStatus status;
    @Column(name = "`grade`")
    private Float grade;
    @Column(name = "`submissionMoodleId`")
    private String submissionMoodleId;
    @Column(name = "`createdAt`")
    private Date createdAt;
    @Column(name = "`updatedAt`")
    private Date updatedAt;
    @Column(name = "`deletedAt`")
    private Date deletedAt;

    public Submission() {
    }

    public Submission(String id, String assignmentId, String link, String note, String submitType, Date timemodified, String userId, String origin, SubmissionStatus status, Float grade, String submissionMoodleId, Date createdAt, Date updatedAt, Date deletedAt) {
        this.id = id;
        this.assignmentId = assignmentId;
        this.link = link;
        this.note = note;
        this.submitType = submitType;
        this.timemodified = timemodified;
        this.userId = userId;
        this.origin = origin;
        this.status = status;
        this.grade = grade;
        this.submissionMoodleId = submissionMoodleId;
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

    public SubmissionStatus getStatus() {
        return status;
    }

    public void setStatus(SubmissionStatus status) {
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

    public Date getTimemodified() {
        return timemodified;
    }

    public void setTimemodified(Date timemodified) {
        this.timemodified = timemodified;
    }

    public String getSubmissionMoodleId() {
        return submissionMoodleId;
    }

    public void setSubmissionMoodleId(String submissionMoodleId) {
        this.submissionMoodleId = submissionMoodleId;
    }

    @Override
    public String toString() {
        return "Submission{" +
                "id='" + id + '\'' + '\n' +
                ", assignmentId='" + assignmentId + '\'' + '\n' +
                ", link='" + link + '\'' + '\n' +
                ", note='" + note + '\'' + '\n' +
                ", submitType='" + submitType + '\'' + '\n' +
                ", userId='" + userId + '\'' + '\n' +
                ", origin='" + origin + '\'' + '\n' +
                ", status=" + status + '\n' +
                ", grade=" + grade + '\n' +
                ", createdAt=" + createdAt + '\n' +
                ", updatedAt=" + updatedAt + '\n' +
                ", deletedAt=" + deletedAt +
                '}';
    }
}
