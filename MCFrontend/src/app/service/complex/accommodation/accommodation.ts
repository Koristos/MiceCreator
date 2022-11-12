import {ShortForm} from "../../basic/shortform";

export class Accommodation {

  constructor(public id: any,
              public checkInDate: Date,
              public checkOutDate: Date,
              public pax: number,
              public price: number,
              public nettoPrice: number,
              public tourId: number,
              public room: ShortForm,
              public accType: ShortForm,
              public hotel: ShortForm,
              public nights: number,
              public roomCount: number,
              public total: number,
              public nettoTotal: number,
              public creationDate: Date
  ) {
  }
}
