import {Component, OnInit} from '@angular/core';
import {CollectionDTO} from '../core/dto/dtos';
import {CollectionService} from '../core/service/collection.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-collection-detail',
  templateUrl: './collection-detail.component.html',
  styleUrls: ['./collection-detail.component.less']
})
export class CollectionDetailComponent implements OnInit {

  id: string;
  collection: CollectionDTO;

  constructor(private collectionService: CollectionService,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.id = params['id'];
    });

    this.collectionService.getCollectionDetail(this.id).subscribe(result => {
      this.collection = result;
    });
  }

}
