import { post } from '@/utils/request'

export default {
  getUserPageList: query => post('/api/admin/user/page/list', query), // 获得用户list
  createUser: query => post('/api/admin/user/edit', query), // 创建用户
  getCurrentUser: () => post('/api/admin/user/current'), // 获得现有用户
  updateUser: query => post('/api/admin/user/update', query), // 更新用户
  selectUser: query => post('/api/admin/user/select/' ,query), // 选择用户
  deleteUser: query => post('/api/admin/user/delete/', query), // 删除用户

  //selectUser: id => post('/api/admin/user/select/' + id), // 选择用户
  //deleteUser: id => post('/api/admin/user/delete/' + id), // 删除用户
  //selectByusername: query => post('/api/admin/user/selectByusername', query)
  // getUserEventPageList: query => post('/api/admin/user/event/page/list', query),
}
