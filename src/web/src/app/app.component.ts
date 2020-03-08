import { Component } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {ArticleDetailedDTO} from './dto/dtos';
import {environment} from '../environments/environment';
import {ArticleService} from './service/article.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'web';

  posts: any;

  constructor(private articleService: ArticleService) {}

  getWorld() {
    this.articleService.getArticleById('1').subscribe(article => {
      this.posts = article.content;
    });
  }
}
