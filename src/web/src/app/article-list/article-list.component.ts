import {Component, OnInit} from '@angular/core';
import {AppUserDTO, ArticleSimpleDTO, ArticleSimpleListDTO} from '../dto/dtos';
import {ArticleService} from '../service/article.service';
import {PageEvent} from '@angular/material/paginator';
import {Router} from '@angular/router';
import {FormBuilder, FormGroup} from '@angular/forms';

@Component({
  selector: 'app-article-list',
  templateUrl: './article-list.component.html',
  styleUrls: ['./article-list.component.less']
})
export class ArticleListComponent implements OnInit {

  numberOfArticles = 0;
  articles: ArticleSimpleDTO[];
  titleName: string;
  lower = 0;
  upper = 10;

  showFilter = false;
  filterFormGroup: FormGroup;

  constructor(private articleService: ArticleService,
              public router: Router,
              private formBuilder: FormBuilder) {
  }

  ngOnInit() {
    this.filterFormGroup = this.formBuilder.group({
      title: '',
      author: '',
      category: '',
      publisher: '',
    });

    this.filterFormGroup.valueChanges.subscribe(value => {
      this.updateArticles();
    });

    this.updateArticles();
  }

  public updateArticles(scrollCount: number = 0) {
    if (this.lower + scrollCount >= 0 && this.lower + scrollCount < this.numberOfArticles) {
      this.lower += scrollCount;
      this.upper += scrollCount;
    }

    this.articleService.getFilteredArticles(
      this.filterFormGroup.get('title').value,
      this.filterFormGroup.get('author').value,
      this.filterFormGroup.get('category').value,
      this.filterFormGroup.get('publisher').value,
      this.lower, this.upper)
      .subscribe(response => {
        this.articles = response.articles;
        this.numberOfArticles = response.numberOfArticles;
      });
  }

  public likeArticle(article: ArticleSimpleDTO) {
    console.log('likearticle', article);

    if (article.liked) {
      this.articleService.unlikeArticle(article.id).subscribe(response => {
        article.likeCount = response;
        article.liked = false;
      });
    } else {
      this.articleService.likeArticle(article.id).subscribe(response => {
        article.likeCount = response;
        article.liked = true;
      });
    }
  }

}
