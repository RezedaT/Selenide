package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    //задание 2
    @Test
    void shouldSendSuccessfulRequestWithCitySearch() {
        open("http://localhost:9999");
        String dueDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='city'] input").setValue("йо");
        $$(".menu-item").findBy(Condition.text("Йошкар-Ола")).click();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(dueDate);
        $("[data-test-id='name'] input").setValue("Иванова Мария");
        $("[data-test-id='phone'] input").setValue("+79876543211");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id='notification']")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Успешно! Встреча успешно забронирована на " + dueDate));
    }
    private String planningDate(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }
    @Test
    void shouldSendSuccessfulRequestWithCalender() {
        open("http://localhost:9999");
        String dueDate = planningDate(10, "dd.MM.yyyy");
        $("[data-test-id='city'] input").setValue("Йошкар-Ола");
        $("button").click();
        if (!planningDate(3, "MM").equals(planningDate(7, "MM"))) {
            // перелестнуть календарь
            $("calendar__arrow calendar__arrow_direction_right").click();
        }
//        $$("calendar__day").findBy(Condition.text(planningDate(7, "d"))).click();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(dueDate);
        $("[data-test-id='name'] input").setValue("Иванова Мария");
        $("[data-test-id='phone'] input").setValue("+79876543211");

        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id='notification']")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Успешно! Встреча успешно забронирована на " + dueDate));

    }
}
