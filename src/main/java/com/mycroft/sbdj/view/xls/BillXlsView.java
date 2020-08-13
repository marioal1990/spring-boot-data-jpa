package com.mycroft.sbdj.view.xls;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.mycroft.sbdj.model.entities.Bill;
import com.mycroft.sbdj.model.entities.BillDetail;
import com.mycroft.sbdj.model.entities.Product;
import com.mycroft.sbdj.model.entities.User;
import com.mycroft.sbdj.utils.ConstantsUtil;
import com.mycroft.sbdj.utils.DateUtil;
import com.mycroft.sbdj.utils.LabelsProperties;

@Component(ConstantsUtil.PATH_BILL_VIEW_XLSX)
public class BillXlsView extends AbstractXlsxView {

	@Autowired
	private LabelsProperties labelsProperties;

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Content-Disposition", "attachment; filename=\"bill_view.xlsx\"");
		Bill bill = (Bill) model.get("bill");
		Sheet sheet = workbook.createSheet("Factura");
		
		//USER
		this.createDataUser(bill, sheet);
		//BILL
		this.createDataBill(bill, sheet);
		//BILL DETAILL
		this.createDataBillDetails(bill, sheet, workbook);
	}

	/**
	 * Create xlsx data User
	 * @param bill The Bill object
	 * @param sheet The Document sheet
	 */
	private void createDataUser(Bill bill, Sheet sheet) {
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		User user = bill.getUser();
		String fullyName = "Nombre: ".concat(user.getName().concat(ConstantsUtil.WHITE_SPACE).concat(user.getLastname()));
		String email = "Email: ".concat(user.getEmail());
		cell.setCellValue(ConstantsUtil.DOC_TITLE_USER);
		row = sheet.createRow(1);
		cell = row.createCell(0);
		cell.setCellValue(fullyName);
		row = sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellValue(email);
	}

	/**
	 * Create xlsx data Bill
	 * @param bill The Bill object
	 * @param sheet The Document sheet
	 */
	private void createDataBill(Bill bill, Sheet sheet) {
		String numberBill = labelsProperties.getBviewTitle()
				.concat(ConstantsUtil.WHITE_SPACE)
				.concat(ConstantsUtil.NUMBER_CIRCLE)
				.concat(ConstantsUtil.WHITE_SPACE)
				.concat(bill.getId().toString());
		String date = "Fecha: ".concat(DateUtil.dateToString(bill.getCreated(), ConstantsUtil.PATTERN_DDMMYYYY_HHMMSS));
		String description = "Descripción: ".concat(bill.getDescription());
		sheet.createRow(4).createCell(0).setCellValue(ConstantsUtil.DOC_TITLE_BILL);
		sheet.createRow(5).createCell(0).setCellValue(numberBill);
		sheet.createRow(6).createCell(0).setCellValue(date);
		sheet.createRow(7).createCell(0).setCellValue(description);
	}

	/**
	 * Create xlsx data Bill Details
	 * @param bill The Bill object
	 * @param sheet The Document sheet
	 */
	private void createDataBillDetails(Bill bill, Sheet sheet, Workbook workbook) {
		CellStyle theaderStyle = workbook.createCellStyle();
		theaderStyle.setBorderBottom(BorderStyle.MEDIUM);
		theaderStyle.setBorderTop(BorderStyle.MEDIUM);
		theaderStyle.setBorderRight(BorderStyle.MEDIUM);
		theaderStyle.setBorderLeft(BorderStyle.MEDIUM);
		theaderStyle.setFillForegroundColor(IndexedColors.GOLD.index);
		theaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		CellStyle tbodyStyle = workbook.createCellStyle();
		tbodyStyle.setBorderBottom(BorderStyle.THIN);
		tbodyStyle.setBorderTop(BorderStyle.THIN);
		tbodyStyle.setBorderRight(BorderStyle.THIN);
		tbodyStyle.setBorderLeft(BorderStyle.THIN);
		
		sheet.createRow(9).createCell(0).setCellValue(ConstantsUtil.DOC_TITLE_BILL_DETAILS);
		int size = 10;
		Row rowBDHeader = sheet.createRow(size);
		rowBDHeader.createCell(0).setCellValue(this.labelsProperties.getBtId());
		rowBDHeader.createCell(1).setCellValue(this.labelsProperties.getBtProduct());
		rowBDHeader.createCell(2).setCellValue(this.labelsProperties.getBtQuantity());
		rowBDHeader.createCell(3).setCellValue(this.labelsProperties.getBtSubTotal());
		rowBDHeader.getCell(0).setCellStyle(theaderStyle);
		rowBDHeader.getCell(1).setCellStyle(theaderStyle);
		rowBDHeader.getCell(2).setCellStyle(theaderStyle);
		rowBDHeader.getCell(3).setCellStyle(theaderStyle);
		
		for(BillDetail billDetail : bill.getBillDetails()) {
			size += 1;
			Row rowBDBody = sheet.createRow(size);
			
			Product product = billDetail.getProduct();
			String quantity = billDetail.getQuantity().toString()
					.concat(ConstantsUtil.WHITE_SPACE)
					.concat(ConstantsUtil.MULTIPLY.toLowerCase())
					.concat(ConstantsUtil.WHITE_SPACE)
					.concat(ConstantsUtil.MONEY)
					.concat(product.getPrice().toString());
			rowBDBody.createCell(0).setCellValue(product.getId().toString());
			rowBDBody.createCell(1).setCellValue(product.getName());
			rowBDBody.createCell(2).setCellValue(quantity);
			rowBDBody.createCell(3).setCellValue(ConstantsUtil.MONEY
					.concat(billDetail.calculateSubTotal().toString()));
			rowBDBody.getCell(0).setCellStyle(tbodyStyle);
			rowBDBody.getCell(1).setCellStyle(tbodyStyle);
			rowBDBody.getCell(2).setCellStyle(tbodyStyle);
			rowBDBody.getCell(3).setCellStyle(tbodyStyle);
		}
		String total = ConstantsUtil.WHITE_SPACE
				.concat(ConstantsUtil.MONEY)
				.concat(bill.calculateTotal().toString());
		Row rowBDTotalBody = sheet.createRow(size+1);
		rowBDTotalBody.createCell(2).setCellValue(this.labelsProperties.getBtTotal()
				.concat(ConstantsUtil.TWO_POINTS));
		rowBDTotalBody.createCell(3).setCellValue(total);
		rowBDTotalBody.getCell(3).setCellStyle(tbodyStyle);
	}
}
