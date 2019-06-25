package com.webanwendung.Entitys;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Entity
public class Sighting {
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @NotEmpty
    @Size(min=3,max=20)
    private String finder;

    @NotNull
    private String locality;

    private String[] daytimeButton = { "morgens", "mittags", "abends" };
    private String daytime;

    private String[] ratingButton = { "*", "**", "***","****","*****" };
    private String rating;

	
    @PastOrPresent
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    
    @OneToOne
    private MetadataPacket metadataPacket;

    @NotNull
    @Size(max = 80)
    private String description;

    @OneToMany
    private List<Comment> commentList = new ArrayList<>();
    
    @OneToOne
    private User author;

	public List<Comment> getCommentList() {
		return this.commentList;
    }
    @Transactional
    public void addComment(Comment comment){
        this.commentList.add(comment);
    } 
    @Transactional
    public void removeComment(Comment comment){
        this.commentList.remove(comment);
    }


    public Sighting() {
    }

    public Sighting(long id, String finder, String locality, String[] daytimeButton, String daytime, String[] ratingButton, String rating, LocalDate date, MetadataPacket metadataPacket, String description, List<Comment> commentList, User author) {
        this.id = id;
        this.finder = finder;
        this.locality = locality;
        this.daytimeButton = daytimeButton;
        this.daytime = daytime;
        this.ratingButton = ratingButton;
        this.rating = rating;
        this.date = date;
        this.metadataPacket = metadataPacket;
        this.description = description;
        this.commentList = commentList;
        this.author = author;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFinder() {
        return this.finder;
    }

    public void setFinder(String finder) {
        this.finder = finder;
    }

    public String getLocality() {
        return this.locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String[] getDaytimeButton() {
        return this.daytimeButton;
    }

    public void setDaytimeButton(String[] daytimeButton) {
        this.daytimeButton = daytimeButton;
    }

    public String getDaytime() {
        return this.daytime;
    }

    public void setDaytime(String daytime) {
        this.daytime = daytime;
    }

    public String[] getRatingButton() {
        return this.ratingButton;
    }

    public void setRatingButton(String[] ratingButton) {
        this.ratingButton = ratingButton;
    }

    public String getRating() {
        return this.rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public MetadataPacket getMetadataPacket() {
        return this.metadataPacket;
    }

    public void setMetadataPacket(MetadataPacket metadataPacket) {
        this.metadataPacket = metadataPacket;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public User getAuthor() {
        return this.author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Sighting id(long id) {
        this.id = id;
        return this;
    }

    public Sighting finder(String finder) {
        this.finder = finder;
        return this;
    }

    public Sighting locality(String locality) {
        this.locality = locality;
        return this;
    }

    public Sighting daytimeButton(String[] daytimeButton) {
        this.daytimeButton = daytimeButton;
        return this;
    }

    public Sighting daytime(String daytime) {
        this.daytime = daytime;
        return this;
    }

    public Sighting ratingButton(String[] ratingButton) {
        this.ratingButton = ratingButton;
        return this;
    }

    public Sighting rating(String rating) {
        this.rating = rating;
        return this;
    }

    public Sighting date(LocalDate date) {
        this.date = date;
        return this;
    }

    public Sighting metadataPacket(MetadataPacket metadataPacket) {
        this.metadataPacket = metadataPacket;
        return this;
    }

    public Sighting description(String description) {
        this.description = description;
        return this;
    }

    public Sighting commentList(List<Comment> commentList) {
        this.commentList = commentList;
        return this;
    }

    public Sighting author(User author) {
        this.author = author;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Sighting)) {
            return false;
        }
        Sighting sighting = (Sighting) o;
        return id == sighting.id && Objects.equals(finder, sighting.finder) && Objects.equals(locality, sighting.locality) && Objects.equals(daytimeButton, sighting.daytimeButton) && Objects.equals(daytime, sighting.daytime) && Objects.equals(ratingButton, sighting.ratingButton) && Objects.equals(rating, sighting.rating) && Objects.equals(date, sighting.date) && Objects.equals(metadataPacket, sighting.metadataPacket) && Objects.equals(description, sighting.description) && Objects.equals(commentList, sighting.commentList) && Objects.equals(author, sighting.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, finder, locality, daytimeButton, daytime, ratingButton, rating, date, metadataPacket, description, commentList, author);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", finder='" + getFinder() + "'" +
            ", locality='" + getLocality() + "'" +
            ", daytimeButton='" + getDaytimeButton() + "'" +
            ", daytime='" + getDaytime() + "'" +
            ", ratingButton='" + getRatingButton() + "'" +
            ", rating='" + getRating() + "'" +
            ", date='" + getDate() + "'" +
            ", metadataPacket='" + getMetadataPacket() + "'" +
            ", description='" + getDescription() + "'" +
            ", commentList='" + getCommentList() + "'" +
            ", author='" + getAuthor() + "'" +
            "}";
    }

    

}