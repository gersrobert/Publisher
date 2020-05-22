package fiit.hipstery.publisher.bl.impl;

import com.github.javafaker.App;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import fiit.hipstery.publisher.bl.service.PdfService;
import fiit.hipstery.publisher.entity.AppUser;
import fiit.hipstery.publisher.entity.Article;
import fiit.hipstery.publisher.entity.Category;
import fiit.hipstery.publisher.entity.Comment;
import fiit.hipstery.publisher.repository.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
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
		Font metadataPrimaryFont = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
		Font metadataSecondaryFont = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
		Font contentFont = FontFactory.getFont(FontFactory.COURIER, 12, BaseColor.BLACK);


		Chunk title = new Chunk("Title : " + article.getTitle(), metadataPrimaryFont);
		Phrase phTitle = new Phrase(title);

		String categoryNames = new String();
		for (Category category : article.getCategories()) {
			if (categoryNames.isBlank()) {
				categoryNames = category.getName();
			} else {
				categoryNames += ", " + category.getName();
			}
		}
		Chunk categories = new Chunk("Categories : " + categoryNames, metadataSecondaryFont);
		Phrase phCategories = new Phrase(categories);

		Chunk publisher = new Chunk("Publisher : " + article.getPublisher().getName(), metadataSecondaryFont);
		Phrase phPublisher = new Phrase(publisher);

		Chunk createdAt = new Chunk("Published : " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(article.getCreatedAt()), metadataSecondaryFont);
		Phrase phCreatedAt = new Phrase(createdAt);

		Chunk likes = new Chunk("Number Of Likes : " + String.valueOf(article.getLikeCount()), metadataSecondaryFont);
		Phrase phLikes = new Phrase(likes);

		Chunk content = new Chunk(article.getContent(), contentFont);
		Phrase phContent = new Phrase(content);

		Paragraph ph = new Paragraph();
		ph.add(phTitle);
		ph.add("\n");
		ph.add("\n");
		ph.add(phCategories);
		ph.add("\n");
		ph.add(phPublisher);
		ph.add("\n");
		ph.add(phCreatedAt);
		ph.add("\n");
		ph.add(phLikes);
		ph.add("\n");
		ph.add("\n");
		ph.add(phContent);
		ph.add("\n");
		ph.add("\n");
		Chunk commentsTitle = new Chunk("Comments", metadataSecondaryFont);
		Phrase phCommentsTitle = new Phrase(commentsTitle);
		ph.add(phCommentsTitle);
		ph.add("\n");

		for (Comment comment : article.getComments()) {
			String commentAuthor = comment.getAuthor().getFirstName() + " " + comment.getAuthor().getLastName();
			Chunk commentChunk = new Chunk(commentAuthor + " : " + comment.getContent(), contentFont);
			Phrase phComment = new Phrase(commentChunk);
			ph.add(phComment);
			ph.add("\n");
		}

		document.add(ph);

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
