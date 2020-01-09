package Validator;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;



@FacesValidator("phoneValidator")
public class PhoneValidator implements Validator {

    private String pattern;

    @Override
    public void validate(FacesContext context, UIComponent component,
                         Object value) throws ValidatorException {
        PhonePattern phonePattern = PhonePattern.getPhonePattern(pattern);
        if (!phonePattern.match(value.toString())) {
            FacesMessage msg =
                    new FacesMessage("Invalid phone format",
                            String.format("invalid input: %s, The valid format regex: %s",
                                    value, phonePattern.getPattern()));
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}