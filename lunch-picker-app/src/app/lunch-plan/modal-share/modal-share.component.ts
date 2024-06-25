import { Component, inject, TemplateRef } from '@angular/core';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { faCopy } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-modal-share',
  templateUrl: './modal-share.component.html',
  styleUrl: './modal-share.component.css'
})
export class ModalShareComponent {
  private modalService = inject(NgbModal);
	url: string = '';
	closeResult = '';
	faCopy = faCopy;
	
	open(content: TemplateRef<any>) {
		this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result.then(
			(result) => {
				this.closeResult = `Closed with: ${result}`;
			},
			(reason) => {
				this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
			},
		);
	}
  
	getCurrentUrl(): string {
	  return window.location.href;
	}
  
	copyLink() {
	  navigator.clipboard.writeText(this.url).then(
		() => {
		  console.log('URL copied to clipboard');
		//   this.showLink = false;
		},
		(err) => console.error('Could not copy URL: ', err)
	  );
	}

	private getDismissReason(reason: any): string {
		switch (reason) {
			case ModalDismissReasons.ESC:
				return 'by pressing ESC';
			case ModalDismissReasons.BACKDROP_CLICK:
				return 'by clicking on a backdrop';
			default:
				return `with: ${reason}`;
		}
	}
}
