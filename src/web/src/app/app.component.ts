import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {ArticleDetailedDTO, ArticleSimpleDTO} from './core/dto/dtos';
import {environment} from '../environments/environment';
import {ArticleService} from './core/service/article.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.less']
})
export class AppComponent implements OnInit {
  ngOnInit(): void {
  }
}
