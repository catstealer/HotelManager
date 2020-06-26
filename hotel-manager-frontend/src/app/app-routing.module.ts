import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {RoomListComponent} from './components/room-list/room-list.component';
import {HomePageComponent} from './components/home-page/home-page.component';
import {RoomCardComponent} from './components/room-card/room-card.component';
import {LoginComponent} from './components/login/login.component';
import {RegisterComponent} from './components/register/register.component';
import {ContactComponent} from './components/contact/contact.component';
import {FooterComponent} from './components/footer/footer.component';
import {SearchComponent} from './components/search/search.component';
import {RoomComponent} from './components/room/room.component';
import {AccountComponent} from './components/account/account.component';
import {ReservationsComponent} from './components/reservations/reservations.component';
import {ReservationDetailsComponent} from './components/reservation-details/reservation-details.component';
import {StartPageComponent} from './components/start-page/start-page.component';
import {LoggedGuard} from './guards/logged.guard';
import {NotloggedGuard} from './guards/notlogged.guard';
import {IsadminGuard} from './guards/isadmin.guard';
import {ReservationCreateComponent} from './components/reservation-create/reservation-create.component';
import {StatisticsComponent} from './components/statistics/statistics.component';


const routes: Routes = [
  {path: 'reservation/create', component: ReservationCreateComponent, canActivate: [LoggedGuard]},
  {path: 'home', component: HomePageComponent},
  {path: 'login', component: LoginComponent, canActivate: [NotloggedGuard]},
  {path: 'register', component: RegisterComponent, canActivate: [NotloggedGuard]},
  {path: 'contact', component: ContactComponent},
  {path: 'account/:email', component: AccountComponent, canActivate: [LoggedGuard]},
  {path: 'room/:id', component: RoomComponent},
  {path: 'reservations', component: ReservationsComponent, canActivate: [LoggedGuard]},
  {path: 'reservation/:id', component: ReservationDetailsComponent, canActivate: [LoggedGuard]},
  {path: 'stats', component: StatisticsComponent, canActivate: [LoggedGuard, IsadminGuard]},
  {path: '', component: StartPageComponent, canActivate: [NotloggedGuard]},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
