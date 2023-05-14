<template>
    <div style="text-align: center;margin: 0 20px">
        <div style="margin-top: 150px">
            <div style="font-size: 25px;font-weight: bold">注册新用户</div>
            <div style="font-size: 15px; color: grey">欢迎注册该平台，请参照提示完成注册</div>
        </div>

        <el-form style="margin-top: 50px" :model="form" :rules="rules" @validate="onValidate" ref="formRef">
            <el-form-item prop="username">
                <el-input v-model="form.username" :maxlength = "12" type="text" placeholder="用户名 || 邮箱">
                    <template #prefix>
                        <el-icon><User /></el-icon>
                    </template>
                </el-input>
            </el-form-item>

            <el-form-item prop="password">
                <el-input v-model="form.password" :maxlength = "16" type="password" placeholder="密码">
                    <template #prefix>
                        <el-icon><Lock /></el-icon>
                    </template>
                </el-input>
            </el-form-item>

            <el-form-item prop="password_repeat">
                <el-input v-model="form.password_repeat" type="password" placeholder="重复密码">
                    <template #prefix>
                        <el-icon><Lock /></el-icon>
                    </template>
                </el-input>
            </el-form-item>

            <el-form-item prop="email">
                <el-input v-model="form.email" type="email" placeholder="请输入邮箱地址">
                    <template #prefix>
                        <el-icon><Message /></el-icon>
                    </template>
                </el-input>
            </el-form-item>

            <el-form-item prop="code">
                <el-row :gutter="10" style="width: 100%">
                    <el-col :span="16">
                        <el-input  v-model="form.code" :maxlength = "6" type="text" placeholder="请输入验证码">
                            <template #prefix>
                                <el-icon><EditPen /></el-icon>
                            </template>
                        </el-input>
                    </el-col>

                    <el-col :span="6">
                        <el-button type="success" @click="sendValidateEmail()"
                                   :disabled="!isEmailValidate || coldEmailTime > 0">
                            {{coldEmailTime > 0? '请稍候：'+coldEmailTime+'秒':'获取验证码'}}</el-button>
                    </el-col>
                </el-row>
            </el-form-item>
        </el-form>

        <div style="margin-top: 10px">
            <div style="margin-top: 80px">
                <el-button @click="register()" style="width: 270px" type="warning" plain>立即注册</el-button>
            </div>
            <div style="font-size: 14px;line-height: 15px;margin-top: 15px">
                已有账号？<el-link @click="router.push('/')" type="primary">立即登陆</el-link>
            </div>
        </div>
    </div>
</template>

<script setup>

import {EditPen, Lock, Message, User} from "@element-plus/icons-vue";
import {reactive, ref} from "vue";
import router from "@/router";
import {ElMessage} from "element-plus";
import {post} from "@/utils";
const form = reactive({
        username: '',
        password: '',
        password_repeat: '',
        email: '',
        hasAccount: false,
        code: '',
    }
)
const formRef = ref()
const isEmailValidate = ref(false)
const coldEmailTime = ref(0)

const validateUsername = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('请输入用户名'))
    } else if (!/^((?!\\|\/|:|\*|\?|<|>|\||'|%|@|#|&|\$|\^|&|\*).){1,8}$/.test(value)
    ){
        callback(new Error("用户名不能包含特殊字符，仅支持 数字+英文"))
    }else {
        callback()
    }
}

const validatePasswordRepeat = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('请输入密码'))
    } else if (value !== form.password
    ){
        callback(new Error("两次输入密码不一致"))
    }else {
        callback()
    }
}

const validateEmail = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('请输入邮箱'))
    } else if (!/^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/.test(value)
    ){
        callback(new Error("邮箱格式不正确"))
    }else {
        callback()
    }
}

const rules = {
    username: [
        { validator: validateUsername, trigger: ['blur','change'] },
        { min: 2, max: 12, message: '用户名长度 [2,12]', trigger: ['blur','change']},
    ],
    password: [
        { required: true, message: '请输入密码', trigger: 'blur' },
        { min: 6, max: 16, message: '密码长度 [6,16]', trigger: ['blur','change']},
    ],
    password_repeat: [
        { validator: validatePasswordRepeat, trigger: ['blur','change'] },
    ],
    email: [
        { validator: validateEmail, trigger: ['blur','change'] },
    ],
    code: [
        { required: true, message: '请输入获取的验证码', trigger: ['blur','change'] },
    ]
}

const onValidate = (prop,isValidate) => {
    if(prop === 'email') isEmailValidate.value = isValidate
}

const register = () => {
    formRef.value.validate((isValidate)=> {
        if (isValidate){
            post('/api/v1/auth/register',{
                username: form.username,
                password: form.password,
                email: form.email,
                code: form.code
            },(data,message)=>{
                ElMessage.success(message)
                router.push("/")
            })
        }else {
            ElMessage.warning("请先完成信息的填写")
        }
    })
}

const sendValidateEmail = () => {
    post('/api/v1/auth/sendValidateEmail',{
            email: form.email,
            hasAccount: form.hasAccount
        },(data,message)=>{
            ElMessage.success(message)
            coldEmailTime.value = 60
            setInterval(()=> coldEmailTime.value --,1000)
        },(message) => {
            ElMessage.warning(message)
        }
    )
}
</script>

<style scoped>

</style>