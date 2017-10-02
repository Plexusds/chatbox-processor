package com.plexustech.pojos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 *
 */
@XmlRootElement(name="stations")
public class CycleHire {


    private List<Station> stations;

    public List<Station> getStations() {
        return stations;
    }

    @XmlElement(name="station")
    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    @XmlRootElement(name = "station")
    public static class Station{
        private int id;
        private String name;
        private String terminalName;
        private float lat;
        private float lon;
        private boolean installed;
        private boolean locked;
        private long installDate;
        private long removalDate;
        private boolean temporary;
        private short nbBikes;
        private short nbEmptyDocks;
        private short nbDocks;

        public int getId() {
            return id;
        }

        @XmlElement
        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        @XmlElement
        public void setName(String name) {
            this.name = name;
        }

        public String getTerminalName() {
            return terminalName;
        }

        @XmlElement
        public void setTerminalName(String terminalName) {
            this.terminalName = terminalName;
        }

        public float getLat() {
            return lat;
        }

        @XmlElement
        public void setLat(float lat) {
            this.lat = lat;
        }

        public float getLon() {
            return lon;
        }

        @XmlElement
        public void setLon(float lon) {
            this.lon = lon;
        }

        public boolean isInstalled() {
            return installed;
        }

        @XmlElement
        public void setInstalled(boolean installed) {
            this.installed = installed;
        }

        public boolean isLocked() {
            return locked;
        }

        @XmlElement
        public void setLocked(boolean locked) {
            this.locked = locked;
        }

        public long getInstallDate() {
            return installDate;
        }

        @XmlElement
        public void setInstallDate(long installDate) {
            this.installDate = installDate;
        }

        public long getRemovalDate() {
            return removalDate;
        }

        @XmlElement
        public void setRemovalDate(long removalDate) {
            this.removalDate = removalDate;
        }

        public boolean isTemporary() {
            return temporary;
        }

        @XmlElement
        public void setTemporary(boolean temporary) {
            this.temporary = temporary;
        }

        public short getNbBikes() {
            return nbBikes;
        }

        @XmlElement
        public void setNbBikes(short nbBikes) {
            this.nbBikes = nbBikes;
        }

        public short getNbEmptyDocks() {
            return nbEmptyDocks;
        }

        @XmlElement
        public void setNbEmptyDocks(short nbEmptyDocks) {
            this.nbEmptyDocks = nbEmptyDocks;
        }

        public short getNbDocks() {
            return nbDocks;
        }

        @XmlElement
        public void setNbDocks(short nbDocks) {
            this.nbDocks = nbDocks;
        }
    }

}
