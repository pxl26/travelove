package com.traveloveapi.controller.publicController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomePageController {
    @RequestMapping("/")
    @ResponseBody
    public String homepage() {
        return
                "<html>\n" +
                        "<head>\n" +
                        "        <title>One Piece</title>\n" +
                        "        <style>\n" +
                        "            div.relative {\n" +
                        "              position: relative;\n" +
                        "            } \n" +
                        "            \n" +
                        "            div.absolute {\n" +
                        "              border-radius: 5px;\n" +
                        "              position: absolute;\n" +
                        "              top: 80px;\n" +
                        "              left: 0;\n" +
                        "              background-color: aliceblue;\n" +
                        "              opacity: 0.5;\n" +
                        "            }\n" +
                        "            body {\n" +
                        "                padding: 0;\n" +
                        "                margin: 0;\n" +
                        "            }\n" +
                        "            html {\n" +
                        "                padding: 0;\n" +
                        "            }\n" +
                        "        </style>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "    <div class=\"relative\">\n" +
                        "        <div class=\"absolute\">\n" +
                        "            <h1>Thời đại mới đã đến!!!</h1>\n" +
                        "            <a href=\"/swagger-ui.html\"> API DOCS </a>\n" +
                        "        </div>\n" +
                        "        <img style=\"object-fit:cover;width:100%;\" src=\"https://travelove-data.s3.ap-southeast-1.amazonaws.com/public/wall_paper.jpg\">\n" +
                        "    </div>\n" +
                        "</body>\n" +
                        "</html>";
    }
}
