package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;


public class TransferPage {

    private SelenideElement heading = $("[data-test-id=dashboard]").shouldHave(text("Пополнение карты"));
    private  SelenideElement amount = $("[data-test-id='amount'] .input__control");
    private  SelenideElement from = $("[data-test-id='from'] .input__control");
    private  SelenideElement messError = $("[data-test-id='error-notification'] .notification__content");
    private  SelenideElement buttonTransfer = $("[data-test-id='action-transfer'");

    public TransferPage() {
        heading.shouldBe(visible);
    }

    public  DashboardPage validTransfer(String sum, DataHelper.CardData cardData) {
        topUpCard(sum, cardData);
        return new DashboardPage();
    }

    public void topUpCard(String sum, DataHelper.CardData cardData) {
        amount.sendKeys(sum);
        from.sendKeys(cardData.getCardNumber());
        buttonTransfer.click();
    }

    public String errorWithInsufficientSum() {
        return messError.getText();
    }
}
