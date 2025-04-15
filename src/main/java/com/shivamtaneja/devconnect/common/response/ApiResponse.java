package com.shivamtaneja.devconnect.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
  private int code;
  private String message;
  private String error;
  private Boolean success;  
}
