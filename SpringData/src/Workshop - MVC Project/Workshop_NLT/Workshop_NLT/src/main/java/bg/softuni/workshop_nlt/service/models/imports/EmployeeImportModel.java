package bg.softuni.workshop_nlt.service.models.imports;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class EmployeeImportModel {

    @JacksonXmlProperty(localName = "first-name")
    private String firstName;

    @JacksonXmlProperty(localName = "last-name")
    private String lastName;

    @JacksonXmlProperty
    private int age;

    @JacksonXmlProperty
    private ProjectImportModel project;

}
