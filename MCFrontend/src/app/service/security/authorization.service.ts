import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {LoginRequest} from "./login-request";
import {User} from "./user";

@Injectable({
  providedIn: 'root'
})
export class AuthorizationService {
  private path: string = 'api/auth';

  constructor(public http: HttpClient) {
  }

  login(username: string, password: string) {
    return this.http.post<User>(this.path + "/login", new LoginRequest(username, password));
  }

  logout() {
    this.http.post(this.path + '/logout', {}).subscribe(result => console.log(result));
  }
}

