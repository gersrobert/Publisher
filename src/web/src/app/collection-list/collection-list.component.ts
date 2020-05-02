import {Component, OnInit} from '@angular/core';
import {CollectionDTO} from '../core/dto/dtos';
import {CollectionService} from '../core/service/collection.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-collection-list',
  templateUrl: './collection-list.component.html',
  styleUrls: ['./collection-list.component.less']
})
export class CollectionListComponent implements OnInit {

  collections: CollectionDTO[];

  constructor(private collectionService: CollectionService,
              public router: Router) {
  }

  ngOnInit(): void {
    this.collectionService.getCollections().subscribe(result => this.collections = result);
  }

}
