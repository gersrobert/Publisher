import {Component, OnInit} from '@angular/core';
import {AppUserDTO, ArticleSimpleDTO, ArticleSimpleListDTO} from '../dto/dtos';
import {ArticleService} from '../service/article.service';
import {PageEvent} from '@angular/material/paginator';
import {Router} from '@angular/router';

@Component({
  selector: 'app-article-list',
  templateUrl: './article-list.component.html',
  styleUrls: ['./article-list.component.less']
})
export class ArticleListComponent implements OnInit {

  numberOfArticles: number;
  articles: ArticleSimpleDTO[];
  titleName: string;
  lower = 0;
  upper = 10;

  constructor(private articleService: ArticleService, public router: Router) {
  }

  ngOnInit() {
    this.getArticlesInRange();
  }

  private getArticlesInRange() {
    this.articleService.getArticlesInRange(this.lower, this.upper).subscribe(response => {
      this.articles = response.articles;
      this.numberOfArticles = response.numberOfArticles;
    });
  }

  public updateArticles(num: number) {
    if (this.lower + num >= 0 && this.lower + num < this.numberOfArticles) {
      this.lower += num;
      this.upper += num;
      this.getArticlesInRange();
    }
  }

  public displayUsers(appUsers: AppUserDTO[]): string {
    let retVal = '';

    appUsers.forEach((user, i) => {
      retVal += user.firstName + ' ' + user.lastName;
      if (i !== appUsers.length - 1) {
        retVal += ', ';
      }
    });

    return retVal;
  }

  public likeArticle(article: ArticleSimpleDTO) {
    console.log("likearticle", article);

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
