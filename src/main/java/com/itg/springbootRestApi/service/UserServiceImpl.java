package com.itg.springbootRestApi.service;

import com.itg.springbootRestApi.model.User;
import com.itg.springbootRestApi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

@Service("userService")
public class UserServiceImpl implements UserService {

    private static final AtomicLong counter = new AtomicLong();

    private static List<User> users;

    @Autowired
    private UserRepository userRepository;

    static {
        users= populateDummyUsers();
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }


    public Optional<User> findById(long id){
        /*for(User user: users){
            if(user.getId()==id)

                return user;
        }*/
        return userRepository.findById(id);
    }

    public User findByName(String name){
        for(User user: users){
            if(user.getName().equalsIgnoreCase(name))
                return user;
        }
        return null;
    }

    public User saveUser(User user){
        /*user.setId(counter.incrementAndGet());
        users.add(user);*/
        return userRepository.save(user);

    }

    public void updateUser(User user){
        int index = users.indexOf(user);
        users.set(index, user);
    }

    public void deleteUserById(long id){
        for(Iterator<User> iterator = users.iterator(); iterator.hasNext();){
            User user = iterator.next();
            if(user.getId()==id)
                iterator.remove();
        }
    }

    public void deleteAllUsers(){
        users.clear();
    }

    public boolean isUserExist(User user){
        return findByName(user.getName())!=null;
    }

    private static List<User> populateDummyUsers(){
        List<User> users = new ArrayList<User>();
        users.add(new User(counter.incrementAndGet(),"Ram",25,30000));
        users.add(new User(counter.incrementAndGet(),"Shayam",27,35000));
        users.add(new User(counter.incrementAndGet(),"Hari",30,40000));
        return users;
    }



}
