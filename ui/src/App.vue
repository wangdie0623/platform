<template>
    <div v-html="msg">
    </div>
</template>
<script>
    export default {
        name: "app",
        data() {
            return {
                msg: "hello"
            }
        },
        created: function () {
            var that = this;
            this.$helper.get("/front/user/isLogin").then(function (r) {
                console.log(r);
                that.msg = r.message                              ;
            })
        }
    }
</script>

