package com.lambakean.RationPlanner.dto.form;

import com.lambakean.RationPlanner.dto.EntityIdReferenceDto;
import com.lambakean.RationPlanner.dto.PhotoDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCreationForm {

    private String name;
    private String producer;
    private ProductQuantityInformation quantity;
    private Double price;
    private PhotoDto photo;

    @Getter
    @Setter
    public static class ProductQuantityInformation {

        private Double amount;
        private EntityIdReferenceDto measurementUnit;
    }
}
