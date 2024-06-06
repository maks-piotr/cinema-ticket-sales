import { TestBed } from '@angular/core/testing';
import { SignInComponent } from './signin.component';
import { RouterTestingModule } from "@angular/router/testing";
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('SignInComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SignInComponent, RouterTestingModule, HttpClientTestingModule],
    }).compileComponents();
  });

  it('should create component', () => {
    const fixture = TestBed.createComponent(SignInComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });
});
