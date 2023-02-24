import { Injectable } from '@angular/core';
import { KeyService } from './key.service';
import * as shajs from 'sha.js';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private isLogged: boolean;

  constructor(private key: KeyService, private http: HttpClient, private router: Router) {
    this.isLogged = (this.key.key !== null);
  }

  get isAuth(): boolean{
    this.isLogged = (this.key.key !== null);
    return this.isLogged;
  }

  logout():void {
    this.key.delete();
    this.router.navigateByUrl("/login");
  }

  login(_email: string, _password: string, _persist: boolean, error?: () => void) {
    this.key.delete();
    const body = { "email": _email, "password": shajs('sha256').update(_password).digest('hex') };
    this.http.post<any>('http://localhost:8080/auth', body).subscribe(
      {
        next: ({key}) => {
          this.key.save(key, _persist);
          this.router.navigateByUrl('/profile');
        },
        error: (error !==undefined)?error:(e) => {},
        complete:() => {
            this.isLogged = this.key.key !== null;
          }
      }
    );
  }
}
