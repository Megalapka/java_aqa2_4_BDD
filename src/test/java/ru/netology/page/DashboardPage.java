package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import org.checkerframework.checker.units.qual.C;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() {
        heading.shouldBe(visible);
    }


    public  String getCardNumber() {
        String cardNumber = "454365";
        return cardNumber;
    }


    public int getCardBalance(DataHelper.CardData cardData) {
        String text = cards.findBy(Condition.text(cardData.getCardNumber().substring(12,16))).getText();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }


    public static void transferOwnToSecondFromFirstCard(String sum) {
        $$("[data-test-id='action-deposit'").get(1).click();
        $("[data-test-id='amount'] .input__control").sendKeys(sum);
        $("[data-test-id='from'] .input__control").sendKeys("5559 0000 0000 0001");
        $("[data-test-id='action-transfer'").click();
        $("[data-test-id='dashboard'").should(visible);
    }

    public String errorWithInsufficientSum() {
        return $("[data-test-id='error-notification'] .notification__content").getText();
    }

    public TransferPage getTransferPage() {
        $$("[data-test-id='action-deposit'").get(0).click();
        return new TransferPage();
    }

}


