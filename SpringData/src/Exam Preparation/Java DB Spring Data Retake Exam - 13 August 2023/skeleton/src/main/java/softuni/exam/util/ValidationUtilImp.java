package softuni.exam.util;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class ValidationUtilImp implements ValidationUtil {


    private final Validator validator;


    public ValidationUtilImp(Validator validator) {
        this.validator = validator;
    }


    @Override
    public <E> boolean isValid(E entity) {
        Set<ConstraintViolation<E>> validate = validator.validate(entity);
        return validate.isEmpty();
    }
}
