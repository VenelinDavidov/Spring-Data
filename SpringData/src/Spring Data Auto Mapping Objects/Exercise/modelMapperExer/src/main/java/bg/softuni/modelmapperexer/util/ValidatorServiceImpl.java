package bg.softuni.modelmapperexer.util;

import jakarta.validation.*;
import jakarta.validation.ConstraintViolation;
import org.springframework.stereotype.Service;

import java.util.Set;



@Service
public class ValidatorServiceImpl implements ValidatorService {

    private final Validator validator = Validation.buildDefaultValidatorFactory ().getValidator ();


    @Override
    public <E> boolean isValid(E entity) {
        return this.validator.validate (entity).isEmpty ();
    }

    @Override
    public <E> Set <ConstraintViolation <E>> validate(E entity) {
        return this.validator.validate (entity);
    }
}
