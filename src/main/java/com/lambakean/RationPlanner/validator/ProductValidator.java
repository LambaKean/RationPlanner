package com.lambakean.RationPlanner.validator;

import com.lambakean.RationPlanner.model.Product;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProductValidator implements Validator {
    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return Product.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {

        Product product = (Product) target;

        validateName(product.getName(), errors);
        validateProducer(product.getProducer(), errors);
        validatePrice(product.getPrice(), errors);
    }

    public void validateName(String name, @NonNull Errors errors) {

        if (name == null) {
            errors.rejectValue(
                    "name",
                    "name.invalid",
                    "Название продукта не должно быть пустым"
            );
            return;
        }

        if(2 > name.length() || name.length() > 50) {
            errors.rejectValue(
                    "name",
                    "name.invalid",
                    "Название продукта должно иметь длину от 2 до 50 символов"
            );
        }
    }

    public void validateProducer(String producer, @NonNull Errors errors) {

        if(producer != null) {

            if(1 > producer.length() || producer.length() > 50) {
                errors.rejectValue(
                        "producer",
                        "producer.invalid",
                        "Имя производителя должно иметь длину от 2 до 50 символов"
                );
            }
        }
    }

    public void validatePrice(Double price, @NonNull Errors errors) {
        if(price == null) {
            errors.rejectValue(
                    "price",
                    "price.invalid",
                    "Вы должны указать цену продукта"
            );
            return;
        }

        if(price <= 0) {
            errors.rejectValue(
                    "price",
                    "price.invalid",
                    "Цена продукта должна быть больше нуля"
            );
        }
    }
}
