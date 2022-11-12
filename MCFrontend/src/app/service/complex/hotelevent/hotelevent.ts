import {ShortForm} from "../../basic/shortform";

export class HotelEvent {

  constructor(public id: any,
              public eventDate: Date,
              public pax: number,
              public price: number,
              public nettoPrice: number,
              public tourId: number,
              public service: ShortForm,
              public total: number,
              public nettoTotal: number,
              public creationDate: Date
  ) {
  }
}
