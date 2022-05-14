import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Airline} from "./airline";

@Injectable({
  providedIn: 'root'
})
export class AirlineserviceService {

  private path: string ='api/v1/airline/';

  constructor(public http: HttpClient) { }

  public findById(id: number) {
    return this.http.get<Airline>(`${this.path}${id}`);
  }

  public save(airline: Airline){
    if (airline.id == null){
     return this.http.post<Airline>(this.path, airline);
    }
    return this.http.put<Airline>(this.path, airline);
  }
}
