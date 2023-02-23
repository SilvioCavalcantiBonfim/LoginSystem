import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ThemeControlComponent } from './theme-control.component';

describe('ThemeControlComponent', () => {
  let component: ThemeControlComponent;
  let fixture: ComponentFixture<ThemeControlComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ThemeControlComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ThemeControlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
