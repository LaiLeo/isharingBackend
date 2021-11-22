package com.fih.ishareing.service.validation.model;

public class EmailValidateRequestVO {
    private String email;
    private String accessPage;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccessPage() {
        return accessPage;
    }

    public void setAccessPage(String accessPage) {
        this.accessPage = accessPage;
    }
}
