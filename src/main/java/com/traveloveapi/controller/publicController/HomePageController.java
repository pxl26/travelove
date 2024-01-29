package com.traveloveapi.controller.publicController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomePageController {
    @RequestMapping("/")
    @ResponseBody
    public String homepage() {
        return
                "<head>" +
                        "<title>One Piece</title>" +
                        "</head><body><h1>Thời đại mới đã đến!!!</h1>" +
                        "<a href=\"/swagger-ui.html\"> API DOCS </a>" +
                        "<img style=\"object-fit:cover;width:100%;\" src=\"https://scontent.fsgn15-1.fna.fbcdn.net/v/t39.30808-6/416451361_884124543498603_8969217201308518175_n.jpg?_nc_cat=102&ccb=1-7&_nc_sid=dd5e9f&_nc_ohc=atCyA3QILbYAX_pV2mx&_nc_ht=scontent.fsgn15-1.fna&oh=00_AfCHVkbgz693q13wQ892geNeKy-iXuiJstaf0h_UpjNvVg&oe=65BC6F47\">" +
                        "</body>";
    }
}
