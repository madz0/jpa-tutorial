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
public class Author extends BaseModel {
    @Column(nullable = false)
    private String name;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "author_book",
    joinColumns = {@JoinColumn(name = "author_id")},
    inverseJoinColumns = {@JoinColumn(name = "book_id")})
    private Set<Book> books;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "author", fetch = FetchType.LAZY, optional = false)
    private Person person;
}
