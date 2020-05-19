package fiit.hipstery.publisher.bl.service;

import java.util.UUID;

public interface PdfService {
	void generatePdf(UUID articleId);
}
