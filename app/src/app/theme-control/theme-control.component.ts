import { Component } from '@angular/core';
import { ThemeService } from '../service/theme.service';

@Component({
  selector: 'app-theme-control',
  templateUrl: './theme-control.component.html',
  styleUrls: ['./theme-control.component.css']
})
export class ThemeControlComponent {
  constructor(public theme: ThemeService){}
}
