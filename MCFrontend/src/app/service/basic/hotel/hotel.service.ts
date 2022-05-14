import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Hotel} from "./hotel";

@Injectable({
  providedIn: 'root'
})
export class HotelService {

  private path: string = 'api/v1/hotel/';

  constructor(public http: HttpClient) { }

  public findById(id: number) {
    return this.http.get<Hotel>(`${this.path}${id}`);
  }

  public save(hotel: Hotel){
    if (hotel.id == null){
      return this.http.post<Hotel>(this.path, hotel);
    }
    return this.http.put<Hotel>(this.path, hotel);
  }
}
