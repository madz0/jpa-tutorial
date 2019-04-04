package com.github.madz0.hibernatetut.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "documents")
public class Document extends BaseModelId {
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "document", orphanRemoval = true)
    Set<UserDocument> userDocuments;
}
