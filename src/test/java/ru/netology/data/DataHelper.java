package ru.netology.data;

import lombok.Value;

import java.util.Random;

public class DataHelper {
    public DataHelper() {
    }

    public AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public AuthInfo getOtherAuthInfo(AuthInfo original) {
        return new AuthInfo("petya", "123qwerty");
    }


    public VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }


    public CardData getFirstCardData() {
        return new CardData("5559 0000 0000 0001", "92df3f1c-a033-48e6-8390-206f6b1f56c0");
    }

    public CardData getSecondCardData() {
        return new CardData("5559 0000 0000 0002", "0f3f5c2a-249e-4c3d-8287-09f7a039391d");
    }

    public int generateValidSum(int balance) {
        return new Random().nextInt(balance) + 1;
    }

    public int generateInvalidSum(int balance) {
        return balance + new Random().nextInt(balance) + 1;
    }

    @Value
    public static class VerificationCode {
        String code;
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    @Value
    public static class CardData {
        String cardNumber;
        String testIdInCss;
    }
}
