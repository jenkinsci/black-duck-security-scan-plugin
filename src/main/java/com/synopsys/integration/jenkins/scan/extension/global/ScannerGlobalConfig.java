package com.synopsys.integration.jenkins.scan.extension.global;

import com.synopsys.integration.jenkins.annotations.HelpMarkdown;
import com.synopsys.integration.jenkins.scan.global.enums.ScanType;
import hudson.Extension;
import hudson.util.ListBoxModel;
import java.io.Serializable;
import java.util.Arrays;
import jenkins.model.GlobalConfiguration;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

@Extension
public class ScannerGlobalConfig extends GlobalConfiguration implements Serializable {
    private static final long serialVersionUID = -3129542889827231427L;

    @HelpMarkdown("Select the Scan type that you to perform.")
    private String scanType;

    @HelpMarkdown("Provide the URL that lets you access your Black Duck server.")
    private String blackDuckUrl;

    @HelpMarkdown("Provide the Black Duck api token through which black duck server can be accessed with proper authorization.")
    private String blackDuckApiToken;

    @HelpMarkdown("Provide the URL that lets you access your Coverity server.")
    private String coverityConnectUrl;

    @HelpMarkdown("Provide the Coverity username through which coverity server can be accessed with proper authorization.")
    private String coverityConnectUserName;

    @HelpMarkdown("Provide the Coverity password through which coverity server can be accessed with proper authorization.")
    private String coverityConnectUserPassword;

    @HelpMarkdown("Provide the Synopsys Bridge artifactory URL from where synopsys-bridge zip file can be downloaded. " +
        "Note: You must need to provide the full download url which includes the zip file path.")
    private String synopsysBridgeDownloadUrl;

    @HelpMarkdown("Provide the bitbucket api access token through which PrComment and FixPr will be done.")
    private String bitbucketToken;

    @HelpMarkdown("Provide the URL that lets you access Polaris server.")
    private String polarisServerUrl;

    @HelpMarkdown("Provide the Polaris access token through which Polaris server can be accessed with proper authorization.")
    private String polarisAccessToken;

    @DataBoundConstructor
    public ScannerGlobalConfig() {
        load();
    }

    @DataBoundSetter
    public void setScanType(String scanType) {
        this.scanType = scanType;
        save();
    }

    @DataBoundSetter
    public void setBlackDuckUrl(String blackDuckUrl) {
        this.blackDuckUrl = blackDuckUrl;
        save();
    }

    @DataBoundSetter
    public void setBlackDuckApiToken(String blackDuckApiToken) {
        this.blackDuckApiToken = blackDuckApiToken;
        save();
    }

    @DataBoundSetter
    public void setCoverityConnectUrl(String coverityConnectUrl) {
        this.coverityConnectUrl = coverityConnectUrl;
        save();
    }

    @DataBoundSetter
    public void setCoverityConnectUserName(String coverityConnectUserName) {
        this.coverityConnectUserName = coverityConnectUserName;
        save();
    }

    @DataBoundSetter
    public void setCoverityConnectUserPassword(String coverityConnectUserPassword) {
        this.coverityConnectUserPassword = coverityConnectUserPassword;
        save();
    }

    @DataBoundSetter
    public void setBitbucketToken(String bitbucketToken) {
        this.bitbucketToken = bitbucketToken;
        save();
    }

    @DataBoundSetter
    public void setSynopsysBridgeDownloadUrl(String synopsysBridgeDownloadUrl) {
        this.synopsysBridgeDownloadUrl = synopsysBridgeDownloadUrl;
        save();
    }

    @DataBoundSetter
    public void setPolarisServerUrl(String polarisServerUrl) {
        this.polarisServerUrl = polarisServerUrl;
        save();
    }

    @DataBoundSetter
    public void setPolarisAccessToken(String polarisAccessToken) {
        this.polarisAccessToken = polarisAccessToken;
        save();
    }

    public String getScanType() {
        return scanType;
    }

    public String getBlackDuckUrl() {
        return blackDuckUrl;
    }

    public String getBlackDuckApiToken() {
        return blackDuckApiToken;
    }

    public String getCoverityConnectUrl() {
        return coverityConnectUrl;
    }

    public String getCoverityConnectUserName() {
        return coverityConnectUserName;
    }

    public String getCoverityConnectUserPassword() {
        return coverityConnectUserPassword;
    }

    public String getSynopsysBridgeDownloadUrl() {
        return synopsysBridgeDownloadUrl;
    }

    public String getBitbucketToken() {
        return bitbucketToken;
    }

    public String getPolarisServerUrl() {
        return polarisServerUrl;
    }

    public String getPolarisAccessToken() {
        return polarisAccessToken;
    }
    
    public ListBoxModel doFillScanTypeItems() {
        ListBoxModel items = new ListBoxModel();
        Arrays.stream(ScanType.values()).forEach(scanType -> items.add(String.valueOf(scanType)));
        return items;
    }

}
