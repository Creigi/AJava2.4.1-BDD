package ru.netology.web.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;


import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.DataHelper.*;

class MoneyTransferTest {

  LoginPage loginPage;
  DashboardPage dashboardPage;

  @BeforeEach
  void setup() {
    loginPage = open("http://localhost:9999", LoginPage.class);
    var authInfo = DataHelper.getAuthInfo();
    var verificationPage = loginPage.validLogin(authInfo);
    var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
    dashboardPage = verificationPage.validVerify(verificationCode);
  }

  @Test
  void shouldTransferFromFirstCardToSecond() {
    var firstCardInfo = getFirstCardInfo();
    var secondCardInfo = getSecondCardInfo();
    var firstCardBalance = dashboardPage.getBalance(firstCardInfo);
    var secondCardBalance = dashboardPage.getBalance(secondCardInfo);
    var amount = generateValidAmount(firstCardBalance);
    var expectedBalanceFirstCard = firstCardBalance - amount;
    var expectedBalanceSecondCard = secondCardBalance + amount;
    var transferPage = dashboardPage.selectCardToDeposit(secondCardInfo);
    dashboardPage = transferPage.makeValidTransfer(amount, firstCardInfo);
    var actualBalanceFirstCard = dashboardPage.getBalance(firstCardInfo);
    var actualBalanceSecondCard = dashboardPage.getBalance(secondCardInfo);
    assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
    assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
  }

  @Test
  void shouldGetErrorMessageIfAmountMoreBalance() {
    var firstCardInfo = getFirstCardInfo();
    var secondCardInfo = getSecondCardInfo();
    var firstCardBalance = dashboardPage.getBalance(firstCardInfo);
    var secondCardBalance = dashboardPage.getBalance(secondCardInfo);
    var amount = generateInvalidAmount(firstCardBalance);
    var transferPage = dashboardPage.selectCardToDeposit(secondCardInfo);
    transferPage.makeTransfer(amount, firstCardInfo);
    transferPage.findErrorMessage("Введена сумма, превышающая значение остатка на карте");
    var actualBalanceFirstCard = dashboardPage.getBalance(firstCardInfo);
    var actualBalanceSecondCard = dashboardPage.getBalance(secondCardInfo);
    assertEquals(firstCardBalance, actualBalanceFirstCard);
    assertEquals(secondCardBalance, actualBalanceSecondCard);
  }
}