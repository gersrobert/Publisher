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
    const body = {
      title: this.articleForm.value['title'],
      content: this.articleForm.value['content'],
      authors: [this.sessionService.getSession()]
    };

    console.log(body);

    const headers = new HttpHeaders({
      'Content-Type':  'application/json',
      'Accept': 'application/json'
    });

    this.httpClient.post(environment.ROOT_URL + '/article/insert', body, {headers}).subscribe((res:Response) => {
      console.log(res.headers);
    });
  }

}
