import {HttpParams} from "@angular/common/http";

export class RequestParams {

  constructor(public name: any = "null",
              public country: any = "null",
              public region: any = "null",
              public location: any = "null",
              public hotel: any = "null",
              public alter_name: boolean = true) {
  }

  private preparedParams: any = null;

  reset() {
    this.name = "null";
    this.country = "null";
    this.region = "null";
    this.location = "null";
    this.hotel = "null";
    this.alter_name = true;
  }

  prepareForSending() {
    this.preparedParams = new HttpParams();
    if (this.name != "null") {
      this.preparedParams = this.preparedParams.append('name', this.name);
    }
    if (this.country != "null") {
      this.preparedParams = this.preparedParams.append('country', this.country);
    }
    if (this.region != "null") {
      this.preparedParams = this.preparedParams.append('region', this.region);
    }
    if (this.location != "null") {
      this.preparedParams = this.preparedParams.append('location', this.location);
    }
    if (this.hotel != "null") {
      this.preparedParams = this.preparedParams.append('hotel', this.hotel);
    }
    this.preparedParams = this.preparedParams.append('alter_name', this.alter_name);
    console.log(this.preparedParams);
    return this.preparedParams;
  }
}
