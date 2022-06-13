import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Flight} from "./flight";

@Injectable({
  providedIn: 'root'
})
export class FlightService {

  private path: string = 'api/v1/flight/';

  constructor(public http: HttpClient) {
  }

  public findById(id: number) {
    return this.http.get<Flight>(`${this.path}${id}`);
  }

  public findByTourId(id: number) {
    return this.http.get<Flight>(`${this.path}by_tour/${id}`);
  }

  public findByParams(params: HttpParams) {
    return this.http.get<Flight[]>(`${this.path}by_params`, {params: params});
  }

  public save(flight: Flight) {
    if (flight.id == null) {
      return this.http.post<Flight>(this.path, flight);
    }
    return this.http.put<Flight>(this.path, flight);
  }

  public delete(id: number) {
    return this.http.delete<boolean>(`${this.path}${id}`);
  }
}
