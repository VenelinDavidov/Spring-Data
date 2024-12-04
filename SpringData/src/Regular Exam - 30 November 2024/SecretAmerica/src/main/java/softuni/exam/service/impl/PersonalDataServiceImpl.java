package softuni.exam.service.impl;

import com.google.gson.Gson;
import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xml.PersonalDateSeedDto;
import softuni.exam.models.dto.xml.PersonalDateSeedRoodDto;
import softuni.exam.models.entity.PersonalData;
import softuni.exam.repository.PersonalDataRepository;
import softuni.exam.service.PersonalDataService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;


@Service
public class PersonalDataServiceImpl implements PersonalDataService {

    private static final String FILE_PATH = "src/main/resources/files/xml/personal_data.xml";

    private final PersonalDataRepository personalDataRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;

    public PersonalDataServiceImpl(PersonalDataRepository personalDataRepository, Gson gson, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtil validationUtil) {
        this.personalDataRepository = personalDataRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.personalDataRepository.count() > 0;
    }

    @Override
    public String readPersonalDataFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importPersonalData() throws JAXBException, IOException {

        StringBuilder stringBuilder = new StringBuilder();


        PersonalDateSeedRoodDto personalDateSeedRoodDto = this.xmlParser.fromFile(FILE_PATH, PersonalDateSeedRoodDto.class);

        for (PersonalDateSeedDto personalDataSeedDTO : personalDateSeedRoodDto.getPersonalDateSeedDtoList()) {
            Optional<PersonalData> personalDataOptional =
                    this.personalDataRepository.findByCardNumber(personalDataSeedDTO.getCardNumber());

            if (!this.validationUtil.isValid(personalDataSeedDTO) || personalDataOptional.isPresent()) {
                stringBuilder.append("Invalid personal data").append(System.lineSeparator());
                continue;
            }
            PersonalData personalData = this.modelMapper.map(personalDataSeedDTO, PersonalData.class);

            this.personalDataRepository.saveAndFlush(personalData);

            stringBuilder.append(String.format("Successfully imported personal data for visitor with card number %s",
                    personalData.getCardNumber())).append(System.lineSeparator());
        }


        return stringBuilder.toString().trim();
    }
}
