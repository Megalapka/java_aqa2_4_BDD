package ru.netology.test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;

import ru.netology.page.LoginPage;



import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

public class TransferTest {

    @AfterEach
    void tearDown() {
        closeWindow();
    }
    @Test
    @DisplayName("Should transfer money to first card from second card")
    void shouldTransferMoneyToFirstFromSecondCard() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);

        var firstCardData = DataHelper.getFirstCardData();
        var secondCardData = DataHelper.getSecondCardData();

        var firstCardBalance = dashboardPage.getCardBalance(firstCardData);
        var secondCardBalance = dashboardPage.getCardBalance(secondCardData);

        var sum = DataHelper.generateValidSum(secondCardBalance);

        var expectedFirstCardBalance = firstCardBalance + sum;
        var expectedSecondCardBalance = secondCardBalance - sum;

        var transferPage = dashboardPage.getTransferPage(firstCardData);
        dashboardPage = transferPage.validTransfer(String.valueOf(sum), secondCardData);

        var actualFirstCardBalance = dashboardPage.getCardBalance(firstCardData);
        var actualSecondCardBalance = dashboardPage.getCardBalance(secondCardData);

        assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        assertEquals(expectedSecondCardBalance,actualSecondCardBalance);
    }

    @Test
    @DisplayName("Should transfer money to second card from first card")
    void shouldTransferMoneyToSecondCardFromFirst() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);

        var firstCardData = DataHelper.getFirstCardData();
        var secondCardData = DataHelper.getSecondCardData();

        var firstCardBalance = dashboardPage.getCardBalance(firstCardData);
        var secondCardBalance = dashboardPage.getCardBalance(secondCardData);

        var sum = DataHelper.generateValidSum(firstCardBalance);

        var expectedFirstCardBalance = firstCardBalance - sum;
        var expectedSecondCardBalance = secondCardBalance + sum;

        var transferPage = dashboardPage.getTransferPage(secondCardData);
        dashboardPage = transferPage.validTransfer(String.valueOf(sum),firstCardData);

        var actualFirstCardBalance = dashboardPage.getCardBalance(firstCardData);
        var actualSecondCardBalance = dashboardPage.getCardBalance(secondCardData);

        assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        assertEquals(expectedSecondCardBalance,actualSecondCardBalance);
    }


    @Test
    @DisplayName("Should not transfer money to first card if on second card insufficient sum")
    void shouldNotTransferMoneyToFirstCardFromSecondInsufficientSum() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);

        var firstCardData = DataHelper.getFirstCardData();
        var secondCardData = DataHelper.getSecondCardData();

        var secondCardBalance = dashboardPage.getCardBalance(secondCardData);

        var sum = DataHelper.generateInvalidSum(secondCardBalance);

        var transferPage = dashboardPage.getTransferPage(firstCardData);
        transferPage.topUpCard(String.valueOf(sum), secondCardData);
        transferPage.errorWithInsufficientSum("Ошибка! Недостаточно средств на карте!");


    }


}
