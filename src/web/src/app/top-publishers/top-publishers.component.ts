import { Component, OnInit } from '@angular/core';
import {Publisher} from '../core/dto/dtos';
import {PublisherService} from '../core/service/publisher.service';

@Component({
  selector: 'app-top-publishers',
  templateUrl: './top-publishers.component.html',
  styleUrls: ['./top-publishers.component.less']
})
export class TopPublishersComponent implements OnInit {
  publishers: Publisher[];

  constructor(private publisherService: PublisherService) {}

  ngOnInit(): void {
    this.publisherService.getTopPublishers().subscribe(response => {
      this.publishers = response;
    });
  }

}
