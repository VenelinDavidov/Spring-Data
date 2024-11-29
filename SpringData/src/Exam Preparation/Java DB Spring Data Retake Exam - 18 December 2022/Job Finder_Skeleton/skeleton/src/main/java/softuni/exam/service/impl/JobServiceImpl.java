package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xml.JobsSeedDTO;
import softuni.exam.models.dto.xml.JobsSeedRoodDTO;
import softuni.exam.models.entity.Company;
import softuni.exam.models.entity.Job;
import softuni.exam.repository.CompanyRepository;
import softuni.exam.repository.JobRepository;
import softuni.exam.service.JobService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {


    private static final String FILE_PATH = "src/main/resources/files/xml/jobs.xml";

    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;

    private final XmlParser xmlParser;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public JobServiceImpl(JobRepository jobRepository, CompanyRepository companyRepository, XmlParser xmlParser, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
        this.xmlParser = xmlParser;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.jobRepository.count() > 0;
    }

    @Override
    public String readJobsFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importJobs() throws JAXBException {

        StringBuilder sb = new StringBuilder();

        JobsSeedRoodDTO jobsSeedRoodDTO = this.xmlParser.fromFile(FILE_PATH, JobsSeedRoodDTO.class);

        for (JobsSeedDTO jobSeedDTO : jobsSeedRoodDTO.getJobsSeedDTOList()) {

            if (!this.validationUtil.isValid(jobSeedDTO)) {
                sb.append("Invalid job").append(System.lineSeparator());
                continue;
            }
            Job job = this.modelMapper.map(jobSeedDTO, Job.class);
            Company company = this.companyRepository.findById(jobSeedDTO.getCompanyId()).get();
            job.getCompanies().add(company);

            this.jobRepository.saveAndFlush(job);


            sb.append(String.format("Successfully imported job %s",
                            job.getTitle()))
                    .append(System.lineSeparator());
        }

        return sb.toString().trim();
    }

    @Override
    public String getBestJobs() {
        return this
                .jobRepository
                .findJobsBySalaryIsGreaterThanEqualAndHoursAWeekLessThanEqualOrderBySalaryDesc(5000.00, 30.00)
                .stream()
                .map(job -> String.format
                                ("Job title %s\n" +
                                "-Salary: %.2f$\n" +
                                "--Hours a week: %.2fh.\n\n",
                        job.getTitle(),
                        job.getSalary(),
                        job.getHoursAWeek()))
                .collect(Collectors.joining());
    }
}
