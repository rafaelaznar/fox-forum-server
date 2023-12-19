package net.ausiasmarch.foxforumserver.dto;

public class EmailValuesDTO {
    private String mailFrom;
    private String mailTo;
    private String mailSubject;
    private String userName;
    private String tokenPassword;

    public EmailValuesDTO() {
    }

    public EmailValuesDTO(String mailFrom, String mailTo, String mailSubject, String userName, String tokenPassword) {
        this.mailFrom = mailFrom;
        this.mailTo = mailTo;
        this.mailSubject = mailSubject;
        this.userName = userName;
        this.tokenPassword = tokenPassword;
    }
    
    public String getMailFrom() {
        return this.mailFrom;
    }

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    public String getMailTo() {
        return this.mailTo;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    public String getMailSubject() {
        return this.mailSubject;
    }

    public void setMailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTokenPassword() {
        return this.tokenPassword;
    }

    public void setTokenPassword(String tokenPassword) {
        this.tokenPassword = tokenPassword;
    }
    
}
