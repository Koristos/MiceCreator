import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {RegEvent} from "./regevent";

@Injectable({
  providedIn: 'root'
})
export class RegionEventService {

  private path: string = 'api/v1/region_event/';

  constructor(public http: HttpClient) {
  }

  public findById(id: number) {
    return this.http.get<RegEvent>(`${this.path}${id}`);
  }

  public findByTourId(id: number) {
    return this.http.get<RegEvent>(`${this.path}by_tour/${id}`);
  }

  public findByParams(params: HttpParams) {
    return this.http.get<RegEvent[]>(`${this.path}by_params`, {params: params});
  }

  public save(regEvent: RegEvent) {
    if (regEvent.id == null) {
      return this.http.post<RegEvent>(this.path, regEvent);
    }
    return this.http.put<RegEvent>(this.path, regEvent);
  }
}
