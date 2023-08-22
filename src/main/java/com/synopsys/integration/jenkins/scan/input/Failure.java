package com.synopsys.integration.jenkins.scan.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Failure {
    @JsonProperty("severities")
    private List<String> severities;

    public List<String> getSeverities() {
        return severities;
    }

    public void setSeverities(List<String> severities) {
        this.severities = severities;
    }
}
