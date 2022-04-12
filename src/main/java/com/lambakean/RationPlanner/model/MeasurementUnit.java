package com.lambakean.RationPlanner.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "measurement_units")
public class MeasurementUnit extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "measurementUnit", fetch = FetchType.LAZY)
    private Set<ProductQuantity> relatedQuantities;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ProductQuantity> getRelatedQuantities() {
        return relatedQuantities;
    }

    public void setRelatedQuantities(Set<ProductQuantity> relatedQuantities) {
        this.relatedQuantities = relatedQuantities;
    }
}