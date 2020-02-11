package Validator;



import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@FacesValidator("phoneValidator")
public class PhoneValidator implements Validator {
    private static final Pattern PHONE_PATTERN = Pattern.compile("(\\d{2})(\\d{2})(\\d{3})");

    @Override
    public void validate(FacesContext context, UIComponent component,
                         Object value) throws ValidatorException {
        Matcher matcher = PHONE_PATTERN.matcher(value.toString());
        if (!matcher.matches()) {
            FacesMessage msg =
                    new FacesMessage("Invalid phone format",
                            String.format("Invalid phone number: %s, Example : 1212123",
                                    value));
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}