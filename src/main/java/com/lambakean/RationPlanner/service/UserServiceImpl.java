package com.lambakean.RationPlanner.service;

import com.lambakean.RationPlanner.dto.UserCredentialsDto;
import com.lambakean.RationPlanner.dto.UserDto;
import com.lambakean.RationPlanner.exception.AuthenticationException;
import com.lambakean.RationPlanner.exception.EntityNotFoundException;
import com.lambakean.RationPlanner.exception.InvalidEntityException;
import com.lambakean.RationPlanner.model.User;
import com.lambakean.RationPlanner.repository.UserRepository;
import com.lambakean.RationPlanner.validator.UserUniquenessValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final Validator userValidator;
    private final UserUniquenessValidator userUniquenessValidator;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           Validator userValidator,
                           UserUniquenessValidator userUniquenessValidator, PasswordEncoder passwordEncoder) {
        this.userValidator = userValidator;
        this.userRepository = userRepository;
        this.userUniquenessValidator = userUniquenessValidator;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto register(@NonNull UserCredentialsDto userCredentialsDto) {  // todo check username uniqueness

        String username = userCredentialsDto.getUsername();
        String password = userCredentialsDto.getPassword();

        User user = new User(username, password);

        validate(user);

        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);

        userRepository.saveAndFlush(user);

        return new UserDto(user.getId(), user.getUsername());
    }

    @Override
    public UserDto login(@NonNull UserCredentialsDto userCredentialsDto) {

        String username = userCredentialsDto.getUsername();
        String password = userCredentialsDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new AuthenticationException("Пользователя с такими логином и паролем не существует")
        );

        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new AuthenticationException("Пользователя с такими логином и паролем не существует");
        }

        return new UserDto(user.getId(), user.getUsername());
    }

    @Override
    public UserDto findUserById(@NonNull String id) {

        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Пользователь с id \"%s\" не найден", id))
        );

        return new UserDto(user.getId(), user.getUsername());
    }

    private void validate(@NonNull User user) {

        DataBinder dataBinder = new DataBinder(user);
        dataBinder.addValidators(userValidator, userUniquenessValidator);
        dataBinder.validate();

        BindingResult userBindingResult = dataBinder.getBindingResult();
        if(userBindingResult.hasErrors()) {
            throw new InvalidEntityException(userBindingResult);
        }
    }
}