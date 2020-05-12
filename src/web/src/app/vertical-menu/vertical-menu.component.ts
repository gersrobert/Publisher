import {Component, OnInit} from '@angular/core';
import {AppuserService} from '../core/service/appuser.service';
import {SessionService} from '../core/service/session.service';
import {TranslateService} from '@ngx-translate/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-vertical-menu',
  templateUrl: './vertical-menu.component.html',
  styleUrls: ['./vertical-menu.component.less']
})
export class VerticalMenuComponent implements OnInit {

  showMenu = true;
  isWriter = false;
  languages = [];
  currentLanguage;

  constructor(private appuserService: AppuserService,
              private sessionService: SessionService,
              private router: Router,
              public translate: TranslateService) {
    this.router.events.subscribe((event: any) => {
      if (event.routerEvent) {
        this.showMenu = !(event.routerEvent?.url === '/home/login' || event.routerEvent?.url === '/home/register');
        this.ngOnInit();
      }
    });
  }

  ngOnInit(): void {
    this.appuserService.getAppUser(this.sessionService.getSession())?.subscribe(response => {
      console.log(response.roles);
      if (response.roles.indexOf('writer') > -1) {
        this.isWriter = true;
      }
    });

    this.languages = [
      {
        code: 'en',
        label: 'English'
      },
      {
        code: 'sk',
        label: 'Slovensky'
      },
    ];
    if (!this.currentLanguage) {
      this.currentLanguage = this.languages[0];
    }
  }

  public setLanguage(language) {
    this.currentLanguage = language;
    this.translate.use(language.code);
  }

  public logout() {
    sessionStorage.removeItem(SessionService.SESSION_KEY);
  }
}
