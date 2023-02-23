import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {
  private theme: number = 0;
  constructor() {
    try {
      this.theme = Number(localStorage.getItem("theme"));
    } catch (error) {
      console.log(error)
    }
  }

  currentTheme(): number{
    return this.theme;
  }

  ToogleTheme(): void{
    this.theme = 1 - this.theme;
    localStorage.setItem("theme",String(this.theme));
  }
}
