import { Injectable, signal } from '@angular/core';

export type NotificationType = 'success' | 'error' | 'info' | 'warning';

export interface AppNotification {
  id: number;
  type: NotificationType;
  message: string;
}

@Injectable({ providedIn: 'root' })
export class NotificationService {
  readonly notifications = signal<AppNotification[]>([]);
  private nextId = 0;

  success(message: string): void {
    this.show('success', message);
  }

  error(message: string): void {
    this.show('error', message);
  }

  info(message: string): void {
    this.show('info', message);
  }

  warning(message: string): void {
    this.show('warning', message);
  }

  dismiss(id: number): void {
    this.notifications.update((notifications) => notifications.filter((notification) => notification.id !== id));
  }

  private show(type: NotificationType, message: string): void {
    const id = ++this.nextId;
    this.notifications.update((notifications) => [...notifications, { id, type, message }]);
    setTimeout(() => this.dismiss(id), type === 'error' ? 6000 : 4000);
  }
}
