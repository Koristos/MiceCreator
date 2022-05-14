import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {HotelServ} from "./hotelserv";

@Injectable({
  providedIn: 'root'
})
export class HotelServService {

  private path: string = 'api/v1/hotel_service/';

  constructor(public http: HttpClient) { }

  public findById(id: number) {
    return this.http.get<HotelServ>(`${this.path}${id}`);
  }

  public save(hotelServ: HotelServ){
    if (hotelServ.id == null){
      return this.http.post<HotelServ>(this.path, hotelServ);
    }
    return this.http.put<HotelServ>(this.path, hotelServ);
  }
}
