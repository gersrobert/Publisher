import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AppUserDTO, ArticleDetailedDTO, ArticleSimpleDTO, CollectionDTO} from '../core/dto/dtos';
import {ArticleService} from '../core/service/article.service';
import {Converter} from 'showdown';
import {FormBuilder, FormGroup} from '@angular/forms';
import {AppuserService} from '../core/service/appuser.service';
import {CollectionService} from '../core/service/collection.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {TranslateService} from '@ngx-translate/core';
import {SessionService} from '../core/service/session.service';
import {DomSanitizer, SafeResourceUrl} from '@angular/platform-browser';

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
    downloadPdf: {
      label: 'Download PDF',
      action: 'downloadPdf',
    },
  };

  @ViewChild('downloadPdfLink')
  downloadPdfLink;
  pdfFileUrl: SafeResourceUrl;

  collections: CollectionDTO[] = [];

  constructor(private articleService: ArticleService,
              private appuserService: AppuserService,
              private collectionService: CollectionService,
              private sessionService: SessionService,
              private route: ActivatedRoute,
              private formBuilder: FormBuilder,
              private snackbar: MatSnackBar,
              private router: Router,
              public translate: TranslateService,
              private sanitizer: DomSanitizer
  ) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.id = params['id'];
    });
    this.articleService.getArticleById(this.id).subscribe(response => {
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
        } else if (item === 'downloadPdf') {
          this.actions.push(this.actionList.downloadPdf);
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
    } else if (action === 'downloadPdf') {
      this.downloadPdf();
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
      result => {
      },
      error => {
        this.translate.get(error.error).subscribe(value => {
          this.snackbar.open(value, null, {duration: 3000, panelClass: ['snackbar-error']});
        });
      }
    );
  }

  public newCollection() {
    this.sessionService.getCurrentUser().subscribe(result => {
      const col = {
        articles: [this.article],
        author: result
      };

      this.router.navigate(['/home/collectionDetail'], {
        state: {
          editMode: true,
          collection: col
        }
      });
    });
  }

  public downloadPdf() {
    const data = this.article.title; // TODO call backend
    const blob = new Blob([data], { type: 'application/octet-stream' });
    this.pdfFileUrl = this.sanitizer.bypassSecurityTrustResourceUrl(window.URL.createObjectURL(blob));
    this.downloadPdfLink.nativeElement.click();
  }
}

