import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AppUserDTO, ArticleDetailedDTO, ArticleSimpleDTO, CollectionDTO} from '../core/dto/dtos';
import {ArticleService} from '../core/service/article.service';
import {Converter} from 'showdown';
import {FormBuilder, FormGroup} from '@angular/forms';
import {AppuserService} from '../core/service/appuser.service';
import {CollectionService} from '../core/service/collection.service';

@Component({
  selector: 'app-article-detail',
  templateUrl: './article-detail.component.html',
  styleUrls: ['./article-detail.component.less']
})
export class ArticleDetailComponent implements OnInit {
  article: ArticleDetailedDTO;
  commentForm: FormGroup;
  id: string;

  @ViewChild('collectionsMenu')
  set collectionsMenu(collectionsMenu) {
    this.actionList.addToCollection.triggerMenu = collectionsMenu;
  }

  actions: any;
  private actionList = {
    addToCollection: {
      label: 'Add to Collection',
      action: 'addToCollection',
      triggerMenu: undefined
    },
    edit: {
      label: 'Edit',
      action: 'edit',
    },
    delete: {
      label: 'Delete',
      action: 'delete',
    },
  };

  collections: CollectionDTO[] = [];

  constructor(private articleService: ArticleService,
              private appuserService: AppuserService,
              private collectionService: CollectionService,
              private route: ActivatedRoute,
              private formBuilder: FormBuilder,
              private router: Router
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
      console.log(this.actionList);
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
      this.router.navigate(['home/updateArticle/' + this.article.id]);
    } else if (action === 'delete') {
      this.articleService.deleteArticle(this.article.id).subscribe(response => {
          this.router.navigate(['home/articleList']);
          console.log('Successful');
        }, error => {
          console.log('Couldn\'t delete article');
        }
      );
    } else if (action === 'addToCollection') {
      this.collectionService.getCollectionForUser().subscribe(result => this.collections = result);
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
    }, 100);
  }

  public assignToCollection(collection: CollectionDTO) {
    console.log('assign');
    this.collectionService.assignToCollection(this.article.id, collection.id).subscribe(
      result => {},
      error => console.log(error.error)
      );
  }
}
