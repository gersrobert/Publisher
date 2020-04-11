import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SessionService} from '../service/session.service';
import {Router} from '@angular/router';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {ArticleService} from '../service/article.service';

@Component({
  selector: 'app-article-insert',
  templateUrl: './article-insert.component.html',
  styleUrls: ['./article-insert.component.less']
})
export class ArticleInsertComponent implements OnInit {
  articleForm: FormGroup;
  insertArticleError = false;

  constructor(private formBuilder: FormBuilder,
              private sessionService: SessionService,
              private router: Router,
              private httpClient: HttpClient,
              private articleService: ArticleService) {
  }

  ngOnInit(): void {
    this.articleForm = this.formBuilder.group({
      title: '',
      categories: '',
      content: ''
    });
  }

  public onSubmit() {
  this.articleService.insertArticle(this.articleForm).subscribe(response => {
      console.log('success', response);
      this.router.navigate(['home/articleList']);
    }, error => {
      console.log('error', error);
      this.insertArticleError = true;
    });
  }
}
