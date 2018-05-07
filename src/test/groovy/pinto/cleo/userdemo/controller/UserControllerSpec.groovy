package pinto.cleo.userdemo.controller

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import pinto.cleo.userdemo.common.dto.ResponseDTO
import pinto.cleo.userdemo.dto.UserDTO
import pinto.cleo.userdemo.service.UserService
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

/**
 * Created by cleo on 5/6/18.
 */
class UserControllerSpec extends Specification {

    UserController userController
    def mockUserService = Mock(UserService)
    def objectMapper = new ObjectMapper()

    MockMvc mockMvc

    def setup() {
        userController = new UserController(mockUserService)
        mockMvc = standaloneSetup(userController).build()
    }

    def "updates a user"() {
        given:
        UserDTO user = new UserDTO()
        def userName = "user1"
        def userEmail = "user1@test.com"
        user.setEmail(userEmail)
        user.setName(userName)
        when: 'performing user update action'
        def response = mockMvc.perform(put("/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(user)))
                .andReturn().response
        then:
        1 * mockUserService.updateUser(_, _) >> { arguments ->
            Integer id = arguments[0]
            UserDTO userArg = arguments[1]
            assert id == 1
            assert userArg.email == userEmail
            assert userArg.name == userName
        }
        assert HttpStatus.NO_CONTENT.value() == response.status
    }

    def "cannot update user - bad email"() {
        given:
        UserDTO user = new UserDTO()
        user.setEmail("@test.com")
        user.setName("user2")
        when: 'performing user update action'
        def response = mockMvc.perform(put("/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(user)))
                .andReturn().response
        then:
        0 * mockUserService.updateUser(_, _)
        HttpStatus.BAD_REQUEST.value() == response.status
    }

    def "cannot update user - bad name"() {
        given:
        UserDTO user = new UserDTO()
        user.setEmail("user2@test.com")
        user.setName("")
        when: 'performing user update action'
        def response = mockMvc.perform(put("/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(user)))
                .andReturn().response
        then:
        0 * mockUserService.updateUser(_, _)
        HttpStatus.BAD_REQUEST.value() == response.status
    }

    def "creates a user"() {
        given:
        UserDTO user = new UserDTO()
        def userName = "user1"
        def userEmail = "user1@test.com"
        user.setEmail(userEmail)
        user.setName(userName)
        when: 'performing user create action'
        def response = mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(user)))
                .andReturn().response
        println ('anc"' + response.contentAsString)
        ResponseDTO<String> responseDTO = objectMapper.readValue(response.contentAsString,
                new TypeReference<ResponseDTO<String>>() {})
        then:
        1 * mockUserService.createUser(_) >> { arguments ->
            UserDTO userArg = arguments[0]
            assert userArg.email == userEmail
            assert userArg.name == userName
            return 10
        }
        assert HttpStatus.CREATED.value() == response.status
        assert responseDTO.messages[0].contains("10")
    }

    def "returns a user"() {
        given:
        def userId = 1
        def userName = "user1"
        def userEmail = "user1@test.com"
        when: 'performing user get action'
        def response = mockMvc.perform(get("/user/1")).andReturn().response
        UserDTO userResponse = objectMapper.readValue(response.contentAsString, UserDTO.class)
        then:
        1 * mockUserService.getUser(1) >> new UserDTO(userId, userName, userEmail)
        assert HttpStatus.OK.value() == response.status
        userResponse.name == userName
        userResponse.email == userEmail
    }
}
