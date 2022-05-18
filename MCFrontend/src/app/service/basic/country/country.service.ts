import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Country} from "./country";

@Injectable({
  providedIn: 'root'
})
export class CountryService {

  private path: string = 'api/v1/country/';

  constructor(public http: HttpClient) {
  }

  public findById(id: number) {
    return this.http.get<Country>(`${this.path}${id}`);
  }

  public save(country: Country) {
    console.log(country);
    if (country.id == null) {
      return this.http.post<Country>(this.path, country);
    }
    return this.http.put<Country>(this.path, country);
  }
}
