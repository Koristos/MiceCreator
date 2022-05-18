import {Tour} from "./tour";
import {Accommodation} from "../accommodation/accommodation";
import {Flight} from "../flight/flight";
import {HotelEvent} from "../hotelevent/hotelevent";
import {RegEvent} from "../regionevent/regevent";

export class FullTour {

  constructor(public tour: Tour,
              public accommodations: Accommodation[],
              public flights: Flight[],
              public hotelEvents: HotelEvent[],
              public regionEvents: RegEvent[]
  ) {
  }

}
