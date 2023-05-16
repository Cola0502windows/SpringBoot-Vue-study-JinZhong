<template>
    <div style="text-align: center;margin: 0 20px">
        <div style="margin: 30px 20px">
            <el-steps :active="active" finish-status="success" align-center>
                <el-step title="验证电子邮件" />
                <el-step title="重新设定密码" />
            </el-steps>
        </div>
        <transition name="el-fade-in-linear">
            <div style="margin-top: 150px;height: 100%" v-if="active === 0">
                <div style="font-size: 50px;font-weight: bold">重置密码</div>
                <div style="font-size: 20px;font-weight: bold">请输入需要重置密码的电子邮件：</div>

                <el-form style="margin-top: 50px" :model="form" :rules="rules" @validate="onValidate" ref="formRef">

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
                <div style="margin-top: 70px">
                    <el-button @click="startReset()" style="width: 270px" type="danger">开始重置密码</el-button>
                </div>
            </div>
        </transition>

        <transition name="el-fade-in-linear">
            <div style="margin-top: 150px;height: 100%" v-if="active === 1">
                <div style="font-size: 50px;font-weight: bold">修改密码</div>
                <div style="font-size: 20px;font-weight: bold">请输入新的密码：</div>

                <el-form style="margin-top: 50px">
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
                </el-form>

                <div style="margin-top: 70px">
                    <el-button @click="resetPwd()" style="width: 270px" type="warning">立即重置密码</el-button>
                </div>
            </div>
        </transition>


    </div>

</template>

<script setup>
import {reactive, ref} from "vue";
import {EditPen, Lock, Message} from "@element-plus/icons-vue";
import {post} from "@/utils";
import {ElMessage} from "element-plus";
import router from "@/router";

const formRef = ref()
const isEmailValidate = ref(false)
const coldEmailTime = ref(0)
const active = ref(0)

const form = reactive({
        password: '',
        password_repeat: '',
        email: '',
        hasAccount: true,
        code: '',
    }
)

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

const rules = {
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

const  startReset = () => {
    post('/api/v1/auth/start-reset',{
            email: form.email,
            code: form.code,
            hasAccount: form.hasAccount
        },(data,_)=>{
            active.value = 1
        },(message) => {
            ElMessage.warning(message)
        }
    )
}

const  resetPwd = () => {
    post('/api/v1/auth/resetPwd',{
            email: form.email,
            password: form.password,
        },(data,message)=>{
        ElMessage.success(message)
        router.push('/')
        active.value = 2
        },(message) => {
            ElMessage.warning(message)
        }
    )
}
</script>

<style scoped>

</style>