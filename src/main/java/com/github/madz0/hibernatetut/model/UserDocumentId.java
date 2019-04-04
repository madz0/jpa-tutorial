package com.github.madz0.hibernatetut.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(of = {"userId", "documentId"})
@Embeddable
public class UserDocumentId implements Serializable {
    @Column(name="usr_id")
    private Long userId;
    @Column(name="document_id")
    private Long documentId;
}
