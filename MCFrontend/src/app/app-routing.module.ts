import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SearchComponent} from "./basic/search/search.component";
import {AccomTypeComponent} from "./basic/simple/accom-type/accom-type.component";
import {AirlineComponent} from "./basic/simple/airline/airline.component";
import {AirportComponent} from "./basic/simple/airport/airport.component";
import {CountryComponent} from "./basic/simple/country/country.component";
import {HotelComponent} from "./basic/simple/hotel/hotel.component";
import {HotelServComponent} from "./basic/simple/hotel-serv/hotel-serv.component";
import {LocationComponent} from "./basic/simple/location/location.component";
import {RegionComponent} from "./basic/simple/region/region.component";
import {RegionServComponent} from "./basic/simple/region-serv/region-serv.component";
import {RoomComponent} from "./basic/simple/room/room.component";
import {StarterComponent} from "./starter/starter.component";
import {FlightSearchComponent} from "./complex/search/flight-search/flight-search.component";
import {AccomSearchComponent} from "./complex/search/accom-search/accom-search.component";
import {RegionEventSearchComponent} from "./complex/search/region-event-search/region-event-search.component";
import {HotelEventSearchComponent} from "./complex/search/hotel-event-search/hotel-event-search.component";
import {TourSearchComponent} from "./complex/search/tour-search/tour-search.component";
import {FlightComponent} from "./complex/flight/flight.component";
import {AccommodationComponent} from "./complex/accommodation/accommodation.component";
import {RegionEventComponent} from "./complex/region-event/region-event.component";
import {HotelEventComponent} from "./complex/hotel-event/hotel-event.component";
import {TourComponent} from "./complex/tour/tour.component";
import {LoginComponent} from "./authorisation/login/login.component";

const routes: Routes = [
  {path: "", pathMatch: "full", component: StarterComponent},
  {path: "search_components/:type", component: SearchComponent},
  {path: "search_components/:type/:cn", component: SearchComponent},
  {path: "accomm_type/:id", component: AccomTypeComponent},
  {path: "accomm_type/new", component: AccomTypeComponent},
  {path: "airline/:id", component: AirlineComponent},
  {path: "airline/new", component: AirlineComponent},
  {path: "airport/:id", component: AirportComponent},
  {path: "airport/new", component: AirportComponent},
  {path: "country/:id", component: CountryComponent},
  {path: "country/new", component: CountryComponent},
  {path: "hotel/:id", component: HotelComponent},
  {path: "hotel/new", component: HotelComponent},
  {path: "hotel_service/:id", component: HotelServComponent},
  {path: "hotel_service/new", component: HotelServComponent},
  {path: "location/:id", component: LocationComponent},
  {path: "location/new", component: LocationComponent},
  {path: "region/:id", component: RegionComponent},
  {path: "region/new", component: RegionComponent},
  {path: "region_service/:id", component: RegionServComponent},
  {path: "region_service/new", component: RegionServComponent},
  {path: "room/:id", component: RoomComponent},
  {path: "room/new", component: RoomComponent},
  {path: "flight_search", component: FlightSearchComponent},
  {path: "accommodation_search", component: AccomSearchComponent},
  {path: "region_event_search", component: RegionEventSearchComponent},
  {path: "hotel_event_search", component: HotelEventSearchComponent},
  {path: "tour_search", component: TourSearchComponent},
  {path: "flight/:id/:tour_id", component: FlightComponent},
  {path: "flight/new/:tour_id", component: FlightComponent},
  {path: "accommodation/:id/:tour_id", component: AccommodationComponent},
  {path: "accommodation/new/:tour_id", component: AccommodationComponent},
  {path: "region_event/:id/:tour_id", component: RegionEventComponent},
  {path: "region_event/new/:tour_id", component: RegionEventComponent},
  {path: "hotel_event/:id/:tour_id", component: HotelEventComponent},
  {path: "hotel_event/new/:tour_id", component: HotelEventComponent},
  {path: "tour/:id", component: TourComponent},
  {path: "tour/new", component: TourComponent},
  {path: "login", component: LoginComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
