import { post } from '@/utils/request'

export default {
  index: () => post('/api/student/dashboard/index'),
  task: () => post('/api/student/dashboard/task'),
  // 生成试卷
  create: query => post('/api/student/dashboard/createPaper', query)
}
