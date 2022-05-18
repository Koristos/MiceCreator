import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Location} from "./location";

@Injectable({
  providedIn: 'root'
})
export class LocationService {

  private path: string = 'api/v1/location/';

  constructor(public http: HttpClient) {
  }

  public findById(id: number) {
    return this.http.get<Location>(`${this.path}${id}`);
  }

  public save(location: Location) {
    if (location.id == null) {
      return this.http.post<Location>(this.path, location);
    }
    return this.http.put<Location>(this.path, location);
  }
}
