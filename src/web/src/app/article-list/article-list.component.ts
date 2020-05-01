import {Component, Input, OnInit} from '@angular/core';
import {AppUserDetailedDTO, AppUserDTO, ArticleSimpleDTO, ArticleSimpleListDTO, CollectionDTO} from '../core/dto/dtos';
import {ArticleService} from '../core/service/article.service';
import {Router} from '@angular/router';
import {FormBuilder, FormGroup} from '@angular/forms';
import {Observable} from 'rxjs';
import {CollectionService} from '../core/service/collection.service';

@Component({
  selector: 'app-article-list',
  templateUrl: './article-list.component.html',
  styleUrls: ['./article-list.component.less']
})
export class ArticleListComponent implements OnInit {

  @Input()
  writer: AppUserDetailedDTO;

  @Input()
  collection: CollectionDTO;

  @Input()
  enablePaging = true;

  @Input()
  enableFilter = true;

  hasMore = true;
  articles: ArticleSimpleDTO[];

  readonly pageSize = 10;
  pageIndex = 1;

  filterLabel = 'Filter';
  showFilter = false;
  filterFormGroup: FormGroup;

  loading = false;

  constructor(private articleService: ArticleService,
              private collectionService: CollectionService,
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
    if (this.getLower() + scrollCount * this.pageSize >= 0 && (scrollCount < 0 || this.hasMore)) {
      this.pageIndex += scrollCount;
    } else {
      return;
    }

    let receivedResponse = false;
    setTimeout(() => {
      if (!receivedResponse) {
        this.loading = true;
      }
    }, 100);

    console.log('updating articles');
    let request: Observable<ArticleSimpleListDTO>;
    if (this.writer != null) {
      request = this.filterByAuthor();
    } else if (this.collection != null) {
      request = new Observable<ArticleSimpleListDTO>((observer) => {
        const val = {
          articles: this.collection.articles,
          hasMore: false
        };
        observer.next(val);
        observer.complete();
      });
    } else if (this.filterFormGroup.get('title').value.length > 2 ||
      this.filterFormGroup.get('author').value.length > 2 ||
      this.filterFormGroup.get('category').value.length > 2 ||
      this.filterFormGroup.get('publisher').value.length > 2) {
      request = this.filterByCriteria();
    } else {
      request = this.filterByPage();
    }

    request.subscribe(response => {
      receivedResponse = true;
      this.articles = response.articles;
      this.hasMore = response.hasMore;
      console.log(this.articles);

      this.loading = false;
    });
  }

  private filterByAuthor() {
    return this.articleService.getArticlesByAuthor(this.writer.id, this.getLower(), this.getUpper());
  }

  private filterByCriteria() {
    return this.articleService.getFilteredArticles(
      this.filterFormGroup.get('title').value,
      this.filterFormGroup.get('author').value,
      this.filterFormGroup.get('category').value,
      this.filterFormGroup.get('publisher').value,
      this.getLower(), this.getUpper());
  }

  private filterByPage() {
    return this.articleService.getArticlesInRange(this.getLower(), this.getUpper());
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
