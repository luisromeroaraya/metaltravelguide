import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { IComment } from "../../models/IComment";
import { CommentsService } from "../../services/comments.service";
import { UsersService } from "../../../users/services/users.service";
import { ToastrService } from "ngx-toastr";

@Component({
  selector: 'app-place-comments',
  templateUrl: './place-comments.component.html',
  styleUrls: ['./place-comments.component.css']
})
export class PlaceCommentsComponent implements OnInit {

  @Input() placeId!: number;
  @Input() userId!: number;
  @Input() isConnected!: boolean;
  @Input() isAdmin!: boolean;

  commentForm = new FormGroup({
    text: new FormControl("", [Validators.required, Validators.minLength(1)]),
    placeId: new FormControl(0)
  },  {updateOn: 'submit'});

  private comments: IComment[] = [];

  // constructor
  constructor(private usersService: UsersService, private commentsService: CommentsService, private toastr: ToastrService) { }

  // getters
  get Comments(): IComment[] {
    return this.comments;
  }

  // methods
  ngOnInit(): void {
    this.loadComments();
  }

  private loadComments(): void {
    this.commentForm.reset();
    this.commentForm.patchValue({placeId: this.placeId})
    this.commentForm.markAsUntouched();
    this.commentForm.markAsPristine();
    this.commentsService.readAllByPlace(this.placeId).subscribe((data: IComment[]) => {
      this.comments = data;
    });
  }

  public add(): void {
    if(this.commentForm.valid) {
      this.commentsService.add(this.commentForm.value).subscribe(() => {
        this.loadComments();
        this.toastr.success("Comment adding successfully", "Success");
      }, error => {
        this.toastr.error("Error adding comment", "Error");
      });
    }
  }

  public delete(id: number): void {
    if (confirm("Are you sure you want to delete this Comment?")) {
      this.commentsService.delete(id).subscribe(() => {
        this.loadComments();
        this.toastr.success("Comment deleted successfully", "Success");
      }, response => {
        this.toastr.error("Error deleting comment", "Error");
      });
    }
  }
}
