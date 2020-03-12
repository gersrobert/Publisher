import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {ArticleDetailedDTO} from './dto/dtos';
import {environment} from '../environments/environment';
import {ArticleService} from './service/article.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'web';

  articleTitle: string;
  article: string;
  publishedAt: Date;
  author: string;

  constructor(private articleService: ArticleService) {}

  ngOnInit() {
    this.getWorld();
  }

  getWorld() {
    this.articleService.getArticleById('72b4a325-7fc0-4a3a-9ae5-00c8a4cde1ab').subscribe(article => {
      this.articleTitle = article.title;
      this.article = article.content;
      this.publishedAt = article.publishedAt;
      this.author = article.author;
    });
  }
}
