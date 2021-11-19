package com.zhixingkeji.api.sys.controller;

import com.zhixingkeji.api.sys.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapUtil;
import com.google.code.kaptcha.Producer;
import com.zhixingkeji.api.common.Const;
import com.zhixingkeji.api.common.ResultTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;


@RestController
public class AuthController extends BaseController{

    @Autowired
    Producer producer;

    @GetMapping("/captcha")
    public ResultTemplate captcha() throws IOException {

        String key = UUID.randomUUID().toString();
        String code = producer.createText();


        BufferedImage image = producer.createImage(code);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);

        BASE64Encoder encoder = new BASE64Encoder();
        String str = "data:image/jpeg;base64,";

        String base64Img = str + encoder.encode(outputStream.toByteArray());

        redisUtil.hset(Const.CAPTCHA_KEY, key, code, 120);

        return ResultTemplate.success(
                MapUtil.builder()
                        .put("randomCode", key)
                        .put("captchaImg", base64Img)
                        .build()

        );
    }

    /**
     * 获取用户信息接口
     * @param principal
     * @return
     */
    @GetMapping("/sys/userInfo")
    public ResultTemplate userInfo(Principal principal) {

        User User = userService.getByUsername(principal.getName());

        return ResultTemplate.success(MapUtil.builder()
                .put("id", User.getId())
                .put("username", User.getUsername())
                .put("avatar", User.getAvatar())
                .put("created", User.getCreated())
                .map()
        );
    }


}
