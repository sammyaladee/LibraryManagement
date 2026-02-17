package com.management.library.data.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import lombok.Data;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Id;



@Data
@Entity
@Table(name="books")
public class Book {
    
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String isbn;
    private String publicationYear;


    @ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    private Author author;

}
