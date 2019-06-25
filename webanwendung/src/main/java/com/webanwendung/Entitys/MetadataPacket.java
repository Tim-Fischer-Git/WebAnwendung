package com.webanwendung.Entitys;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class MetadataPacket {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDate date;
    private double degreeOfLatitude;
    private double degreeOfLongitude;

    public MetadataPacket() {
    }

    public MetadataPacket(LocalDate date, double degreeOfLatitude, double degreeOfLongitude) {
        this.date = date;
        this.degreeOfLatitude = degreeOfLatitude;
        this.degreeOfLongitude = degreeOfLongitude;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getDegreeOfLatitude() {
        return this.degreeOfLatitude;
    }

    public void setDegreeOfLatitude(double degreeOfLatitude) {
        this.degreeOfLatitude = degreeOfLatitude;
    }

    public double getDegreeOfLongitude() {
        return this.degreeOfLongitude;
    }

    public void setDegreeOfLongitude(double degreeOfLongitude) {
        this.degreeOfLongitude = degreeOfLongitude;
    }

    public MetadataPacket date(LocalDate date) {
        this.date = date;
        return this;
    }

    public MetadataPacket degreeOfLatitude(double degreeOfLatitude) {
        this.degreeOfLatitude = degreeOfLatitude;
        return this;
    }

    public MetadataPacket degreeOfLongitude(double degreeOfLongitude) {
        this.degreeOfLongitude = degreeOfLongitude;
        return this;
    }

   
   

    @Override
    public String toString() {
        return "{" +
            " date='" + getDate() + "'" +
            ", degreeOfLatitude='" + getDegreeOfLatitude() + "'" +
            ", degreeOfLongitude='" + getDegreeOfLongitude() + "'" +
            "}";
    }

}