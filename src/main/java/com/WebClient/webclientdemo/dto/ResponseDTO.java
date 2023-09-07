package com.WebClient.webclientdemo.dto;

import com.WebClient.webclientdemo.dto.webclient.BaskaBilgiler;
import com.WebClient.webclientdemo.dto.webclient.UserSorgulamaDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDTO {

    private int status;
    @JsonProperty("data")
    private UserSorgulamaDTO userSorgulamaDTO;

    private BaskaBilgiler baskaBilgiler;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public UserSorgulamaDTO getUserSorgulamaDTO() {
        return userSorgulamaDTO;
    }

    public void setUserSorgulamaDTO(UserSorgulamaDTO userSorgulamaDTO) {
        this.userSorgulamaDTO = userSorgulamaDTO;
    }

    public BaskaBilgiler getBaskaBilgiler() {
        return baskaBilgiler;
    }

    public void setBaskaBilgiler(BaskaBilgiler baskaBilgiler) {
        this.baskaBilgiler = baskaBilgiler;
    }
}
