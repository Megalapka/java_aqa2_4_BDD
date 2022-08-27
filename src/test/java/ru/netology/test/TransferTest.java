package ru.netology.test;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import java.util.Locale;

import static com.codeborne.selenide.Selenide.*;

public class TransferTest {

    @Test
    @DisplayName("Should transfer money to first card from second card")
    void shouldTransferMoneyToFirstFromSecondCard() {

        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);

        DashboardPage balanceCard = new DashboardPage();
        int startBalanceFirstCard = balanceCard.getCardBalance("92df3f1c-a033-48e6-8390-206f6b1f56c0");
        int startBalanceSecondCard =balanceCard.getCardBalance("0f3f5c2a-249e-4c3d-8287-09f7a039391d");
        int sum;
        if (startBalanceSecondCard > 3000 ) {
        sum = 3000;
        } else sum = startBalanceSecondCard;
        DashboardPage.transferOwnToFirstFromSecondCard(String.valueOf(sum));

        int actualBalanceFirstCard = balanceCard.getCardBalance("92df3f1c-a033-48e6-8390-206f6b1f56c0");
        int actualBalanceSecondCard =balanceCard.getCardBalance("0f3f5c2a-249e-4c3d-8287-09f7a039391d");

        Assertions.assertEquals((startBalanceFirstCard + sum), actualBalanceFirstCard);
        Assertions.assertEquals((startBalanceSecondCard - sum), actualBalanceSecondCard);
    }

    @Test
    @DisplayName("Should transfer money to second card from first card")
    void shouldTransferMoneyToSecondCardFromFirst() {

        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);

        DashboardPage balanceCard = new DashboardPage();
        int startBalanceFirstCard = balanceCard.getCardBalance("92df3f1c-a033-48e6-8390-206f6b1f56c0");
        int startBalanceSecondCard =balanceCard.getCardBalance("0f3f5c2a-249e-4c3d-8287-09f7a039391d");

        int sum;
        if (startBalanceFirstCard > 2000 ) {
            sum = 2000;
        } else sum = startBalanceFirstCard;

        DashboardPage.transferOwnToSecondFromFirstCard(String.valueOf(sum));

        int actualBalanceFirstCard = balanceCard.getCardBalance("92df3f1c-a033-48e6-8390-206f6b1f56c0");
        int actualBalanceSecondCard =balanceCard.getCardBalance("0f3f5c2a-249e-4c3d-8287-09f7a039391d");

        Assertions.assertEquals((startBalanceFirstCard - sum), actualBalanceFirstCard);
        Assertions.assertEquals((startBalanceSecondCard + sum), actualBalanceSecondCard);
    }

    @Test
    @DisplayName("Should not transfer money to first card if on second card insufficient sum")
    void shouldNotTransferMoneyToFirstCardFromSecondInsufficientSum() {

        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);

        DashboardPage balanceCard = new DashboardPage();

        int startBalanceSecondCard =balanceCard.getCardBalance("0f3f5c2a-249e-4c3d-8287-09f7a039391d");

        int sum = 10000 + startBalanceSecondCard;
        DashboardPage.transferOwnToFirstFromSecondCard(String.valueOf(sum));
        String actualMsg = balanceCard.errorWithInsufficientSum();
        String expectedMsg = "Ошибка! Недостаточно средств на карте!";
        Assertions.assertEquals(expectedMsg, actualMsg);

    }

}
