import {Injectable} from "@angular/core";
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse} from "@angular/common/http";
import {catchError, map, throwError} from "rxjs";
import {AppComponent} from "../../app.component";
import {Router} from "@angular/router";

const TOKEN_HEADER_KEY = 'Authorization';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  private token: any = "";

  constructor(private router: Router) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    let authReq = req;
    this.token = localStorage.getItem("MC_TOKEN");
    if (this.token != null && this.token.length != 0 && !req.url.toString().includes("api/auth/login")) {
      authReq = req.clone({
        headers: req.headers.set(TOKEN_HEADER_KEY, 'Bearer ' + this.token)
      });
    }
    return next.handle(authReq).pipe(
      catchError((error: HttpErrorResponse) => {
        console.log(error);
        console.log("error");
        if (error.status == 401) {
          localStorage.removeItem("MC_TOKEN");
          localStorage.removeItem("MC_USER");
          this.router.navigateByUrl("/login");
        }
        return throwError(error.message);
      }));
  }
}

