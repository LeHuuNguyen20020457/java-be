package com.example.pjtraining.apacheVelocity;

import io.jsonwebtoken.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Configuration
public class ReadExcel {

    private final static int COLUMN_INDEX_PHYSICAL_NAME = 2;
    private final static int COLUMN_INDEX_TYPE = 3;
    private final static int COLUMN_INDEX_LENGTH = 4;
    private final static int COLUMN_INDEX_ACCURACY = 5;
    private final static int COLUMN_INDEX_REQUIRED = 6;
    private final static int COLUMN_INDEX_MAIN_KEY = 7;
    private final static int COLUMN_INDEX_DEFAULT = 8;


    public ReadExcel() {
    }


    public List<PropertyBatchEntry> readFileExcel() throws IOException, java.io.IOException {
        System.out.println("vaooooo 2");
        final String excelFilePath = "C:\\Users\\ADMIN\\Documents\\pjtraining\\pjtraining\\pjtraining\\src\\main\\resources\\documents\\エンティティ定義書バッチエントリーVN.xlsx";

        return this.poiReadExcel(excelFilePath);

    }


    private List<PropertyBatchEntry> poiReadExcel(String excelFilePath) throws IOException, java.io.IOException {
        List<PropertyBatchEntry> listProperties= new ArrayList<>();

        InputStream inputStream = new FileInputStream((new File(excelFilePath)));

        Workbook workbook = getWorkbook(inputStream, excelFilePath);

        Sheet sheet = workbook.getSheetAt(0);

        Iterator<Row> iterator = sheet.iterator();

        System.out.println("vaoooooo 3");

        while (iterator.hasNext()) {
            Row row = iterator.next();
            if(row.getRowNum() >= 0 && row.getRowNum() <= 6) {
                continue;
            }

            Iterator<Cell> cellIterator = row.cellIterator();

            PropertyBatchEntry propertyBatchEntry = new PropertyBatchEntry();
            System.out.println("vaooooo 4");
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();

                Object cellValue = getCellValue(cell);

                if(cellValue == null || cellValue.toString().isEmpty()) {
                    continue;
                }

                int columnIndex = cell.getColumnIndex();

                System.out.println("vaoooo 5");
                switch (columnIndex) {
                    case COLUMN_INDEX_PHYSICAL_NAME:
                        System.out.println("COLUMN_INDEX_PHYSICAL_NAME");
                        propertyBatchEntry.setPhysicalName((String) getCellValue(cell));
                        break;
                    case COLUMN_INDEX_TYPE:
                        System.out.println("COLUMN_INDEX_TYPE");
                        propertyBatchEntry.setType((String) getCellValue(cell));
                        break;
                    case COLUMN_INDEX_LENGTH:
                        System.out.println("COLUMN_INDEX_LENGTH");
                        propertyBatchEntry.setLength((Integer) getCellValue(cell));
                        System.out.println("COLUMN_INDEX_LENGTH");
                        break;
                    case COLUMN_INDEX_ACCURACY:
                        System.out.println("COLUMN_INDEX_ACCURACY");
                        propertyBatchEntry.setAccuracy((Integer) getCellValue(cell));
                        break;
                    case COLUMN_INDEX_REQUIRED:
                        propertyBatchEntry.setRequired((String) getCellValue(cell) == "Y" ? true : false);
                        break;
                    case COLUMN_INDEX_MAIN_KEY:
                        propertyBatchEntry.setMainKey((Integer) getCellValue(cell));
                        break;
                    case COLUMN_INDEX_DEFAULT:
                        propertyBatchEntry.setDefaultValue((String) getCellValue(cell));
                    default:
                        break;
                }

            }
            System.out.println("vaooooo 6");
            System.out.println(propertyBatchEntry);
            listProperties.add(propertyBatchEntry);
        }

        workbook.close();
        inputStream.close();

        System.out.println(listProperties);
        return listProperties;
    }


    private Workbook getWorkbook(InputStream inputStream, String excelFilePath) throws IOException, java.io.IOException {
        Workbook workbook = null;

        if(excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        }
        else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else  {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }
        return workbook;
    }


    private Object getCellValue(Cell cell) {
        CellType cellType = cell.getCellTypeEnum();
        Object cellValue = null;

        switch (cellType) {
            case BOOLEAN:
                cellValue = cell.getBooleanCellValue();
                break;
            case FORMULA:
                Workbook workbook = cell.getSheet().getWorkbook();

                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

                cellValue = evaluator.evaluate(cell).getNumberValue();
                break;
            case NUMERIC:
                cellValue = cell.getNumericCellValue();
                break;
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case _NONE:
            case BLANK:
            case ERROR:
                break;
            default:
                break;
        }

        return cellValue;
    }


}
