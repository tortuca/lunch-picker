import { Component, EventEmitter, Output } from '@angular/core';
import { FormControl } from '@angular/forms';
import { faUser } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrl: './user.component.css'
})
export class UserComponent {
  @Output() change = new EventEmitter<any>();

  username: string = localStorage.getItem('username') || '';
  editable: boolean;

  faUser = faUser;

  editUsername() {
    this.editable = true;
  }

  save(event: Event) {
    this.username = (event.target as HTMLInputElement).value;
    localStorage.setItem('username', this.username);
    this.editable = false;
    this.change.emit();
  }
}
