import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { Location } from '@angular/common';
import { CheckoutComponent } from './checkout.component';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

describe('CheckoutComponent', () => {
  let component: CheckoutComponent;
  let fixture: ComponentFixture<CheckoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, CheckoutComponent],
      providers: [
        {
          provide: Location,
          useValue: {
            getState: jasmine.createSpy('getState').and.returnValue({ selectedSeats: ['1-2', '3-4'] })
          }
        },
        {
          provide: ActivatedRoute,
          useValue: {
            params: of({})
          }
        }
      ]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CheckoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  it('should initialize selectedSeats correctly', () => {
    expect(component.selectedSeats).toEqual(['Row 2, Seat 3', 'Row 4, Seat 5']);
  });
});
