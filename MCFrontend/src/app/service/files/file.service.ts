import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";

import {saveAs} from "file-saver";

@Injectable({
  providedIn: 'root'
})
export class FileService {
  private path: string = 'api/v1/files/';

  constructor(public http: HttpClient) {
  }

  public getEstimate(id: number) {
    this.http.get<Blob>(`${this.path}estimate/${id}`, {
      responseType: 'blob' as 'json',
    }).subscribe(result => {
      console.log(result);
      saveAs(result, `Tour №${id} estimate.xls`);
    });
  }
}
