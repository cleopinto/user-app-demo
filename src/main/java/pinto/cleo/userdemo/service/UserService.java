package pinto.cleo.userdemo.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import pinto.cleo.userdemo.common.exception.ResourceNotFoundException;
import pinto.cleo.userdemo.dto.UserDTO;
import pinto.cleo.userdemo.entity.UserEntity;
import pinto.cleo.userdemo.repository.UserRepository;

import java.util.Optional;

/**
 * Created by cleo on 5/5/18.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserDTO getUser(Integer id){

        Optional<UserDTO> user = userRepository.findById(id)
                .map(userEntity -> new UserDTO(userEntity.getId(), userEntity.getName(), userEntity.getEmail()));

        if(!user.isPresent()){
            throw new ResourceNotFoundException(String.format("The requested resource %s was not found", id));
        }
        return user.get();
    }

    public void updateUser(Integer id, UserDTO user) {
        Optional<UserEntity> userEntity = userRepository.findById(id);

        if(!userEntity.isPresent()){
            throw new ResourceNotFoundException(String.format("The resource %s was not found. Hence could not be updated.", id));
        }

        if(StringUtils.isNotBlank(user.getName())){
            userEntity.get().setName(user.getName());
        }
        if(StringUtils.isNotBlank(user.getEmail())){
            userEntity.get().setEmail(user.getEmail());
        }
        userRepository.save(userEntity.get());
    }

    public Integer createUser(UserDTO user) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        UserEntity createdUser = userRepository.save(userEntity);
        return createdUser.getId();
    }
}
