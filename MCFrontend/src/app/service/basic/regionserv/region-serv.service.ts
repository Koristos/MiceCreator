import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {RegionServ} from "./regionserv";

@Injectable({
  providedIn: 'root'
})
export class RegionServService {

  private path: string = 'api/v1/region_service/';

  constructor(public http: HttpClient) {
  }

  public findById(id: number) {
    return this.http.get<RegionServ>(`${this.path}${id}`);
  }

  public save(regionServ: RegionServ) {
    if (regionServ.id == null) {
      return this.http.post<RegionServ>(this.path, regionServ);
    }
    return this.http.put<RegionServ>(this.path, regionServ);
  }
}
