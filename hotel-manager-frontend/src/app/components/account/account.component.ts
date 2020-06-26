import {Component, OnInit} from '@angular/core';
import {faLink} from '@fortawesome/free-solid-svg-icons';
import {FormBuilder, FormGroup, ValidationErrors, ValidatorFn, Validators} from '@angular/forms';
import {UserService} from '../../service/user.service';
import {Contact} from '../../entities/contact';
import {User} from '../../entities/user';
import {tap} from 'rxjs/operators';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {

  faLogin = faLink;
  loginData: FormGroup;
  userData: FormGroup;
  contactForm: FormGroup;
  userUser: User;

  loginDataErr = '';
  userDataErr = '';
  contactErr = '';

  constructor(private formBuilder: FormBuilder, private userService: UserService) {
  }

  ngOnInit(): void {
    this.loginData = this.formBuilder.group({
      password: ['', Validators.compose([
        Validators.required,
        Validators.maxLength(24),
        Validators.minLength(8)
      ])],
      repeatPassword: ['', Validators.compose([
        Validators.required,
        Validators.maxLength(24),
        Validators.minLength(8)
      ])],
    }, {validators: this.checkIfRepeatPasswordEqToPassword});
    this.userData = this.formBuilder.group({
      name: ['', Validators.compose([
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(18)
      ])],
      lastName: ['', Validators.compose([
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(18)
      ])],
    });

    this.contactForm = this.formBuilder.group({
      contact: ['', Validators.compose([
        Validators.pattern(/^[0-9]{9}$/),
        Validators.required
      ])]
    });

    this.userService.currentUser.pipe(
      tap(user => {
        if (this.userService.currentUserDataSubject.getValue()) {
          this.attachUser(this.userService.currentUserDataSubject.getValue());
        } else {
          if (user?.username) {
            this.userService.getUserInfo(user.username)
              .subscribe(val => this.attachUser(val));
          }
        }
      })
    ).subscribe();
  }

  attachUser(user: User): void {
    this.userUser = user;

    this.userData.patchValue({
      name: this.userUser?.person?.firstName || '',
      lastName: this.userUser?.person?.lastName || '',
    });

    this.contactForm.patchValue({
      contact: this.userUser?.person?.contactSet[0]?.phoneNumber || ''
    });
  }

  checkIfRepeatPasswordEqToPassword: ValidatorFn = (control: FormGroup): ValidationErrors | null => {
    const password = control.get('password');
    const repeatPassword = control.get('repeatPassword');
    return password && repeatPassword && password.value === repeatPassword.value ? null : {passwordNotTheSame: true};
  };

  onSubmitLoginData(): void {
    this.userService.editUser({
      email: this.userUser.email,
      password: this.loginData.value.password
    }, this.userUser.email)
      .subscribe(
        res => {
          this.userUser = res;
        },
        err => {
          if (err.status === 409) {
            this.loginDataErr = 'Użytkownik o podanym email już istnieje';
          } else {
            this.loginDataErr = 'Coś poszło nie tak';
          }
        });
  }

  onSubmitUserData(): void {
    const {name: firstName, lastName, pesel = '33333333398'} = this.userData.value;
    this.userService.editPerson({firstName, lastName, pesel}, this.userUser.person.id)
      .subscribe(
        newPerson => {
          this.userUser.person = newPerson;
          this.attachUser(this.userUser);
        },
        error => this.userDataErr = 'Dane nie mogą być edytowane'
      );
  }

  onSubmitContactForm(): void {
    const contact = this.contactForm.value.contact;
    this.userService.editContact({phoneNumber: contact}, this.userUser.person.contactSet[0].id)
      .subscribe(
        newContacts => {
          this.userUser.person.contactSet = [newContacts];
          this.attachUser(this.userUser);
        },
        error => this.contactErr = 'Dane nie mogą być edytowane'
      );
  }
}
