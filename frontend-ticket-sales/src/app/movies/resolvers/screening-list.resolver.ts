import {inject} from '@angular/core';
import {ActivatedRouteSnapshot, ResolveFn, RouterStateSnapshot} from '@angular/router';
import {ScreeningsService} from '../services/screenings.service';
import {Screening} from '../model/screening';

export const screeningListResolver: ResolveFn<Screening[]> = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot,
) => {
  return inject(ScreeningsService).getAllScreenings();
};
