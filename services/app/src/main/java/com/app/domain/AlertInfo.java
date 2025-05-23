package com.app.domain;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AlertInfo {
    private String receiver;
    private String status;
    private List<AlertEntry> alerts;
    private Map<String,String> groupLabels;
    private Map<String,String> commonLabels;
    private Map<String,String> commonAnnotations;
    private String externalURL;
    private String version;
    private String groupKey;
    private Integer truncatedAlerts;
    private Integer orgId;
    private String title;
    private String state;
    private String message;
}
