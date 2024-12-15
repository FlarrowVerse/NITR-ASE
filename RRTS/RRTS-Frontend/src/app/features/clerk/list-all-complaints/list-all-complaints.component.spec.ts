import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListAllComplaintsComponent } from './list-all-complaints.component';

describe('ListAllComplaintsComponent', () => {
  let component: ListAllComplaintsComponent;
  let fixture: ComponentFixture<ListAllComplaintsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListAllComplaintsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListAllComplaintsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
