package org.hcmus.datn.temporal.model.request;

import java.util.Date;

public class Result {
    private String projectId;
    private int  bugs;
    private  int vulnerabilities;
    private int smells;
    public Result() {
    }

    public Result(String projectId) {
        this.projectId = projectId;
    }

    public Result(String projectId, int bugs, int vulnerabilities, int smells) {
        this.projectId = projectId;
        this.bugs = bugs;
        this.vulnerabilities = vulnerabilities;
        this.smells = smells;
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

}
