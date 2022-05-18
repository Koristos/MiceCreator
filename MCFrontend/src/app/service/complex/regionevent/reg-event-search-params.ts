import {HttpParams} from "@angular/common/http";
import {formatDate} from "@angular/common";

export class RegEventSearchParams {

  constructor() {
  }

  public regionServId: number = 0;
  public firstDate: Date = new Date('01.01.2020');
  public lastDate: Date = new Date();
  private preparedParams: any = null;
  private datePattern: string = 'dd.MM.yyyy';

  prepareForSending() {
    this.preparedParams = new HttpParams();
    if (this.regionServId != 0) {
      this.preparedParams = this.preparedParams.append('region_service', this.regionServId);
    }
    this.preparedParams = this.preparedParams.append('first_date', formatDate(this.firstDate, this.datePattern, 'en-US'));
    this.preparedParams = this.preparedParams.append('second_date', formatDate(this.lastDate, this.datePattern, 'en-US'));
    return this.preparedParams;
  }

}
