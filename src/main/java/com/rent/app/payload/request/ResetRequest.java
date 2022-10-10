package com.rent.app.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ResetRequest {

    @NotBlank
    private String email;

    private String otp;

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOtp() {
        return otp;
    }

    public String getEmail() {
        return email;
    }

}
