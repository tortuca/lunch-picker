import { Injectable } from '@angular/core';
import { Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  public connect() {
      let socket = new SockJS(`http://localhost:8080/socket`);

      let stompClient = Stomp.over(socket);

      return stompClient;
  }
}