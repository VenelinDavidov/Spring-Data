package com.example.football.models.dto.xml;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Getter
@Setter
@XmlRootElement(name = "town")
@XmlAccessorType(XmlAccessType.FIELD)
public class TownPlayerSeedDTO implements Serializable {

    @XmlElement(name = "name")
    private String name;
}