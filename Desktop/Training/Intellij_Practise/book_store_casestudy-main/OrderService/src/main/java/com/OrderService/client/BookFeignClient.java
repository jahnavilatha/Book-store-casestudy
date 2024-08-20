package com.OrderService.client;

import com.OrderService.model.Book;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "Book-Service", url = "http://localhost:8080/books")
public interface BookFeignClient {
    @GetMapping("/orders/{id}")
    Book getBookById(@PathVariable("id") Long id);

}

