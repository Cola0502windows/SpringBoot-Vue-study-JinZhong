import axios from "axios";
import {ElMessage} from "element-plus";

const defaultFailure = (message) => ElMessage.error(message)
const defaultError = () => ElMessage.error("发生了一些错误，请联系管理员 1203952894@qq.com")


function post(url,data,success,failure = defaultFailure,error = defaultError){
    axios.post(url,data,{
        headers:  {
            'Content-Type': "application/x-www-form-urlencoded"
        },
        /*请求携带 Cookie */
        withCredentials: true
    }).then(({data}) =>{
        if (data.success)
            success(data.data, data.code)
        else
            failure(data.data,data.code)
    }).catch(error)
}

function get(url,success,failure = defaultFailure,error = defaultError){
    axios.get(url,{
        /*请求携带 Cookie */
        withCredentials: true
    }).then(({data}) =>{
        if (data.success)
            success(data.data, data.code)
        else
            failure(data.data,data.code)
    }).catch(error)
}

export {get,post}