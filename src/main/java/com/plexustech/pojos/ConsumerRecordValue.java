package com.plexustech.pojos;

/**
 *
 */
public class ConsumerRecordValue {

    private String payload;
    private Schema schema;

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public Schema getSchema() {
        return schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    public class Schema{
        private Boolean optional;
        private String type;

        public Boolean getOptional() {
            return optional;
        }

        public void setOptional(Boolean optional) {
            this.optional = optional;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

}
