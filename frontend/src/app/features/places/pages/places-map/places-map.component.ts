import { AfterViewInit, Component, ElementRef, OnInit, Renderer2, ViewChild } from '@angular/core';
import { PlacesService } from "../../services/places.service";
import { IPlace } from "../../models/IPlace";
declare var MarkerClusterer: any;

@Component({
  selector: 'app-places-map',
  templateUrl: './places-map.component.html',
  styleUrls: ['./places-map.component.css']
})
export class PlacesMapComponent implements OnInit, AfterViewInit {
  @ViewChild('divMap') divMap!: ElementRef;
  private map!: google.maps.Map;
  private marker?: google.maps.Marker;
  private markers: google.maps.Marker[] = [];
  private infoWindow?: google.maps.InfoWindow;


  private places: IPlace[] = [];

  constructor(private placesService: PlacesService, private renderer: Renderer2) { }

  ngOnInit(): void {
    this.placesService.readAll().subscribe((places: IPlace[]) => {
      this.places = places
      places.forEach((place) => {
        this.createMarker(place);
      });
      new MarkerClusterer(this.map, this.markers, {imagePath: "https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m"});
      this.infoWindow = new google.maps.InfoWindow();
    });
  }

  ngAfterViewInit(): void {
    this.loadMap();
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((position) => {
          this.map.setCenter(new google.maps.LatLng(position.coords.latitude, position.coords.longitude));
          this.map.setZoom(11);
        }
      );
    }
  }

  private loadMap(): any {
    const options = {
      center: new google.maps.LatLng(20, -30),
      zoom: 2,
      gestureHandling: "greedy",
      mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    this.map = new google.maps.Map(this.renderer.selectRootElement(this.divMap.nativeElement), options);
  }

  private createMarker(place: IPlace): any {
    const marker = new google.maps.Marker({position: new google.maps.LatLng(place.address.lat, place.address.lon), title: place.name, map: this.map, icon: {url: "assets/markers/" + place.type.toLowerCase() + ".png", scaledSize: new google.maps.Size(32, 32)}});
    marker.addListener("click", () => {
      this.marker = marker;
      this.map.setZoom(17);
      this.map.setCenter(marker.getPosition() as google.maps.LatLng);
      this.infoWindow?.setContent(
        "<a class='text-decoration-none text-black' href='/places/" + marker.getTitle() + "'><div class='card shadow-sm'><img class='bd-placeholder-img card-img-top' src='" + marker.getTitle() + "' alt='" + marker.getTitle() + "'/><div class='card-body'><h5 class='card-title'>" + marker.getTitle() + "</h5><div class='d-flex justify-content-between'><p>" + marker.getTitle() + " " + marker.getTitle() + ", " + marker.getTitle() + " " + marker.getTitle() + "<span class='fi fi-" + "BE" + "'></span></p></div><div class='d-flex justify-content-between align-items-center'><div class='d-flex align-items-center'><img class='me-2' height='24' src='assets/logos/" + "bar" + ".svg' alt='" + "bar" + "' style='opacity: 60%'/><small class='text-muted'>" + "BAR" + "</small></div></div></div></div></a>"
      );
      this.infoWindow?.open(this.map, this.marker);
    });
    this.markers.push(marker);
  }
}
