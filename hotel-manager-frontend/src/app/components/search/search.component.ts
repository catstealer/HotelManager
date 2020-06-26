import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {faCalendarAlt, faSearch} from '@fortawesome/free-solid-svg-icons';
import {NgbCalendar, NgbDate, NgbDateParserFormatter} from '@ng-bootstrap/ng-bootstrap';
import {RoomSerching, validRoomSearching} from '../../service/room-serching';
import {NgForm} from '@angular/forms';
import {TagService} from '../../service/tag.service';
import {Tag} from '../../entities/tag';
import {TypeService} from '../../service/type.service';
import {Type} from '../../entities/type';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  faCalendar = faCalendarAlt;
  faSearch = faSearch;
  hoveredDate: NgbDate | null = null;

  fromDate: NgbDate | null;
  toDate: NgbDate | null;

  searching: RoomSerching;

  tags: Tag[] = [];
  types: Type[] = [];

  @Output('searchClicked')
  searchEvent: EventEmitter<RoomSerching> = new EventEmitter<RoomSerching>();

  constructor(private calendar: NgbCalendar,
              public formatter: NgbDateParserFormatter,
              private tagService: TagService,
              private typeService: TypeService) {
  }

  ngOnInit(): void {
    this.tagService.getTags().subscribe(tagList => {
      this.tags = tagList;
    });
    this.typeService.getTypes().subscribe(typeList => {
      this.types = typeList;
    });
  }

  onSubmitClick(formRef: NgForm): void {
    this.searching = {
      fromDate: this.formatter.format(this.fromDate),
      toDate: this.formatter.format(this.toDate),
      area: formRef.value.area,
      fromPrice: formRef.value.minPrice || null,
      toPrice: formRef.value.maxPrice || null,
      types: formRef.value.type,
      personAmount: formRef.value.personAmount,
      tags: this.getStringOfCheckedTags(formRef.value.tags)
    };
    for (const key in this.searching) {
      if (this.searching.hasOwnProperty(key) && this.searching[key] === 'all') {
        this.searching[key] = '';
      }
    }
    if (this.searching && validRoomSearching(this.searching)) {
      this.searchEvent.emit({...this.searching});
    } else {
      alert('Podano złe dane');
    }
  }

  getStringOfCheckedTags(tags: object): string[] {
    return Object.keys(tags).reduce((list, key) => {
      if (tags.hasOwnProperty(key) && tags[key]) {
        list.push(key);
      }
      return list;
    }, []);
  }

  convertToDateString(date: NgbDate): string {
    if (date) {
      return new Date(date.year, date.month - 1, date.day).toJSON().slice(0, 10);
    } else {
      return '';
    }
  }

  onDateSelection(date: NgbDate) {
    if (!this.fromDate && !this.toDate) {
      this.fromDate = date;
    } else if (this.fromDate && !this.toDate && date && date.after(this.fromDate)) {
      this.toDate = date;
    } else {
      this.toDate = null;
      this.fromDate = date;
    }
  }

  isHovered(date: NgbDate) {
    return this.fromDate && !this.toDate && this.hoveredDate && date.after(this.fromDate) && date.before(this.hoveredDate);
  }

  isInside(date: NgbDate) {
    return this.toDate && date.after(this.fromDate) && date.before(this.toDate);
  }

  isRange(date: NgbDate) {
    return date.equals(this.fromDate) || (this.toDate && date.equals(this.toDate)) || this.isInside(date) || this.isHovered(date);
  }

  validateInput(currentValue: NgbDate | null, input: string): NgbDate | null {
    if (!input.length) {
      return null;
    }
    const parsed = this.formatter.parse(input);
    return parsed && this.calendar.isValid(NgbDate.from(parsed)) ? NgbDate.from(parsed) : currentValue;
  }

  onClick(a) {
    console.log(a);
  }

}
