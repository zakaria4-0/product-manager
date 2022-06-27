package productmanager.productmanager.model;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class StockExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Storage> storage;

    public StockExcelExporter(List<Storage> storage) {
        this.storage = storage;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Stock");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Code_Article", style);
        createCell(row, 1, "Produit", style);
        createCell(row, 2, "Quantité initiale", style);
        createCell(row, 3, "Quantité", style);
        createCell(row, 4, "Prix", style);
        createCell(row, 5, "Prix promotionnel", style);
        createCell(row, 6, "Catégorie", style);
        createCell(row, 7, "Etat", style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else if (value instanceof Float) {
            cell.setCellValue((Float) value);
        }else if (value==null) {
            cell.setCellValue("null");
        }else {
            cell.setCellValue(value.toString());
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Storage article : storage) {

                Row row = sheet.createRow(rowCount++);
                int columnCount = 0;

                createCell(row, columnCount++, article.getId(), style);
                createCell(row, columnCount++, article.getProductName(), style);
                createCell(row, columnCount++, article.getProductQuantityI(), style);
                createCell(row, columnCount++, article.getProductQuantity(), style);
                createCell(row, columnCount++, article.getProductPrice(), style);
                createCell(row, columnCount++, article.getPromotionPrice(), style);
                createCell(row, columnCount++, article.getCategory(), style);
                createCell(row, columnCount++, article.getState(), style);

        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }
}
