package bg.softuni.xml_processing_exer.util;

import jakarta.validation.ConstraintViolation;

import java.util.Set;

public interface ValidationService {



    <E> boolean isValid(E entity);

    <E> Set<ConstraintViolation<E>> getViolations(E entity);
}
