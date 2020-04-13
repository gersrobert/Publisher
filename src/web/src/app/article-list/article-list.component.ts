import {Component, OnInit} from '@angular/core';
import {ArticleSimpleDTO, ArticleSimpleListDTO} from '../dto/dtos';
import {ArticleService} from '../service/article.service';
import {Router} from '@angular/router';
import {FormBuilder, FormGroup} from '@angular/forms';
import {Observable} from 'rxjs';

@Component({
  selector: 'app-article-list',
  templateUrl: './article-list.component.html',
  styleUrls: ['./article-list.component.less']
})
export class ArticleListComponent implements OnInit {

  numberOfArticles = 0;
  articles: ArticleSimpleDTO[];
  titleName: string;

  readonly pageSize = 50;
  lower = 0;
  upper = this.pageSize;

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

    let request: Observable<ArticleSimpleListDTO>;
    if (this.filterFormGroup.get('title').value.length > 2 ||
      this.filterFormGroup.get('author').value.length > 2 ||
      this.filterFormGroup.get('category').value.length > 2 ||
      this.filterFormGroup.get('publisher').value.length > 2) {

      request = this.articleService.getFilteredArticles(
        this.filterFormGroup.get('title').value,
        this.filterFormGroup.get('author').value,
        this.filterFormGroup.get('category').value,
        this.filterFormGroup.get('publisher').value,
        this.lower, this.upper);

    } else {
      request = this.articleService.getArticlesInRange(this.lower, this.upper);
    }

    request.subscribe(response => {
      if (response.numberOfArticles > 0 && this.lower >= response.numberOfArticles) {
        this.upper = this.pageSize;
        this.lower = 0;
        this.updateArticles(0);
        return;
      }

      this.articles = response.articles;
      this.numberOfArticles = response.numberOfArticles;
      console.log(this.articles);
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
