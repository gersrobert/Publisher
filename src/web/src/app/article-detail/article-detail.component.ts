import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {AppUserDTO, ArticleDetailedDTO, ArticleSimpleDTO} from '../dto/dtos';
import {ArticleService} from '../service/article.service';
import { Converter } from 'showdown';

@Component({
  selector: 'app-article-detail',
  templateUrl: './article-detail.component.html',
  styleUrls: ['./article-detail.component.less']
})
export class ArticleDetailComponent implements OnInit {
  article: ArticleDetailedDTO;

  constructor(private articleService: ArticleService, private route:ActivatedRoute) {}

  ngOnInit() {
    let id: string;
    this.route.params.subscribe(params => {
       id = params['id'];
    });
    console.log(id);
    this.articleService.getArticleById(id).subscribe(response => {
      console.log(response);
      this.article = response;

      const converter = new Converter();
      this.article.content = converter.makeHtml(this.article.content);
    });
  }

  public likeArticle() {
    if (this.article.liked) {
      this.articleService.unlikeArticle(this.article.id).subscribe(response => {
        this.article.likeCount = response;
        this.article.liked = false;
      });
    } else {
      this.articleService.likeArticle(this.article.id).subscribe(response => {
        this.article.likeCount = response;
        this.article.liked = true;
      });
    }
  }
}
