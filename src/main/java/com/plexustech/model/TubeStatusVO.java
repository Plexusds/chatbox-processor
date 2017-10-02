package com.plexustech.model;

import java.io.Serializable;

/**
 *
 */
public class TubeStatusVO implements Serializable {

    private String tubeLineId;
    private String lineName;
    private String statusSeverityDescription;
    private long statusId;
    private String reason;
    private long statusSeverity;


    public TubeStatusVO(String tubeLineId, String lineName, String statusSeverityDescription, long statusId, String reason,
                        long statusSeverity) {
        this.tubeLineId = tubeLineId;
        this.lineName = lineName;
        this.statusSeverityDescription = statusSeverityDescription;
        this.statusId = statusId;
        this.reason = reason;
        this.statusSeverity = statusSeverity;
    }

    public String getTubeLineId() {
        return tubeLineId;
    }

    public String getLineName() {
        return lineName;
    }

    public String getStatusSeverityDescription() {
        return statusSeverityDescription;
    }

    public long getStatusId() {
        return statusId;
    }

    public String getReason() {
        return reason;
    }

    public long getStatusSeverity() {
        return statusSeverity;
    }
}
