package com.app.domain;

import lombok.Data;

import java.util.Map;

@Data
public class AlertEntry {

    private String status;
    private Map<String,String> labels;
    private Map<String,String> annotations;
    private String startsAt;
    private String endsAt;
    private String generatorURL;
    private String fingerprint;
    private String silenceURL;
    private String dashboardURL;
    private String panelURL;
    private Map<String,Long> values;
    private String  valueString;
}
