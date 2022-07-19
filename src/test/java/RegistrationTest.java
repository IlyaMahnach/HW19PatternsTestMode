
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.openqa.selenium.By.cssSelector;

public class RegistrationTest {
    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldLogInIfUserIsValid() {
        var validUser = Registration.getRegisteredUser("active");

        SelenideElement form = $("[action]");
        form.$(cssSelector("[name=login]")).sendKeys(validUser.getLogin());
        form.$(cssSelector("[name=password]")).sendKeys(validUser.getPassword());
        form.$(cssSelector("[type=button]")).click();
        $(byText("Личный кабинет")).shouldBe(Condition.visible, Duration.ofSeconds(15000));
    }

    @Test
    void shouldNotLogInIfBlockedUser() {
        var validUser = Registration.getRegisteredUser("blocked");

        SelenideElement form = $("[action]");
        form.$(cssSelector("[name=login]")).sendKeys(validUser.getLogin());
        form.$(cssSelector("[name=password]")).sendKeys(validUser.getPassword());
        form.$(cssSelector("[type=button]")).click();
        $(byText("Пользователь заблокирован")).shouldBe(Condition.visible, Duration.ofSeconds(15000));
    }

    @Test
    void shouldNotLogInIfInvalidLogin() {
        var validUser = Registration.getRegisteredUser("active");

        SelenideElement form = $("[action]");
        form.$(cssSelector("[name=login]")).sendKeys("invalid.login");
        form.$(cssSelector("[name=password]")).sendKeys(validUser.getPassword());
        form.$(cssSelector("[type=button]")).click();
        $(byText("Неверно указан логин или пароль")).shouldBe(Condition.visible, Duration.ofSeconds(15000));
    }

    @Test
    void shouldNotLogInIfInvalidPassword() {
        var validUser = Registration.getRegisteredUser("active");

        SelenideElement form = $("[action]");
        form.$(cssSelector("[name=login]")).sendKeys(validUser.getLogin());
        form.$(cssSelector("[name=password]")).sendKeys("invalid.password");
        form.$(cssSelector("[type=button]")).click();
        $(byText("Неверно указан логин или пароль")).shouldBe(Condition.visible, Duration.ofSeconds(15000));
    }
}