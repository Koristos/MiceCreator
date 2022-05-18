import {ShortForm} from "../../basic/shortform";

export class Flight {

  constructor(public id: any,
              public departureDate: Date,
              public arrivalDate: Date,
              public pax: number,
              public price: number,
              public tourId: number,
              public airline: ShortForm,
              public departureAirport: ShortForm,
              public arrivalAirport: ShortForm,
              public total: number
  ) {
  }
}
