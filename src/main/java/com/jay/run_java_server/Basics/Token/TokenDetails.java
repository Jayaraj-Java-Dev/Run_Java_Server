package com.jay.run_java_server.Basics.Token;

import java.util.Calendar;
import java.util.Date;

import com.google.gson.Gson;
import com.jay.run_java_server.Basics.AES.DataSafety;
import com.jay.run_java_server.Basics.AES.DataSafety.TokenTypes;

public class TokenDetails {
    public final static Integer DefaultExpirationMinutes = 30;
    TokenTypes encryptionLevel;

    private String Data;
    private java.util.Date ExpiredOn;
    public Boolean isValid;

    public <T> TokenDetails(T Data, Integer MinutesForExpiration, TokenTypes encryptionLevel) {
        this.encryptionLevel = encryptionLevel;
        if (MinutesForExpiration == null) {
            this.ExpiredOn = getDefaultExpirationTime();
        } else {
            this.ExpiredOn = getExpirationTime(new Date(), MinutesForExpiration);
        }
        this.storeDataAs(Data);
        isValid = true;
    }

    public TokenDetails(String encryptedToken, TokenTypes tokenType) {
        this.encryptionLevel = tokenType;
        try {
            decrypt(encryptedToken);
            isValid = true;
        } catch (Exception e) {
            isValid = false;
        }
    }

    public String getDataRaw() {
        return new String(Data);
    }

    public Boolean isExpired() {
        return ExpiredOn.before(new Date());
    }

    public String toString() {
        return new Gson().toJson(this);
    }

    public String encrypt() {
        String tokenString = this.toString();
        String encryptedTokenString = DataSafety.encrypt(tokenString, encryptionLevel);
        return encryptedTokenString;
    }

    public void decrypt(String encryptedToken) {
        String decryptedTokenString = DataSafety.decrypt(encryptedToken, encryptionLevel);
        Gson gson = new Gson();
        TokenDetails decryptedToken = gson.fromJson(decryptedTokenString, TokenDetails.class);

        Data = decryptedToken.Data;
        ExpiredOn = decryptedToken.ExpiredOn;
    }

    public <T> void storeDataAs(T object) {
        Gson gson = new Gson();
        Data = gson.toJson(object);
    }

    public <T> T parseDataAs(Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(Data, clazz);
    }

    static Date getDefaultExpirationTime() {
        return getExpirationTime(new Date(), DefaultExpirationMinutes);
    }

    static Date getExpirationTime(Date expireDate, Integer expirationMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(expireDate); // Set the calendar to the provided expireDate
        calendar.add(Calendar.MINUTE, expirationMinutes); // Add the minutes
        return calendar.getTime(); // Return the updated date
    }
}
