package com.example.SpringBoot_project_2.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Book name should not be empty")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Book author should not be empty")
    @Column(name = "author")
    private String author;

    @NotNull(message = "Date of issue should not be empty")
    @Column(name = "date_of_issue")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateOfIssue;

    @Column(name = "date_of_receipt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfReceipt;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Person owner;

    @Transient
    private boolean overdue;

    public Book() {
    }
    public Book(String name, String author, Date dateOfIssue) {
        this.name = name;
        this.author = author;
        this.dateOfIssue = dateOfIssue;
    }

    public boolean getOverdue() {
        return overdue;
    }

    public void setOverdue(boolean overdue) {
        this.overdue = overdue;
    }

    public Date getDateOfReceipt() {
        return dateOfReceipt;
    }

    public void setDateOfReceipt(Date dateOfReceipt) {
        this.dateOfReceipt = dateOfReceipt;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(Date dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", dateOfIssue=" + dateOfIssue +
                ", owner=" + owner +
                '}';
    }
}


















