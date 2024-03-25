package com.traveloveapi.controller.publicController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.traveloveapi.DTO.oauth.google.GoogleAccountData;
import com.traveloveapi.DTO.other.BingWallPaperResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

@Controller
public class HomePageController {
    @RequestMapping("/")
    @ResponseBody
    public String homepage() throws JsonProcessingException {
        String dataURL = "https://www.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1&mkt=en-US";
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(dataURL)).method("GET", HttpRequest.BodyPublishers.noBody()).build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.out.println("loi r");
        }
        String data = response.body();
        BingWallPaperResponse wall_paper_rs = new ObjectMapper().readValue(data, BingWallPaperResponse.class);
        String img_src = "https://www.bing.com"+wall_paper_rs.getImages()[0].getUrl();
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
                        "              border-radius: 5px;padding: 10px;\n" +
                        "              position: fixed;\n" +
                        "              top: 80px;\n" +
                        "              left: 20;\n" +
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
                        "        <img style=\"object-fit:cover;width:100%;\" src=\""+ img_src + "\">\n" +
                        "    </div>\n" +
                        "</body>\n" +
                        "</html>";
    }
}
