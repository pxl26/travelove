package com.traveloveapi.controller.publicController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomePageController {
    @RequestMapping("/")
    @ResponseBody
    public String homepage() {
        return "<meta name=\"zalo-platform-site-verification\" content=\"GFoq08lq1Wr9_z0goUbM1X71vb3zjpGBCJCn\" />";
    }
}
