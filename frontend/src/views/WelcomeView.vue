<template>
  <!-- 外层 div   -->
    <div style="width: 100vw; height: 100vh;overflow: hidden; display: flex">
        <!--  封面 div      -->
        <div style="flex: 1">
            <el-image style="width: 100%; height: 100%;" fit="cover"
                      src="../public/welcome_background.png">
            </el-image>
        </div>

        <!--描述-->
        <div class="welcome-title">
            <div style="font-size: 40px;font-weight: bold;color: aqua">欢迎来到该项目</div>
            <div style="margin-top: 10px;font-size: 25px; font-weight: revert">鼬先生：你在临死之际是否看清了自己。</div>
            <div style="margin-top: 10px">在这里你可以学习 SpringBoot 和 ElementPlus 的入门操作</div>
        </div>

        <!--  登陆 div      -->
        <div style="width: 400px; background-color: white;z-index: 1">
            <router-view v-slot="{ Component }">
                <transition name="el-fade-in-linear">
                    <component :is="Component" style="height: 100%"/>
                </transition>
            </router-view>
        </div>
    </div>
</template>

<script setup>

</script>

<style scoped>
.welcome-title {
    position: absolute;
    bottom: 30px;
    left: 30px;
    color: white;
    text-shadow: 0 0 10px black;
}
</style>