package io.jenkins.plugins.security.scan.action;

import hudson.model.Action;
public class IssueActionItems implements  Action {
    private final String product;
    private final int defectCount;
    private final String issueViewUrl;

    public IssueActionItems(String product, int defectCount, String issueViewUrl) {
        this.product = product;
        this.defectCount = defectCount;
        this.issueViewUrl = issueViewUrl;
    }

    public String getProduct() {
        return product;
    }

    public int getDefectCount() {
        return defectCount;
    }

    public String getIssueViewUrl() {
        return issueViewUrl;
    }

    @Override
    public String getIconFileName() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return null;
    }

    @Override
    public String getUrlName() {
        return null;
    }
}
