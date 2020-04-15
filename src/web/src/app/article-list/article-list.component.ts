import {Component, Input, OnInit} from '@angular/core';
import {AppUserDetailedDTO, AppUserDTO, ArticleSimpleDTO, ArticleSimpleListDTO} from '../dto/dtos';
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

  @Input() writer: AppUserDetailedDTO;

  hasMore = true;
  articles: ArticleSimpleDTO[];

  readonly pageSize = 10;
  pageIndex = 1;

  @Input()
  enablePaging = true;

  filterLabel = 'Filter';
  showFilter = false;
  filterFormGroup: FormGroup;

  loading = false;

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

    this.updateArticles();
  }

  public toggleFilter() {
    this.showFilter = !this.showFilter;

    if (!this.showFilter) {
      if (this.filterFormGroup.get('title').value.length > 2 ||
        this.filterFormGroup.get('author').value.length > 2 ||
        this.filterFormGroup.get('category').value.length > 2 ||
        this.filterFormGroup.get('publisher').value.length > 2) {

        this.pageIndex = 1;
        this.enablePaging = false;
      } else {
        this.enablePaging = true;
        this.hasMore = true;
      }
      this.updateArticles();
      this.filterLabel = 'Filter';
    } else {
      this.filterLabel = 'Apply Filter';
    }
  }

  public updateArticles(scrollCount: number = 0) {
    if (this.getLower() + scrollCount * this.pageSize >= 0 && this.hasMore) {
      this.pageIndex += scrollCount;
    } else {
      return;
    }

    let receivedReponse = false;
    setTimeout(() => {
      if (!receivedReponse) {
        this.loading = true;
      }
    }, 100);

    console.log('updating articles');
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
        this.getLower(), this.getUpper());

    } else if (this.writer != null) {
      console.log(this.writer.id);
      console.log(this.writer.firstName);

      request = this.articleService.getFilteredArticles(
        this.filterFormGroup.get('title').value,
        this.writer.firstName + ' ' + this.writer.lastName,
        this.filterFormGroup.get('category').value,
        this.filterFormGroup.get('publisher').value,
        this.getLower(), this.getUpper());
    } else {
      request = this.articleService.getArticlesInRange(this.getLower(), this.getUpper());
    }

    request.subscribe(response => {
      receivedReponse = true;
      this.articles = response.articles;
      this.hasMore = response.hasMore;
      console.log(this.articles);

      this.loading = false;
    });
  }

  private getLower() {
    return (this.pageIndex - 1) * this.pageSize;
  }

  private getUpper() {
    return this.pageIndex * this.pageSize;
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
