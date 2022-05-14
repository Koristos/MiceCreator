import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Room} from "./room";

@Injectable({
  providedIn: 'root'
})
export class RoomService {

  private path: string = 'api/v1/room/';

  constructor(public http: HttpClient) { }

  public findById(id: number) {
    return this.http.get<Room>(`${this.path}${id}`);
  }

  public save(room: Room){
    if (room.id == null){
      return this.http.post<Room>(this.path, room);
    }
    return this.http.put<Room>(this.path, room);
  }
}
