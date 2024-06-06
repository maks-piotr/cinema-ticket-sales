import { TestBed } from '@angular/core/testing';
import { VerifyComponent } from './verify.component';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TicketService } from '../../services/ticket.service';

describe('VerifyComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientTestingModule, VerifyComponent],
      providers: [TicketService]
    }).compileComponents();
  });

  it('should create component', () => {
    const fixture = TestBed.createComponent(VerifyComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });
});
