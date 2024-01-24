package net.ausiasmarch.foxforumserver.dto;

import jakarta.validation.constraints.NotBlank;

public class ChangePasswordDTO {
    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Confirm password")
    private String confirmPassword;

    @NotBlank(message = "tokenPassword is required")
    private String tokenPassword;

    public ChangePasswordDTO() {
    }

    public ChangePasswordDTO(String password, String confirmPassword, String tokenPassword) {
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.tokenPassword = tokenPassword;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String strPassword) {
        this.password = strPassword;
    }

    public String getConfirmPassword() {
        return this.confirmPassword;
    }

    public void setConfirmPassword(String strConfirmPassword) {
        this.confirmPassword = strConfirmPassword;
    }

    public String getTokenPassword() {
        return this.tokenPassword;
    }

    public void setTokenPassword(String strtokenPassword) {
        this.tokenPassword = strtokenPassword;
    }
}
