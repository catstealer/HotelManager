import {Component, OnInit} from '@angular/core';
import {RoomSerching} from '../../service/room-serching';
import {RoomService} from '../../service/room.service';
import {Room} from '../../entities/room';
import {ObjectUnsubscribedError} from 'rxjs';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {

  searchItem: RoomSerching = {};
  roomList: Room[] = [];
  roomAmount = 0;
  totalPagesAmount = 0;
  pageNumber = 0;

  constructor(private roomService: RoomService) {
  }

  ngOnInit(): void {
    this.roomService.getPageOfRooms({size: 20, page: 0}, {})
      .subscribe(roomList => {
        this.roomList = roomList.roomList;
        this.roomAmount = roomList.page.totalElements;
        this.totalPagesAmount = roomList.page.totalPages;
      });
  }

  onSearchSelected(searchItem: RoomSerching) {
    this.changePage(searchItem, 0);
  }

  changePage(searchItem: RoomSerching, page: number) {
    this.searchItem = searchItem;
    this.pageNumber = page;
    this.roomService.getPageOfRooms({size: 20, page}, this.searchItem)
      .subscribe(roomPage => {
        this.roomAmount = roomPage.page.totalElements;
        this.totalPagesAmount = roomPage.page.totalPages;
        if (this.roomAmount) {
          this.roomList = roomPage.roomList;
        } else {
          this.roomList = [];
        }
      });
  }

  onPageChanged(pageNum: number): void {
    this.changePage(this.searchItem, pageNum);
  }
}
