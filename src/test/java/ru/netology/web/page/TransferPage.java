package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement fromCardField = $("[data-test-id=from] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");

    public TransferPage() {
        amountField.shouldBe(visible);
    }

    public TransferPage setAmount(int amount) {
        amountField.setValue(String.valueOf(amount));
        return this;
    }

    public TransferPage setFromCard(String cardNumber) {
        fromCardField.setValue(cardNumber);
        return this;
    }

    public DashboardPage transfer() {
        transferButton.click();
        return new DashboardPage();
    }
}