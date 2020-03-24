import { Component, OnInit } from '@angular/core';
import {ArticleSimpleDTO} from '../dto/dtos';
import {ArticleService} from '../service/article.service';

@Component({
  selector: 'app-article-list',
  templateUrl: './article-list.component.html',
  styleUrls: ['./article-list.component.less']
})
export class ArticleListComponent implements OnInit {

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
