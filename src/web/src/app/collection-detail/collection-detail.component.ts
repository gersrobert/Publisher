import {Component, Input, OnInit} from '@angular/core';
import {ArticleSimpleListDTO, CollectionDTO} from '../core/dto/dtos';
import {CollectionService} from '../core/service/collection.service';
import {ActivatedRoute, Router} from '@angular/router';
import {SessionService} from '../core/service/session.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {TranslateService} from '@ngx-translate/core';
import {MatSnackBar} from '@angular/material/snack-bar';
import {Observable} from 'rxjs';

@Component({
  selector: 'app-collection-detail',
  templateUrl: './collection-detail.component.html',
  styleUrls: ['./collection-detail.component.less']
})
export class CollectionDetailComponent implements OnInit {

  editMode = false;
  isOwner = false;

  id: string;

  collection: CollectionDTO;
  headerForm: FormGroup;

  constructor(private collectionService: CollectionService,
              private sessionService: SessionService,
              private formBuilder: FormBuilder,
              private translate: TranslateService,
              private snackbar: MatSnackBar,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.id = params['id'];

      let observable: Observable<CollectionDTO>;
      if (this.id) {
        observable = this.collectionService.getCollectionDetail(this.id);
      } else {
        observable = new Observable<CollectionDTO>((observer) => {
          if (window.history.state.editMode) {
            this.editMode = true;
            observer.next(window.history.state.collection);
          }
          observer.complete();
        });
      }

      observable.subscribe(result => {
        this.collection = result;

        if (this.collection.author.id === this.sessionService.getSession()) {
          this.isOwner = true;
        }

        this.headerForm = this.formBuilder.group({
          title: this.collection.title,
          description: this.collection.description
        });
      });
    });
  }

  public onSubmit() {
    if (!this.editMode) {
      this.editMode = true;
      return;
    }

    this.collection.title = this.headerForm.get('title').value;
    this.collection.description = this.headerForm.get('description').value;
    if (!this.collection.id) {
      this.collectionService.insertCollection(this.collection).subscribe(
        result => {
          this.router.navigate(['/home/collectionDetail/' + result.id]);
        },
        error => {
          this.translate.get(error.error).subscribe(value => {
            this.snackbar.open(value, null, {duration: 3000, panelClass: ['snackbar-error']});
          });
        });
    }
  }
}
