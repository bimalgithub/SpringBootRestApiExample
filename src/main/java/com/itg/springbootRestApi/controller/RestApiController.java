package com.itg.springbootRestApi.controller;

import java.util.List;
import java.util.Optional;

import com.itg.springbootRestApi.util.CustomErrorType;
import com.itg.springbootRestApi.model.User;
import com.itg.springbootRestApi.service.UserService;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api")
public class RestApiController {

    public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

    @Autowired
    UserService userService; //service which will perform all data retrival and manipulation

    //------------Retrive all users--------------------------------------------------------

    @RequestMapping(value = "/user/", method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAllUsers(){
        List<User> users = userService.findAllUsers();
        if(users.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<User>>(users,HttpStatus.OK);

    }

    //--------------Retrive single user---------------------------------------------------

    @RequestMapping(value="/user/{id}",method = RequestMethod.GET)
    public ResponseEntity<Optional<User>> getUser(@PathVariable("id") long id){
        logger.info("Fetching user with id {}", id);
        Optional<User> user = userService.findById(id);
        if(user == null){
            logger.error("User with id {} not found.",id);
//            return new ResponseEntity<CustomErrorType>(new CustomErrorType("User with id : "+id+" not found."),HttpStatus.NOT_FOUND);
//             return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(user, HttpStatus.OK);
    }

    //----------------Create a user--------------------------------------------------------

    @RequestMapping(value="/user",method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestBody User user){
        logger.info("Creating an User : {}", user);
        /*if(userService.isUserExist(user)){
            logger.error("Unable to create. An user with name {} already exists.",user.getName());
            return new ResponseEntity<>(new CustomErrorType("Unable to create. An user with name "+user.getName()+
                    "already exists."),HttpStatus.CONFLICT);

        }*/
       return ResponseEntity.ok().body(userService.saveUser(user));

    }

    //----------------Update a User-------------------------------------------------------------

    @RequestMapping(value="/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user ){
        logger.info("Updating a user with id {}.",id);
        if(user.getId() == null){
            logger.error("Unable to update. User with id {} not found",id);
            return new ResponseEntity(new CustomErrorType("Unable to update. User with id "+id+" not found."),
                    HttpStatus.NOT_FOUND);

        }


        return new ResponseEntity( userService.saveUser(user), HttpStatus.OK );
    }

    //-------------------Delete a user---------------------------------------------------------------

    @RequestMapping(value="/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id){
        logger.info("Fetching and deleting an user with id {}",id);

        Optional<User> user = userService.findById(id);
        if(user == null){
            logger.error("Unable to delete. The user with id {} not found",id);
            return new ResponseEntity<>(new CustomErrorType("Unable to delete. The user with id "+id+
                    " not found."),HttpStatus.NOT_FOUND);
        }

        userService.deleteUserById(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);

    }

    //-------------------Delete all users-----------------------------------------------------------------

    @RequestMapping(value="/user/", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteAllUsers(){
        logger.info("Deleting all users...");

        userService.deleteAllUsers();
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }



}



