package com.github.madz0.hibernatetut.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "user_document")
public class UserDocument extends BaseModel {

    @EmbeddedId
    private UserDocumentId userDocumentId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("documentId")
    private Document document;
}
