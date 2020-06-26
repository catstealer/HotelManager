import {Component, OnInit} from '@angular/core';
import {AgmMap} from '@agm/core';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css'],
  providers: [AgmMap]
})
export class ContactComponent implements OnInit {
  lat = 54.667163;
  lng = 17.051837;
  zoom = 10;

  constructor() {
  }

  ngOnInit(): void {
  }

}
