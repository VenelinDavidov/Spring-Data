package bg.softuni.workshop_nlt.service.models.imports;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ProjectImportModel {

    @JacksonXmlProperty
    private String name;

    @JacksonXmlProperty
    private String description;

    @JacksonXmlProperty(localName = "start-date")
    private String startDate;

    @JacksonXmlProperty(localName = "is-finished")
    private boolean isFinished;

    @JacksonXmlProperty
    private BigDecimal payment;

    @JacksonXmlProperty(localName = "company")
    private CompanyImportModel companyName;



    public ProjectImportModel(String name,
                              String description,
                              String startDate,
                              boolean isFinished,
                              BigDecimal payment,
                              CompanyImportModel companyName) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.isFinished = isFinished;
        this.payment = payment;
        this.companyName = companyName;
    }
}
