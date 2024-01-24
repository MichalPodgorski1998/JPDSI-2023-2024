package org.example.library.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDto {

    @NotBlank(message = "Imię jest wymagane")
    @Size(min = 2, max = 30, message = "Imię powinno zawierać od 2 do 30 znaków")
    @Pattern(regexp = "^[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ]+$", message = "Imię powinno zawierać tylko litery")
    private String firstName;

    @NotBlank(message = "Nazwisko jest wymagane")
    @Size(min = 2, max = 30, message = "Nazwisko powinno zawierać od 2 do 30 znaków")
    @Pattern(regexp = "^[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ]+$", message = "Nazwisko powinno zawierać tylko litery")
    private String lastName;

    @NotBlank(message = "Adres e-mail jest wymagany")
    @Email(message = "Nieprawidłowy format adresu e-mail")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Nieprawidłowy format adresu e-mail")
    private String username;

    @NotBlank(message = "Hasło jest wymagane")
    @Size(min = 8, max = 20, message = "Hasło powinno zawierać od 8 do 20 znaków")
    private String password;

    @NotBlank(message = "Powtórzenie hasła jest wymagane")
    private String repeatPassword;
}
