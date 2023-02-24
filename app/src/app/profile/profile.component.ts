import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { interval, Subscription } from 'rxjs';
import { AuthService } from '../service/auth.service';
import { KeyService } from '../service/key.service';
import { ThemeService } from '../service/theme.service';

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

  state: number = 0;

  constructor(private key: KeyService, private http: HttpClient, private theme: ThemeService, private router: Router, private auth: AuthService){}

  get Theme(): number {
    return this.theme.currentTheme();
  }
  subscription: Subscription | any;

  onSubmit(): void{
    this.state = 1;
    //requisição nome
    this.http.put<any>("http://localhost:8080/profile/update",{key: this.key.key, firstname: this.profile.name[0], lastname: this.profile.name[1]})
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
