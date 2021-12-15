package com.lambakean.RationPlanner.validator;

import com.lambakean.RationPlanner.model.User;
import com.lambakean.RationPlanner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserUniquenessValidator implements Validator {

    private final UserRepository userRepository;

    @Autowired
    public UserUniquenessValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        
        User user = (User) target;
        
        if(userRepository.existsByUsername(user.getUsername())) {
            errors.rejectValue(
                    "username",
                    "username.invalid",
                    "Пользователь с таким именем уже существует"
            );
        }
    }
}
