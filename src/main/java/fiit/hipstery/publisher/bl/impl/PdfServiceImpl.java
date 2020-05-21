package fiit.hipstery.publisher.bl.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import fiit.hipstery.publisher.bl.service.PdfService;
import fiit.hipstery.publisher.entity.Article;
import fiit.hipstery.publisher.repository.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Service
public class PdfServiceImpl implements PdfService {

	@Autowired
	private ArticleRepository articleRepository;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public String generatePdf(UUID articleId) throws DocumentException {
		Article article = articleRepository.getOne(articleId);

		Document document = new Document();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		PdfWriter.getInstance(document, stream);

		document.open();
		Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
		Chunk chunk = new Chunk(article.getTitle(), font);
		document.add(chunk);
		document.close();

		try {
			// TODO remove after testing
			File tempFile = File.createTempFile("article_", "_" + article.getId().toString() + ".pdf", null);
			FileOutputStream fos = new FileOutputStream(tempFile);
			fos.write(stream.toByteArray());
			logger.info(tempFile.getAbsolutePath());
		} catch (IOException e) {
			logger.error("error saving temp file", e);
		}

		return Base64.getEncoder().encodeToString(stream.toByteArray());
	}
}
