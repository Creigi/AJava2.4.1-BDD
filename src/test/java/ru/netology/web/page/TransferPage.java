package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private SelenideElement actionTransfer = $(".button[data-test-id=action-transfer]");
    private SelenideElement actionCancel = $(".button[data-test-id=action-cancel]");
    private SelenideElement inputAmount = $(".input[data-test-id=amount] .input__control");
    private SelenideElement inputFrom = $(".input[data-test-id=from] .input__control");
    private SelenideElement inputTo = $(".input[data-test-id=to] .input__control");
    private SelenideElement errorMessage = $("[data-test-id='error-notification']");
    public TransferPage() {
        heading.shouldBe(visible);
    }

    public DashboardPage makeValidTransfer(int amount, DataHelper.CardInfo cardInfo) {
        makeTransfer(amount, cardInfo);
        return new DashboardPage();
    }

    public void makeTransfer(int amount, DataHelper.CardInfo cardInfo) {
        inputAmount.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        inputAmount.setValue(String.valueOf(amount));
        inputFrom.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        inputFrom.setValue(cardInfo.getCardNumber());
        actionTransfer.click();
    }

    public void findErrorMessage(String expectedText) {
        errorMessage.shouldBe(visible).shouldHave(exactText(expectedText), Duration.ofSeconds(15));
    }
}
