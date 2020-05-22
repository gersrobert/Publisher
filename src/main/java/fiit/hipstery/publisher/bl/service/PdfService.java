package fiit.hipstery.publisher.bl.service;

import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.util.UUID;

public interface PdfService {
	/**
	 * This method gets article from DB and returns it in PDF format
	 * @param articleId is UUID of article, which user wants to export
	 * @return Base64 encoded pdf
	 */
	String generatePdf(UUID articleId) throws DocumentException, IOException;
}
