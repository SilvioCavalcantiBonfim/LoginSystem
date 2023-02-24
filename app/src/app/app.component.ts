import { Component, OnInit } from '@angular/core';
import { KeyService } from './service/key.service';
import { ThemeService } from './service/theme.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent{
  title = 'app';
  constructor(private theme: ThemeService, private key: KeyService){
  }

  get Theme(){
    return this.theme.currentTheme();
  }
}
