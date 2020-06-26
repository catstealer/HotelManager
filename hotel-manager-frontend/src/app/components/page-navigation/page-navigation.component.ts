import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-page-navigation',
  templateUrl: './page-navigation.component.html',
  styleUrls: ['./page-navigation.component.css']
})
export class PageNavigationComponent implements OnInit {

  @Input()
  page = 0;
  private maxNumberProp = 0;
  numbers: number[] = [];

  @Input()
  set maxNumber(value: number) {
    this.maxNumberProp = value;
    const newNumbers = [];
    for (let i = 0; i < value; i++) {
      newNumbers.push(i);
    }
    this.numbers = newNumbers;
  }

  @Output() pageSelection = new EventEmitter<number>();

  constructor() {
  }

  ngOnInit(): void {
  }

  prevPage() {
    if (this.page > 0) {
      this.page--;
      this.pageSelection.emit(this.page);
    }
  }

  toPageNumber(numberOfNextPage: number) {
    this.page = numberOfNextPage;
    this.pageSelection.emit(this.page);
  }

  nextPage() {
    if (this.numbers.length - 1 > this.page) {
      this.page++;
      this.pageSelection.emit(this.page);
    }
  }


}
