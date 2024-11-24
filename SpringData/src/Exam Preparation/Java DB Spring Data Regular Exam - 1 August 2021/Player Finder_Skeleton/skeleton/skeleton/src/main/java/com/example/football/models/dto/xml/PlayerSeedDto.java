package com.example.football.models.dto.xml;

import lombok.Getter;

import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;


@Getter
@Setter
@XmlRootElement(name = "player")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlayerSeedDto implements Serializable {


    @XmlElement(name = "first-name")
    @Size(min = 2)
    private String firstName;

    @XmlElement(name = "last-name")
    @Size(min = 2)
    private String lastName;

    @XmlElement
    @Email
    private String email;

    @XmlElement(name = "birth-date")
    private String birthDate;

    @XmlElement(name = "position")
    private String position;

    @XmlElement(name = "town")
    private TownPlayerSeedDTO town;

    @XmlElement(name = "team")
    private TeamPlayerSeedDTO team;

    @XmlElement(name = "stat")
    private StatPlayerSeedDTO stat;
}
