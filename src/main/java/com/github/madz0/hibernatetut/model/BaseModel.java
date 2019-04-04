package com.github.madz0.hibernatetut.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;

/**
 * @author Mohamad Zeinali [mohammad.basu@gmail.com] at 11/26/18
 */
@Getter
@Setter
@MappedSuperclass
public class BaseModel implements Serializable {
    @Version
    protected Long version;
}
