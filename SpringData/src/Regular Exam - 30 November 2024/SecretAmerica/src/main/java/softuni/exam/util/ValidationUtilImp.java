package softuni.exam.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Component;

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