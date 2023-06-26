import axios from "axios";
import {ElMessage} from "element-plus";

const defaultError = () => ElMessage.error("发生错误!");
const defaultFailure = (message) => ElMessage.warning(message);

function post(url, data, success, failure=defaultFailure, error=defaultError) {
    axios.post(url, data, {
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        withCredentials: true
    }).then(({data}) => {
        if (data.success)
            success(data.message, data.success);
        else
            failure(data.message, data.success);
    }).catch(error)

}

function get(url, success, failure=defaultFailure, error=defaultError) {
    axios.get(url,{
        withCredentials: true
    }).then(({data}) => {
        if (data.success)
            success(data.message, data.success);
        else
            failure(data.message, data.success);
    }).catch(error)
}


export {post,get}