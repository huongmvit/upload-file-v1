package com.vn.iam.api.v1.event;

import com.vn.lib.common.resp.ResponseApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {
    @GetMapping("/list")
    public ResponseApi list() {
        return new ResponseApi("1111111111111");
    }
}
