package com.wang.platform.plugins.fegin;

import com.wang.platform.beans.ResultInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("redis")
public interface RedisFeign {

    @PostMapping("list/right/minutes/{timeOut}")
    ResultInfo pushListRM(String key, String value, @PathVariable Long timeOut);

    @PostMapping("list/left/minutes/{timeOut}")
    ResultInfo pushListLM(String key, String value, @PathVariable Long timeOut);

    @PutMapping("obj/minutes/{timeOut}")
    ResultInfo pushObjM(String key, String value, @PathVariable Long timeOut);

    @GetMapping("list/top/{key}")
    ResultInfo listTop(@PathVariable String key);

    @GetMapping("list/tail/{key}")
    ResultInfo listTail(@PathVariable String key);

    @GetMapping("full")
    ResultInfo fullVal(String key);

    @DeleteMapping("keys")
    ResultInfo del(String keyVal);

    @DeleteMapping("keys/obj")
    ResultInfo delObj(String keyVal);

    @DeleteMapping("keys/list")
    ResultInfo delList(String keyVal);

    @GetMapping("keys")
    ResultInfo keys(String queryStr);
}
