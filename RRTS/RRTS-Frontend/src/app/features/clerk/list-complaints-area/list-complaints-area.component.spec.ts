import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListComplaintsAreaComponent } from './list-complaints-area.component';

describe('ListComplaintsAreaComponent', () => {
  let component: ListComplaintsAreaComponent;
  let fixture: ComponentFixture<ListComplaintsAreaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListComplaintsAreaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListComplaintsAreaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
