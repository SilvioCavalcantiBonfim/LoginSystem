import { Component } from '@angular/core';
import * as shajs from 'sha.js';
import { HttpClient } from '@angular/common/http';
import { interval, Subscription } from 'rxjs';
import { ThemeService } from '../service/theme.service';
import { KeyService } from '../service/key.service';
import {AuthService} from '../service/auth.service';

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

  error: boolean[] = [false,false];

  state: number = 0;

  constructor(private theme: ThemeService, private http: HttpClient,private auth: AuthService) { }

  onTheme = () => this.theme.ToogleTheme();

  get Theme(): number {
    return this.theme.currentTheme();
  }


  onSubmit(): void {
    this.error = [this.email === "", this.password === ""]
    if(this.error.indexOf(true) !== -1){
      return
    }
    this.state = 1;
    this.auth.login(this.email,this.password,this.persist,() => {
      this.error = [true,true];      
      this.state = 0;
    });
  }
}
