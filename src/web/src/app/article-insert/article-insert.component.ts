import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SessionService} from '../core/service/session.service';
import {Router} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {ArticleService} from '../core/service/article.service';
import {ArticleDetailedDTO, ArticleSimpleDTO} from '../core/dto/dtos';

@Component({
  selector: 'app-article-insert',
  templateUrl: './article-insert.component.html',
  styleUrls: ['./article-insert.component.less']
})
export class ArticleInsertComponent implements OnInit {

  @Input() updateArticle: ArticleDetailedDTO;
  articleForm: FormGroup;
  insertArticleError = false;

  constructor(private formBuilder: FormBuilder,
              private sessionService: SessionService,
              private router: Router,
              private httpClient: HttpClient,
              private articleService: ArticleService) {
  }

  ngOnInit(): void {
    if (this.updateArticle == null) {
      this.articleForm = this.formBuilder.group({
        id: '',
        title: '',
        categories: '',
        content: ''
      });
    } else {
      let categories = '';

      this.updateArticle.categories.forEach(category => {
        categories += category.name + ', ';
      });
      categories = categories.replace(/, $/, '');
      this.articleForm = this.formBuilder.group({
        id: this.updateArticle.id,
        title: this.updateArticle.title,
        categories: categories,
        content: this.updateArticle.content
      });
      console.log(this.articleForm);
    }
  }

  public onSubmit() {
    this.articleService.insertArticle(this.articleForm).subscribe(response => {
      console.log('success', response);
      this.router.navigate(['home/articleDetail/' + response.id]);
    }, error => {
      console.log('error', error);
    });
  }
}
