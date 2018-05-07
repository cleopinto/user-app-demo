package pinto.cleo.userdemo.service

import pinto.cleo.userdemo.common.exception.ResourceNotFoundException
import pinto.cleo.userdemo.dto.UserDTO
import pinto.cleo.userdemo.entity.UserEntity
import pinto.cleo.userdemo.repository.UserRepository
import spock.lang.Specification
/**
 * Created by cleo on 5/6/18.
 */
class UserServiceSpec extends Specification {

    UserService userService
    def mockUserRepository = Mock(UserRepository)

    def setup() {
        userService = new UserService(mockUserRepository)
    }

    def 'create a user'() {
        given:
        UserDTO user = new UserDTO()
        user.name = "test"
        user.email = "test@abc.com"
        when:
        Integer id = userService.createUser(user)
        then:
        1 * mockUserRepository.save(_) >> {args ->
            UserEntity entity = args[0]
            assert entity.name == "test"
            assert entity.email == "test@abc.com"
            entity.id = 10
            return entity
        }
        assert id == 10
    }

    def 'update a user'() {
        given:
        UserDTO user = new UserDTO()
        user.name = "test"
        user.email = "test@abc.com"
        when:
        userService.updateUser(10, user)
        then:
        1 * mockUserRepository.findById(10) >> Optional.of(new UserEntity(10, "existingName",
                "existingEmail"))
        1 * mockUserRepository.save(_) >> {args ->
            UserEntity entity = args[0]
            assert entity.name == "test"
            assert entity.email == "test@abc.com"
            assert entity.id == 10
        }
    }

    def 'update a user - 404'() {
        given:
        UserDTO user = new UserDTO()
        user.name = "test"
        user.email = "test@abc.com"
        when:
        userService.updateUser(10, user)
        then:
        1 * mockUserRepository.findById(10) >> {throw new ResourceNotFoundException("Not found")}
        0 * mockUserRepository.save(_)
        thrown ResourceNotFoundException
    }

    def 'get a user'() {
        when:
        UserDTO user = userService.getUser(10)
        then:
        1 * mockUserRepository.findById(10) >> Optional.of(new UserEntity(10, "existingName",
                "existingEmail"))
        assert user.email == "existingEmail"
        assert user.name == "existingName"
        assert user.id == 10
    }

    def 'get a user - 404'() {
        when:
        UserDTO user = userService.getUser(10)
        then:
        1 * mockUserRepository.findById(10) >> {throw new ResourceNotFoundException("Not found")}
        thrown ResourceNotFoundException
    }
}
