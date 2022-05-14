import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AccType} from "./acctype";

@Injectable({
  providedIn: 'root'
})
export class AccomTypeService {

  private path: string ='api/v1/accomm_type/';

    constructor(public http: HttpClient) { }

  public findById(id: number) {
    return this.http.get<AccType>(`${this.path}${id}`);
  }

  public save(accType: AccType){
    console.log(accType);
    if (accType.id == null){
      return this.http.post<AccType>(this.path, accType);
    }
    return this.http.put<AccType>(this.path, accType);
  }
}
