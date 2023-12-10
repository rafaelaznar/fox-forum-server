package net.ausiasmarch.foxforumserver.dto;

public class EmailValuesDTO {
    private String strMailFrom;
    private String strMailTo;
    private String strSubject;
    private String strUserName;
    private String strToken;

    public EmailValuesDTO() {
    }

    public EmailValuesDTO(String strMailFrom, String strMailTo, String strSubject, String strUserName, String strToken) {
        this.strMailFrom = strMailFrom;
        this.strMailTo = strMailTo;
        this.strSubject = strSubject;
        this.strUserName = strUserName;
        this.strToken = strToken;
    }
    
    public String getStrMailFrom() {
        return this.strMailFrom;
    }

    public void setStrMailFrom(String strMailFrom) {
        this.strMailFrom = strMailFrom;
    }

    public String getStrMailTo() {
        return this.strMailTo;
    }

    public void setStrMailTo(String strMailTo) {
        this.strMailTo = strMailTo;
    }

    public String getStrSubject() {
        return this.strSubject;
    }

    public void setStrSubject(String strSubject) {
        this.strSubject = strSubject;
    }

    public String getStrUserName() {
        return this.strUserName;
    }

    public void setStrUserName(String strUserName) {
        this.strUserName = strUserName;
    }

    public String getStrToken() {
        return this.strToken;
    }

    public void setStrToken(String strToken) {
        this.strToken = strToken;
    }
    
}
