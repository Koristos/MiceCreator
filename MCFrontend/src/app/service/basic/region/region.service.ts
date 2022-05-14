import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Region} from "./region";

@Injectable({
  providedIn: 'root'
})
export class RegionService {

  private path: string = 'api/v1/region/';

  constructor(public http: HttpClient) { }

  public findById(id: number) {
    return this.http.get<Region>(`${this.path}${id}`);
  }

  public save(region: Region){
    if (region.id == null){
      return this.http.post<Region>(this.path, region);
    }
    return this.http.put<Region>(this.path, region);
  }
}
