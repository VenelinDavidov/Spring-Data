package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.json.LibraryMemberSeedRoodDTO;
import softuni.exam.models.entity.LibraryMember;
import softuni.exam.repository.LibraryMemberRepository;
import softuni.exam.service.LibraryMemberService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class LibraryMemberServiceImpl implements LibraryMemberService {

    private static final String FILE_PATH = "src/main/resources/files/json/library-members.json";

    private final LibraryMemberRepository libraryMemberRepository;
    private final Gson gson;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public LibraryMemberServiceImpl(LibraryMemberRepository libraryMemberRepository, Gson gson, XmlParser xmlParser, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.libraryMemberRepository = libraryMemberRepository;
        this.gson = gson;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.libraryMemberRepository.count() > 0;
    }

    @Override
    public String readLibraryMembersFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importLibraryMembers() throws IOException {

        StringBuilder sb = new StringBuilder();

        LibraryMemberSeedRoodDTO[] libraryMemberSeedRoodDTOS = this.gson.fromJson(readLibraryMembersFileContent(), LibraryMemberSeedRoodDTO[].class);

        for (LibraryMemberSeedRoodDTO libraryMemberSeedRoodDTO : libraryMemberSeedRoodDTOS) {

            Optional<LibraryMember> optionalLibraryMember =
                    this.libraryMemberRepository
                            .findLibraryMemberByPhoneNumber(libraryMemberSeedRoodDTO.getPhoneNumber());

            if (!this.validationUtil.isValid(libraryMemberSeedRoodDTO) || optionalLibraryMember.isPresent()) {
                sb.append("Invalid library member").append(System.lineSeparator());
                continue;
            }

            LibraryMember libraryMember = this.modelMapper.map(libraryMemberSeedRoodDTO, LibraryMember.class);
            this.libraryMemberRepository.saveAndFlush(libraryMember);


            sb.append(String.format("Successfully imported library member %s - %s",
                    libraryMember.getFirstName(),
                    libraryMember.getLastName()))
                    .append(System.lineSeparator());

        }


        return sb.toString().trim();
    }
}
