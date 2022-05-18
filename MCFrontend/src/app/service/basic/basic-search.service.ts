import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {ShortForm} from "./shortform";

@Injectable({
  providedIn: 'root'
})
export class BasicSearchService {

  private path: string = 'api/v1/basic/';

  constructor(public http: HttpClient) {
  }

  public findAll(type: string) {
    return this.http.get<ShortForm[]>(`${this.path}${type}`);
  }

  public findByParams(type: string, params: HttpParams) {
    return this.http.get<ShortForm[]>(`${this.path}by_params/${type}`, {params: params});
  }

  public findById(type: string, id: number) {
    return this.http.get<ShortForm>(`${this.path}by_id/${type}/${id}`);
  }

  public findParentById(type: string, id: number) {
    return this.http.get<ShortForm>(`${this.path}get_parent/${type}/${id}`);
  }
}
