package com.wang.platform.plugins.website;

import com.wang.platform.exceptions.ServiceException;

public class TokenInvalidException extends ServiceException {
   public TokenInvalidException(String msg){
       super(msg);
   }
}
