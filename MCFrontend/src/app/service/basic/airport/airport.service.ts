import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Airport} from "./airport";

@Injectable({
  providedIn: 'root'
})
export class AirportService {

  private path: string ='api/v1/airport/';

  constructor(public http: HttpClient) { }

  public findById(id: number) {
    return this.http.get<Airport>(`${this.path}${id}`);
  }

  public save(airport: Airport){
    if (airport.id == null){
      return this.http.post<Airport>(this.path, airport);
    }
    return this.http.put<Airport>(this.path, airport);
  }
}
