package com.traveloveapi.controller.publicController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomePageController {
    @RequestMapping("/")
    @ResponseBody
    public String homepage() {
        return "<head><title>One piece</title></head><body><h1>Thời đại mới đã đến!!!</h1></body>";
    }
}
