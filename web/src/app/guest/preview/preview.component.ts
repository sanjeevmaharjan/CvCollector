import {Component, OnInit} from '@angular/core';
import {isNullOrUndefined} from '@swimlane/ngx-datatable/release/utils';
import {CvModel} from '../../models/cv/cv.model';
import {NgbDateParserFormatter} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-preview',
  templateUrl: '../../shared/cv-view/cv-view.component.html',
  styleUrls: ['../../shared/cv-view/cv-view.component.css']
})
export class PreviewComponent implements OnInit {
  cv: CvModel;

  tab = 1;

  constructor(private dateParser: NgbDateParserFormatter) {
  }

  ngOnInit() {
    const cvFromLocal = localStorage.getItem('cv-data');
    if (!isNullOrUndefined(cvFromLocal)) {
      const obj = JSON.parse(cvFromLocal);
      this.cv = obj;
      this.cv.personal.dateOfBirth = this.dateParser.format(obj.personal.dateOfBirth);
      this.cv.education.institutions.forEach((value, index) => {
        value.suruDate = this.dateParser.format(obj.education.institutions[index].suruDate);
        value.antimDate = this.dateParser.format(obj.education.institutions[index].antimDate);
      });
    }
  }

  getGender(id: number): string {
    switch (id) {
      case 0:
        return 'Unspecified';
      case 1:
        return 'Male';
      case 2:
        return 'Female';
      default:
        return 'Unspecified';
    }
  }

  selectTab(id: number): void {
    this.tab = id;
  }

}
