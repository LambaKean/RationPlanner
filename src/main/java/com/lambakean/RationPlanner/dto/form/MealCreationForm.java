package com.lambakean.RationPlanner.dto.form;

import com.lambakean.RationPlanner.dto.EntityIdReferenceDto;
import com.lambakean.RationPlanner.dto.PhotoDto;
import com.lambakean.RationPlanner.dto.TimeDto;
import com.lambakean.RationPlanner.model.ProductQuantity;

import java.util.Set;

public class MealCreationForm {

    private String name;
    private String description;
    private String recipe;
    private TimeDto cookingDuration;
    private Set<IngredientInformation> ingredients;
    private PhotoDto photoDto;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public TimeDto getCookingDuration() {
        return cookingDuration;
    }

    public void setCookingDuration(TimeDto cookingDuration) {
        this.cookingDuration = cookingDuration;
    }

    public Set<IngredientInformation> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<IngredientInformation> ingredients) {
        this.ingredients = ingredients;
    }

    public PhotoDto getPhotoDto() {
        return photoDto;
    }

    public void setPhotoDto(PhotoDto photoDto) {
        this.photoDto = photoDto;
    }

    public static class IngredientInformation {

        private String name;
        private EntityIdReferenceDto product;
        private ProductQuantityInformation productQuantity;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public EntityIdReferenceDto getProduct() {
            return product;
        }

        public void setProduct(EntityIdReferenceDto product) {
            this.product = product;
        }

        public ProductQuantityInformation getProductQuantity() {
            return productQuantity;
        }

        public void setProductQuantity(ProductQuantityInformation productQuantity) {
            this.productQuantity = productQuantity;
        }

        public static class ProductQuantityInformation {

            private Double amount;
            private EntityIdReferenceDto measurementUnit;

            public Double getAmount() {
                return amount;
            }

            public void setAmount(Double amount) {
                this.amount = amount;
            }

            public EntityIdReferenceDto getMeasurementUnit() {
                return measurementUnit;
            }

            public void setMeasurementUnit(EntityIdReferenceDto measurementUnit) {
                this.measurementUnit = measurementUnit;
            }
        }
    }
}
