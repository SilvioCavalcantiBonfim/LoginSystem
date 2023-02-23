import { Component } from '@angular/core';
import * as shajs from 'sha.js';
import { HttpClient } from '@angular/common/http';
import { interval, Subscription } from 'rxjs';
import { ThemeService } from '../service/theme.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {


  subscription: Subscription | any;

  email: string = "";

  password: string = "";

  persist: boolean = false;

  state: number = 0;

  constructor(private theme: ThemeService, private http: HttpClient) { }

  onTheme = () => this.theme.ToogleTheme();

  get Theme(): number {
    return this.theme.currentTheme();
  }


  onSubmit(): void {
    const body = { "email": this.email, "password": shajs('sha256').update(this.password).digest('hex') }
    console.log(body);
    this.state = 1;
    this.http.post<any>('http://localhost:8080/auth', body).subscribe(r => {
      if ('error' in r) {
        this.state = 2;
        const inter = interval(1000);
        this.subscription = inter.subscribe(() => {
          this.state = 0;
          this.subscription.unsubscribe();
        })
      }else{
        this.state = 3;
      }

    });
  }
}
