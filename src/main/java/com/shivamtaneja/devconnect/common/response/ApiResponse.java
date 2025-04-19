package com.shivamtaneja.devconnect.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
  private int code;
  private T data;
  private String error;
  private Boolean success;  
}
