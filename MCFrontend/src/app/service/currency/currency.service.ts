import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ShortForm} from "../basic/shortform";

@Injectable({
  providedIn: 'root'
})
export class CurrencyService {

  private path: string = 'api/v1/currency/';

  constructor(public http: HttpClient) {
  }

  public findAll() {
    return this.http.get<ShortForm[]>(`${this.path}`);
  }
}
