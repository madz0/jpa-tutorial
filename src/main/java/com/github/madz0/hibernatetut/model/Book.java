package com.github.madz0.hibernatetut.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Mohamad Zeinali [mohammad.basu@gmail.com] at 11/26/18
 */
@Getter
@Setter
@Entity
public class Book extends BaseModel {

    @Column(nullable = false)
    private String name;
    @ManyToMany(mappedBy = "books")
    private Set<Author> authors;
    @ManyToOne(fetch = FetchType.LAZY)
    private Publisher publisher;
}
