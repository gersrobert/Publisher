import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {AppuserService} from '../service/appuser.service';
import {AppUserDTO} from '../dto/dtos';

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.less']
})
export class UserDetailComponent implements OnInit {
  userId: string;
  appUser: AppUserDTO;

  constructor(private appUserService: AppuserService,
              private route:ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.userId = params['id'];
    });

    this.appUserService.getAppUser(this.userId).subscribe(response => this.appUser = response);
  }
}
