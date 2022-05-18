import {HttpParams} from "@angular/common/http";
import {formatDate} from "@angular/common";

export class FlightSearchParams {

  constructor() {
  }

  public airlineId: any = null;
  public departureAirportId: number = 0;
  public arrivalAirportId: number = 0;
  public firstDate: Date = new Date('01.01.2020');
  public lastDate: Date = new Date();
  private preparedParams: any = null;
  private datePattern: string = 'dd.MM.yyyy';

  prepareForSending() {
    this.preparedParams = new HttpParams();
    if (this.airlineId != 0) {
      this.preparedParams = this.preparedParams.append('airline', this.airlineId);
    }
    if (this.departureAirportId != 0) {
      this.preparedParams = this.preparedParams.append('dep_port', this.departureAirportId);
    }
    if (this.arrivalAirportId != 0) {
      this.preparedParams = this.preparedParams.append('arr_port', this.arrivalAirportId);
    }
    this.preparedParams = this.preparedParams.append('first_date', formatDate(this.firstDate, this.datePattern, 'en-US'));
    this.preparedParams = this.preparedParams.append('second_date', formatDate(this.lastDate, this.datePattern, 'en-US'));
    return this.preparedParams;
  }

}
