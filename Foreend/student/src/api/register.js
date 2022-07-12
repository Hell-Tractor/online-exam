import { postWithLoadTip } from '@/utils/request'
// 用户注册
export default {
  register: query => postWithLoadTip(`/api/student/user/register`, query)
}
