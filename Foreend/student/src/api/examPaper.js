import { post } from '@/utils/request'

// 考卷部分api
export default {
  select: id => post('/api/student/exam/paper/select/' + id),
  pageList: query => post('/api/student/exam/paper/pageList', query)
}
