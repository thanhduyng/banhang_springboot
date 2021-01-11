package learncode.spring.comon;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import learncode.spring.model.BillDetail;

public class BillDetailPDFExporter {

	private List<BillDetail> HoaDonChiTiet;

	public BillDetailPDFExporter(List<BillDetail> HoaDonChiTiet) {
		this.HoaDonChiTiet = HoaDonChiTiet;
	}

	private void writeTableHeader(PdfPTable table) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.ORANGE);
		cell.setPadding(5);

		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);

		cell.setPhrase(new Phrase("Ma don hang", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Ten khach hang", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Ten san pham", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("So luong", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Tong tien", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Trang thai", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Gio va Ngay", font));
		table.addCell(cell);
	}

	private void writeTableData(PdfPTable table) {
		for (BillDetail billDetail : HoaDonChiTiet) {
			table.addCell(String.valueOf(billDetail.getId()));
			table.addCell(billDetail.getBills().getDisplay_name());
			table.addCell(billDetail.getProducts().getName());
			table.addCell(String.valueOf(billDetail.getQuantity()));
			table.addCell(String.valueOf(billDetail.getTotal()));
			table.addCell(String.valueOf(billDetail.getTrangthai()));
			table.addCell(String.valueOf(billDetail.getDatetime()));
		}
	}

	public void export(HttpServletResponse response) throws DocumentException, IOException {
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());

		document.open();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(Color.BLUE);

		Paragraph p = new Paragraph("Danh sach hoa don", font);
		p.setAlignment(Paragraph.ALIGN_CENTER);

		document.add(p);

		PdfPTable table = new PdfPTable(7);
		table.setWidthPercentage(90f);
		table.setWidths(new float[] { 1f, 1f, 1f, 1f, 1f, 1f, 1f});
		table.setSpacingBefore(10);

		writeTableHeader(table);
		writeTableData(table);

		document.add(table);

		document.close();

	}
}
