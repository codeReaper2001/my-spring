package com.demo.springframework.test.forCircleDepend;

import lombok.Data;

@Data
public class Husband {

   private Wife wife;

   public String queryWife() {
       return "Husband.wife";
   }

}
