import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SessionService} from '../service/session.service';
import {Router} from '@angular/router';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {environment} from '../../environments/environment';

@Component({
  selector: 'app-article-insert',
  templateUrl: './article-insert.component.html',
  styleUrls: ['./article-insert.component.css']
})
export class ArticleInsertComponent implements OnInit {
  articleForm: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private sessionService: SessionService,
              private router: Router,
              private httpClient: HttpClient) { }

  ngOnInit(): void {
    this.articleForm = this.formBuilder.group({
      title: '',
      content: ''
    });
  }

  public onSubmit() {
    let title = this.articleForm.value['title'];
    let content = this.articleForm.value['content'];
    let authors = [this.sessionService.getSession()];

    if (title != '' && content != '' && authors[0] != null) {
      const body = {
        title: title,
        content: content,
        authors: authors
      };

      console.log(body);

      const headers = new HttpHeaders({
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      });

      this.httpClient.post(environment.ROOT_URL + '/article/insert', body, {headers}).subscribe((res: Response) => {
        console.log(res.status);
      });
    }
  }

}
