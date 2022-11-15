package ir.jimsa.datagame.ui.controller;

import ir.jimsa.datagame.service.DataService;
import ir.jimsa.datagame.shared.Utils;
import ir.jimsa.datagame.ui.model.request.RequestOperationStatus;
import ir.jimsa.datagame.ui.model.response.SaveResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/data")
public class DataController {

    final Utils utils;
    final DataService dataService;

    @Autowired
    public DataController(Utils utils, DataService dataService) {
        this.utils = utils;
        this.dataService = dataService;
    }

    @PostMapping("/upload")
    public SaveResponseModel uploadCsvFile(@RequestParam("file")MultipartFile file) throws Exception {

        if (!utils.hasCsvFormat(file)) {
            throw new FileNotFoundException(file.getName());
        }

        int savedCount = dataService.saveFile(file);

        SaveResponseModel returnValue = new SaveResponseModel();
        returnValue.setCount(savedCount);
        returnValue.setMessage(RequestOperationStatus.SUCCESS.name());

        return returnValue;
    }
}
