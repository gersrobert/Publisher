package fiit.hipstery.publisher.bl.service;

import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.util.UUID;

public interface PdfService {
	String generatePdf(UUID articleId) throws DocumentException, IOException;
}
