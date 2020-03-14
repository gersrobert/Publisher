import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {ArticleDetailedDTO, ArticleSimpleDTO} from './dto/dtos';
import {environment} from '../environments/environment';
import {ArticleService} from './service/article.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  articles: ArticleSimpleDTO[];
  titleName: string;

  constructor(private articleService: ArticleService) {}

  ngOnInit() {
    this.articleService.getArticles().subscribe(response => this.articles = response);
  }

  nameOfButton(article) {
    console.log(article);
    this.titleName = article.title;
  }
}
