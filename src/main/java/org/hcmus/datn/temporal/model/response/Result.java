package org.hcmus.datn.temporal.model.response;

import jakarta.persistence.*;
import org.hcmus.datn.utils.ProjectType;
import org.hcmus.datn.utils.Utils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Entity()
@Table(name = "`result`")
public class Result {
    @Id
    @UuidGenerator()
    private String id;
    @Column(name = "`submissionId`")
    private String submissionId;
    @Column(name = "`total`")
    private int total;
    @Column(name = "`codeSmell`")
    private int codeSmell;

    @Column(name = "`bug`")
    private int bug;

    @Column(name = "`vulnerabilities`")
    private int vulnerabilities;

    @Column(name = "`blocker`")
    private int blocker;

    @Column(name = "`critical`")
    private int critical;

    @Column(name = "`major`")
    private int major;

    @Column(name = "`minor`")
    private int minor;

    @Column(name = "`info`")
    private int info;

    @Column(name = "`duplicatedLinesDensity`")
    private float duplicatedLinesDensity;
    @Column(name = "`coverage`")
    private float coverage;
    @Column(name = "`createdAt`")
    private Date createdAt;
    @Column(name = "`updatedAt`")
    private Date updatedAt;
    @Column(name = "`deletedAt`")
    private Date deletedAt;
    public Result() {
    }

    public Result(String submissionId, HashMap<String, Double> metricMap) {
        this.submissionId = submissionId;
        try {
            this.total = metricMap.get("violations").intValue() ;
            this.codeSmell = metricMap.get("code_smells").intValue() ;
            this.bug = metricMap.get("bugs").intValue() ;
            this.vulnerabilities = metricMap.get("vulnerabilities").intValue() ;
            this.blocker = metricMap.get("blocker_violations").intValue() ;
            this.critical = metricMap.get("critical_violations").intValue() ;
            this.major = metricMap.get("major_violations").intValue() ;
            this.minor = metricMap.get("minor_violations").intValue() ;
            this.info = metricMap.get("info_violations").intValue() ;
            this.duplicatedLinesDensity = metricMap.get("duplicated_lines_density").floatValue() ;
            this.coverage = metricMap.get("coverage").floatValue() ;
        }
        catch (Exception e){}
    }

    public Result(String id, String submissionId, int total, int codeSmell, int bug, int vulnerabilities, int blocker, int critical, int major, int minor, int info, float duplicatedLinesDensity, float coverage, Date createdAt, Date updatedAt, Date deletedAt) {
        this.id = id;
        this.submissionId = submissionId;
        this.total = total;
        this.codeSmell = codeSmell;
        this.bug = bug;
        this.vulnerabilities = vulnerabilities;
        this.blocker = blocker;
        this.critical = critical;
        this.major = major;
        this.minor = minor;
        this.info = info;
        this.duplicatedLinesDensity = duplicatedLinesDensity;
        this.coverage = coverage;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public Result(String submissionId, int total, int codeSmell, int bug, int vulnerabilities, int blocker, int critical, int major, int minor, int info, float duplicatedLinesDensity, float coverage) {
        this.submissionId = submissionId;
        this.total = total;
        this.codeSmell = codeSmell;
        this.bug = bug;
        this.vulnerabilities = vulnerabilities;
        this.blocker = blocker;
        this.critical = critical;
        this.major = major;
        this.minor = minor;
        this.info = info;
        this.duplicatedLinesDensity = duplicatedLinesDensity;
        this.coverage = coverage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(String submissionId) {
        this.submissionId = submissionId;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCodeSmell() {
        return codeSmell;
    }

    public void setCodeSmell(int codeSmell) {
        this.codeSmell = codeSmell;
    }

    public int getBug() {
        return bug;
    }

    public void setBug(int bug) {
        this.bug = bug;
    }

    public int getVulnerabilities() {
        return vulnerabilities;
    }

    public void setVulnerabilities(int vulnerabilities) {
        this.vulnerabilities = vulnerabilities;
    }

    public int getBlocker() {
        return blocker;
    }

    public void setBlocker(int blocker) {
        this.blocker = blocker;
    }

    public int getCritical() {
        return critical;
    }

    public void setCritical(int critical) {
        this.critical = critical;
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    public int getInfo() {
        return info;
    }

    public void setInfo(int info) {
        this.info = info;
    }

    public float getDuplicatedLinesDensity() {
        return duplicatedLinesDensity;
    }

    public void setDuplicatedLinesDensity(float duplicatedLinesDensity) {
        this.duplicatedLinesDensity = duplicatedLinesDensity;
    }

    public float getCoverage() {
        return coverage;
    }

    public void setCoverage(float coverage) {
        this.coverage = coverage;
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
