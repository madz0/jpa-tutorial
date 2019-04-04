package com.github.madz0.hibernatetut.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Map;
import java.util.Set;

/**
 * @author Mohamad Zeinali [mohammad.basu@gmail.com] at 11/26/18
 */
@Getter
@Setter
@Entity
public class Publisher extends BaseModelId {
    @Column(nullable = false)
    private String name;
    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL)
    private Set<Book> books;
    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL)
    @MapKeyColumn(name = "speciality")
    private Map<String, Editor> editorMap;
}
