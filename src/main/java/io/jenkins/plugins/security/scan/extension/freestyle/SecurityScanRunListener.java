package io.jenkins.plugins.security.scan.extension.freestyle;

import hudson.Extension;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.model.listeners.RunListener;
import io.jenkins.plugins.security.scan.action.IssueAction;

@Extension
public class SecurityScanRunListener extends RunListener<Run<?, ?>> {

    @Override
    public void onCompleted(Run<?, ?> run, TaskListener listener) {

        run.addAction(new IssueAction(run.getParent()));
    }
}