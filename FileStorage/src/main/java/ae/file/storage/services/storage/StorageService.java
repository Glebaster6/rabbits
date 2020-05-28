package ae.file.storage.services.storage;


import main.dto.MainDto;

public interface StorageService {
    void saveFile(MainDto mainDto);
    void getModelFile(String user);
}
