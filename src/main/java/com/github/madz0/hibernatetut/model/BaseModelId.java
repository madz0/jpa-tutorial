package com.github.madz0.hibernatetut.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
public class BaseModelId extends BaseModel {
    @Id
    @Column(length = 16)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    protected UUID id;
}
