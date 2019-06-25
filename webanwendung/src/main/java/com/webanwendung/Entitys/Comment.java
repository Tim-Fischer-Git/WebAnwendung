package com.webanwendung.Entitys;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Comment {
    @Id
    @GeneratedValue
    private Long id;

 
    @OneToOne
    private User author;
    
    @NotNull
    @Size(min = 3, max =80)
    private String comment;
    
    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;


    public Comment() {
        this.date = LocalDate.now();
    }

    public Comment(Long id, User author, String comment, LocalDate date) {
        this.id = id;
        this.author = author;
        this.comment = comment;
        this.date = date;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return this.author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Comment id(Long id) {
        this.id = id;
        return this;
    }

    public Comment author(User author) {
        this.author = author;
        return this;
    }

    public Comment comment(String comment) {
        this.comment = comment;
        return this;
    }

    public Comment date(LocalDate date) {
        this.date = date;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Comment)) {
            return false;
        }
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id) && Objects.equals(author, comment.author) && Objects.equals(comment, comment.comment) && Objects.equals(date, comment.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, comment, date);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", author='" + getAuthor() + "'" +
            ", comment='" + getComment() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }

   
    




}