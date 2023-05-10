package com.pocosoft.demo.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class PortalWebAuthenticationDetails extends WebAuthenticationDetails {

    private String OTP;

    public PortalWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        OTP = request.getParameter("OTP");
    }

    public String getOneTimePassword() {
        return OTP;
    }
}
