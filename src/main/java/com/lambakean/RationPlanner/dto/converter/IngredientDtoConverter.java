package com.lambakean.RationPlanner.dto.converter;

import com.lambakean.RationPlanner.dto.IngredientDto;
import com.lambakean.RationPlanner.model.Ingredient;
import com.lambakean.RationPlanner.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IngredientDtoConverter {

    private final IngredientRepository ingredientRepository;
    private final ProductDtoConverter productDtoConverter;
    private final ProductQuantityDtoConverter productQuantityDtoConverter;

    @Autowired
    public IngredientDtoConverter(IngredientRepository ingredientRepository,
                                  ProductDtoConverter productDtoConverter,
                                  ProductQuantityDtoConverter productQuantityDtoConverter) {
        this.ingredientRepository = ingredientRepository;
        this.productDtoConverter = productDtoConverter;
        this.productQuantityDtoConverter = productQuantityDtoConverter;
    }

    public Ingredient toIngredient(IngredientDto ingredientDto) {

        if(ingredientDto == null) {
            return null;
        }

        if(ingredientDto.getId() != null) {
            return ingredientRepository.findById(ingredientDto.getId()).orElse(null);
        }

        Ingredient ingredient = new Ingredient();

        ingredient.setName(ingredientDto.getName());
        ingredient.setProduct(productDtoConverter.toProduct(ingredientDto.getProduct()));
        ingredient.setProductQuantity(
                productQuantityDtoConverter.toProductQuantity(ingredientDto.getProductQuantity())
        );

        return ingredient;
    }

    public IngredientDto toIngredientDto(Ingredient ingredient) {

        if(ingredient == null) {
            return null;
        }

        IngredientDto ingredientDto = new IngredientDto();

        ingredientDto.setId(ingredient.getId());
        ingredientDto.setName(ingredient.getName());
        ingredientDto.setProduct(productDtoConverter.toProductDto(ingredient.getProduct()));
        ingredientDto.setProductQuantity(
                productQuantityDtoConverter.toProductQuantityDto(ingredient.getProductQuantity())
        );
        ingredientDto.setPrice(ingredient.getPrice());

        return ingredientDto;
    }
}
