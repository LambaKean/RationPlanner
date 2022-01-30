package com.lambakean.RationPlanner.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Класс, от которого должны наследоваться все сущности, хранимые в базе данных
 */
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;

    public BaseEntity() {}


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
