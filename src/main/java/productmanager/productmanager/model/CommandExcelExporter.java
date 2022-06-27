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
public class CommandExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Reservation> listCommands;

    public CommandExcelExporter(List<Reservation> listCommands) {
        this.listCommands = listCommands;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Commandes");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Code_Commande", style);
        createCell(row, 1, "Nom", style);
        createCell(row, 2, "Email", style);
        createCell(row, 3, "Catégorie", style);
        createCell(row, 4, "Date", style);
        createCell(row, 5, "Heure", style);
        createCell(row, 6, "Adresse", style);
        createCell(row, 7, "Région", style);
        createCell(row, 8, "Ville", style);
        createCell(row, 9, "Produit", style);
        createCell(row, 10, "Quantité", style);
        createCell(row, 11, "Prix", style);
        createCell(row, 12, "Total", style);

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

        for (Reservation command : listCommands) {
            for (Product product:command.getProducts()){
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, command.getId(), style);
            createCell(row, columnCount++, command.getName(), style);
            createCell(row, columnCount++, command.getEmail(), style);
            createCell(row, columnCount++, command.getCategory(), style);
            createCell(row, columnCount++, command.getDate(), style);
            createCell(row, columnCount++, command.getTime(), style);
            createCell(row, columnCount++, command.getAddress(), style);
            createCell(row, columnCount++, command.getRegion(), style);
            createCell(row, columnCount++, command.getVille(), style);



                createCell(row,columnCount++,product.getName(),style);
                createCell(row,columnCount++,product.getQte(),style);
                createCell(row,columnCount++,product.getPrice(),style);

            createCell(row, columnCount++, command.getTotal(), style);
            }
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
