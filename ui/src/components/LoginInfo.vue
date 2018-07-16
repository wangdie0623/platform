<template>
    <div class="row">
        <div class="grid col-2 col-offset-2">
            <h2>{{name}}</h2>
            <div class="row">
                <VueInput icon-left="search" placeholder="测试"></VueInput>
            </div>
        </div>
    </div>
</template>

<script>

    export default {
        name: "login-info",
        data() {
            return {
                token: "",//任务标识
                name: "book17k",//数据源名称（英文）
                isImg: false,//有图片验证码 false无 ,true 有
                isPhoneCode: false,//有短信验证码
                isQImg: false,//有二维码
                img: "data:image/jpeg;base64,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",//图片验证码值
                qImg: null,//二维码值
                detail: {
                    "name": "book17k",
                    "loginParams": [
                        {
                            "code": "1303",
                            "cn": "账号，图片验证码,密码登录",
                            "en": "account,img-code,password login",
                            "fields": [
                                {
                                    "name": "account",
                                    "type": "text",
                                    "desc": "请输入账号",
                                    "defaultValue": null
                                },
                                {
                                    "name": "code",
                                    "type": "text",
                                    "desc": "请输入图片验证码",
                                    "defaultValue": null
                                },
                                {
                                    "name": "pwd",
                                    "type": "password",
                                    "desc": "请输入密码",
                                    "defaultValue": null
                                }
                            ]
                        }
                    ]
                }
            }
        }
    }
</script>

<style scoped>

</style>