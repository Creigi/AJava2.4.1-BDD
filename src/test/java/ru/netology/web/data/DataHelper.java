package ru.netology.web.data;

import lombok.Value;

import java.util.Random;

public class DataHelper {
  private DataHelper() {}

  @Value
  public static class AuthInfo {
    private String login;
    private String password;
  }

  @Value
  public static class CardInfo{
    int cardPosition;
    String cardNumber;
  }

  public static AuthInfo getAuthInfo() {
    return new AuthInfo("vasya", "qwerty123");
  }

  public static AuthInfo getOtherAuthInfo(AuthInfo original) {
    return new AuthInfo("petya", "123qwerty");
  }

  @Value
  public static class VerificationCode {
    private String code;
  }

  public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
    return new VerificationCode("12345");
  }

  public static CardInfo getFirstCardInfo() {
    return new CardInfo(1, "5559 0000 0000 0001");
  }

  public static CardInfo getSecondCardInfo() {
    return new CardInfo(2, "5559 0000 0000 0002");
  }

  public static int generateValidAmount(int balance) {
    return new Random().nextInt(balance) + 1;
  }

  public static int generateInvalidAmount(int balance) {
    return Math.abs(balance) + new Random().nextInt(10000);
  }
}
