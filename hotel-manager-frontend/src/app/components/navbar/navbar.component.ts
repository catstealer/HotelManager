import {Component, OnInit} from '@angular/core';
import {UserService} from '../../service/user.service';
import {Observable} from 'rxjs';
import {LoginCredentials} from '../../entities/login-credentials';
import {faChartBar, faUser} from '@fortawesome/free-solid-svg-icons';
import {Router} from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  faUser = faUser;
  userObservable: Observable<LoginCredentials>;
  faStats = faChartBar;

  constructor(private userService: UserService, private route: Router) {
  }

  ngOnInit(): void {
    this.userObservable = this.userService.currentUser;
  }

  logout(): void {
    this.userService.logout();
    this.route.navigate(['/']);
  }

  onLogoCLick() {
    if (localStorage.getItem('userValue')) {
      this.route.navigate(['/home']);
    } else {
      this.route.navigate(['/']);
    }
  }

  canAccess() {
    return this.userService.isAdmin();
  }
}
