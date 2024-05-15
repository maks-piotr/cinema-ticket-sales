import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of, throwError } from 'rxjs';
import { Hall } from '../model/hall';

const authApiPrefix = '/api/auth';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private readonly http: HttpClient) { }

  signIn(email: string, password: string): Observable<any> {
    if (email == '') {
        return throwError(Error("Empty email"))
    }
    return of<any>(true)
  }
  verifyCode(code: string): Observable<any> {
    return of<any>(true)
  }
}
