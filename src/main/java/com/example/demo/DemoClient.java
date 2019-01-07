package com.example.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public interface DemoClient {
    
    @RequestMapping(path = "/test", method = RequestMethod.GET)
    String test(@RequestParam("param") String param);

}
