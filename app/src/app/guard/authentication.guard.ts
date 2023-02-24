import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../service/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationGuard implements CanActivate {
  constructor(private auth: AuthService, private router: Router){}
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      switch (state.url) {
        case '/profile':
          return this.auth.isAuth || this.router.createUrlTree(['/login']);
          break;
        case '/login':
          return !this.auth.isAuth || this.router.createUrlTree(['/profile']);
          break
        default:
          break;
      }
      return true;
  }
  
}
