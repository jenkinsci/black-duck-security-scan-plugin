package io.jenkins.plugins.security.scan.action;

import hudson.model.Action;
import hudson.model.Job;

public class IssueAction implements Action {
    private final Job<?, ?> job;

    public IssueAction(Job<?, ?> job) {
        this.job = job;
    }
    @Override
    public String getIconFileName() {
        return "/plugin/blackduck-security-scan/icons/blackduck.png";
    }

    @Override
    public String getDisplayName() {
        return "Blackduck - Custom Action";
    }

    @Override
    public String getUrlName() {
        return "custom-action";
    }
}
