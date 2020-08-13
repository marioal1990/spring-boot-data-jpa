package com.mycroft.sbdj.view.pdf;

import java.awt.Color;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.itextpdf.text.Element;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.mycroft.sbdj.model.entities.Bill;
import com.mycroft.sbdj.model.entities.BillDetail;
import com.mycroft.sbdj.model.entities.Product;
import com.mycroft.sbdj.model.entities.User;
import com.mycroft.sbdj.utils.ConstantsUtil;
import com.mycroft.sbdj.utils.DateUtil;
import com.mycroft.sbdj.utils.LabelsProperties;

@Component(ConstantsUtil.PATH_BILL_VIEW)
public class BillPdfView extends AbstractPdfView {

	@Autowired
	private LabelsProperties labelsProperties;
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Bill bill = (Bill) model.get("bill");
		
		//USER
		PdfPTable tableUser = this.createTableUser(bill);
		//BILL
		PdfPTable tableBill = this.createTableBillHeader(bill);
		//BILL DETAILL
		PdfPTable tableBillDetails = this.createTableBillDetails(bill);
		
		document.add(tableUser);
		document.add(tableBill);
		document.add(tableBillDetails);
		document.close();
	}

	/**
	 * Create pdf table with data User
	 * @param bill The Bill object
	 * @return The PdfPTable object with data
	 */
	private PdfPTable createTableUser(Bill bill) {
		PdfPCell cell = new PdfPCell(new Phrase(ConstantsUtil.DOC_TITLE_USER));
		cell.setBackgroundColor(new Color(184, 218, 255));
		cell.setPadding(8f);
		cell.setColspan(2);
		
		User user = bill.getUser();
		String fullyName = "Nombre: ".concat(user.getName().concat(ConstantsUtil.WHITE_SPACE).concat(user.getLastname()));
		String email = "Email: ".concat(user.getEmail());
		PdfPTable tableUser = new PdfPTable(2);
		tableUser.addCell(cell);
		tableUser.setSpacingAfter(10);
		tableUser.setWidths(ConstantsUtil.PDF_TWO_WIDTH);
		tableUser.addCell(fullyName);
		tableUser.addCell(email);
		return tableUser;
	}

	/**
	 * Create pdf table with data Bill 
	 * @param bill The Bill object
	 * @return The PdfPTable object with data
	 */
	private PdfPTable createTableBillHeader(Bill bill) {
		PdfPCell cell = new PdfPCell(new Phrase(ConstantsUtil.DOC_TITLE_BILL));
		cell.setBackgroundColor(new Color(195, 230, 255));
		cell.setPadding(8f);
		cell.setColspan(2);

		String numberBill = labelsProperties.getBviewTitle()
				.concat(ConstantsUtil.WHITE_SPACE)
				.concat(ConstantsUtil.NUMBER_CIRCLE)
				.concat(ConstantsUtil.WHITE_SPACE)
				.concat(bill.getId().toString());
		String date = "Fecha: ".concat(DateUtil.dateToString(bill.getCreated(), ConstantsUtil.PATTERN_DDMMYYYY_HHMMSS));
		String description = "Descripción: ".concat(bill.getDescription());
		PdfPTable tableBill = new PdfPTable(2);
		tableBill.addCell(cell);
		tableBill.setSpacingAfter(10);
		tableBill.setWidths(ConstantsUtil.PDF_TWO_WIDTH);
		tableBill.addCell(numberBill);
		tableBill.addCell(date);
		PdfPCell cellBill = new PdfPCell(new Phrase(description));
		cellBill.setColspan(2);
		tableBill.addCell(cellBill);
		cellBill = new PdfPCell(new Phrase(ConstantsUtil.WHITE_SPACE));
		cellBill.setColspan(2);
		tableBill.addCell(cellBill);
		return tableBill;
	}

	/**
	 * Create pdf table with data BillDetails
	 * @param bill The Bill object
	 * @return The PdfPTable object with data
	 * @throws IOException The exception for IO
	 */
	private PdfPTable createTableBillDetails(Bill bill) throws IOException {
		PdfPCell cell = new PdfPCell(new Phrase(ConstantsUtil.DOC_TITLE_BILL_DETAILS));
		cell.setBackgroundColor(new Color(195, 230, 255));
		cell.setPadding(8f);
		cell.setColspan(4);

		PdfPTable tableBillDetails = new PdfPTable(4);
		tableBillDetails.addCell(cell);
		tableBillDetails.setSpacingAfter(10);
		tableBillDetails.setWidths(ConstantsUtil.PDF_FOUR_WIDTH);
		tableBillDetails.addCell(this.labelsProperties.getBtId());
		tableBillDetails.addCell(this.labelsProperties.getBtProduct());
		tableBillDetails.addCell(this.labelsProperties.getBtQuantity());
		tableBillDetails.addCell(this.labelsProperties.getBtSubTotal());
		
		for(BillDetail billDetail : bill.getBillDetails()) {
			Product product = billDetail.getProduct();
			String quantity = billDetail.getQuantity().toString()
					.concat(ConstantsUtil.WHITE_SPACE)
					.concat(ConstantsUtil.MULTIPLY.toLowerCase())
					.concat(ConstantsUtil.WHITE_SPACE)
					.concat(ConstantsUtil.MONEY)
					.concat(product.getPrice().toString());
			tableBillDetails.addCell(product.getId().toString());
			tableBillDetails.addCell(product.getName());
			tableBillDetails.addCell(quantity);
			tableBillDetails.addCell(ConstantsUtil.MONEY
					.concat(billDetail.calculateSubTotal().toString()));
		}
		Font boldFont = new Font(Font.TIMES_ROMAN, 18, Font.BOLD);
		String total = this.labelsProperties.getBtTotal()
				.concat(ConstantsUtil.TWO_POINTS)
				.concat(ConstantsUtil.WHITE_SPACE)
				.concat(ConstantsUtil.MONEY)
				.concat(bill.calculateTotal().toString());
		cell = new PdfPCell(new Phrase(total, boldFont));
		cell.setColspan(5);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tableBillDetails.addCell(cell);
		return tableBillDetails;
	}
}
