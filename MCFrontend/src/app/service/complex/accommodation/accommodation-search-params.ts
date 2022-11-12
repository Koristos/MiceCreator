import {HttpParams} from "@angular/common/http";
import {formatDate} from "@angular/common";

export class AccommodationSearchParams {

  constructor() {
  }

  public roomId: number = 0;
  public hotelId: number = 0;
  public accTypeId: number = 0;
  public firstDate: Date = new Date('01.01.2020');
  public lastDate: Date = new Date();
  private preparedParams: any = null;
  private datePattern: string = 'dd.MM.yyyy';
  public firstDateOfCreation: Date = new Date('01.01.2020');
  public secondDateOfCreation: Date = new Date();

  prepareForSending() {
    this.preparedParams = new HttpParams();
    if (this.roomId != 0) {
      this.preparedParams = this.preparedParams.append('room', this.roomId);
    }
    if (this.hotelId != 0) {
      this.preparedParams = this.preparedParams.append('hotel', this.hotelId);
    }
    if (this.accTypeId != 0) {
      this.preparedParams = this.preparedParams.append('acc_type', this.accTypeId);
    }
    this.preparedParams = this.preparedParams.append('first_date', formatDate(this.firstDate, this.datePattern, 'en-US'));
    this.preparedParams = this.preparedParams.append('second_date', formatDate(this.lastDate, this.datePattern, 'en-US'));
    this.preparedParams = this.preparedParams.append('first_date_creation', formatDate(this.firstDateOfCreation, this.datePattern, 'en-US'));
    this.preparedParams = this.preparedParams.append('second_date_creation', formatDate(this.secondDateOfCreation, this.datePattern, 'en-US'));
    return this.preparedParams;
  }

}
