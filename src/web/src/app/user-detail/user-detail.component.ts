import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AppuserService} from '../service/appuser.service';
import {AppUserDetailedDTO, ArticleSimpleDTO} from '../dto/dtos';
import {ArticleService} from '../service/article.service';

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.less']
})
export class UserDetailComponent implements OnInit {
  userId: string;
  appUser: AppUserDetailedDTO;
  isWriter = false;

  constructor(private appUserService: AppuserService,
              private articleService: ArticleService,
              public router: Router,
              private route:ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.userId = params['id'];
    });

    this.appUserService.getAppUser(this.userId).subscribe(response => {
      this.appUser = response;

      if (this.appUser.roles.indexOf('writer') > -1) {
        this.isWriter = true;
      }
    });

  }
}
