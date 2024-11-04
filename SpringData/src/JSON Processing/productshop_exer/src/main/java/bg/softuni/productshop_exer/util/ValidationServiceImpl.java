package bg.softuni.productshop_exer.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ValidationServiceImpl implements ValidationService {

    // Validator
    private final Validator validator;

    // constructor
    public ValidationServiceImpl(Validator validator) {
        this.validator = validator;
    }


    // implements methods
    @Override
    public <E> boolean isValid(E entity) {
        return this.getViolations(entity).isEmpty();
    }

    @Override
    public <E> Set<ConstraintViolation<E>> getViolations(E entity) {
        return this.validator.validate(entity);
    }
}
