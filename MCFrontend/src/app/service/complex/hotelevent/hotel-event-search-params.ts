import {HttpParams} from "@angular/common/http";
import {formatDate} from "@angular/common";

export class HotelEventSearchParams {

  constructor() {
  }

  public hotel_service: number = 0;
  public firstDate: Date = new Date('01.01.2020');
  public lastDate: Date = new Date();
  private preparedParams: any = null;
  private datePattern: string = 'dd.MM.yyyy';

  prepareForSending() {
    this.preparedParams = new HttpParams();
    if (this.hotel_service != 0) {
      this.preparedParams = this.preparedParams.append('hotel_service', this.hotel_service);
    }
    this.preparedParams = this.preparedParams.append('first_date', formatDate(this.firstDate, this.datePattern, 'en-US'));
    this.preparedParams = this.preparedParams.append('second_date', formatDate(this.lastDate, this.datePattern, 'en-US'));
    return this.preparedParams;
  }

}
