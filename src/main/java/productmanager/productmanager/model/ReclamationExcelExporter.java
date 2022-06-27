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
public class ReclamationExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Reclamation> reclamations;

    public ReclamationExcelExporter(List<Reclamation> reclamations) {
        this.reclamations = reclamations;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Réclamations");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Code_Réclamation", style);
        createCell(row, 1, "Nom", style);
        createCell(row, 2, "Email", style);
        createCell(row, 3, "Code_Commande", style);
        createCell(row, 4, "Produit", style);
        createCell(row, 5, "Code_Article", style);
        createCell(row, 6, "Quantité", style);
        createCell(row, 7, "Motif", style);
        createCell(row, 8, "Date", style);
        createCell(row, 9, "Heure", style);
        createCell(row, 10, "Etat", style);
        createCell(row, 11, "Date_Clôture", style);
        createCell(row, 12, "Catégorie", style);


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

        for (Reclamation reclamation : reclamations) {
            for (ProductClaimed product:reclamation.getProductClaimeds()){
                Row row = sheet.createRow(rowCount++);
                int columnCount = 0;

                createCell(row, columnCount++, reclamation.getId(), style);
                createCell(row, columnCount++, reclamation.getClientName(), style);
                createCell(row, columnCount++, reclamation.getClientEmail(), style);
                createCell(row, columnCount++, reclamation.getCodeCommand(), style);
                createCell(row, columnCount++, product.getName(), style);
                createCell(row, columnCount++, product.getCodeArticle(), style);
                createCell(row, columnCount++, product.getQte(), style);
                createCell(row, columnCount++, product.getMotif(), style);
                createCell(row, columnCount++, reclamation.getDate(), style);
                createCell(row,columnCount++,reclamation.getTime(),style);
                createCell(row,columnCount++,reclamation.getEtat(),style);
                createCell(row,columnCount++,reclamation.getDateCloture(),style);
                createCell(row, columnCount++, reclamation.getCategorie(), style);
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
