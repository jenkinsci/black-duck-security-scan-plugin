/*
 * synopsys-security-scan-plugin
 *
 * Copyright (c) 2023 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jenkins.scan.global;

public class LogMessages {
    public static final String ASTERISKS = "******************************************************************************";
    public static final String INVALID_SYNOPSYS_BRIDGE_DOWNLOAD_URL = "Invalid Synopsys Bridge download URL: %s %n";
    public static final String FAILED_TO_FETCH_PLUGINS_DEFAULT_INSTALLATION_PATH = "Failed to fetch plugin's default installation path: %s %n";
    public static final String BLACKDUCK_PARAMETER_VALIDATION_FAILED = "BlackDuck parameters are not valid";
    public static final String COVERITY_PARAMETER_VALIDATION_FAILED = "Coverity parameters are not valid";
    public static final String POLARIS_PARAMETER_VALIDATION_FAILED = "Polaris parameters are not valid";
    public static final String NO_BITBUCKET_TOKEN_FOUND = "PrComment or FixPr is set true but no bitbucket token found!";
    public static final String INVALID_BRIDGE_DOWNLOAD_PARAMETERS = "Bridge download parameters are not valid";
    public static final String EMPTY_BRIDGE_DOWNLOAD_URL_PROVIDED = "The provided Bridge download URL is empty";
    public static final String INVALID_BRIDGE_DOWNLOAD_URL_PROVIDED = "The provided Bridge download URL is not valid: %s %n";
    public static final String INVALID_BRIDGE_DOWNLOAD_VERSION_PROVIDED = "The provided Bridge download version is not valid: %s %n";
    public static final String EXCEPTION_OCCURRED_WHILE_INVOKING_SYNOPSYS_BRIDGE = "An exception occurred while invoking synopsys-bridge from the plugin: %s %n";
    public static final String EXCEPTION_OCCURRED_WHILE_DOWNLOADING_OR_INSTALLING_SYNOPSYS_BRIDGE = "An exception occurred while installing/downloading synopsys-bridge: %s %n";
}
