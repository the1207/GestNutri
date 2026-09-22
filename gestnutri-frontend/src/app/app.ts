import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NotificationCenter } from './notification-center';

@Component({
  imports: [RouterOutlet, NotificationCenter],
  selector: 'app-root',
  styleUrl: './app.css',
  templateUrl: './app.html',
})
export class App {
  protected readonly title = signal('gestnutri-frontend');
}
