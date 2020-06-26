import {Component, OnInit} from '@angular/core';
import {UserService} from '../../service/user.service';
import {map} from 'rxjs/operators';
import {LoginCredentials} from '../../entities/login-credentials';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'hotelmanagerapp';

  constructor(private userService: UserService) {
  }


  ngOnInit(): void {
    const val = this.userService.currentUserSubject.getValue();
    if (val) {
      this.userService.getUserInfo(val.username)
        .subscribe();
    }
  }

}
