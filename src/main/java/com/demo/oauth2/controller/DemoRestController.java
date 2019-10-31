package com.demo.oauth2.controller;

import com.demo.oauth2.dao.UserRepository;
import com.demo.oauth2.entity.AppUser;
import com.demo.oauth2.form.AppUserForm;
import com.demo.oauth2.service.UserDetailsServiceImpl;
import com.demo.oauth2.service.UserMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.social.linkedin.api.Product;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Optional;

@RestController
public class DemoRestController {
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapperImpl userMapperImpl;

    @RequestMapping(value = "/restapi/alluser",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AppUserForm>> findAllProduct() {
        List<AppUserForm> userList = userDetailsServiceImpl.getAllUsers();
        if (userList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @RequestMapping(value = "/restapi/alluser/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppUserForm> getProductById(
            @PathVariable("id") Long id) {
        Optional<AppUserForm> userForm = Optional.ofNullable(userMapperImpl.toUserForm(userRepository.findByUserId(id)));
        if (!userForm.isPresent()) {
            return new ResponseEntity<>(userForm.get(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userForm.get(), HttpStatus.OK);
    }


}
