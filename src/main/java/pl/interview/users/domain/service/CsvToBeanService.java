package pl.interview.users.domain.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Component;
import pl.interview.users.domain.model.UserBean;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.List;

@Component
public class CsvToBeanService {

    private final CsvValidationService csvValidationService;

    public CsvToBeanService(CsvValidationService csvValidationService) {
        this.csvValidationService = csvValidationService;
    }

    public List<UserBean> readCsvFile(Path path) throws FileNotFoundException {
        FileReader reader = new FileReader(String.valueOf(path));
        CsvToBean<UserBean> csvToBean = new CsvToBeanBuilder<UserBean>(reader)
                .withType(UserBean.class)
                .withSeparator(';')
                .withFilter(csvValidationService::validate)
                .build();
        return csvToBean.parse();
    }
}
