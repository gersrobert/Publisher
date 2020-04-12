import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TopPublishersComponent } from './top-publishers.component';

describe('TopPublishersComponent', () => {
  let component: TopPublishersComponent;
  let fixture: ComponentFixture<TopPublishersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TopPublishersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TopPublishersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
