import {Component, Input, OnInit} from '@angular/core';
import {RoomService} from '../../service/room.service';
import {Room} from '../../entities/room';
import {Page} from '../../entities/page';

@Component({
  selector: 'app-room-list',
  templateUrl: './room-list.component.html',
  styleUrls: ['./room-list.component.css']
})
export class RoomListComponent implements OnInit {

  constructor(private roomService: RoomService) {
  }

  @Input() roomList: Room[] = [];
  // rooms: Room[] = [
  //   {
  //     id: 1,
  //     roomNumber: '100A',
  //     personAmount: 1,
  //     description: 'Nasz najlepszo 1 osobwoy pokuj. W sam raz dla ludzi dla ktorych hotel to tylko miejsce spoczynku',
  //     price: 100,
  //     roomTypeSet: [{id: 1, name: 'Balkon'}, {id: 2, name: 'Taras'}],
  //     tagSet: [{id: 1, name: 'Balkon'}, {id: 2, name: 'Taras'}],
  //     area: 20
  //   },
  //   {
  //     id: 1,
  //     roomNumber: '100A',
  //     personAmount: 1,
  //     description: 'Nasz najlepszo 1 osobwoy pokuj. W sam raz dla ludzi dla ktorych hotel to tylko miejsce spoczynku',
  //     price: 100,
  //     roomTypeSet: [{id: 1, name: 'Balkon'}, {id: 2, name: 'Taras'}],
  //     tagSet: [{id: 1, name: 'Balkon'}, {id: 2, name: 'Taras'}],
  //     area: 20
  //   },
  //   {
  //     id: 1,
  //     roomNumber: '100A',
  //     personAmount: 1,
  //     description: 'Nasz najlepszo 1 osobwoy pokuj. W sam raz dla ludzi dla ktorych hotel to tylko miejsce spoczynku',
  //     price: 100,
  //     roomTypeSet: [{id: 1, name: 'Balkon'}, {id: 2, name: 'Taras'}],
  //     tagSet: [{id: 1, name: 'Balkon'}, {id: 2, name: 'Taras'}],
  //     area: 20
  //   }
  // ];

  ngOnInit(): void {
    // this.roomService.getPageOfRooms({size: 20, page: 0}, {})
    //   .subscribe(roomList => {
    //     this.rooms = roomList.roomList;
    //     this.page = roomList.page;
    //   });
  }

}
