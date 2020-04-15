import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {AppUserDTO, ArticleDetailedDTO, ArticleSimpleDTO} from '../dto/dtos';
import {ArticleService} from '../service/article.service';
import {Converter} from 'showdown';
import {FormBuilder, FormGroup} from '@angular/forms';
import {AppuserService} from '../service/appuser.service';

@Component({
  selector: 'app-article-detail',
  templateUrl: './article-detail.component.html',
  styleUrls: ['./article-detail.component.less']
})
export class ArticleDetailComponent implements OnInit {
  article: ArticleDetailedDTO;
  commentForm: FormGroup;
  id: string;

  actions;
  private readonly actionList = {
    addToCollection: {
      label: 'Add to Collection',
      action: 'addToCollection'
    },
    edit: {
      label: 'Edit',
      action: 'edit'
    },
    delete: {
      label: 'Delete',
      action: 'delete'
    },
  };

  constructor(private articleService: ArticleService,
              private appuserService: AppuserService,
              private route: ActivatedRoute,
              private formBuilder: FormBuilder,
  ) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.id = params['id'];
    });
    console.log(this.id);
    this.articleService.getArticleById(this.id).subscribe(response => {
      console.log(response);
      this.article = response;

      const converter = new Converter();
      this.article.content = converter.makeHtml(this.article.content);

      this.getActions();
    });
    this.commentForm = this.formBuilder.group({
      comment: ''
    });
  }

  private getActions() {
    this.appuserService.getActions(this.id).subscribe(response => {
      this.actions = [];
      for (let item of response) {
        if (item === 'addToCollection') {
          this.actions.push(this.actionList.addToCollection);
        } else if (item === 'edit') {
          this.actions.push(this.actionList.edit);
        } else if (item === 'delete') {
          this.actions.push(this.actionList.delete);
        }
      }
    });
  }

  public actionClick(action) {
    console.log(action);

    if (action === 'edit') {

    } else if (action === 'delete') {

    } else if (action === 'addToCollection') {

    }
  }

  public likeArticle() {
    if (this.article.liked) {
      this.articleService.unlikeArticle(this.article.id).subscribe(response => {
        this.article.likeCount = response;
        this.article.liked = false;
      });
    } else {
      this.articleService.likeArticle(this.article.id).subscribe(response => {
        this.article.likeCount = response;
        this.article.liked = true;
      });
    }
  }

  public insertComment() {
    this.articleService.insertComment(this.article.id, this.commentForm).subscribe(response => {
      console.log('success', response);
    });
    this.commentForm = this.formBuilder.group({
      comment: ''
    });
    setTimeout(() => {
      this.articleService.getArticleById(this.id).subscribe(response => {
        console.log(response);
        this.article = response;
      });
    }, 10);
  }
}
