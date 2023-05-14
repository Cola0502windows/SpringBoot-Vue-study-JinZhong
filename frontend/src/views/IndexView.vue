<template>
    <div>
        欢迎来到 Index 页面
    </div>

    <div>
        <el-button @click="logout()" type="danger" plain>退出登陆</el-button>
    </div>
</template>

<script setup>

import {get} from "@/utils";
import {ElMessage} from "element-plus";
import router from "@/router";

const logout = () =>{
    get('/v1/api/auth/logout',(message)=>{
        ElMessage.success(message)
        router.push('/')
    })
}
</script>

<style scoped>

</style>