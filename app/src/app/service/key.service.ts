import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class KeyService {
  
  private _key: string = "SESSION_ID";
  constructor(private cookies: CookieService) { }

  /**
   * delete
   */
  public delete(): void{
    this.cookies.delete(this._key);
  }

  public save(__value: string, __persist?: boolean): void{
    if(__persist === undefined || __persist === false)
      this.cookies.set(this._key, __value);
    else{
      const current: number = Date.now();
      const date = new Date(current + 604800000);
      this.cookies.set(this._key, __value, {expires: date});
    }
  }

  get key(): any{
    const _aux_key = this.cookies.get(this._key);
    return (_aux_key === '')? null: _aux_key;
  }
}
