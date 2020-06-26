import {Component, Input, OnInit} from '@angular/core';
import {Room} from '../../entities/room';

@Component({
  selector: 'app-room-card',
  templateUrl: './room-card.component.html',
  styleUrls: ['./room-card.component.css']
})
export class RoomCardComponent implements OnInit {

  @Input('room') room: Room;

  constructor() {
  }

  ngOnInit(): void {
  }

}
