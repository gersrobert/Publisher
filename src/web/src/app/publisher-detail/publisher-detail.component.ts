import { Component, OnInit } from '@angular/core';
import {Publisher} from '../core/dto/dtos';
import {ActivatedRoute} from '@angular/router';
import {PublisherService} from '../core/service/publisher.service';

@Component({
  selector: 'app-publisher-detail',
  templateUrl: './publisher-detail.component.html',
  styleUrls: ['./publisher-detail.component.less']
})
export class PublisherDetailComponent implements OnInit {
  publisher: Publisher;

  constructor(private route: ActivatedRoute,
              private publisherService: PublisherService
  ) {}


  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.publisherService.getRowOfPublisher(params['id']).subscribe(
        response => this.publisher = response);
    });
  }

}
