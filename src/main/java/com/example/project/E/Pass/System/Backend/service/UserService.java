package com.example.project.E.Pass.System.Backend.service;

import com.example.project.E.Pass.System.Backend.dto.request.UserRequestDto;
import com.example.project.E.Pass.System.Backend.dto.response.UserResponseDto;
import com.example.project.E.Pass.System.Backend.entity.User;
import com.example.project.E.Pass.System.Backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    public UserResponseDto createUser(UserRequestDto requestDto) {
        User newUser = modelMapper.map(requestDto, User.class);
        User savedUser = userRepository.save(newUser);
        UserResponseDto responseDto = modelMapper.map(savedUser, UserResponseDto.class);
        return responseDto;
    }

    public List<UserResponseDto> getAllUsers() {
        List<User> userList = userRepository.findAll();
        return userList
                .stream()
                .map(user -> modelMapper.map(user, UserResponseDto.class))
                .toList();
    }

    public UserResponseDto getUserById(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found with id : " + id));
        UserResponseDto responseDto = modelMapper.map(user, UserResponseDto.class);
        return responseDto;
    }

    public void deleteUserById(String id) {

        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("User does not exists with id: " + id);
        }
    }

}
















