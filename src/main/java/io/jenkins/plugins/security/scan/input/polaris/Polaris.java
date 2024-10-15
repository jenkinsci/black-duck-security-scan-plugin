package io.jenkins.plugins.security.scan.input.polaris;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.jenkins.plugins.security.scan.input.report.Reports;

public class Polaris {
    @JsonProperty("serverUrl")
    private String serverUrl;

    @SuppressWarnings("lgtm[jenkins/plaintext-storage]")
    @JsonProperty("accesstoken")
    private String accessToken;

    @JsonProperty("application")
    private ApplicationName applicationName;

    @JsonProperty("project")
    private PolarisProject polarisProject;

    @JsonProperty("assessment")
    private AssessmentTypes assessmentTypes;

    @JsonProperty("branch")
    private Branch branch;

    @JsonProperty("prComment")
    private Prcomment prcomment;

    @JsonProperty("test")
    private Test test;

    @JsonProperty("reports")
    private Reports reports;

    @JsonProperty("waitForScan")
    private Boolean waitForScan;

    public Polaris() {
        applicationName = new ApplicationName();
        polarisProject = new PolarisProject();
        assessmentTypes = new AssessmentTypes();
        branch = new Branch();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public ApplicationName getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(ApplicationName applicationName) {
        this.applicationName = applicationName;
    }

    public PolarisProject getPolarisProject() {
        return polarisProject;
    }

    public void setPolarisProject(PolarisProject polarisProject) {
        this.polarisProject = polarisProject;
    }

    public AssessmentTypes getAssessmentTypes() {
        return assessmentTypes;
    }

    public void setAssessmentTypes(AssessmentTypes assessmentTypes) {
        this.assessmentTypes = assessmentTypes;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Reports getReports() {
        return reports;
    }

    public void setReports(Reports reports) {
        this.reports = reports;
    }

    public Prcomment getPrcomment() {
        return prcomment;
    }

    public void setPrcomment(Prcomment prcomment) {
        this.prcomment = prcomment;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Boolean isWaitForScan() {
        return waitForScan;
    }

    public void setWaitForScan(Boolean waitForScan) {
        this.waitForScan = waitForScan;
    }
}
