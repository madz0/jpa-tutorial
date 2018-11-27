package com.github.madz0.hibernatetut.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * @author Mohamad Zeinali [mohammad.basu@gmail.com] at 11/27/18
 */
@Getter
@Setter
@Entity
public class Editor extends BaseModel {

    @Column(nullable = false)
    private String speciality;
    @ManyToOne(fetch = FetchType.LAZY)
    private Publisher publisher;
}
