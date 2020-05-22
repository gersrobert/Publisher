package fiit.hipstery.publisher.bl.impl;

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
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PdfServiceImpl implements PdfService {

	@Autowired
	private ArticleRepository articleRepository;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public String generatePdf(UUID articleId) throws DocumentException {
		Article article = articleRepository.getOne(articleId);
		List<AppUser> authorList = articleRepository.getAuthors(articleId);

		Document document = new Document();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		PdfWriter.getInstance(document, stream);

		document.open();
		Font metadataPrimaryFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);
		Font metadataSecondaryFont = FontFactory.getFont(FontFactory.HELVETICA, 14, BaseColor.BLACK);
		Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);


		Chunk title = new Chunk(article.getTitle(), metadataPrimaryFont);
		Phrase phTitle = new Phrase(title);

		List<String> authorStrings = authorList.stream().map(a -> a.getFirstName() + " " + a.getLastName()).collect(Collectors.toList());
		String authorString = String.join(", ", authorStrings);

		String categoryNames = "";
		for (Category category : article.getCategories()) {
			if (categoryNames.isBlank()) {
				categoryNames = category.getName();
			} else {
				categoryNames += ", " + category.getName();
			}
		}

		Chunk authors = new Chunk("Authors: " + authorString, metadataSecondaryFont);
		Phrase phAuthors = new Phrase(authors);

		Chunk categories = new Chunk("Categories: " + categoryNames, metadataSecondaryFont);
		Phrase phCategories = new Phrase(categories);

		Chunk publisher = new Chunk("Publisher: " + article.getPublisher().getName(), metadataSecondaryFont);
		Phrase phPublisher = new Phrase(publisher);

		Chunk createdAt = new Chunk("Published: " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(article.getCreatedAt()), metadataSecondaryFont);
		Phrase phCreatedAt = new Phrase(createdAt);

		Chunk likes = new Chunk("Number Of Likes: " + String.valueOf(article.getLikeCount()), metadataSecondaryFont);
		Phrase phLikes = new Phrase(likes);

		Chunk content = new Chunk(article.getContent(), contentFont);
		Phrase phContent = new Phrase(content);

		Paragraph ph = new Paragraph();
		ph.setAlignment(Element.ALIGN_JUSTIFIED);
		ph.add(phTitle);
		ph.add("\n");
		ph.add("\n");
		ph.add(phAuthors);
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
			Chunk commentChunk = new Chunk(commentAuthor + ": " + comment.getContent(), contentFont);
			Phrase phComment = new Phrase(commentChunk);
			ph.add(phComment);
			ph.add("\n");
		}

		document.add(ph);

		document.close();
		return Base64.getEncoder().encodeToString(stream.toByteArray());
	}
}
