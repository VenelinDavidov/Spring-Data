package com.example.football.service.impl;


import com.example.football.models.dto.xml.StatSeedDto;
import com.example.football.models.dto.xml.StatsSeedRootDTO;
import com.example.football.models.entity.Stat;
import com.example.football.repository.StatRepository;
import com.example.football.service.StatService;
import com.example.football.util.ValidationUtil;
import com.example.football.util.XmlParser;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import javax.xml.bind.JAXBException;

import java.io.FileNotFoundException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class StatServiceImpl implements StatService {

    private static final String FILE_PATH = "src/main/resources/files/xml/stats.xml";

    private final StatRepository statRepository;
    private final XmlParser xmlParser;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public StatServiceImpl(StatRepository statRepository, XmlParser xmlParser, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.statRepository = statRepository;
        this.xmlParser = xmlParser;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }


    @Override
    public boolean areImported() {
        return this.statRepository.count() > 0;
    }

    @Override
    public String readStatsFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importStats() throws JAXBException, FileNotFoundException {

        StringBuilder sb = new StringBuilder();

        StatsSeedRootDTO statsSeedRootDTO = this.xmlParser.fromFile(FILE_PATH, StatsSeedRootDTO.class);

        for (StatSeedDto statSeedDto : statsSeedRootDTO.getStatSeedDtoList()) {

            Optional<Stat> optionalStat = this.statRepository.findByPassingAndShootingAndEndurance
                    (statSeedDto.getPassing(),
                            statSeedDto.getShooting(),
                            statSeedDto.getEndurance());

            if (!this.validationUtil.isValid(optionalStat) || optionalStat.isPresent()) {
                sb.append("Invalid Stat").append(System.lineSeparator());
                continue;
            }

            Stat stat = this.modelMapper.map(statSeedDto, Stat.class);
            this.statRepository.save(stat);
            sb.append(String.format("Successfully imported Stat %.2f - %.2f - %.2f",
                    stat.getPassing(),
                    stat.getShooting(),
                    stat.getEndurance()));
        }


        return sb.toString().trim();
    }
}
