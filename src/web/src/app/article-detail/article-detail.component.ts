import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {ArticleDetailedDTO, ArticleSimpleDTO} from '../dto/dtos';
import {ArticleService} from '../service/article.service';

@Component({
  selector: 'app-article-detail',
  templateUrl: './article-detail.component.html',
  styleUrls: ['./article-detail.component.less']
})
export class ArticleDetailComponent implements OnInit {
  article: ArticleDetailedDTO;

  constructor(private articleService: ArticleService, private route:ActivatedRoute) {}

  ngOnInit() {
    var id:string
    this.route.params.subscribe(params => {
       id = params['id'];
    });
    console.log(id);
    this.articleService.getArticleById(id).subscribe(response => this.article = response);
  }
}
