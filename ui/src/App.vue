<template>
    <div id="app">
        <router-view class="main-page"/>
    </div>
</template>
<script>
    export default {
        name: "app",
        data() {
            return {
                openVal: false
            }
        },
        created: function () {
            var that = this;
            this.$helper.get("/front/user/isLogin").then(function (r) {
                console.log(r);
                that.msg = r.message;
            })
        },
        watch: {
            openVal: function (nV, oV) {
                console.log(nV, oV);
            }
        }
    }
</script>

