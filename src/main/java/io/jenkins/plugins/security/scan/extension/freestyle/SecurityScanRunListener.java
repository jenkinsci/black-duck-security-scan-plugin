package io.jenkins.plugins.security.scan.extension.freestyle;

import hudson.Extension;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.model.listeners.RunListener;
import io.jenkins.plugins.security.scan.action.IssueAction;
import io.jenkins.plugins.security.scan.action.IssueActionItems;

@Extension
public class SecurityScanRunListener extends RunListener<Run<?, ?>> {

    @Override
    public void onCompleted(Run<?, ?> run, TaskListener listener) {

        IssueActionItems issueActionItems = run.getAction(IssueActionItems.class);

        String product = issueActionItems != null ? issueActionItems.getProduct() : "Unknown Product";
        String issueViewUrl = issueActionItems != null ? issueActionItems.getIssueViewUrl() : "Unknown Issue URL";
        int defectCount = issueActionItems != null ? issueActionItems.getDefectCount(): -112112121;

        run.addAction(new IssueAction(product, defectCount, issueViewUrl));
    }
}