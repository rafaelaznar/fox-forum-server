package net.ausiasmarch.foxforumserver.bean;

public class CaptchaResponseBean {

    private String token;
    private byte[] captchaImage;

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public byte[] getCaptchaImage() {
        return captchaImage;
    }
    public void setCaptchaImage(byte[] captcha) {
        this.captchaImage = captcha;
    }
    
}
