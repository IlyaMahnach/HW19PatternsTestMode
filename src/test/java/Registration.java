import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor

public class Registration {
    private String login;
    private String password;
    private String status;

    private Registration() {
    }

    public static Registration getUser(String status) {
        return new Registration(Generator.getRandomLogin(), Generator.getRandomPassword(), status);
    }

    public static Registration getRegisteredUser(String status) {
        var registeredUser = getUser(status);
        Generator.makeRegistration(registeredUser);
        return registeredUser;
    }
}