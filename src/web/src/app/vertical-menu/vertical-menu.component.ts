import { Component, OnInit } from '@angular/core';
import {AppuserService} from '../service/appuser.service';
import {SessionService} from '../service/session.service';

@Component({
  selector: 'app-vertical-menu',
  templateUrl: './vertical-menu.component.html',
  styleUrls: ['./vertical-menu.component.less']
})
export class VerticalMenuComponent implements OnInit {
  constructor(private appuserService: AppuserService,
              private sessionService: SessionService
  ) {}

  isWriter = false;

  ngOnInit(): void {
    this.appuserService.getAppUser(this.sessionService.getSession()).subscribe( response => {
      console.log(response.roles);
      if (response.roles.indexOf('writer') > -1) {
        this.isWriter = true;
      }
    });
  }
}
