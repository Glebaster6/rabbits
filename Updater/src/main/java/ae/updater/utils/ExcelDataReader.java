package ae.updater.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import main.dto.*;
import org.apache.poi.ss.usermodel.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ExcelDataReader {

    private final Map<String, Integer> nameIndexMap;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public ExcelDataReader(){
        nameIndexMap = new HashMap<>();
    }

    @SneakyThrows
    public void readData(File file, String evaluationData){
        Workbook workbook = WorkbookFactory.create(file);
        int sheetsNum = workbook.getNumberOfSheets();

        EvaluationDto evaluationDto = (EvaluationDto) MainUtil.stringJsonToObject(evaluationData, EvaluationDto.class);


        MainDto mainDto = MainDto.builder()
                .action(MainDto.Action.SAVE_EVALUATION)
                .json(MainUtil.objectToJsonString(evaluationDto))
                .build();
        rabbitTemplate.convertAndSend("updaterToDataStorageQueue", MainUtil.objectToByteArray(mainDto));

        for (int i = 0; i < sheetsNum; i++){
            Sheet sheet = workbook.getSheetAt(i);
            readCGFromSheet(sheet, i + 1, evaluationDto.getHash());
        }

        file.delete();
    }

    /**
     * Создание товарных групп из листа
     *
     * @param sheet
     * @param period
     */
    @SneakyThrows
    private void readCGFromSheet(Sheet sheet, int period, String hash) {
        int firstRowNum = sheet.getFirstRowNum();
        int lastRowNum = sheet.getLastRowNum();

        parseHeaders(sheet.getRow(firstRowNum));

        List<EvaluationDataRowDto> rowData = new ArrayList<>();

        for (int i = firstRowNum + 1; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);
            if (checkIfRowCorrect(row)) {
                rowData.add(createCommodityGroup(row, period));
                if (rowData.size() >= 200){
                   sendRowData(rowData,hash, false);
                    rowData = new ArrayList<>();
                }
            }
        }

            sendRowData(rowData,hash, true);
    }

    /**
     * Заполняет nameIndexMap
     *
     * @param row
     */
    private void parseHeaders(Row row) {
        for (int c = 0; c <= row.getLastCellNum(); c++) {
            Cell headerCell = row.getCell(c);
            if (headerCell != null) {
                switch (headerCell.getStringCellValue().toLowerCase().replaceAll("\n", " ")
                        .replaceAll("  ", "").trim()) {
                    case "товарная группа": {
                        nameIndexMap.put("name", c);
                        break;
                    }
                    case "объем продаж ед.": {
                        nameIndexMap.put("volume_of_sales", c);
                        break;
                    }
                    case "выручка": {
                        nameIndexMap.put("revenue", c);
                        break;
                    }
                    case "расход": {
                        nameIndexMap.put("consumption", c);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Прверяет ряд на корректность данных в нём
     *
     * @param row
     * @return bool
     */
    private boolean checkIfRowCorrect(Row row) {
        for (Map.Entry<String, Integer> entry : nameIndexMap.entrySet()) {
            Cell cell = row.getCell(entry.getValue());
            if (cell != null) {
                switch (entry.getKey()) {
                    case "name": {
                        if (!cell.getCellType().equals(CellType.STRING)) {
                            return false;
                        }
                        break;
                    }
                    case "volume_of_sales":
                    case "revenue":
                    case "consumption": {
                        if (!(cell.getCellType().equals(CellType.NUMERIC)
                                || cell.getCellType().equals(CellType.BLANK)
                                || cell.getCellType().equals(CellType.FORMULA))) {
                            return false;
                        }
                        break;
                    }
                }
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * Создание товарной группы в базе
     *
     * @param row
     * @param period
     */
    private EvaluationDataRowDto createCommodityGroup(Row row, int period) {
        return EvaluationDataRowDto.builder()
                .period(period)
                .name(getStringValue(row, "name"))
                .volumeOfSales(getDoubleValue(row, "volume_of_sales").intValue())
                .revenue(getDoubleValue(row, "revenue"))
                .consumption(getDoubleValue(row, "consumption"))
                .build();
    }

    /**
     * Получает строковое значение по из ряда по ключу
     *
     * @param row
     * @param key
     * @return
     */
    private String getStringValue(Row row, String key) {
        return row.getCell(nameIndexMap.get(key)).getStringCellValue();
    }

    /**
     * Получаеть вещественно числовое значение из ряда
     *
     * @param row
     * @param key
     * @return
     */
    private Double getDoubleValue(Row row, String key) {
        return row.getCell(nameIndexMap.get(key)).getNumericCellValue();
    }

    /**
     *
     * @param rowData
     * @param hash
     */
    private void sendRowData(List<EvaluationDataRowDto> rowData, String hash, Boolean isLast){
        EvaluationDataDto evaluationDataDto = EvaluationDataDto.builder()
                .evaluationDataRows(rowData)
                .isLast(isLast)
                .hash(hash)
                .isLast(false)
                .build();

        MainDto mainDto = MainDto.builder()
                .json(MainUtil.objectToJsonString(evaluationDataDto))
                .action(MainDto.Action.SAVE_EVALUATION_DATA)
                .build();

        rabbitTemplate.convertAndSend("updaterToDataStorageQueue", MainUtil.objectToByteArray(mainDto));
    }
}
