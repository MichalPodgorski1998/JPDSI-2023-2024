package org.example.library.dto;
import jakarta.validation.GroupSequence;
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
@GroupSequence({AdminDto.NonBlankGroup.class, AdminDto.SizeGroup.class, AdminDto.PatternGroup.class, AdminDto.class})
public class AdminDto {

    public interface NonBlankGroup {}
    public interface SizeGroup {}
    public interface PatternGroup {}

    @Size(min = 2, max = 30, message = "Imię powinno zawierać od 2 do 30 znaków", groups = SizeGroup.class)
    @Pattern(regexp = "^[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ]+$", message = "Imię powinno zawierać tylko litery", groups = PatternGroup.class)
    @NotBlank(message = "Imię jest wymagane", groups = NonBlankGroup.class)
    private String firstName;

    @NotBlank(message = "Nazwisko jest wymagane", groups = NonBlankGroup.class)
    @Size(min = 2, max = 30, message = "Nazwisko powinno zawierać od 2 do 30 znaków", groups = SizeGroup.class)
    @Pattern(regexp = "^[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ]+$", message = "Nazwisko powinno zawierać tylko litery", groups = PatternGroup.class)
    private String lastName;

    @NotBlank(message = "Adres e-mail jest wymagany", groups = NonBlankGroup.class)
    @Email(message = "Nieprawidłowy format adresu e-mail")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Nieprawidłowy format adresu e-mail", groups = PatternGroup.class)
    private String username;

    @NotBlank(message = "Hasło jest wymagane", groups = NonBlankGroup.class)
    @Size(min = 8, max = 20, message = "Hasło powinno zawierać od 8 do 20 znaków", groups = SizeGroup.class)
    private String password;

    @NotBlank(message = "Powtórzenie hasła jest wymagane", groups = NonBlankGroup.class)
    private String repeatPassword;
}

