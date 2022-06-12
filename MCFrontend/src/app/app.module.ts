import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from "@angular/common/http";
import {ReactiveFormsModule, FormsModule} from "@angular/forms";
import {HeaderComponent} from './header/header.component';
import {FooterComponent} from './footer/footer.component';
import {StarterComponent} from './starter/starter.component';
import {AccomTypeComponent} from './basic/simple/accom-type/accom-type.component';
import {AirlineComponent} from './basic/simple/airline/airline.component';
import {AirportComponent} from './basic/simple/airport/airport.component';
import {CountryComponent} from './basic/simple/country/country.component';
import {HotelComponent} from './basic/simple/hotel/hotel.component';
import {HotelServComponent} from './basic/simple/hotel-serv/hotel-serv.component';
import {LocationComponent} from './basic/simple/location/location.component';
import {RegionComponent} from './basic/simple/region/region.component';
import {RegionServComponent} from './basic/simple/region-serv/region-serv.component';
import {RoomComponent} from './basic/simple/room/room.component';
import {SearchComponent} from './basic/search/search.component';
import {AccommodationComponent} from './complex/accommodation/accommodation.component';
import {FlightComponent} from './complex/flight/flight.component';
import {HotelEventComponent} from './complex/hotel-event/hotel-event.component';
import {RegionEventComponent} from './complex/region-event/region-event.component';
import {TourComponent} from './complex/tour/tour.component';
import {AccomSearchComponent} from './complex/search/accom-search/accom-search.component';
import {FlightSearchComponent} from './complex/search/flight-search/flight-search.component';
import {HotelEventSearchComponent} from './complex/search/hotel-event-search/hotel-event-search.component';
import {RegionEventSearchComponent} from './complex/search/region-event-search/region-event-search.component';
import {TourSearchComponent} from './complex/search/tour-search/tour-search.component';
import {LoginComponent} from './authorisation/login/login.component';
import {AuthInterceptor} from "./service/security/auth-interceptor";

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    StarterComponent,
    AccomTypeComponent,
    AirlineComponent,
    AirportComponent,
    CountryComponent,
    HotelComponent,
    HotelServComponent,
    LocationComponent,
    RegionComponent,
    RegionServComponent,
    RoomComponent,
    SearchComponent,
    AccommodationComponent,
    FlightComponent,
    HotelEventComponent,
    RegionEventComponent,
    TourComponent,
    AccomSearchComponent,
    FlightSearchComponent,
    HotelEventSearchComponent,
    RegionEventSearchComponent,
    TourSearchComponent,
    LoginComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: AuthInterceptor,
    multi: true,
  }],
  bootstrap: [AppComponent]
})
export class AppModule {
}
