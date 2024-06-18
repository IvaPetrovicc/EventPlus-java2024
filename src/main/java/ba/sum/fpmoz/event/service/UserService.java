package ba.sum.fpmoz.event.service;

import ba.sum.fpmoz.event.dto.UserDto;
import ba.sum.fpmoz.event.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);

    List<UserDto> findAllUsers();
}