package com.edatwhite.smkd.message;

public class ResponseMessage {
  private String message;
  private String document_id;

  public ResponseMessage(String message) {
    this.message = message;
  }

  public ResponseMessage(String message, String document_id) {
    this.message = message;
    this.document_id = document_id;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getDocument_id() {
    return document_id;
  }

  public void setDocument_id(String document_id) {
    this.document_id = document_id;
  }
}
