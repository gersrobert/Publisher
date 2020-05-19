package fiit.hipstery.publisher.bl.impl;

import fiit.hipstery.publisher.bl.service.ArticleService;
import fiit.hipstery.publisher.bl.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PdfServiceImpl implements PdfService {

	@Autowired
	private ArticleService articleService;

	@Override
	public void generatePdf(UUID articleId) {
		// TODO
	}
}
