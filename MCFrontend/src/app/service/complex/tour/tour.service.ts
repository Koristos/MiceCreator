import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Tour} from "./tour";
import {FullTour} from "./full-tour";

@Injectable({
  providedIn: 'root'
})
export class TourService {

  private path: string = 'api/v1/tour/';

  constructor(public http: HttpClient) {
  }

  public findById(id: number) {
    return this.http.get<FullTour>(`${this.path}${id}`);
  }

  public findByParams(params: HttpParams) {
    return this.http.get<Tour[]>(`${this.path}by_params`, {params: params});
  }

  public save(tour: Tour) {
    if (tour.id == null) {
      return this.http.post<Tour>(this.path, tour);
    }
    return this.http.put<Tour>(this.path, tour);
  }
}

