import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {User} from "./service/security/user";
import {Subject} from "rxjs";
import {AuthorizationService} from "./service/security/authorization.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'MCFrontend';
  public getUserName = new Subject<string>();
  public getAuthenticated = new Subject<boolean>();
  public authenticated: boolean = false;
  public user: User = new User("", [""], "");

  constructor(private router: Router,
              private authorizationService: AuthorizationService) {
  }

  ngOnInit(): void {
    this.loginCheck();
  }

  loginCheck() {
    if (!localStorage.getItem("MC_TOKEN") || !localStorage.getItem("MC_USER")) {
      this.authenticated = false;
      this.user = new User("", [""], "");
      this.router.navigateByUrl("/login");
    } else {
      this.user = JSON.parse(localStorage.getItem("MC_USER") || 'new User("", [""], "")');
      if (!this.authenticated) {
        this.authenticated = true;
      }
    }
    this.getUserName.next(this.user.name);
    this.getAuthenticated.next(this.authenticated);
  }

  logout() {
    localStorage.removeItem("MC_TOKEN");
    localStorage.removeItem("MC_USER");
    this.authorizationService.logout();
    this.loginCheck();
  }

}
