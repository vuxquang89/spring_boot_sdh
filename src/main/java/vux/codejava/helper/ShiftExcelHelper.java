package vux.codejava.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import vux.codejava.entity.shift.ShiftEntity;
import vux.codejava.lib.Convert;
import vux.codejava.util.Variables;

public class ShiftExcelHelper {

	private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<ShiftEntity> listShiftActions, listShifts;
    
    static String[] HEADER_SHIFT = { "Username", "Thời gian nhận ca", "Thời gian giao ca", "Tổng giờ" };
    static String[] HEADER_ACTION = {"Username", "Thời gian", "Khu vực", "Ghi chú sự kiện", "Ghi chú","Ghi chú trước khi nhận ca"};
    
    static DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    public ShiftExcelHelper(List<ShiftEntity> listShifts, List<ShiftEntity> listShiftActions) {
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
//    	int colSumHours = writeHeaderLineShift(sheet, month);
//    	writeDataLinesShift(colSumHours);
    	try {
    		int colSumHours = writeHeaderLineShift(sheet, month);
			writeDataLinesShift_1(colSumHours);
		} catch (DecoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//    	int countRow = writeHeaderLeftLineShift(sheet, month);
//    	try {
//			writeDataShift(countRow);
//		} catch (DecoderException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    }
    
    private int writeHeaderLineShift(XSSFSheet sheet, String month) {
    	String[] months = Convert.convertStringToMonthYear(month);
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
        createCell(row, col++, "Số ca trực", style);
        createCell(row, col++, "Tổng thời gian", style);
		return daysInMonth + 1;
    }
    
    private int writeHeaderLeftLineShift(XSSFSheet sheet, String month) {
    	String[] months = Convert.convertStringToMonthYear(month);
		int y = Integer.parseInt(months[0]);
		int m = Integer.parseInt(months[1]);
		
		// Get the number of days in that month
		YearMonth yearMonthObject = YearMonth.of(y, m);
		int daysInMonth = yearMonthObject.lengthOfMonth();
		
		
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        
        int countRow = 1;
        Row row = sheet.createRow(countRow);        
        createCell(row, 0, "Username", style);
        
        for (; countRow <= daysInMonth; countRow++) {
        	row = sheet.createRow(countRow+1);
            createCell(row, 0, countRow+"/"+m+"/"+y, style);
        }
//        createCell(row, col++, "Số ca trực", style);
//        createCell(row, col++, "Tổng thời gian", style);
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
        style.setWrapText(true);
                 
        for (ShiftEntity shift : listShiftActions) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
             
            createCell(row, columnCount++, shift.getUserReceive(), style);
            //String formattedString = shift.getDateShift().format(CUSTOM_FORMATTER);
            LocalDate localDate = shift.getDateShift().toLocalDate();
            createCell(row, columnCount++, localDate.toString(), style);
            createCell(row, columnCount++, shift.getDistrict(), style);
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
        int size = listShifts.size();
        int count = 1, countShift = 0;
        
        for (ShiftEntity shift : listShifts) {
        	
            int columnCount = 0;
            
        	if(!rowUser.equals(shift.getUserReceive())) {
        		if(!rowUser.equals("none")) {
        			createCell(row, colSumHours, countShift+"", style);
        			createCell(row, colSumHours+1, Convert.convertToHoursMinutes(sumHours), style);
        			sumHours = 0;
        			countShift = 0;
        		}
        		row = sheet.createRow(rowCount);
        		createCell(row, columnCount, shift.getUserReceive(), style);
        		
        		rowUser = shift.getUserReceive();
        		rowCount++;
        		
        		
        	}
        	
        	long t = Convert.convertToMinutes(shift.getDateReceive(), shift.getDateShift());
    		createCell(row, shift.getDateReceive().getDayOfMonth(), Convert.convertToHoursMinutes(t), style);
    		sumHours += t;
    		countShift++;
        	
    		if(size == count) {
    			createCell(row, colSumHours, countShift+"", style);
    			createCell(row, colSumHours+1, Convert.convertToHoursMinutes(sumHours), style);
    			sumHours = 0;
    			countShift = 0;
    		}
    		
            count++;
            
        }
    }
    
    private void writeDataLinesShift_1(int colSumHours) throws DecoderException {
        int rowCount = 1;
 
        CellStyle styleHeader = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();        
        font.setFontHeight(14);
        styleHeader.setFont(font);       
        
        String rowUser = "none";
        Row row = null;
        long sumHours = 0;
        int size = listShifts.size();
        int count = 1, countShift = 0;
        
        for (ShiftEntity shift : listShifts) {
        	
            int columnCount = 0;
            
        	if(!rowUser.equals(shift.getUserReceive())) {
        		if(!rowUser.equals("none")) {
        			createCell(row, colSumHours, countShift+"", styleHeader);
        			createCell(row, colSumHours+1, Convert.convertToHoursMinutes(sumHours), styleHeader);
        			sumHours = 0;
        			countShift = 0;
        		}
        		row = sheet.createRow(rowCount);
        		createCell(row, columnCount, shift.getUserReceive(), styleHeader);
        		
        		rowUser = shift.getUserReceive();
        		rowCount++;
        		
        		
        	}
        	
        	int r = shift.getDateReceive().getDayOfMonth();
        	int s = shift.getDateShift().getDayOfMonth();
        	int iColor = 2;
//        	row = sheet.createRow(rowCount);
        	
        	long t = Convert.convertToMinutes(shift.getDateReceive(), shift.getDateShift());
        	if(r == s) {
        		iColor = 1;
        	}else if((int)t/60 > 20) {
				iColor = 0;
			}        	
        	
        	String content = "- Nhận: " + Convert.dateToString(shift.getDateReceive().toString()) + "\n- Giao: " + Convert.dateToString(shift.getDateShift().toString());
        	
    		createCell(row, shift.getDateReceive().getDayOfMonth(), content, createStyle(iColor));
    		sumHours += t;
    		if((int)t/60 > 40) {
    			countShift += 4;
    		}else if((int)t/60 > 29) {
    			countShift += 3;
    		}else if((int)t/60 > 20) {
    			countShift += 2;
    		}else if((int)t/60 > 5) {
    			countShift++;
    		}
        	
    		if(size == count) {
    			createCell(row, colSumHours, countShift+"", styleHeader);
    			createCell(row, colSumHours+1, Convert.convertToHoursMinutes(sumHours), styleHeader);
    			sumHours = 0;
    			countShift = 0;
    		}
    		
            count++;
            
        }
    }
    
    private void writeDataShift(int countRow) throws DecoderException {
        
 
        CellStyle styleHeader = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();        
        font.setFontHeight(14);
        styleHeader.setFont(font);
        
        XSSFCellStyle styleCell = workbook.createCellStyle();
        XSSFFont fontCell = workbook.createFont();  
        fontCell.setColor(IndexedColors.WHITE.index);
        fontCell.setFontHeight(14);
        styleCell.setFont(fontCell);
        styleCell.setFillPattern(FillPatternType.SOLID_FOREGROUND);        
        
        String rowUser = "none";
        Row row = null;
        long sumHours = 0;
        int size = listShifts.size();
        int count = 1, countShift = 0;
        
        int rowCount = 0;
        int columnCount = 1;
        for (ShiftEntity shift : listShifts) {
            
        	if(!rowUser.equals(shift.getUserReceive())) {
        		if(!rowUser.equals("none")) {
        			row = sheet.createRow(countRow + 1);
        			createCell(row, columnCount, countShift+"", styleHeader);
        			createCell(row, columnCount+1, Convert.convertToHoursMinutes(sumHours), styleHeader);
        			sumHours = 0;
        			countShift = 0;
        			columnCount += 2;
        		}
        		
        		row = sheet.createRow(rowCount);
        		createCell(row, columnCount, "Nhận ca", styleHeader);
        		createCell(row, columnCount+1, "Giao ca", styleHeader);
        		
        		row = sheet.createRow(rowCount+1);
        		createCell(row, columnCount, shift.getUserReceive(), styleHeader);
        		
        		rowUser = shift.getUserReceive();
        		
//        		columnCount += 2;
        		
        		
        	}
        	
        	int r = shift.getDateReceive().getDayOfMonth();
        	int s = shift.getDateShift().getDayOfMonth();
        	int iColor = 2;
        	row = sheet.createRow(r + 1);
        	
        	long t = Convert.convertToMinutes(shift.getDateReceive(), shift.getDateShift());
        	if(r == s) {
        		iColor = 1;
        	}else if(t > 20) {
				iColor = 0;
			}
        	XSSFColor bgColor = getColor(iColor);
            styleCell.setFillForegroundColor(bgColor);
            
        	createCell(row, columnCount, shift.getDateReceive().toString(), styleCell);
        	createCell(row, columnCount+1, shift.getDateShift().toString(), styleCell);

    		sumHours += t;
    		countShift++;
        	
    		if(size == count) {
    			row = sheet.createRow(countRow + 1);
    			createCell(row, columnCount, countShift+"", styleHeader);
    			createCell(row, columnCount+1, Convert.convertToHoursMinutes(sumHours), styleHeader);
    			sumHours = 0;
    			countShift = 0;
    		}
    		
            count++;
            
        }
    }
    
    public ByteArrayInputStream export(int select, String month) {
    	switch (select) {
		case 1://create action
			System.out.println("create action");
			createSheetAction();
			break;
		case 2://create shift
			System.out.println("create shift");
			createSheetShift(month);
			break;
		default:
			System.out.println("create all");
			createSheetAction();
			createSheetShift(month);
			break;
		}
        
        try {
        	ByteArrayOutputStream out = new ByteArrayOutputStream();
        	workbook.write(out);
        	workbook.close();
            return new ByteArrayInputStream(out.toByteArray());
        }catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
        
    }
    
    private XSSFCellStyle createStyle(int iColor) throws DecoderException {
    	XSSFCellStyle styleCell = workbook.createCellStyle();
        XSSFFont fontCell = workbook.createFont();  
        fontCell.setColor(IndexedColors.WHITE.index);
        fontCell.setFontHeight(11);
        styleCell.setFont(fontCell);
        styleCell.setWrapText(true);
        XSSFColor bgColor = getColor(iColor);
        styleCell.setFillForegroundColor(bgColor);
        styleCell.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return styleCell;
    }
    
    private XSSFColor getColor(int index) throws DecoderException {
    	String rgbS = Variables.HEX_COLORS[index];
        byte[] rgbB = Hex.decodeHex(rgbS); // get byte array from hex string
        XSSFColor color = new XSSFColor(rgbB, null); //IndexedColorMap has no usage until now. So it can be set null.
        return color;
    }
}
