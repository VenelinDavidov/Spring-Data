package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.gson.SaleSeedDto;
import softuni.exam.models.entity.Sale;
import softuni.exam.models.entity.Seller;
import softuni.exam.repository.SaleRepository;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.SaleService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class SaleServiceImpl implements SaleService {

    private static final String PATH_FILE = "src/main/resources/files/json/sales.json";

    private final SaleRepository saleRepository;
    private final SellerRepository sellerRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    public SaleServiceImpl(SaleRepository saleRepository, SellerRepository sellerRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.saleRepository = saleRepository;
        this.sellerRepository = sellerRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.saleRepository.count() > 0;
    }

    @Override
    public String readSalesFileContent() throws IOException {
        return Files.readString(Path.of(PATH_FILE));
    }

    @Override
    public String importSales() throws IOException {

        StringBuilder sb = new StringBuilder();

        SaleSeedDto[] saleSeedDtos =
                this.gson.fromJson(readSalesFileContent(), SaleSeedDto[].class);

        for (SaleSeedDto saleSeedDto : saleSeedDtos) {

            Optional<Sale> saleOptional =
                    this.saleRepository.findByNumber((saleSeedDto.getNumber()));

            if (!this.validationUtil.isValid(saleSeedDto) || saleOptional.isPresent()) {
                 sb.append("Invalid sale").append(System.lineSeparator());
                 continue;
            }

            Sale sale = this.modelMapper.map(saleSeedDto, Sale.class);
            Seller seller = this.sellerRepository.findById(saleSeedDto.getSeller()).get();

            if (this.validationUtil.isValid(seller)) {
                sale.setSeller(seller);
            }

            this.saleRepository.saveAndFlush(sale);

            sb.append(String.format("Successfully imported sale with number %s",
                    sale.getNumber()))
                    .append(System.lineSeparator());
        }


        return sb.toString().trim();
    }
}
