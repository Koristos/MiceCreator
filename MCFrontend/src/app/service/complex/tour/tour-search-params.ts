import {HttpParams} from "@angular/common/http";
import {formatDate} from '@angular/common';

export class TourSearchParams {

  constructor() {
  }

  public countryId: number = 0;
  public firstDate: Date = new Date('01.01.2020');
  public lastDate: Date = new Date();
  private preparedParams: any = null;
  private datePattern: string = 'dd.MM.yyyy';
  public userName: string = "";

  prepareForSending() {
    this.preparedParams = new HttpParams();
    if (this.countryId != 0) {
      this.preparedParams = this.preparedParams.append('country', this.countryId);
    }
    this.preparedParams = this.preparedParams.append('first_date', formatDate(this.firstDate, this.datePattern, 'en-US'));
    this.preparedParams = this.preparedParams.append('second_date', formatDate(this.lastDate, this.datePattern, 'en-US'));
    this.preparedParams = this.preparedParams.append('user_name', this.userName);
    return this.preparedParams;
  }

}
