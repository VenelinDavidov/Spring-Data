package com.example.football.models.dto.gsons;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class TownImportDto implements Serializable {

    @Expose
    @Size(min = 2)
    private String name;

    @Expose
    @Positive
    private int population;

    @Expose
    @Size(min = 10)
    @NotNull
    private String travelGuide;
}
