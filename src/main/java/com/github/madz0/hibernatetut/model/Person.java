package com.github.madz0.hibernatetut.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Mohamad Zeinali [mohammad.basu@gmail.com] at 11/26/18
 */
@Getter
@Setter
@Entity
public class Person extends BaseModelId {

    @Column
    private String firstName;
    @Column
    private String lastName;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private Author author;
}
