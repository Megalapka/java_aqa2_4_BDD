package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class TransferPage {

    private SelenideElement heading = $("[data-test-id=dashboard]").shouldHave(text("Пополнение карты"));

    public TransferPage() {
        heading.shouldBe(visible);
    }


    public static void topUpCard(String sum, String cardNumber) {
        $("[data-test-id='amount'] .input__control").sendKeys(sum);
        $("[data-test-id='from'] .input__control").sendKeys(cardNumber);
        $("[data-test-id='action-transfer'").click();
        $("[data-test-id='dashboard'").should(visible);
    }
}
