package com.lambakean.RationPlanner.validator;

import com.lambakean.RationPlanner.model.User;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {

        User user = (User) target;

        validateUsername(user.getUsername(), errors);
        validatePassword(user.getPassword(), errors);
    }

    public void validateUsername(String username, @NonNull Errors errors) {
        if (username == null) {
            errors.rejectValue(
                    "username",
                    "username.invalid",
                    "Имя пользователя не должно быть пустым"
            );
            return;
        }

        if(4 > username.length() || username.length() > 20) {
            errors.rejectValue(
                    "username",
                    "username.invalid",
                    "Имя пользователя должно иметь длину от 4 до 20 символов"
            );
            return;
        }

        if(username.startsWith("_") || username.startsWith(".") || username.endsWith("_") || username.endsWith(".")) {
            errors.rejectValue(
                    "username",
                    "username.invalid",
                    "Символы \".\" или \"_\" не должны находиться в начале или в конце имени пользователя"
            );
        }

        if(username.contains("__") || username.contains("_.") || username.contains("._") || username.contains("..")) {
            errors.rejectValue(
                    "username",
                    "username.invalid",
                    "Имя пользователя не должно содержать пары символов \"__\", \"_.\", \"._\" или \"..\""
            );
        }

        for(char c : username.toCharArray()) {
            if(!Character.isLetterOrDigit(c) && c != '_' && c != '.') {
                errors.rejectValue(
                        "username",
                        "username.invalid",
                        "Имя пользователя должно состоять только из букв, цифр и символов \".\" и \"-\""
                );
            }
        }
    }

    public void validatePassword(String password, @NonNull Errors errors) {

        if(password == null) {
            errors.rejectValue(
                    "password",
                    "password.invalid",
                    "Пароль не должен быть пустым"
            );
            return;
        }

        if(8 > password.length() || password.length() > 60) {
            errors.rejectValue(
                    "password",
                    "password.invalid",
                    "Пароль должен иметь длину от 8 до 60 символов"
            );
        }
    }
}
