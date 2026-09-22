import { isPlatformBrowser } from '@angular/common';
import { inject, PLATFORM_ID } from '@angular/core';
import { HttpInterceptorFn } from '@angular/common/http';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';
import { NotificationService } from './services/notification.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const platformId = inject(PLATFORM_ID);
  const router = inject(Router);
  const notificationService = inject(NotificationService);
  const token = isPlatformBrowser(platformId) ? localStorage.getItem('token') : null;

  if (token) {
    req = req.clone({
      setHeaders: { Authorization: `Bearer ${token}` },
    });
  }

  return next(req).pipe(
    catchError((error) => {
      if (error.status === 0) {
        notificationService.error('Serveur inaccessible. Vérifiez que le backend fonctionne sur le port 8080.');
      } else if (error.status === 401 && !req.url.includes('/api/auth/login')) {
        notificationService.warning('Session expirée. Veuillez vous reconnecter.');
        if (isPlatformBrowser(platformId)) {
          localStorage.removeItem('token');
        }
        router.navigate(['/login']);
      } else if (error.status === 403) {
        notificationService.error('Accès refusé pour cette opération.');
      }
      return throwError(() => error);
    })
  );
};
