package ir.jimsa.datagame.service.impl;

import ir.jimsa.datagame.io.CarRepository;
import ir.jimsa.datagame.io.entity.CarEntity;
import ir.jimsa.datagame.service.CarService;
import ir.jimsa.datagame.shared.Utils;
import ir.jimsa.datagame.shared.dto.CarDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {


    final Utils utils;
    final CarRepository carRepository;

    @Autowired
    public CarServiceImpl(Utils utils, CarRepository carRepository) {
        this.utils = utils;
        this.carRepository = carRepository;
    }

    @Override
    public int saveFile(MultipartFile file) {

        ModelMapper modelMapper = new ModelMapper();

        try {
            List<CarEntity> carEntities = utils.csvToCarDto(file)
                    .stream()
                    .map(carDto -> {
                        CarEntity carEntity = modelMapper.map(carDto, CarEntity.class);
                        carEntity.setCarId(utils.generateCarId(16));
                        return carEntity;
                    })
                    .collect(Collectors.toList());

            List<CarEntity> savedCars = carRepository.saveAll(carEntities);
            return savedCars.size();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public List<CarDto> getAllCars() {
        List<CarEntity> carEntities = carRepository.findAll();
        ModelMapper modelMapper = new ModelMapper();
        return carEntities
                .stream()
                .map(carEntity -> modelMapper.map(carEntity, CarDto.class))
                .toList();
    }

    @Override
    public CarDto getCarByCarId(String carId) {
        CarDto returnValue = new CarDto();

        CarEntity carEntity = carRepository.findCarEntityByCarId(carId);
        if (carEntity == null) {
            throw new RuntimeException();
        }
        BeanUtils.copyProperties(carEntity, returnValue);
        return returnValue;
    }

    @Override
    public void deleteAllUsers() {
        List<CarEntity> carEntities = carRepository.findAll();
        if (carEntities.size() != 0) {
            carRepository.deleteAll();
        }
    }

}
