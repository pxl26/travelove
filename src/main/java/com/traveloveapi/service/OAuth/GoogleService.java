package com.traveloveapi.service.OAuth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.traveloveapi.DTO.TokenResponse;
import com.traveloveapi.DTO.oauth.google.GoogleAccountData;
import com.traveloveapi.DTO.oauth.google.GoogleTokenResponse;
import com.traveloveapi.entity.GoogleEntity;
import com.traveloveapi.entity.UserEntity;
import com.traveloveapi.entity.UserDetailEntity;
import com.traveloveapi.repository.GoogleRepository;
import com.traveloveapi.repository.UserDetailRepository;
import com.traveloveapi.repository.UserRepository;
import com.traveloveapi.utility.JwtProvider;
import com.traveloveapi.utility.SecurityContext;
import com.traveloveapi.constrain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GoogleService {
    private String client_id="226555921335-sg94jf64gf5lq0rdfbo4ulr28i2u8fqu.apps.googleusercontent.com";
    private String client_secret="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";
    private String grant_type = "authorization_code";
    private String scope="https://www.googleapis.com/auth/userinfo.email+https://www.googleapis.com/auth/userinfo.email+https://www.googleapis.com/auth/userinfo.email+https://www.googleapis.com/auth/userinfo.email+https://www.googleapis.com/auth/userinfo.profile+https://www.googleapis.com/auth/userinfo.email+https://www.googleapis.com/auth/userinfo.profile";
    //private String redirect_uri="http%3A%2F%2Flocalhost%3A8080%2Fauth%2Foauth%2Fgoogle";
    private String redirect_uri="https%3A%2F%2Fec2-3-0-65-96.ap-southeast-1.compute.amazonaws.com%3A2511//auth/oauth/google";
    final private GoogleRepository googleRepository;
    final private UserRepository userRepository;
    final private UserDetailRepository userDetailRepository;

    public TokenResponse login(String code) throws IOException {
        String getTokenURL = "https://oauth2.googleapis.com/token?";
        String access_type = "offline";
        getTokenURL+= "client_id=" + client_id + "&client_secret=" + client_secret + "&access_type=" + access_type + "&grant_type=" + grant_type + "&code=" + code + "&redirect_uri=" + redirect_uri;
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(getTokenURL)).method("POST",HttpRequest.BodyPublishers.noBody()).build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.out.println("loi r");
        }
        assert response != null;
        String data = response.body();

        GoogleTokenResponse tokenResponse = new ObjectMapper().readValue(data, GoogleTokenResponse.class);

        //-----------------------GET DATA BY ACCESS TOKEN--------------------------------------
        String dataURL = "https://www.googleapis.com/oauth2/v2/userinfo?access_token="+tokenResponse.getAccess_token();
        request = HttpRequest.newBuilder().uri(URI.create(dataURL)).method("GET", HttpRequest.BodyPublishers.noBody()).build();
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.out.println("loi r");
        }
        data = response.body();
        GoogleAccountData userInfo = new ObjectMapper().readValue(data, GoogleAccountData.class);

        //--------------------- CASE: Account is exist-----
        GoogleEntity myGoogleData = googleRepository.find(userInfo.getId());
        if (myGoogleData!=null)
        {
            System.out.println(myGoogleData.getId());
            UserEntity user = userRepository.find(myGoogleData.getUser_id());
            SecurityContext.setUser(user.getId(), user.getRole().toString());
            return JwtProvider.generateTokenResponse(user.getId(), user.getRole());
        }
        //---------------CASE: New user (Register by Google)
        //---- convert response data to GoogleEntity
        //----- SAVE GOOGLE OAuth
        GoogleEntity googleEntity = new GoogleEntity();
        googleEntity.setId(userInfo.getId());
        googleEntity.setName(userInfo.getName());
        googleEntity.setGiven_name(userInfo.getGiven_name());
        googleEntity.setFamily_name(userInfo.getFamily_name());
        googleEntity.setPicture(userInfo.getPicture());
        googleEntity.setLocale(userInfo.getLocale());
        googleEntity.setEmail(userInfo.getEmail());
        googleEntity.setVerified_email(userInfo.isVerified_email());

        googleEntity.setAccess_token(tokenResponse.getAccess_token());
        googleEntity.setRefresh_token((tokenResponse.getRefresh_token()));
        googleEntity.setExpires_in(tokenResponse.getExpires_in());
        googleEntity.setScope(tokenResponse.getScope());
        googleEntity.setId_token(tokenResponse.getId_token());

        //---------- USER DATA-----------
        UserEntity newUser = new UserEntity();
        UserDetailEntity newDetail = new UserDetailEntity();
        UUID newId = UUID.randomUUID();
        newUser.setRole(Role.USER);
        newUser.setId(newId.toString());
        newUser.setFull_name(userInfo.getFamily_name()+" "+userInfo.getGiven_name()); // full = family + given ?
        newDetail.setUser_id(newUser.getId());
        newDetail.setEmail(userInfo.getEmail());
        newDetail.setCreate_at(new Timestamp(System.currentTimeMillis()));
        //---------------
        googleEntity.setUser_id(newUser.getId());
        googleRepository.save(googleEntity);
        userRepository.save(newUser);
        userDetailRepository.save(newDetail);
        //-------------------------
        return JwtProvider.generateTokenResponse(newUser.getId(), newUser.getRole());
    }
}
