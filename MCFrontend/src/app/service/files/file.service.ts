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

  public getPresentation(id: number) {
    this.http.get<Blob>(`${this.path}presentation/${id}`, {
      responseType: 'blob' as 'json',
    }).subscribe(result => {
      console.log(result);
      saveAs(result, `Tour №${id} presentation.pdf`);
    });
  }

  public getImage(id: string) {
    return this.http.get<Blob>(`${this.path}image/${id}`, {
      responseType: 'blob' as 'json',
    });
  }

  public saveImage(entityType: string, entityId: number, imageId: number, image: File) {
    const formData: FormData = new FormData();
    formData.set("image", image);
    formData.set("entityType", entityType);
    formData.set("entityId", entityId.toString());
    formData.set("imageNum", imageId.toString());
    return this.http.post<any>(`${this.path}image`, formData);
  }

  public deleteImage(entityType: string, entityId: number, imageId: number) {
    return this.http.delete<any>(`${this.path}image/${entityType}/${entityId}/${imageId}`);
  }
}
