package ir.jimsa.datagame.shared;

import ir.jimsa.datagame.exception.CarServiceException;
import ir.jimsa.datagame.shared.dto.CarDto;
import ir.jimsa.datagame.ui.model.response.ErrorMessages;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class Utils {

    private final Random RANDOM = new SecureRandom();
    private final String ALPHABET = "0123456789AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";

    public boolean hasCsvFormat(MultipartFile file) {
        try {
            return file.getContentType().equals("text/csv");
        } catch (Exception e) {
            throw new CarServiceException(ErrorMessages.FILE_BAD_FORMAT.getErrorMessage());
        }
    }

    public List<CarDto> csvToCarDto(MultipartFile file) throws Exception {
        try {
            InputStream inputStream = file.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

            List<CarDto> cars = csvParser.getRecords().stream()
                    .map(item -> {
                        CarDto carDto = new CarDto();
                        carDto.setSource(item.get("source"));
                        carDto.setCodeListCode(item.get("codeListCode"));
                        carDto.setCode(item.get("code"));
                        carDto.setDisplayValue(item.get("displayValue"));
                        carDto.setLongDescription(item.get("longDescription"));
                        try {
                            carDto.setFromDate(convertData(item.get("fromDate")));
                        } catch (ParseException e) {
                            carDto.setFromDate(null);
                        }
                        try {
                            carDto.setToDate(convertData(item.get("toDate")));
                        } catch (ParseException e) {
                            carDto.setToDate(null);
                        }
                        carDto.setSortingPriority(item.get("sortingPriority"));
                        return carDto;
                    }).toList();

            return cars;
        } catch (Exception e) {
            throw new CarServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage());
        }
    }

    public Date convertData(String stringFormat) throws ParseException {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");
            return simpleDateFormat.parse(stringFormat);
        } catch (ParseException e) {
            throw new ParseException(e.getMessage(), e.getErrorOffset());
        }
    }

    public String convertData(Date date) {
        if (date != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");
            return simpleDateFormat.format(date);
        } else {
            return "";
        }
    }

    public String generateCarId(int length) {
        return generateRandomString(length);
    }

    private String generateRandomString(int length) {
        StringBuilder returnValue = new StringBuilder();
        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return returnValue.toString();
    }

}
