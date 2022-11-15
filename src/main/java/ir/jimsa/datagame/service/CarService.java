package ir.jimsa.datagame.service;

import ir.jimsa.datagame.shared.dto.CarDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CarService {
    int saveFile(MultipartFile file);

    List<CarDto> getAllCars();

    CarDto getCarByCarId(String carId);

    void deleteAllUsers();
}
