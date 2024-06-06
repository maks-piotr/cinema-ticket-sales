import { TestBed } from '@angular/core/testing';
import { TicketComponent } from './ticket.component';
import { RouterTestingModule } from "@angular/router/testing";
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('TicketComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TicketComponent, RouterTestingModule, HttpClientTestingModule],
    }).compileComponents();
  });

  it('should create component', () => {
    const fixture = TestBed.createComponent(TicketComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });
});
