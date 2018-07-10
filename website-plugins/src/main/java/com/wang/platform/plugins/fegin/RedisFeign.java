package com.wang.platform.plugins.fegin;

import com.wang.platform.beans.ResultInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("redis")
public interface RedisFeign {

    @PostMapping("list/right/minutes/{timeOut}")
    ResultInfo pushListRM(@RequestParam(name = "key") String key,
                          @RequestParam(name = "value") String value,
                          @PathVariable(name = "timeOut") Long timeOut);

    @PostMapping("list/left/minutes/{timeOut}")
    ResultInfo pushListLM(@RequestParam(name = "key") String key,
                          @RequestParam(name = "value") String value,
                          @PathVariable(name = "timeOut") Long timeOut);

    @PutMapping("obj/minutes/{timeOut}")
    ResultInfo pushObjM(@RequestParam(name = "key") String key,
                        @RequestParam(name = "value") String value,
                        @PathVariable(name = "timeOut") Long timeOut);

    @GetMapping("list/top/{key}")
    ResultInfo listTop(@PathVariable(name = "key") String key);

    @GetMapping("list/tail/{key}")
    ResultInfo listTail(@PathVariable(name = "key") String key);

    @GetMapping("full")
    ResultInfo fullVal( @RequestParam(name = "key")String key);

    @DeleteMapping("keys")
    ResultInfo del(@RequestParam(name = "keyVal")String keyVal);

    @DeleteMapping("keys/obj")
    ResultInfo delObj(@RequestParam(name = "keyVal")String keyVal);

    @DeleteMapping("keys/list")
    ResultInfo delList(@RequestParam(name = "keyVal")String keyVal);

    @GetMapping("keys")
    ResultInfo keys(@RequestParam(name = "queryStr")String queryStr);
}
