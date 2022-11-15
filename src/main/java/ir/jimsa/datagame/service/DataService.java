package ir.jimsa.datagame.service;

import org.springframework.web.multipart.MultipartFile;

public interface DataService {
    int saveFile(MultipartFile file);
}
