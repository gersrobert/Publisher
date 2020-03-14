import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {ArticleDetailedDTO, ArticleSimpleDTO} from './dto/dtos';
import {environment} from '../environments/environment';
import {ArticleService} from './service/article.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  ngOnInit(): void {
  }
}
