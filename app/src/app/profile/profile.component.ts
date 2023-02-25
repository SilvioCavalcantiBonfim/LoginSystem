import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { interval, Subscription } from 'rxjs';
import { AuthService } from '../service/auth.service';
import { KeyService } from '../service/key.service';
import { ThemeService } from '../service/theme.service';
import * as shajs from 'sha.js';

interface profileInterface{
  name: string[],
  email: string
}

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit{
  profile: profileInterface = {name: ['', ''], email: ''}

  password: string[] = ['',''];

  state: number = 0;

  constructor(private key: KeyService, private http: HttpClient, private theme: ThemeService, private router: Router, private auth: AuthService){}

  get Theme(): number {
    return this.theme.currentTheme();
  }
  subscription: Subscription | any;

  onSubmit(): void{
    this.state = 1;
    //update password
    if(this.password.indexOf('') === -1 && this.password[0] === this.password[1])
    this.http.put<any>("http://localhost:8080/profile/update/password",{key: this.key.key, password:  shajs('sha256').update(this.password[0]).digest('hex')})
    .subscribe({
      error: () => {
        this.auth.logout();  
      },
      complete: ()=> {
        this.password = ['',''];
      }
    })
    //requisição nome
    this.http.put<any>("http://localhost:8080/profile/update",{key: this.key.key, firstname: this.profile.name[0], lastname: this.profile.name[1], email: this.profile.email})
    .subscribe({
      error: () => {
        this.auth.logout();  
      },
      complete: ()=> {
        this.state = 2;
        const inter = interval(1000);
        this.subscription = inter.subscribe(() => {
          this.state = 0;
          this.subscription.unsubscribe();
        })
      }
    })
    
  }

  onRevoke(): void{
    this.http.put<any>('http://localhost:8080/auth/revoke',{key: this.key.key}).subscribe({
      error: () => {
        this.auth.logout();
      },
      complete: () => {
        alert("All keys successfully revoked.");
        this.auth.logout();
      }
    });
  }

  onLogout(): void{
    this.auth.logout();
  }

  ngOnInit(): void {
    const headerDict = {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'Access-Control-Allow-Headers': 'Content-Type',
      'key': this.key.key
    }

    this.http.get<any>('http://localhost:8080/profile', {headers: new HttpHeaders(headerDict)}).subscribe({
      next: (r) => {
        this.profile = r;
      },
      error: (error) => {
        this.auth.logout();
      }
    });
  }
}
