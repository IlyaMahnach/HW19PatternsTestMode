
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
        Registration user = Generator.generateNewActiveValidUser();

        SelenideElement form = $("[action]");
        form.$(cssSelector("[name=login]")).sendKeys(user.getLogin());
        form.$(cssSelector("[name=password]")).sendKeys(user.getPassword());
        form.$(cssSelector("[type=button]")).click();
        $(byText("Личный кабинет")).shouldBe(Condition.visible, Duration.ofSeconds(15000));
    }

    @Test
    void shouldNotLogInIfBlockedUser() {
        Registration user = Generator.generateNewBlockedUser();

        SelenideElement form = $("[action]");
        form.$(cssSelector("[name=login]")).sendKeys(user.getLogin());
        form.$(cssSelector("[name=password]")).sendKeys(user.getPassword());
        form.$(cssSelector("[type=button]")).click();
        $(byText("Пользователь заблокирован")).shouldBe(Condition.visible, Duration.ofSeconds(15000));
    }

    @Test
    void shouldNotLogInIfInvalidLogin() {
        Registration user = Generator.generateNewActiveUserInvalidLogin();
        SelenideElement form = $("[action]");
        form.$(cssSelector("[name=login]")).sendKeys(user.getLogin());
        form.$(cssSelector("[name=password]")).sendKeys(user.getPassword());
        form.$(cssSelector("[type=button]")).click();
        $(byText("Неверно указан логин или пароль")).shouldBe(Condition.visible, Duration.ofSeconds(15000));
    }

    @Test
    void shouldNotLogInIfInvalidPassword() {
        Registration user = Generator.generateNewActiveInvalidPassword();
        SelenideElement form = $("[action]");
        form.$(cssSelector("[name=login]")).sendKeys(user.getLogin());
        form.$(cssSelector("[name=password]")).sendKeys(user.getPassword());
        form.$(cssSelector("[type=button]")).click();
        $(byText("Неверно указан логин или пароль")).shouldBe(Condition.visible, Duration.ofSeconds(15000));
    }
}