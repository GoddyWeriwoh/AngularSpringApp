package com.example.AngularDev1.rest;

import com.example.AngularDev1.dto.UserDTO;
import com.example.AngularDev1.exception.CustErrorType;
import com.example.AngularDev1.repository.UserJpaRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> users = userJpaRepository.findAll();
        if (users.isEmpty()) {
            return new ResponseEntity<List<UserDTO>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<UserDTO>>(users, HttpStatus.OK);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> registerUser(@RequestBody final UserDTO userDTO){
        System.out.println("Here I am");
        if (userJpaRepository.findByName(userDTO.getName()) != null) {
            return new ResponseEntity<UserDTO>(new CustErrorType(
                    "Unable to create new user. A User with name "
                            + userDTO.getName() + " already exist."),HttpStatus.CONFLICT);
        }
        userJpaRepository.save(userDTO);

        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findUserById(@PathVariable("id") final Long id){
        UserDTO userDTO1 = new UserDTO();

        Optional<UserDTO> userDTO = userJpaRepository.findById(id);
        if(userDTO.isPresent()){
            userDTO1 = userDTO.get();
            if(userDTO1 == null){
                return new ResponseEntity<UserDTO>(new CustErrorType("User with id" + id + "not found"), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<UserDTO>(userDTO1, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<UserDTO>(new CustErrorType("User with id" + id + "not found"), HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> updateUserById(@PathVariable("id") final Long id, @RequestBody final UserDTO nUserDTO){
        UserDTO userDTO = new UserDTO();
        Optional<UserDTO> user = userJpaRepository.findById(id);

        if(user.isPresent()){
            userDTO = user.get();

            if (userDTO == null) {
                return new ResponseEntity<UserDTO>(
                        new CustErrorType("Unable to upate. User with id "
                                + id + " not found."), HttpStatus.NOT_FOUND);
            }

            userDTO.setAddress(nUserDTO.getAddress());
            userDTO.setEmail(nUserDTO.getEmail());
            userDTO.setId(nUserDTO.getId());
            userDTO.setName(nUserDTO.getName());

            userJpaRepository.saveAndFlush(userDTO);

            return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);

        }

        return new ResponseEntity<UserDTO>(
                new CustErrorType("Unable to upate. User with id "
                        + id + " not found."), HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDTO> deleteUserById(@PathVariable("id") Long id){
        Optional<UserDTO> user = userJpaRepository.findById(id);
        UserDTO userDTO = new UserDTO();

        if(user.isPresent()){
            userDTO = user.get();
            if (userDTO == null) {
                return new ResponseEntity<UserDTO>(
                        new CustErrorType("Unable to delete. User with id "
                                + id + " not found."), HttpStatus.NOT_FOUND);
            }

            userJpaRepository.deleteById(id);
            return new ResponseEntity<UserDTO>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<UserDTO>(
                new CustErrorType("Unable to delete. User with id "
                        + id + " not found."), HttpStatus.NOT_FOUND);
    }

    @RequestMapping("/hello")
    public String greeting(){
        return "looooooo";
    }
}
