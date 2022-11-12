import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  private path: string = 'api/v1/user_list/';

  constructor(public http: HttpClient) {
  }

  public findAll() {
    return this.http.get<string[]>(`${this.path}`);
  }
}
