import {Component, OnInit} from '@angular/core';
import {MatIconRegistry} from '@angular/material/icon';
import {DomSanitizer} from '@angular/platform-browser';
import {AppUserDTO} from '../core/dto/dtos';
import {SessionService} from '../core/service/session.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.less']
})
export class HeaderComponent implements OnInit {

  currentUser: AppUserDTO;

  constructor(private matIconRegistry: MatIconRegistry,
              private domSanitizer: DomSanitizer,
              private router: Router,
              private sessionService: SessionService) {
  }

  ngOnInit(): void {
    this.sessionService.getCurrentUser()?.subscribe(user => this.currentUser = user);

    const obs = {
      next: id => this.sessionService.getCurrentUser().subscribe(user => this.currentUser = user),
      error: () => {},
      complete: () => {},
    };
    this.sessionService.subscribeLoginEvents(obs);

    this.matIconRegistry.addSvgIcon(
      'publisher_logo',
      this.domSanitizer.bypassSecurityTrustResourceUrl(
        '../assets/img/publisher_logo.svg')
    );
  }

  public logout() {
    sessionStorage.removeItem(SessionService.SESSION_KEY);
    this.currentUser = null;
  }
}
