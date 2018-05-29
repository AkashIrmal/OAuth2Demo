package com.fintec.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fintec.oauth.model.User;
import com.fintec.utils.BaseResponse;


@RestController(value="/admin/")
public class AdminController {

	
  @RequestMapping(value = "loadUsers", method = RequestMethod.POST)
  public ResponseEntity<BaseResponse<List<User>>> loadUser(){
    return null; 
  }
}
