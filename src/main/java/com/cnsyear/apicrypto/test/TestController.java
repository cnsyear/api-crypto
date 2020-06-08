package com.cnsyear.apicrypto.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/test")
    public String test(String name){
        System.out.println("获取："+name);
        return name;
    }

    @PostMapping("/api/save")
    public UserDto save(@RequestBody UserDto dto) {
        System.err.println(dto.getId() + "\t" + dto.getName());
        return dto;
    }
}