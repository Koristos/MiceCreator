import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Accommodation} from "./accommodation";

@Injectable({
  providedIn: 'root'
})
export class AccommodationService {

  private path: string = 'api/v1/accommodation/';

  constructor(public http: HttpClient) {
  }

  public findById(id: number) {
    return this.http.get<Accommodation>(`${this.path}${id}`);
  }

  public findByTourId(id: number) {
    return this.http.get<Accommodation>(`${this.path}by_tour/${id}`);
  }

  public findByParams(params: HttpParams) {
    return this.http.get<Accommodation[]>(`${this.path}by_params`, {params: params});
  }

  public save(accomm: Accommodation) {
    console.log(accomm);
    if (accomm.id == null) {
      return this.http.post<Accommodation>(this.path, accomm);
    }
    return this.http.put<Accommodation>(this.path, accomm);
  }
}
