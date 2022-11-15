package ir.jimsa.datagame.ui.controller;

import ir.jimsa.datagame.service.CarService;
import ir.jimsa.datagame.shared.Utils;
import ir.jimsa.datagame.shared.dto.CarDto;
import ir.jimsa.datagame.ui.model.request.RequestOperationStatus;
import ir.jimsa.datagame.ui.model.response.Car;
import ir.jimsa.datagame.ui.model.response.SaveResponseModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {

    final Utils utils;
    final CarService carService;

    @Autowired
    public CarController(Utils utils, CarService carService) {
        this.utils = utils;
        this.carService = carService;
    }

    @PostMapping("/upload")
    public SaveResponseModel uploadCsvFile(@RequestParam("file")MultipartFile file) throws Exception {

        if (!utils.hasCsvFormat(file)) {
            throw new FileNotFoundException(file.getName());
        }

        int savedCount = carService.saveFile(file);

        SaveResponseModel returnValue = new SaveResponseModel();
        returnValue.setCount(savedCount);
        returnValue.setMessage(RequestOperationStatus.SUCCESS.name());

        return returnValue;
    }

    @GetMapping
    public List<Car> getAllCars() {
        List<CarDto> carDtos = carService.getAllCars();

        ModelMapper modelMapper = new ModelMapper();
        return carDtos.stream()
                .map(carDto ->modelMapper.map(carDto, Car.class))
                .toList();
    }

    @GetMapping(path = "/{carId}")
    public Car getCar(@PathVariable String carId) {
        Car returnValue = new Car();
        CarDto carDto = carService.getCarByCarId(carId);
        BeanUtils.copyProperties(carDto, returnValue);
        return returnValue;
    }
}
