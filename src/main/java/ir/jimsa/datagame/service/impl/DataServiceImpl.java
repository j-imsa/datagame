package ir.jimsa.datagame.service.impl;

import ir.jimsa.datagame.io.CarRepository;
import ir.jimsa.datagame.io.entity.CarEntity;
import ir.jimsa.datagame.service.DataService;
import ir.jimsa.datagame.shared.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataServiceImpl implements DataService {


    final Utils utils;
    final CarRepository carRepository;

    @Autowired
    public DataServiceImpl(Utils utils, CarRepository carRepository) {
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

}
