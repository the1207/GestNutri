import { Component, inject } from '@angular/core';
import { NotificationService } from './services/notification.service';

@Component({
  selector: 'app-notification-center',
  standalone: true,
  template: `
    <aside class="notification-center" aria-live="polite" aria-atomic="true">
      @for (notification of notificationService.notifications(); track notification.id) {
        <div class="notification" [class]="'notification notification--' + notification.type" role="alert">
          <span>{{ notification.message }}</span>
          <button type="button" aria-label="Fermer" (click)="notificationService.dismiss(notification.id)">×</button>
        </div>
      }
    </aside>
  `,
  styles: `
    .notification-center {
      position: fixed;
      z-index: 1000;
      top: 1rem;
      right: 1rem;
      display: grid;
      gap: 0.75rem;
      width: min( min(26rem, calc(100vw - 2rem)), 26rem );
    }

    .notification {
      display: flex;
      align-items: flex-start;
      justify-content: space-between;
      gap: 1rem;
      padding: 0.9rem 1rem;
      border: 1px solid;
      border-radius: 0.5rem;
      box-shadow: 0 8px 24px rgb(15 23 42 / 18%);
      color: #172033;
      background: #fff;
      font-size: 0.95rem;
      animation: notification-in 180ms ease-out;
    }

    .notification--success { border-color: #65a30d; background: #f7fee7; }
    .notification--error { border-color: #dc2626; background: #fef2f2; }
    .notification--info { border-color: #0284c7; background: #f0f9ff; }
    .notification--warning { border-color: #d97706; background: #fffbeb; }

    button {
      flex: 0 0 auto;
      border: 0;
      padding: 0;
      background: transparent;
      color: inherit;
      cursor: pointer;
      font-size: 1.25rem;
      line-height: 1;
    }

    @keyframes notification-in {
      from { opacity: 0; transform: translateY(-0.5rem); }
      to { opacity: 1; transform: translateY(0); }
    }
  `,
})
export class NotificationCenter {
  protected readonly notificationService = inject(NotificationService);
}
