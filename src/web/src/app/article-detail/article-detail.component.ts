import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {AppUserDTO, ArticleDetailedDTO, ArticleSimpleDTO} from '../dto/dtos';
import {ArticleService} from '../service/article.service';
import { Converter } from 'showdown';
import {FormBuilder, FormGroup} from '@angular/forms';

@Component({
  selector: 'app-article-detail',
  templateUrl: './article-detail.component.html',
  styleUrls: ['./article-detail.component.less']
})
export class ArticleDetailComponent implements OnInit {
  article: ArticleDetailedDTO;
  commentForm: FormGroup;
  id: string;

  constructor(private articleService: ArticleService,
              private route: ActivatedRoute,
              private formBuilder: FormBuilder,
  ) {}

  ngOnInit() {
    this.route.params.subscribe(params => {
       this.id = params['id'];
    });
    console.log(this.id);
    this.articleService.getArticleById(this.id).subscribe(response => {
      console.log(response);
      this.article = response;

      const converter = new Converter();
      this.article.content = converter.makeHtml(this.article.content);
    });
    this.commentForm = this.formBuilder.group({
      comment: ''
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

  public insertComment() {
    this.articleService.insertComment(this.article.id, this.commentForm).subscribe(response => {
      console.log('success', response);
    });
    this.commentForm = this.formBuilder.group({
      comment: ''
    });
    setTimeout(() => {
      this.articleService.getArticleById(this.id).subscribe(response => {
        console.log(response);
        this.article = response;
      });
    }, 10);
  }
}
