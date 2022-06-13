import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {HotelEvent} from "./hotelevent";

@Injectable({
  providedIn: 'root'
})
export class HotelEventService {

  private path: string = 'api/v1/hotel_event/';

  constructor(public http: HttpClient) {
  }

  public findById(id: number) {
    return this.http.get<HotelEvent>(`${this.path}${id}`);
  }

  public findByTourId(id: number) {
    return this.http.get<HotelEvent>(`${this.path}by_tour/${id}`);
  }

  public findByParams(params: HttpParams) {
    return this.http.get<HotelEvent[]>(`${this.path}by_params`, {params: params});
  }

  public save(hotelEvent: HotelEvent) {
    if (hotelEvent.id == null) {
      return this.http.post<HotelEvent>(this.path, hotelEvent);
    }
    return this.http.put<HotelEvent>(this.path, hotelEvent);
  }

  public delete(id: number) {
    return this.http.delete<boolean>(`${this.path}${id}`);
  }

}
