package vux.codejava.util;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import vux.codejava.entity.shift.ShiftEntity;
import vux.codejava.lib.Convert;

public class ShiftExcelExportUtil {

	private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<ShiftEntity> listShiftActions, listShifts;
    
    static String[] HEADER_SHIFT = { "Username", "Thời gian nhận ca", "Thời gian giao ca", "Tổng giờ" };
    static String[] HEADER_ACTION = {"Username", "Thời gian", "Khu vực", "Ghi chú sự kiện", "Ghi chú", ""};
    static DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    
    public ShiftExcelExportUtil(List<ShiftEntity> listShifts, List<ShiftEntity> listShiftActions) {
    	this.listShifts = listShifts;
    	this.listShiftActions = listShiftActions;
    	workbook = new XSSFWorkbook();
    }
    
    private void createSheetAction() {
    	sheet = workbook.createSheet("Sự kiện");
    	writeHeaderLine(sheet, HEADER_ACTION);
    	writeDataLinesAction();
    }
    
    private void createSheetShift(String month) {
    	sheet = workbook.createSheet("Giao-Nhận ca");
    	//writeHeaderLine(sheet, HEADER_SHIFT);
    	int colSumHours = writeHeaderLineShift(sheet, month);
    	writeDataLinesShift(colSumHours);
    }
    
    private int writeHeaderLineShift(XSSFSheet sheet, String month) {
    	String[] months = month.split("-");
		int y = Integer.parseInt(months[0]);
		int m = Integer.parseInt(months[1]);
		
		// Get the number of days in that month
		YearMonth yearMonthObject = YearMonth.of(y, m);
		int daysInMonth = yearMonthObject.lengthOfMonth();
		
		Row row = sheet.createRow(0);
        
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "Username", style);
        int col = 1;
        for (; col <= daysInMonth; col++) {
            createCell(row, col, col+"/"+m+"/"+y, style);
        }
        createCell(row, col, "Tổng thời gian", style);
		return daysInMonth + 1;
    }
    
    private void writeHeaderLine(XSSFSheet sheet, String[] header) {
        
         
        Row row = sheet.createRow(0);
         
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        for (int col = 0; col < header.length; col++) {
            createCell(row, col, header[col], style);
        }    
          
    }
    
    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
    
    private void writeDataLinesAction() {
        int rowCount = 1;
 
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
                 
        for (ShiftEntity shift : listShiftActions) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
             
            createCell(row, columnCount++, shift.getUserReceive(), style);
            //String formattedString = shift.getDateShift().format(CUSTOM_FORMATTER);
            LocalDate localDate = shift.getDateShift().toLocalDate();
            createCell(row, columnCount++, localDate.toString(), style);
            //createCell(row, columnCount++, shift.getDistrict(), style);
            //createCell(row, columnCount++, shift.getAction() == 0 ? "Không sự kiện" : "Có sự kiện", style);
            createCell(row, columnCount++, shift.toString(), style);
            createCell(row, columnCount++, shift.getNote(), style);
            createCell(row, columnCount++, shift.getNoteReceive(), style);
        }
    }
     
    private void writeDataLinesShift(int colSumHours) {
        int rowCount = 1;
 
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        String rowUser = "none";
        Row row = null;
        long sumHours = 0;
        int size = listShifts.size() - 1;
        int count = 0;
        
        for (ShiftEntity shift : listShifts) {
        	
            int columnCount = 0;
            
        	if(rowUser.equals(shift.getUserReceive())) {
        		long t = Convert.convertToMinutes(shift.getDateReceive(), shift.getDateShift());
        		createCell(row, shift.getDateReceive().getDayOfMonth(), Convert.convertToHoursMinutes(t), style);
        		sumHours += t;
        		
        		if(size == count) {
        			createCell(row, colSumHours, Convert.convertToHoursMinutes(sumHours), style);
        		}
        	}else {
        		if(!rowUser.equals("none")) {
        			createCell(row, colSumHours, Convert.convertToHoursMinutes(sumHours), style);
        		}
        		row = sheet.createRow(rowCount);
        		createCell(row, columnCount, shift.getUserReceive(), style);
        		long t = Convert.convertToMinutes(shift.getDateReceive(), shift.getDateShift());
        		sumHours += t;
        		createCell(row, shift.getDateReceive().getDayOfMonth(), Convert.convertToHoursMinutes(t), style);
        		rowUser = shift.getUserReceive();
        		rowCount++;
        	}
            count++;
            
        }
    }
     
    public void export(HttpServletResponse response, int select, String month) throws IOException {
    	switch (select) {
		case 1://create action
			createSheetAction();
			break;
		case 2://create shift
			createSheetShift(month);
			break;
		default:
			createSheetAction();
			createSheetShift(month);
			break;
		}
        
         
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
         
        outputStream.close();
         
    }
    
}
