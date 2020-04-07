import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import {SessionService} from '../service/session.service';

@Injectable()
export class LoginRedirect implements CanActivate {

  constructor(private sessionService: SessionService, private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (route.routeConfig.path !== 'home/login' && this.sessionService.getSession() === null) {
      this.router.navigate(['/home/login']);
      return false;
    }

    return true;
  }
}
