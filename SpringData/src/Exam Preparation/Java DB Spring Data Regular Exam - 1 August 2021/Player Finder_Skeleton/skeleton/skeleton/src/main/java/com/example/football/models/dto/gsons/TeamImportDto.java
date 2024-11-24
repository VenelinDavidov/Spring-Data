package com.example.football.models.dto.gsons;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class TeamImportDto implements Serializable {

    @Expose
    @Size(min = 3)
    private String name;

    @Expose
    @Size(min = 3)
    private String stadiumName;

    @Expose
    @Min(1000)
    private int fanBase;

    @Expose
    @Size(min = 10)
    private String history;

    @Expose
    private String townName;
}
