import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {ArticleDetailedDTO} from '../core/dto/dtos';
import {ArticleService} from '../core/service/article.service';

@Component({
  selector: 'app-article-update',
  templateUrl: './article-update.component.html',
  styleUrls: ['./article-update.component.css']
})
export class ArticleUpdateComponent implements OnInit {
  article: ArticleDetailedDTO;

  constructor(
    private articleService: ArticleService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.articleService.getArticleById(params['id']).subscribe(
        response => {
          this.article = response;
          console.log(this.article);
        }
      );
    });
  }
}
