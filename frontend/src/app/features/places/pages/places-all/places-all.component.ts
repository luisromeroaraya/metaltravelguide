import { Component, OnInit } from '@angular/core';
import { IPlace } from "../../models/IPlace";
import { PlacesService } from "../../services/places.service";
import { SessionService } from "../../../../core/security/services/session.service";
import { Country } from "../../../../core/enums/Country";
import { Type } from "../../../../core/enums/Type";

@Component({
  selector: 'app-places-all',
  templateUrl: './places-all.component.html',
  styleUrls: ['./places-all.component.css']
})
export class PlacesAllComponent implements OnInit {
  public countryEnum = Country;
  private countries : [string, Country][] = [];
  public typeEnum = Type;
  private types : [string, Type][] = [];

  private places: IPlace[] = [];
  public countryIso: string = "Filter by country...";
  public type: string = "Filter by type...";

  // constructor
  constructor(private service: PlacesService, private session: SessionService) { }

  // getters
  get Places(): IPlace[] {
    return this.places;
  }

  get Countries(): [string, Country][] {
    return this.countries;
  }

  get Types(): [string, Type][] {
    return this.types;
  }

  get IsAdmin(): boolean {
    return this.session.isAdmin();
  }

  // methods
  ngOnInit(): void {
    this.countries = Object.entries(this.countryEnum);
    this.types = Object.entries(this.typeEnum);
    this.loadPlaces();
  }

  private loadPlaces(): void {
    this.service.readAll().subscribe((data: IPlace[]) => {
      this.places = data.filter((place) => place.status).sort((place1, place2) => place2.id - place1.id);
      const placesIso = this.places.map( (element) => element.address.countryIso ).filter((e, i, self) => self.indexOf(e) === i);
      this.countries = this.countries.filter((entry) => placesIso.includes(entry[0]));
      const placesType = this.places.map( (element) => element.type ).filter((e, i, self) => self.indexOf(e) === i);
      this.types = this.types.filter((entry) => placesType.includes(entry[0]));
    });
  }
}
