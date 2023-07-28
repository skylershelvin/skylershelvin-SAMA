package org.skylershelvin.SAMABackened;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {

    @GetMapping("/Test")
        public String test(){
            return "test";
        }
}
