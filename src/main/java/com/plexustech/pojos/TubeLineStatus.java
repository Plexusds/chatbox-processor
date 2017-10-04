package com.plexustech.pojos;

/**
 *
 */
public class TubeLineStatus {

    private TubeStatus[] tubeStatuses;

    public TubeStatus[] getTubeStatuses() {
        return tubeStatuses;
    }

    public void setTubeStatuses(TubeStatus[] tubeStatuses) {
        this.tubeStatuses = tubeStatuses;
    }

    public class TubeStatus{
        private String $type;
        private String created;
        private Crowding crowding;
        private String[] disruptions;
        private String id;
        private LineStatus[] lineStatuses;
        private String modeName;
        private String modified;
        private String name;
        private String[] routeSections;
        private ServiceType[] serviceTypes;

        public String get$type() {
            return $type;
        }

        public void set$type(String $type) {
            this.$type = $type;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public Crowding getCrowding() {
            return crowding;
        }

        public void setCrowding(Crowding crowding) {
            this.crowding = crowding;
        }

        public String[] getDisruptions() {
            return disruptions;
        }

        public void setDisruptions(String[] disruptions) {
            this.disruptions = disruptions;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public LineStatus[] getLineStatuses() {
            return lineStatuses;
        }

        public void setLineStatuses(LineStatus[] lineStatuses) {
            this.lineStatuses = lineStatuses;
        }

        public String getModeName() {
            return modeName;
        }

        public void setModeName(String modeName) {
            this.modeName = modeName;
        }

        public String getModified() {
            return modified;
        }

        public void setModified(String modified) {
            this.modified = modified;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String[] getRouteSections() {
            return routeSections;
        }

        public void setRouteSections(String[] routeSections) {
            this.routeSections = routeSections;
        }

        public ServiceType[] getServiceTypes() {
            return serviceTypes;
        }

        public void setServiceTypes(ServiceType[] serviceTypes) {
            this.serviceTypes = serviceTypes;
        }

    }

    public class Crowding{
        private String $type;

        public String get$type() {
            return $type;
        }

        public void set$type(String $type) {
            this.$type = $type;
        }
    }


    public class LineStatus{
        private String $type;
        private String created;
        private Disruption disruption;
        private long id;
        private String lineId;
        private String reason;
        private long statusSeverity;
        private String statusSeverityDescription;
        private ValidityPeriod[] validityPeriods;

        public String get$type() {
            return $type;
        }

        public void set$type(String $type) {
            this.$type = $type;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public Disruption getDisruption() {
            return disruption;
        }

        public void setDisruption(Disruption disruption) {
            this.disruption = disruption;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getLineId() {
            return lineId;
        }

        public void setLineId(String lineId) {
            this.lineId = lineId;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public long getStatusSeverity() {
            return statusSeverity;
        }

        public void setStatusSeverity(long statusSeverity) {
            this.statusSeverity = statusSeverity;
        }

        public String getStatusSeverityDescription() {
            return statusSeverityDescription;
        }

        public void setStatusSeverityDescription(String statusSeverityDescription) {
            this.statusSeverityDescription = statusSeverityDescription;
        }

        public ValidityPeriod[] getValidityPeriods() {
            return validityPeriods;
        }

        public void setValidityPeriods(ValidityPeriod[] validityPeriods) {
            this.validityPeriods = validityPeriods;
        }
    }

    public class ValidityPeriod{
        private String $type;
        private String fromDate;
        private Boolean isnow;
        private Boolean toDate;

        public String get$type() {
            return $type;
        }

        public void set$type(String $type) {
            this.$type = $type;
        }

        public String getFromDate() {
            return fromDate;
        }

        public void setFromDate(String fromDate) {
            this.fromDate = fromDate;
        }

        public Boolean getIsnow() {
            return isnow;
        }

        public void setIsnow(Boolean isnow) {
            this.isnow = isnow;
        }

        public Boolean getToDate() {
            return toDate;
        }

        public void setToDate(Boolean toDate) {
            this.toDate = toDate;
        }
    }

    public class Disruption{
        private String $type;
        private String[] affectedRoutes;
        private String[] affectedStops;
        private String category;
        private String categoryDescription;
        private String closureText;
        private String description;
        private Boolean isBlocking;

        public String get$type() {
            return $type;
        }

        public void set$type(String $type) {
            this.$type = $type;
        }

        public String[] getAffectedRoutes() {
            return affectedRoutes;
        }

        public void setAffectedRoutes(String[] affectedRoutes) {
            this.affectedRoutes = affectedRoutes;
        }

        public String[] getAffectedStops() {
            return affectedStops;
        }

        public void setAffectedStops(String[] affectedStops) {
            this.affectedStops = affectedStops;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getCategoryDescription() {
            return categoryDescription;
        }

        public void setCategoryDescription(String categoryDescription) {
            this.categoryDescription = categoryDescription;
        }

        public String getClosureText() {
            return closureText;
        }

        public void setClosureText(String closureText) {
            this.closureText = closureText;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Boolean getBlocking() {
            return isBlocking;
        }

        public void setBlocking(Boolean blocking) {
            isBlocking = blocking;
        }
    }

    public class ServiceType{
        private String $type;
        private String name;
        private String uri;

        public String get$type() {
            return $type;
        }

        public void set$type(String $type) {
            this.$type = $type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }
    }


}
