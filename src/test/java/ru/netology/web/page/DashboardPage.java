package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.Value;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
  private SelenideElement heading = $("[data-test-id=dashboard]");
  private ElementsCollection depositButton = $$("[data-test-id=action-deposit]");
  private SelenideElement depositButtonSecondCard = $$("[data-test-id=action-deposit]").get(1);
  private SelenideElement reloadButton = $("[data-test-id=action-reload]");
  //private SelenideElement closerNotificationButton = $("[data-test-id=action-deposit]");
  private ElementsCollection cardsText = $$(".list__item");
  private SelenideElement actionTransfer = $(".button[data-test-id=action-transfer]");
  private SelenideElement actionCancel = $(".button[data-test-id=action-cancel]");
  private SelenideElement inputAmount = $(".input[data-test-id=amount] .input__control");
  private SelenideElement inputFrom = $(".input[data-test-id=from] .input__control");
  private SelenideElement inputTo = $(".input[data-test-id=to] .input__control");

  @Value
  public static class CardInfo{
    String lastFourNumber;
    int sum;
  }
  List<CardInfo> cards = new ArrayList<>();
  public DashboardPage() {
    heading.shouldBe(visible);
  }

  public void updateInfoCard() {
    cards.clear();
    for (int i = 0; i < cardsText.size(); i++) {
      CardInfo card = new CardInfo(cardsText.get(i).text().split(" ")[3].
              substring(0, cardsText.get(i).text().split(" ")[3].length() - 1),
              Integer.parseInt(cardsText.get(i).text().split(" ")[5]));
      cards.add(card);
    }
  }

  public void depositCard(int amount, int cardToDeposit, int cardFromDeposit) {
    depositButton.get(cardToDeposit - 1).click();
    inputAmount.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
    inputAmount.setValue(String.valueOf(amount));
    inputFrom.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
    inputFrom.setValue(DataHelper.cardsNumber(cardFromDeposit - 1));
    actionTransfer.click();
  }

  public int getBalance(String lastFourNumbers) {
    updateInfoCard();
    int balance = -1;
    for (CardInfo card : cards) {
      if (card.getLastFourNumber().equals(lastFourNumbers)) {
        balance = card.getSum();
      }
    }
    return balance;
  }
}
